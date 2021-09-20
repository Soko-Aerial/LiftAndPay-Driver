package com.example.liftandpay_driver.accounts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liftandpay_driver.R;

import java.util.ArrayList;
import java.util.List;

public class Withdrawal extends Fragment {

    List<Amount> list_amount;
    RecyclerView recyclerview;
     AmountAdapter amountAdapter;

    public Withdrawal(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.all, container, false);
        recyclerview = view.findViewById(R.id.recyclerview);
        loadAccount();

        amountAdapter = new  AmountAdapter(getContext(),list_amount);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false );
        recyclerview.setLayoutManager(layoutManager);
//        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(amountAdapter);

        return view;
    }

    void loadAccount() {
        list_amount = new ArrayList<>();

        list_amount.add(new Amount(11.27, "Kofi", "9-2-2001"));
        list_amount.add(new Amount(15.80, "Jerry", "4-12-2020"));
        list_amount.add(new Amount(87.20, "Boss Max", "7-8-1998"));
        list_amount.add(new Amount(14.90, "Joel", "2-5-2019"));
    }


}