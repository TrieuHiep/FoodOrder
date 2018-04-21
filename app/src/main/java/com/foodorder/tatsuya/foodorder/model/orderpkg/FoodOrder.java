package com.foodorder.tatsuya.foodorder.model.orderpkg;

import java.io.Serializable;

/**
 * Created by tatsuya on 07/04/2018.
 */

public class FoodOrder implements Serializable{
    private int ID;
    private Meal meal;
    private String creationDate;
    private String paymentType;
    private String status;
    private String address;

    public FoodOrder() {
    }

    public FoodOrder(Meal meal, String creationDate, String paymentType,
                     String status, String address) {
        this.meal = meal;
        this.creationDate = creationDate;
        this.paymentType = paymentType;
        this.status = status;
        this.address = address;
    }

    public FoodOrder(int ID, Meal meal, String creationDate,
                     String paymentType, String status, String address) {
        this.ID = ID;
        this.meal = meal;
        this.creationDate = creationDate;
        this.paymentType = paymentType;
        this.status = status;
        this.address = address;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
