package com.example.liftandpay_driver;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Ratings extends AlertDialog.Builder {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RatingBar ratingBar;
    private EditText commentText;
    private String commentString ="";
    private String passengerStatus = "";
    private String rideStatus = " ";
    private TextView option;
    private AlertDialog alertDialog;
    private HashMap<Object,Object> rateReviews = new HashMap<>();

    public Ratings(@NonNull Context context, String driverId, String passengerStatus, String rideStatus) {
        super(context);

        AlertDialog.Builder ratingDialog = new AlertDialog.Builder(context);

        View v = LayoutInflater.from(context).inflate(R.layout.alert_rate_passenger,null);

        ratingBar = v.findViewById(R.id.ratingsId);
        commentText = v.findViewById(R.id.ratingsCommentId);

      option = v.findViewWithTag("option");


      option.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              commentString = commentString +"\n" + option.getText().toString();
              Toast.makeText(context,commentString, Toast.LENGTH_LONG).show();
          }
      });

        v.findViewById(R.id.submitId).setOnClickListener(V->{

            commentString = commentString +"\n" + commentText.getText().toString();
            rateReviews.put("Comment",commentString);
            rateReviews.put("Rate",ratingBar.getRating());
            rateReviews.put("passengerStatus", passengerStatus);
            rateReviews.put("rideStatus", rideStatus);


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            String currentDateTime = sdf.format(new Date());
            Toast.makeText(context,currentDateTime, Toast.LENGTH_LONG).show();

            db.collection("Review").document("Drivers").collection(driverId).document(currentDateTime).set(rateReviews).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context,"Submitted",Toast.LENGTH_LONG).show();

                }
            });

          alertDialog.dismiss();
        });


        ratingDialog.setView(v);
       alertDialog = ratingDialog.create();
       alertDialog.show();
    }

    public Ratings(@NonNull Context context, int themeResId) {
        super(context, themeResId);

    }



}