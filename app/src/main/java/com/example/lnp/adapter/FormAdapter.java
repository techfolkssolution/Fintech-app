package com.example.lnp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lnp.DataModel.UtilityServiceModel;
import com.example.lnp.R;
import com.example.lnp.activity.MainActivity;

import java.util.ArrayList;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.FormAdapterViewHolder> {
    private Context context;
    private ArrayList<UtilityServiceModel> utilityServiceModelArrayList;

    public FormAdapter(Context context, ArrayList<UtilityServiceModel> utilityServiceModelArrayList) {
        this.context = context;
        this.utilityServiceModelArrayList = utilityServiceModelArrayList;
    }

    @NonNull
    @Override
    public FormAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FormAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.form_item_container,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FormAdapterViewHolder holder, int position) {
        UtilityServiceModel utilityServiceModel=utilityServiceModelArrayList.get(position);
        holder.textViewName.setText("Name : "+utilityServiceModel.getName());
        holder.textViewServiceName.setText("Service Name : "+utilityServiceModel.getLoanCategory());

        holder.cardViewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDetails(utilityServiceModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return utilityServiceModelArrayList.size();
    }

    public class FormAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName,textViewServiceName;
        CardView cardViewContainer;

        public FormAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName=itemView.findViewById(R.id.textViewUserName);
            textViewServiceName=itemView.findViewById(R.id.tvServiceName);
            cardViewContainer=itemView.findViewById(R.id.cardViewContainer);
        }
    }
    public void displayDetails(UtilityServiceModel utilityServiceModel){
        TextView textViewUserName,textViewMobileNumber,textViewAddress;
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.request_service_detail_container);
        textViewUserName=dialog.findViewById(R.id.textViewUserName);
        textViewMobileNumber=dialog.findViewById(R.id.textViewMobileNumber);
        textViewAddress=dialog.findViewById(R.id.textViewAddress);

        textViewUserName.setText("Name : "+utilityServiceModel.getName());
        textViewMobileNumber.setText("Mobile Number : "+utilityServiceModel.getMobileNumber());
        textViewAddress.setText("Address : "+utilityServiceModel.getAddress());

        dialog.show();
    }
}
