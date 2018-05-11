package com.foodorder.tatsuya.foodorder;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;


import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private AdapterFood adapterFood;
    private ListView listView;
    private List<Food> listFood;
    private ImageView imgMeal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        loadAllFood();


    }

    private void init() {

        listView = findViewById(R.id.listView);
        listFood = new ArrayList<>();
        String image = "https://vignette.wikia.nocookie.net/ronaldmcdonald/images/c/c7/1955_Burger.png/revision/latest?cb=20151206183328";
        Food food = new Food("hhh", 123, 12, image);
        Food food1 = new Food("hhh", 123, 12, image);
        listFood.add(food);
        listFood.add(food1);
        adapterFood = new AdapterFood(this, listFood);
        listView.setAdapter(adapterFood);
        imgMeal= findViewById(R.id.img_meal);
        imgMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ViewMealActivity.class);
                startActivity(i);
            }
        });

    }
    private void loadAllFood(){
        adapterFood.notifyDataSetChanged();
    }

}
