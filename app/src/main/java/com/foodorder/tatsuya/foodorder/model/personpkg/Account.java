package com.foodorder.tatsuya.foodorder.model.personpkg;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Created by tatsuya on 07/04/2018.
 */

public class Account implements KvmSerializable, Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String username;
    private String password;

    public Account() {

    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public Object getProperty(int i) {
        Object object = null;
        switch (i) {
            case 0: {
                object = this.username;
                break;
            }
            case 1: {
                object = this.password;
                break;
            }

        }
        return object;
    }

    @Override
    public int getPropertyCount() {
        return 2;
    }

    @Override
    public void setProperty(int i, Object o) {
// TODO Auto-generated method stub
        switch (i) {
            case 0: {
                this.username = o.toString();
                break;
            }
            case 1: {
                this.password = o.toString();
                break;
            }
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i) {
            case 0: {
                propertyInfo.name = "username";
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                break;
            }
            case 1: {
                propertyInfo.name = "password";
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                break;

            }
        }
    }
}
