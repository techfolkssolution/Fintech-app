package com.example.lnp.DataModel;

public class AdminAccessInformation {
    private long id;
    private String adminAccessInformationKey;
    private String adminAccessInformationValue;

    public AdminAccessInformation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAdminAccessInformationKey() {
        return adminAccessInformationKey;
    }

    public void setAdminAccessInformationKey(String adminAccessInformationKey) {
        this.adminAccessInformationKey = adminAccessInformationKey;
    }

    public String getAdminAccessInformationValue() {
        return adminAccessInformationValue;
    }

    public void setAdminAccessInformationValue(String adminAccessInformationValue) {
        this.adminAccessInformationValue = adminAccessInformationValue;
    }
}
