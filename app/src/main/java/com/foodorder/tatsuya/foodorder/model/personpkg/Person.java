package com.foodorder.tatsuya.foodorder.model.personpkg;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by tatsuya on 07/04/2018.
 */

public class Person implements KvmSerializable{
    protected int id;
    protected Account account;
    protected int age;
    protected String fullName;

    public Person() {
    }

    public Person(int id, Account account, int age, String fullName) {
        this.id = id;
        this.account = account;
        this.age = age;
        this.fullName = fullName;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
        }
        return object;
    }

    @Override
    public int getPropertyCount() {
        return 4;
    }

    @Override
    public String toString() {
        return "Person{" +
                " account=" + account +
                ", age=" + age +
                ", fullName='" + fullName + '\'' +
                '}';
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
        }
    }

}
