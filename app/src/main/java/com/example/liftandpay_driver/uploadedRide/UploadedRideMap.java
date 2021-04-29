package com.example.liftandpay_driver.uploadedRide;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.liftandpay_driver.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

public class UploadedRideMap extends FragmentActivity implements OnMapReadyCallback, PermissionsListener {


    private MapView mapView;
    private MapboxMap mapboxMap;

    private final String geojsonSourceLayerId = "geojsonSourceLayerId";
    private final String symbolIconId = "symbolIconId";
    private final String mapBoxStyleUrl ="mapbox://styles/hubert-brako/cknk4g1t6031l17to153efhbs";


    private LatLng myLoc;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationComponent locationComponent;
    private PermissionsManager permissionsManager;
    private SharedPreferences sharedPreferences;


    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(UploadedRideMap.this, getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_uploaded_ride_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.



        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(UploadedRideMap.this);


    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {

        sharedPreferences =  getApplicationContext().getSharedPreferences("RIDEFILE",MODE_PRIVATE);
        sharedPreferences.edit().putString("TheOrderId","hELLO").apply();

        this.mapboxMap = mapboxMap;
        locationComponent = mapboxMap.getLocationComponent();




        mapboxMap.setStyle(new Style.Builder().fromUri(mapBoxStyleUrl), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                // Add the symbol layer icon to map for future use
                style.addImage(symbolIconId, BitmapFactory.decodeResource(
                        UploadedRideMap .this.getResources(), R.drawable.mapbox_logo_icon));
                // Create an empty GeoJSON source using the empty feature collection
                setUpSource(style);
                // Set up a new symbol layer for displaying the searched location's feature coordinates
                setupLayer(style);
                enableLocationComponent(style);
                addDestinationIconSymbolLayer(style);


            }
        });



        fusedLocationProviderClient = getFusedLocationProviderClient(UploadedRideMap.this);

        if (ActivityCompat.checkSelfPermission(UploadedRideMap.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(UploadedRideMap.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        myLoc = new LatLng(location.getLatitude(), location.getLongitude());
                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                                new CameraPosition.Builder()
                                        .target(new LatLng(location.getLatitude(),
                                                location.getLongitude()))
                                        .zoom(17)
                                        .build()), 4000);

                        sharedPreferences.edit().putString("TheDriverLatitude", (myLoc.getLatitude()+"")).apply();
                        sharedPreferences.edit().putString("TheDriverLongitude", (myLoc.getLongitude()+"")).apply();
                        Toast.makeText(getApplicationContext(), myLoc.getLatitude()+"",Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), myLoc.getLongitude()+"",Toast.LENGTH_LONG).show();

                        String theCurrentLat = sharedPreferences.getString("TheDriverLatitude","Null");
                        String theCurrentLong = sharedPreferences.getString("TheDriverLongitude","Null");

                        if(!theCurrentLat.equals("Null") && !theCurrentLong.equals("Null")) {

                            double myLat = Double.parseDouble(theCurrentLat);
                            double myLong = Double.parseDouble(theCurrentLong);
                            LatLng points = new LatLng( myLat, myLong);
                            LatLng pointd = new LatLng(5.58860529, -0.184086699);

                            Point destinationPoint = Point.fromLngLat(pointd.getLongitude(), pointd.getLatitude());
                            Point originPoint = Point.fromLngLat(points.getLongitude(), points.getLatitude());
                            getRoute(originPoint,destinationPoint);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "The Cordinates are null, Route could not render",Toast.LENGTH_LONG).show();
                        }



                    }
                });



    }




    @SuppressLint("WrongConstant")
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
// Activate the MapboxMap LocationComponent to show user location
// Adding in LocationComponentOptions is also an optional parameter

            locationComponent.activateLocationComponent(this, loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setRenderMode(RenderMode.GPS);


// Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING_GPS);
            locationComponent.tiltWhileTracking(45);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "R.string.user_location_permission_explanation" , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationComponent(mapboxMap.getStyle());
        } else {
            Toast.makeText(this, "R.string.user_location_permission_not_granted", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void setUpSource(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(geojsonSourceLayerId));
    }

    private void setupLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addLayer(new SymbolLayer("SYMBOL_LAYER_ID", geojsonSourceLayerId).withProperties(
                iconImage(symbolIconId),
                iconOffset(new Float[]{0f, -8f})
        ));
    }


    private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("destination-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);
    }


    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                              @Override
                              public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
// You can get the generic HTTP info about the response
                                  Log.d("TAG", "Response code: " + response.code());
                                  if (response.body() == null) {
                                      Timber.e("No routes found, make sure you set the right user and access token.");
                                      return;
                                  } else if (response.body().routes().size() < 1) {
                                      Timber.e("No routes found");
                                      return;
                                  }

                                  currentRoute = response.body().routes().get(0);

// Draw the route on the map
                                  if (navigationMapRoute != null) {
                                      navigationMapRoute.removeRoute();
                                  } else {
                                      navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                                  }
                                  navigationMapRoute.addRoute(currentRoute);
                              }

                              @Override
                              public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                                  Timber.e(t.toString());

                              }
                          });
    }






    // Add the mapView lifecycle to the activity's lifecycle methods
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
        sharedPreferences.edit().clear().apply();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        sharedPreferences.edit().clear().apply();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}