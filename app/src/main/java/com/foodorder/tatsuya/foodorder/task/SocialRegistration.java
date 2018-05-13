package com.foodorder.tatsuya.foodorder.task;

import android.content.Context;
import android.util.Log;

import com.foodorder.tatsuya.foodorder.model.personpkg.Person;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by tatsuya on 20/04/2018.
 */

public class SocialRegistration extends BasicTask<Void, Void, Boolean> {
    private Person person;
    private String email;

    public SocialRegistration(Context context, OnTaskCompleted<Boolean> listener,
                              Person person, String email) {
        super(context, listener);
        this.person = person;
        this.email = email;
        super.URL = super.HOST_NAME + "/customerws/customerws?WSDL";
        super.METHOD_NAME = "addSocialCustomer";
        super.SOAP_ACTION = super.NAMESPACE + super.METHOD_NAME;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Pass value for fname variable of the web service
            PropertyInfo personProp = new PropertyInfo();
            personProp.setName("person");//Define the variable name in the web service method
            personProp.setValue(this.person);//Define value for fname variable
            personProp.setType(Person.class);//Define the type of the variable
            request.addProperty(personProp);//Pass properties to the variable

            //Pass value for fname variable of the web service
            PropertyInfo emailProp = new PropertyInfo();
            emailProp.setName("email");//Define the variable name in the web service method
            emailProp.setValue(this.email);//Define value for fname variable
            emailProp.setType(String.class);//Define the type of the variable
            request.addProperty(emailProp);//Pass properties to the variable

//            //Pass value for fname variable of the web service
//            PropertyInfo typeProp = new PropertyInfo();
//            typeProp.setName("type");//Define the variable name in the web service method
//            typeProp.setValue("FB");//Define value for fname variable
//            typeProp.setType(String.class);//Define the type of the variable
//            request.addProperty(typeProp);//Pass properties to the variable


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
}
