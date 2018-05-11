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

public class OrderGetter extends BasicTask<Account, Void, List<FoodOrder>> {

    public OrderGetter(Context context, OnTaskCompleted<List<FoodOrder>> listener) {
        super(context, listener);
        super.URL = super.HOST_NAME + "/foodorderws/foodorderws?WSDL";
        super.METHOD_NAME = "getOrders";
        super.SOAP_ACTION = this.NAMESPACE + this.METHOD_NAME;
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
                SoapObject orderObject = (SoapObject) o;
                FoodOrder order = new FoodOrder();
                SoapObject mealObject = (SoapObject) orderObject.getProperty("mealID");
                SoapObject foodObject = (SoapObject) mealObject.getProperty("foodID");

                Food food = new Food();
                food.setId(Integer.parseInt(foodObject.getProperty("id").toString()));
                food.setProductName(foodObject.getProperty("productName").toString());
                food.setPrice(Long.parseLong(foodObject.getProperty("price").toString()));
                food.setQuantity(Integer.parseInt(foodObject.getProperty("quantity").toString()));
                food.setImageURL(foodObject.getProperty("imageURL").toString());

                Meal meal = new Meal();
                meal.setFood(food);

                FoodOrder foodOrder = new FoodOrder();
                foodOrder.setID(Integer.valueOf(orderObject.getProperty("id").toString()));
                foodOrder.setMeal(meal);
                foodOrder.setStatus(orderObject.getProperty("status").toString());
                foodOrderList.add(foodOrder);
            } else if (o instanceof Vector) {
                System.out.println("GETTING LIST OF ORDER........");
                Vector<SoapObject> result = (Vector<SoapObject>) o;
                int length = result.size();
                System.out.println("NUM OF ORDER = "+ length);
                for (int i = 0; i < length; ++i) {
                    System.out.println("i = "+i);
                    SoapObject orderObject = result.get(i);
                    System.out.println(orderObject.toString());
                    SoapObject mealObject = (SoapObject) orderObject.getProperty("mealID");
                    SoapObject foodObject = (SoapObject) mealObject.getProperty("foodID");

                    Food food = new Food();
                    food.setId(Integer.parseInt(foodObject.getProperty("id").toString()));
                    food.setProductName(foodObject.getProperty("productName").toString());
                    food.setPrice(Long.parseLong(foodObject.getProperty("price").toString()));
                    food.setQuantity(Integer.parseInt(foodObject.getProperty("quantity").toString()));
                    food.setImageURL(foodObject.getProperty("imageURL").toString());
                    System.out.println("GETTING IMG = " + foodObject.getProperty("imageURL").toString());

                    Meal meal = new Meal();
                    meal.setFood(food);

                    FoodOrder foodOrder = new FoodOrder();
                    foodOrder.setID(Integer.valueOf(orderObject.getProperty("id").toString()));
                    foodOrder.setMeal(meal);
                    foodOrder.setStatus(orderObject.getProperty("status").toString());

                    foodOrderList.add(foodOrder);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return foodOrderList;
    }
}
