
package com.foodorder.tatsuya.foodorder.model.ctmpkg;

import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.model.personpkg.Person;

import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.model.personpkg.Person;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Created by tatsuya on 07/04/2018.
 */

public class Customer extends Person implements KvmSerializable{
    private String email;

    public Customer() {
    }

    public Customer(Account account, int age, String fullName, String email) {
        super(account, age, fullName);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Object getProperty(int i) {
        switch (i){
            case 0: return Account.class;
            case 1: return age;
            case 2: return fullName;
            case 3: return email;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int i, Object o) {

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}

