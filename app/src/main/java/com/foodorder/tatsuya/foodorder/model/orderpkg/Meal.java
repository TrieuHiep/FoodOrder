package com.foodorder.tatsuya.foodorder.model.orderpkg;

import com.foodorder.tatsuya.foodorder.model.ctmpkg.Customer;
import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;

import java.io.Serializable;

/**
 * Created by tatsuya on 07/04/2018.
 */

public class Meal implements Serializable {
    private int ID;
    private Customer customer;
    private Food food;
    private int quantity;

    public Meal() {
    }

    public Meal(Customer customer, Food food, int quantity) {
        this.customer = customer;
        this.food = food;
        this.quantity = quantity;
    }

    public Meal(int ID, Customer customer, Food food, int quantity) {
        this.ID = ID;
        this.customer = customer;
        this.food = food;
        this.quantity = quantity;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
