package com.example.cashcarry;

public class ProductModel {

    String pID;
    String pName;
    String pDes;
    String pPrice;

    public ProductModel() {
    }

    public ProductModel(String pID, String pName, String pType, String pPrice) {
        this.pID = pID;
        this.pName = pName;
        this.pDes = pType;
        this.pPrice = pPrice;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpType() {
        return pDes;
    }

    public void setpType(String pType) {
        this.pDes = pType;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }
}
