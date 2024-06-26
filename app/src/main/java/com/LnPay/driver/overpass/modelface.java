package com.LnPay.driver.overpass;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface modelface {

    String subUrl = "search";
//    String subUrl ="api/interpreter?data=%2F*%0AThis%20has%20been%20generated%20by%20the%20overpass-turbo%20wizard.%0AThe%20original%20search%20was%3A%0A“highway%20%3D%20bus_stop”%0A*%2F%0A%5Bout%3Ajson%5D%5Btimeout%3A25%5D%3B%0A%2F%2F%20gather%20results%0A%28%0A%20%20%2F%2F%20query%20part%20for%3A%20“highway%3Dbus_stop”%0A%20%20node%5B\"highway\"%3D\"bus_stop\"%5D%285.592836888797203%2C-0.22942543029785153%2C5.6071449153480515%2C-0.21131515502929685%29%3B%0A%20%20way%5B\"highway\"%3D\"bus_stop\"%5D%285.592836888797203%2C-0.22942543029785153%2C5.6071449153480515%2C-0.21131515502929685%29%3B%0A%20%20relation%5B\"highway\"%3D\"bus_stop\"%5D%285.592836888797203%2C-0.22942543029785153%2C5.6071449153480515%2C-0.21131515502929685%29%3B%0A%29%3B%0A%2F%2F%20print%20results%0Aout%20body%3B%0A>%3B%0Aout%20skel%20qt%3B";
//  "api/interpreter?data=%2F*%0AThis%20has%20been%20generated%20by%20the%20overpass-turbo%20wizard.%0AThe%20original%20search%20was%3A%0A“highway%20%3D%20bus_stop”%0A*%2F%0A%5Bout%3Ajson%5D%5Btimeout%3A25%5D%3B%0A%2F%2F%20gather%20results%0A%28%0A%20%20%2F%2F%20query%20part%20for%3A%20“highway%3Dbus_stop”%0A%20%20node%5B\"highway\"%3D\"bus_stop\"%5D%285.592836888797203%2C-0.22942543029785153%2C5.6071449153480515%2C-0.21131515502929685%29%3B%0A%20%20way%5B\"highway\"%3D\"bus_stop\"%5D%285.592836888797203%2C-0.22942543029785153%2C5.6071449153480515%2C-0.21131515502929685%29%3B%0A%20%20relation%5B\"highway\"%3D\"bus_stop\"%5D%285.592836888797203%2C-0.22942543029785153%2C5.6071449153480515%2C-0.21131515502929685%29%3B%0A%29%3B%0A%2F%2F%20print%20results%0Aout%20body%3B%0A>%3B%0Aout%20skel%20qt%3B";

    @GET(subUrl)
    Call<ArrayList<model>> getData(@Query("q") String q,@Query("countrycodes") String country , @Query("limit") int limit ,@Query("format") String format);
}

//https://overpass-api.de/api/interpreter?data=%2F*%0AThis%20has%20been%20generated%20by%20the%20overpass-turbo%20wizard.%0AThe%20original%20search%20was%3A%0A%E2%80%9Chighway%20%3D%20bus_stop%E2%80%9D%0A*%2F%0A%5Bout%3Ajson%5D%5Btimeout%3A25%5D%3B%0A%28%0A%20%20node%5B%22highway%22%3D%22bus_stop%22%5D%285.4546931824367935%2C-0.85693359375%2C6.292093743755242%2C0.30212402343749994%29%3B%0A%20%20way%5B%22highway%22%3D%22bus_stop%22%5D%285.4546931824367935%2C-0.85693359375%2C6.292093743755242%2C0.30212402343749994%29%3B%0A%20%20relation%5B%22highway%22%3D%22bus_stop%22%5D%285.4546931824367935%2C-0.85693359375%2C6.292093743755242%2C0.30212402343749994%29%3B%0A%29%3B%0Aout%20body%3B%0A%3E%3B%0Aout%20skel%20qt%3B