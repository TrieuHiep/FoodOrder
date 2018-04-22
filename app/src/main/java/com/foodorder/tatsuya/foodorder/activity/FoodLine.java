package com.foodorder.tatsuya.foodorder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.foodorder.tatsuya.foodorder.R;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;

public class FoodLine extends AppCompatActivity implements OnTaskCompleted<Boolean>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_line);
    }

    @Override
    public void handle(Boolean value) {
        System.out.println(value);
    }
}
