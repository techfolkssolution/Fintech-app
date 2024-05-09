package com.example.lnp.DataModel;

public class UtilityServiceModel {
    private String name,address,comment,loanCategory,loanType,CAType,engineerBuildingType,engineerServiceType,tenure;
    private long mobileNumber,loanAmount,savingAmount;

    public UtilityServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLoanCategory() {
        return loanCategory;
    }

    public void setLoanCategory(String loanCategory) {
        this.loanCategory = loanCategory;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getCAType() {
        return CAType;
    }

    public void setCAType(String CAType) {
        this.CAType = CAType;
    }

    public String getEngineerBuildingType() {
        return engineerBuildingType;
    }

    public void setEngineerBuildingType(String engineerBuildingType) {
        this.engineerBuildingType = engineerBuildingType;
    }

    public String getEngineerServiceType() {
        return engineerServiceType;
    }

    public void setEngineerServiceType(String engineerServiceType) {
        this.engineerServiceType = engineerServiceType;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public long getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(long loanAmount) {
        this.loanAmount = loanAmount;
    }

    public long getSavingAmount() {
        return savingAmount;
    }

    public void setSavingAmount(long savingAmount) {
        this.savingAmount = savingAmount;
    }
}
