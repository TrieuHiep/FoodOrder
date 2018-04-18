package com.foodorder.tatsuya.foodorder.model.ctmpkg;

import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.model.personpkg.Person;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by tatsuya on 07/04/2018.
 */

public class Customer extends Person implements KvmSerializable {
    private String email;

    public Customer() {
    }

    public Customer(int ID, Account account, int age, String fullName, String email) {
        super(ID, account, age, fullName);
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
        Object object = null;
        switch (i) {
            case 0: {
                object = this.id;
                break;
            }
            case 1: {
                object = this.account;
                break;
            }
            case 2: {
                object = this.age;
                break;
            }
            case 3: {
                object = this.fullName;
                break;
            }
            case 4:{
                object = this.email;
            }
        }
        return object;
    }

    @Override
    public int getPropertyCount() {
        return 5;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i) {
            case 0: {
                this.id = Integer.parseInt(o.toString());
                break;
            }
            case 1: {
                this.account = (Account) o;
                break;
            }
            case 2: {
                this.age = Integer.parseInt(o.toString());
                break;
            }
            case 3: {
                this.fullName = o.toString();
                break;
            }
            case 4: {
                this.email = o.toString();
                break;
            }
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i) {
            case 0: {
                propertyInfo.name = "id";
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                break;
            }
            case 1: {
                propertyInfo.name = "accountUsername";
                propertyInfo.type = PropertyInfo.OBJECT_CLASS;
                break;
            }
            case 2: {
                propertyInfo.name = "age";
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                break;
            }
            case 3: {
                propertyInfo.name = "fullName";
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                break;
            }
            case 4: {
                propertyInfo.name = "email";
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                break;
            }
        }
    }

}
