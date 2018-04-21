package com.foodorder.tatsuya.foodorder.task;

import android.content.Context;
import android.util.Log;

import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;
import com.foodorder.tatsuya.foodorder.model.orderpkg.FoodOrder;
import com.foodorder.tatsuya.foodorder.model.orderpkg.Meal;
import com.foodorder.tatsuya.foodorder.model.personpkg.Account;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by tatsuya on 17/04/2018.
 */

public class OrderGetter extends BasicTask<Account, FoodOrder, List<FoodOrder>> {

    public OrderGetter(Context context, OnTaskCompleted<List<FoodOrder>> listener) {
        super(context, listener);
        super.URL = super.HOST_NAME + "/foodorderws/foodorderws?WSDL";
        super.METHOD_NAME = "getOrders";
        super.SOAP_ACTION = super.NAMESPACE + super.METHOD_NAME;
    }

    @Override
    protected void onProgressUpdate(FoodOrder... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected List<FoodOrder> doInBackground(Account... accounts) {
        List<FoodOrder> foodOrderList = new ArrayList<>();
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Pass value for fname variable of the web service
            PropertyInfo accountProp = new PropertyInfo();
            accountProp.setName("account");//Define the variable name in the web service method
            accountProp.setValue(accounts[0]);//Define value for fname variable
            accountProp.setType(Account.class);//Define the type of the variable
            request.addProperty(accountProp);//Pass properties to the variable

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 60000);
            Log.i("bitching", "guys");

            androidHttpTransport.call(SOAP_ACTION, envelope);
            Log.i("fuck you", "bitch");
            Object o = envelope.getResponse();
            if (o instanceof SoapObject) {
                SoapObject object = (SoapObject) o;
                FoodOrder order = new FoodOrder();
                order.setID(Integer.parseInt(object.getProperty("id").toString()));
                Log.i("fuck you1", "bitch");
                order.setStatus(object.getProperty("status").toString());
                Log.i("fuck you2", "bitch");

//                order.setProductName(object.getProperty("productName").toString());
//                order.setPrice(Long.parseLong(object.getProperty("price").toString()));
//                order.setQuantity(Integer.parseInt(object.getProperty("quantity").toString()));
//                order.setImageURL(object.getProperty("imageURL").toString());
                foodOrderList.add(order);
            } else if (o instanceof Vector) {
                Log.i("fuck you3", "bitch");
                Vector<SoapObject> result = (Vector<SoapObject>) o;
                int length = result.size();
                for (int i = 0; i < length; ++i) {
                    Log.i("fuck you4", "bitch");
                    SoapObject object = result.get(i);
                    System.out.println("object: "+object.toString());
                    FoodOrder order = new FoodOrder();
                    order.setID(Integer.parseInt(object.getProperty("id").toString()));
                    System.out.println("id:"+Integer.parseInt(object.getProperty("id").toString()));

                    order.setStatus(object.getProperty("status").toString());
                    Object k = object.getProperty("mealID").toString();
                    System.out.println(i);
//                    Meal meal = new Meal();
//              //      String productName = object.getProperty("productName").toString();
//                    String imageUrl = object.getProperty("imageURL").toString();
//                    Food food = new Food();
//                   // food.setProductName(productName);
//                    food.setImageURL(imageUrl);
//                    meal.setFood(food);
//                    order.setMeal(meal);
                    foodOrderList.add(order);
                    Log.i("fuck you5", "bitch");

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return foodOrderList;
    }

}
