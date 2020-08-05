package com.abhiandroid.ecommercestorein.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.abhiandroid.ecommercestorein.Adapters.SearchProductListAdapter;
import com.abhiandroid.ecommercestorein.MVP.Product;
import com.abhiandroid.ecommercestorein.Activities.MainActivity;
import com.abhiandroid.ecommercestorein.R;
import com.abhiandroid.ecommercestorein.Activities.SplashScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class SearchProducts extends Fragment {

    @BindView(R.id.searchProductsRecyclerView)
    RecyclerView searchProductsRecyclerView;
    //@BindView(R.id.searchEditText)
    public  static  EditText searchEditText;
    List<Product> productList;
    private  static final int requestcode=1000;
    @BindView(R.id.defaultMessage)
    TextView defaultMessage;
    View view;
    ImageButton qr,voice;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_search_products, container, false);
        ButterKnife.bind(this, view);
        defaultMessage.setText("Search Any Product");
        searchEditText=(EditText)view.findViewById(R.id.searchEditText);
        qr=(ImageButton)view.findViewById(R.id.qr);
        voice=(ImageButton)view.findViewById(R.id.voice);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("text", editable.toString());
                searchProducts(editable.toString());
            }
        });

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},1);
                }else {
                    startActivity(new Intent(getActivity(),Qr_Activity.class));
                }
            }
        });

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).lockUnlockDrawer(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        MainActivity.title.setText("Search");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void searchProducts(String s) {
        productList = new ArrayList<>();
        if (s.length() > 0) {
            for (int i = 0; i < SplashScreen.allProductsData.size(); i++)
                if (SplashScreen.allProductsData.get(i).getProductName().toLowerCase().contains(s.toLowerCase().trim())) {
                    productList.add(SplashScreen.allProductsData.get(i));
                }
            if (productList.size() < 1) {
                defaultMessage.setText("Record Not Found");
                defaultMessage.setVisibility(View.VISIBLE);
            } else {
                defaultMessage.setVisibility(View.GONE);
            }
            Log.d("size", productList.size() + "" + SplashScreen.allProductsData.size());
        } else {
            productList = new ArrayList<>();
            defaultMessage.setText("Search Any Product");
            defaultMessage.setVisibility(View.VISIBLE);
        }
        setProductsData();


    }

    private void setProductsData() {
        SearchProductListAdapter productListAdapter;
        GridLayoutManager gridLayoutManager;
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        searchProductsRecyclerView.setLayoutManager(gridLayoutManager);
        productListAdapter = new SearchProductListAdapter(getActivity(), productList);
        searchProductsRecyclerView.setAdapter(productListAdapter);

    }

    public void speak(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi speak Something");

        try {
            startActivityForResult(intent,requestcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case requestcode:{
                if (resultCode==RESULT_OK && null!=data ){
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchEditText.setText(result.get(0));
                }
            }
            break;
        }
    }
}
