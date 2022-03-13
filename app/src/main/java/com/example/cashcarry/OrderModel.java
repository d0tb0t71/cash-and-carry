package com.example.cashcarry;

public class OrderModel {

    String orderID;
    String orderTitle;
    String orderDescription;
    String orderPrice;
    String time;
    String orderStatus;
    String buyerUID;
    String shopID;


    public OrderModel() {
    }

    public OrderModel(String orderID, String orderTitle, String orderDescription, String orderPrice, String time, String orderStatus, String buyerUID, String shopID) {
        this.orderID = orderID;
        this.orderTitle = orderTitle;
        this.orderDescription = orderDescription;
        this.orderPrice = orderPrice;
        this.time = time;
        this.orderStatus = orderStatus;
        this.buyerUID = buyerUID;
        this.shopID = shopID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getBuyerUID() {
        return buyerUID;
    }

    public void setBuyerUID(String buyerUID) {
        this.buyerUID = buyerUID;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }
}
