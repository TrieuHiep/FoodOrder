package com.foodorder.tatsuya.foodorder.model.personpkg;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by tatsuya on 07/04/2018.
 */

public class Person implements KvmSerializable {
    protected Account account;
    protected int age;
    protected String fullName;

    public Person() {
    }

    public Person(Account account, int age, String fullName) {
        this.account = account;
        this.age = age;
        this.fullName = fullName;
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
        switch (i) {
            case 0:
                return this.account;
            case 1:
                return this.age;
            case 2:
                return this.fullName;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 3;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i) {
            case 0: {
                this.account = (Account) o;
                break;
            }

            case 1: {
                this.age = Integer.parseInt(o.toString());
                break;
            }
            case 2: {
                this.fullName = o.toString();
                break;
            }
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i) {
            case 0: {
                propertyInfo.type = PropertyInfo.OBJECT_CLASS;
                propertyInfo.name = "accountUsername";
                //name nay cua WSDL nha :3
                break;

            }
            case 1: {
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "age";
                break;
            }
            case 2: {
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "fullName";
                break;
            }
        }
    }
}
