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
        return new FormAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.form_item_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FormAdapterViewHolder holder, int position) {
        UtilityServiceModel utilityServiceModel = utilityServiceModelArrayList.get(position);
        holder.textViewName.setText("Name : " + utilityServiceModel.getName());
        holder.textViewServiceName.setText("Service Name : " + utilityServiceModel.getFormType());

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

    public class FormAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewServiceName;
        CardView cardViewContainer;

        public FormAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewUserName);
            textViewServiceName = itemView.findViewById(R.id.tvServiceName);
            cardViewContainer = itemView.findViewById(R.id.cardViewContainer);
        }
    }

    public void displayDetails(UtilityServiceModel utilityServiceModel) {
        TextView textViewUserName, textViewMobileNumber, textViewAddress, textViewLoanCategory, textViewLoanType, textViewLoanAmount, textViewComment,textViewCAType,textViewEngineerBuildingType,textViewEngineerServiceType,textViewTenure,textViewSavingAmount;
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.request_service_detail_container);
        textViewUserName = dialog.findViewById(R.id.textViewUserName);
        textViewMobileNumber = dialog.findViewById(R.id.textViewMobileNumber);
        textViewAddress = dialog.findViewById(R.id.textViewAddress);
        textViewLoanCategory = dialog.findViewById(R.id.textViewLoanCategory);
        textViewLoanType = dialog.findViewById(R.id.textViewLoanType);
        textViewLoanAmount = dialog.findViewById(R.id.textViewLoanAmount);
        textViewComment = dialog.findViewById(R.id.textViewComment);
        textViewCAType=dialog.findViewById(R.id.textViewCAType);
        textViewEngineerBuildingType=dialog.findViewById(R.id.textViewEngineerBuildingType);
        textViewEngineerServiceType=dialog.findViewById(R.id.textViewEngineerServiceType);
        textViewTenure=dialog.findViewById(R.id.textViewTenure);
        textViewSavingAmount=dialog.findViewById(R.id.textViewSavingAmount);


        textViewUserName.setText("Name : " + utilityServiceModel.getName());
        textViewMobileNumber.setText("Mobile Number : " + utilityServiceModel.getMobileNumber());
        textViewAddress.setText("Address : " + utilityServiceModel.getAddress());
        textViewComment.setText("Comment : " + utilityServiceModel.getComment());
        if (utilityServiceModel.getFormType().equals("Loans")) {
            textViewLoanCategory.setVisibility(View.VISIBLE);
            textViewLoanAmount.setVisibility(View.VISIBLE);

            textViewLoanCategory.setText("Loan Category : " + utilityServiceModel.getLoanCategory());
            textViewLoanAmount.setText("Loan Amount : " + utilityServiceModel.getLoanAmount() + " Rs");
            if (utilityServiceModel.getLoanCategory().equals("Housing Loan")) {
                textViewLoanType.setVisibility(View.VISIBLE);
                textViewLoanType.setText("Loan Type : " + utilityServiceModel.getLoanType());
            }
        } else if (utilityServiceModel.getFormType().equals("CA")) {
            textViewCAType.setVisibility(View.VISIBLE);
            textViewCAType.setText("CA Type : "+utilityServiceModel.getCAType());
        } else if (utilityServiceModel.getFormType().equals("Engineer")) {
            textViewEngineerBuildingType.setVisibility(View.VISIBLE);
            textViewEngineerServiceType.setVisibility(View.VISIBLE);

            textViewEngineerBuildingType.setText("Engineer Building Type : "+utilityServiceModel.getEngineerBuildingType());
            textViewEngineerServiceType.setText("Engineer Building Type : "+utilityServiceModel.getEngineerServiceType());

        } else if (utilityServiceModel.getFormType().equals("Savings")) {
                textViewTenure.setVisibility(View.VISIBLE);
                textViewSavingAmount.setVisibility(View.VISIBLE);


                textViewTenure.setText("Tenure : "+utilityServiceModel.getTenure());
                textViewSavingAmount.setText("Saving Amount : "+ utilityServiceModel.getSavingAmount()+" Rs");
        }

        dialog.show();
    }
}
