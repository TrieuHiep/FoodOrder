package com.foodorder.tatsuya.foodorder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.foodorder.tatsuya.foodorder.R;
import com.foodorder.tatsuya.foodorder.UserSession;
import com.foodorder.tatsuya.foodorder.adapter.MealAdapter;
import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;
import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.task.GetMyMeal;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;

import java.util.ArrayList;
import java.util.List;

public class ViewMyMealActivity extends AppCompatActivity implements OnTaskCompleted<List<Food>> {
    private ListView listViewMeals;
    private List<Food> foodList = new ArrayList<>();
    private TextView label;
    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_meal);
        this.listViewMeals = super.findViewById(R.id.listViewMeals);
        this.label = super.findViewById(R.id.myMeal);
        this.btnContinue = super.findViewById(R.id.btnContinue);

        Account loggedAccount = UserSession.getInstance().getLoggedAccount();
        this.label.setText("Meal for " + loggedAccount.getUsername());
        new GetMyMeal(ViewMyMealActivity.this, ViewMyMealActivity.this)
                .execute();

        btnContinue.setOnClickListener(view -> {
            int amount = 0;
            for (Food food : foodList) {
                amount += food.getPrice();
            }
            System.out.println("Amount = " + amount);
            amount = amount / 1000;
            super.finish();
            startActivity(new Intent(ViewMyMealActivity.this, OrderConfirmation.class)
                    .putExtra("amount", amount)
            );
        });
    }

    @Override
    public void handle(List<Food> value) {
        this.foodList = value;
        MealAdapter mealAdapter
                = new MealAdapter(this, R.layout.activity_meal_line, value);
        this.listViewMeals.setAdapter(mealAdapter);
    }
}
