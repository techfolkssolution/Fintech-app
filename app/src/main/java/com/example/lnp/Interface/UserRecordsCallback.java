package com.example.lnp.Interface;

import com.example.lnp.DataModel.UserInformationDataModel;
import com.example.lnp.DataModel.UtilityServiceModel;

import java.util.ArrayList;

public interface UserRecordsCallback {
    void onSuccess(ArrayList<UserInformationDataModel> userInformationDataModelArrayList);
    void pageCounter(int pageNumber);
    void onError(String error);
}
