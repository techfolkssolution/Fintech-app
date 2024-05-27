package com.example.lnp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lnp.DataModel.UserInformationDataModel;
import com.example.lnp.R;

import java.util.ArrayList;

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.AllUserViewHolder>{
    private Context context;
    private ArrayList<UserInformationDataModel> userInformationDataModelArrayList;

    public AllUserAdapter(Context context, ArrayList<UserInformationDataModel> userInformationDataModelArrayList) {
        this.context = context;
        this.userInformationDataModelArrayList = userInformationDataModelArrayList;
    }

    @NonNull
    @Override
    public AllUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllUserViewHolder(LayoutInflater.from(context).inflate(R.layout.form_item_container,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllUserViewHolder holder, int position) {
        UserInformationDataModel userInfo=userInformationDataModelArrayList.get(position);
        holder.textViewUserName.setText(userInfo.getFirstName()+" "+userInfo.getLastName());
        holder.textViewUserDesignation.setText(userInfo.getDesignation());
    }

    @Override
    public int getItemCount() {
        return userInformationDataModelArrayList.size();
    }

    public class AllUserViewHolder extends RecyclerView.ViewHolder{
        TextView textViewUserName,textViewUserDesignation;

        public AllUserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewUserDesignation = itemView.findViewById(R.id.tvServiceName);
        }
    }
}
