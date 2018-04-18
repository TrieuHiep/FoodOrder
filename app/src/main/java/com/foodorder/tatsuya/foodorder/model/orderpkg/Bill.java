package com.foodorder.tatsuya.foodorder.model.orderpkg;

import com.foodorder.tatsuya.foodorder.model.empkg.Employee;

/**
 * Created by tatsuya on 07/04/2018.
 */

public class Bill {
    private int ID;
    private FoodOrder foodOrder;
    private Employee employee;

    public Bill() {
    }

    public Bill(FoodOrder foodOrder, Employee employee) {
        this.foodOrder = foodOrder;
        this.employee = employee;
    }

    public Bill(int ID, FoodOrder foodOrder, Employee employee) {
        this.ID = ID;
        this.foodOrder = foodOrder;
        this.employee = employee;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public FoodOrder getFoodOrder() {
        return foodOrder;
    }

    public void setFoodOrder(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
