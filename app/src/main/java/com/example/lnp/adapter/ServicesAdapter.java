package com.example.lnp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lnp.Interface.OnItemClick;
import com.example.lnp.R;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder> {
    private Context context;
    private String[] servicesName;
    private int[] servicesIcon;
    private OnItemClick onItemClick;

    public ServicesAdapter(Context context, String[] servicesName, int[] servicesIcon, OnItemClick onItemClick) {
        this.context = context;
        this.servicesName = servicesName;
        this.servicesIcon = servicesIcon;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.services_container,parent,false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        holder.imageViewServiceIcon.setImageResource(servicesIcon[position]);
        holder.textViewServiceName.setText(servicesName[position]);

        holder.cardViewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClickListener(servicesName[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return servicesName.length;
    }


    public class ServiceViewHolder extends RecyclerView.ViewHolder{
        TextView textViewServiceName;
        ImageView imageViewServiceIcon;
        CardView cardViewContainer;


        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewServiceName=itemView.findViewById(R.id.txtServiceName);
            imageViewServiceIcon=itemView.findViewById(R.id.imageViewService);
            cardViewContainer=itemView.findViewById(R.id.cardViewContainer);

        }
    }
}
