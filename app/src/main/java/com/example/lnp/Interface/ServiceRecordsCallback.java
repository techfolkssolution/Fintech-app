package com.example.lnp.Interface;

import com.example.lnp.DataModel.UtilityServiceModel;

import java.util.ArrayList;

public interface ServiceRecordsCallback {
    void onSuccess(ArrayList<UtilityServiceModel> utilityServiceModelArrayList);
    void onError(String error);
}
