package com.example.liftandpay_driver.uploadedRide;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liftandpay_driver.R;
import com.example.liftandpay_driver.uploadedRide.RequestedPassenger.RequestedPassengersSheet;

import java.util.ArrayList;

public class UploadedRidesAdapter extends RecyclerView.Adapter<UploadedRidesAdapter.uploadedViewHolder> {

    Context context;
    ArrayList<uploadedRidesModel> uploadedRidesModels;

    public UploadedRidesAdapter(Context context, ArrayList<uploadedRidesModel> uploadedRidesModels) {
        this.context = context;
        this.uploadedRidesModels = uploadedRidesModels;
    }

    @NonNull
    @Override
    public UploadedRidesAdapter.uploadedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_uploaded_rides,parent, false);

        return new uploadedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadedRidesAdapter.uploadedViewHolder holder, int position) {

        uploadedRidesModel uploadedRidesModel = uploadedRidesModels.get(position);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.toMapPrograssBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(context, UploadedRideMap.class);
                context.startActivity(intent);
            }
        });

        holder.requestedPassengersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestedPassengersSheet requestedPassengersSheet = new RequestedPassengersSheet();
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                requestedPassengersSheet.setNumberOfRequests(Integer.parseInt(holder.rNumberOfPassengers.getText().toString()));
                requestedPassengersSheet.show(manager,null);
            }
        });


        holder.rTimeView.setText(uploadedRidesModel.getRIDETIME());
        holder.rDateView.setText(uploadedRidesModel.getRIDEDATE());
        holder.rJourney.setText(uploadedRidesModel.getJOURNEY());
        String rNOP = String.valueOf(uploadedRidesModel.getNUMBEROFREQUESTS());
        holder.rNumberOfPassengers.setText(rNOP);
    }

    @Override
    public int getItemCount() {
        return uploadedRidesModels.size();
    }


    public class uploadedViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout linearLayout;
        private ProgressBar toMapPrograssBar;
        private ImageView addRideBtn;
        private RelativeLayout requestedPassengersBtn;

        private TextView rNumberOfPassengers;
        private TextView rJourney;
        private TextView rDateView;
        private TextView rTimeView;


        public uploadedViewHolder(@NonNull View itemView) {
            super(itemView);

            toMapPrograssBar = itemView.findViewById(R.id.proceedToMapProgressbarrId);
            linearLayout = itemView.findViewById(R.id.uploadedLinearLayoutId);
            requestedPassengersBtn = itemView.findViewById(R.id.requestedPassengerRelativeLayoutId);


            rJourney = itemView.findViewById(R.id.journeyId);
            rDateView = itemView.findViewById(R.id.dateModelId);
            rTimeView = itemView.findViewById(R.id.timeModelId);
            rNumberOfPassengers = itemView.findViewById(R.id.numberOfRequestedPassengersId);


        }
    }
}
