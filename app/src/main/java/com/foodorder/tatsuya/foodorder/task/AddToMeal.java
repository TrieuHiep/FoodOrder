package com.foodorder.tatsuya.foodorder.task;

import android.content.Context;
import android.util.Log;

import com.foodorder.tatsuya.foodorder.UserSession;
import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;
import com.foodorder.tatsuya.foodorder.model.personpkg.Account;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by tatsuya on 30/03/2018.
 */

public class AddToMeal extends BasicTask<Food, Void, Boolean> {

    public AddToMeal(Context context, OnTaskCompleted<Boolean> listener) {
        super(context,listener);
        super.URL = super.HOST_NAME + "/mealws/mealws?WSDL";
        super.METHOD_NAME = "addProduct";
        super.SOAP_ACTION = super.NAMESPACE + super.METHOD_NAME;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Food... food) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            //Pass value for fname variable of the web service
            PropertyInfo accountProp = new PropertyInfo();
            accountProp.setName("account");//Define the variable name in the web service method
            accountProp.setValue(UserSession.getInstance().getLoggedAccount());//Define value for fname variable
            accountProp.setType(Account.class);//Define the type of the variable
            request.addProperty(accountProp);//Pass properties to the variable

            //Pass value for fname variable of the web service
            PropertyInfo foodProp = new PropertyInfo();
            foodProp.setName("food");//Define the variable name in the web service method
            foodProp.setValue(food[0]);//Define value for fname variable
            foodProp.setType(Food.class);//Define the type of the variable
            request.addProperty(foodProp);//Pass properties to the variable

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 60000);
            Log.i("bitching", "guys");
            androidHttpTransport.call(SOAP_ACTION, envelope);
            Log.i("fuck you", "bitch");
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            System.out.println(response.toString());
            return Boolean.valueOf(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean value) {
        super.onPostExecute(value);
        super.listener.handle(value);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}