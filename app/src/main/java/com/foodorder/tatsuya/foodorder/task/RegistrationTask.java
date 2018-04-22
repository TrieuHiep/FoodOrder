package com.foodorder.tatsuya.foodorder.task;

import android.content.Context;
import android.util.Log;

import com.foodorder.tatsuya.foodorder.model.ctmpkg.Customer;
import com.foodorder.tatsuya.foodorder.model.personpkg.Person;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by YenNguyen196 on 04/21/18.
 */

public class RegistrationTask extends BasicTask<Person,Void, Boolean>{

    private Person person;
    private String email;

    public RegistrationTask(Context context, OnTaskCompleted<Boolean> listener, Person person, String email) {
        super(context, listener);
        this.person = person;
        this.email = email;
        super.URL = super.HOST_NAME + "/customerws/customerws?WSDL";
        super.METHOD_NAME = "addCustomer";
        super.SOAP_ACTION = this.NAMESPACE + this.METHOD_NAME;
    }

    @Override
    protected Boolean doInBackground(Person... persons) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo propertyInfo = new PropertyInfo();
            propertyInfo.setName("person");
            propertyInfo.setValue(person);
            propertyInfo.setType(Person.class);
            request.addProperty(propertyInfo);

            PropertyInfo propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("email");
            propertyInfo1.setValue(email);
            propertyInfo1.setType(String.class);
            request.addProperty(propertyInfo1);

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
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
