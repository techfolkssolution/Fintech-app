package com.example.lnp.DataModel;

public class ImportantLinkUpdateDto {

    private String aadhaarUrl="";
    private String panUrl="";
    private String updateType;
    private Long id;

    public ImportantLinkUpdateDto() {
    }

    public String getAadhaarUrl() {
        return aadhaarUrl;
    }

    public void setAadhaarUrl(String aadhaarUrl) {
        this.aadhaarUrl = aadhaarUrl;
    }

    public String getPanUrl() {
        return panUrl;
    }

    public void setPanUrl(String panUrl) {
        this.panUrl = panUrl;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
