package com.foodorder.tatsuya.foodorder.model.foodpkg;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by tatsuya on 07/04/2018.
 */

public class Hamburger extends Food implements KvmSerializable {
    @Override
    public Object getProperty(int i) {
        return super.getProperty(i);
    }

    @Override
    public int getPropertyCount() {
        return super.getPropertyCount();
    }

    @Override
    public void setProperty(int i, Object o) {
        super.setProperty(i, o);
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        super.getPropertyInfo(i, hashtable, propertyInfo);
    }
}