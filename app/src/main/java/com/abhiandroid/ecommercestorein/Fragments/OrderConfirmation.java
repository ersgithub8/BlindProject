package com.abhiandroid.ecommercestorein.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.abhiandroid.ecommercestorein.Activities.MainActivity;
import com.abhiandroid.ecommercestorein.R;

import butterknife.ButterKnife;

public class OrderConfirmation extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int layout = R.layout.fragment_order_confirmation;
        View view = inflater.inflate(layout, container, false);

        return view;
    }
}
