package com.foodorder.tatsuya.foodorder.model.empkg;

import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.model.personpkg.Person;

/**
 * Created by tatsuya on 07/04/2018.
 */

public class Employee extends Person {
    private String internalMail;

    public Employee() {
    }

    public Employee( Account account, int age, String fullName, String internalMail) {
        super( account, age, fullName);
        this.internalMail = internalMail;
    }

    public String getInternalMail() {
        return internalMail;
    }

    public void setInternalMail(String internalMail) {
        this.internalMail = internalMail;
    }
}
