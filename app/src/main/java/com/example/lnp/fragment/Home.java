package com.example.lnp.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.lnp.Interface.OnItemClick;
import com.example.lnp.R;
import com.example.lnp.activity.EditProfile;
import com.example.lnp.activity.RequestService;
import com.example.lnp.activity.RetailerDocumentVerification;
import com.example.lnp.activity.ViewForms;
import com.example.lnp.adapter.ServicesAdapter;

import java.util.ArrayList;


public class Home extends Fragment implements OnItemClick{
    private ImageSlider imageSlider;
    private String[] servicesName = {
            "Loans", "CA", "Engineer", "CIBIL", "Savings","View Forms","Admin"
    };
    private int[] servicesIcon = {
            R.drawable.bank, R.drawable.accountant, R.drawable.engineers, R.drawable.cibil, R.drawable.piggybank,R.drawable.form,R.drawable.admin
    };

    private String[] billsAndUtilityServiceName = {
            "Recharge", "PAN Card", "EMI", "Broadband", "Bill Payment", "Insurance", "Water", "DTH", "Landline", "Electricity", "Cable", "Fastag", "LPG"
    };

    private int[] billsAndUtilityServiceIcon = {
            R.drawable.recharge, R.drawable.pancard, R.drawable.emi,
            R.drawable.broadband, R.drawable.bill, R.drawable.insurance,
            R.drawable.water, R.drawable.dish, R.drawable.telephone,
            R.drawable.electricity, R.drawable.television, R.drawable.fastag, R.drawable.lpg
    };
    private RecyclerView recyclerViewQuery, recyclerViewBills;
    private ServicesAdapter queryAdapter, billAdapter;
    private TextView textViewRetailer;

    public Home() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider = view.findViewById(R.id.imageSlider);
        recyclerViewQuery = view.findViewById(R.id.recyclerViewServices);
        recyclerViewBills = view.findViewById(R.id.recyclerViewBills);
        textViewRetailer=view.findViewById(R.id.textViewRetailer);


        //Display Images in slider.
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.logo, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.logo, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.logo, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        displayAssistanceQueryItem();
        displayBillsAndUtilityItem();
        textViewRetailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(), RetailerDocumentVerification.class);
                startActivity(i);

            }
        });


        return view;
    }

    public void displayAssistanceQueryItem() {
        queryAdapter = new ServicesAdapter(getActivity(), servicesName, servicesIcon,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewQuery.setLayoutManager(layoutManager);
        recyclerViewQuery.setAdapter(queryAdapter);
    }

    public void displayBillsAndUtilityItem() {
        billAdapter = new ServicesAdapter(getActivity(), billsAndUtilityServiceName, billsAndUtilityServiceIcon,this);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerViewBills.setLayoutManager(layoutManager);
        recyclerViewBills.setAdapter(billAdapter);
    }

    @Override
    public void onItemClickListener(String serviceName) {
        boolean isRetailer=false;
        if(serviceName.equals("View Forms")){
            Intent intent=new Intent(getActivity(), ViewForms.class);
            intent.putExtra("serviceName",serviceName);
            startActivity(intent);
        }else{
            if (isRetailer || isServiceAccessibleByAll(serviceName)) {
                Intent i = new Intent(getActivity(), RequestService.class);
                i.putExtra("serviceName", serviceName);
                startActivity(i);
            } else {
                Toast.makeText(getActivity(), "This service is only available to retailers.", Toast.LENGTH_SHORT).show();
            }
        }


//        Toast.makeText(getActivity(), "Service Name : "+serviceName, Toast.LENGTH_SHORT).show();
    }


    private boolean isServiceAccessibleByAll(String serviceName) {
        // Define which services are accessible by all users
        return serviceName.equals("Loans") || serviceName.equals("CA") || serviceName.equals("Engineer")
                || serviceName.equals("CIBIL") || serviceName.equals("Savings");
    }
}