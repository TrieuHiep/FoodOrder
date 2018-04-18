package com.foodorder.tatsuya.foodorder.model.foodpkg;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Created by tatsuya on 07/04/2018.
 */

public class Food implements KvmSerializable, Serializable {
    private int id;
    private String productName;
    private long price;
    private int quantity;
    private String imageURL;

    public Food() {
    }

    public Food(int id, String productName,
                long price, int quantity, String imageURL) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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
                object = this.productName;
                break;
            }
            case 2: {
                object = this.price;
                break;
            }
            case 3: {
                object = this.quantity;
                break;
            }
            case 4: {
                object = this.imageURL;
                break;
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
                this.productName = o.toString();
                break;
            }
            case 2: {
                this.price = Long.parseLong(o.toString());
                break;
            }
            case 3: {
                this.quantity = Integer.parseInt(o.toString());
                break;
            }
            case 4: {
                this.imageURL = o.toString();
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
                propertyInfo.name = "productName";
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                break;
            }
            case 2: {
                propertyInfo.name = "price";
                propertyInfo.type = PropertyInfo.LONG_CLASS;
                break;
            }
            case 3: {
                propertyInfo.name = "quantity";
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                break;
            }
            case 4: {
                propertyInfo.name = "imageURL";
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                break;
            }
        }
    }
}
