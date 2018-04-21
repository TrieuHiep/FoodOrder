package com.foodorder.tatsuya.foodorder.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.foodorder.tatsuya.foodorder.R;
import com.foodorder.tatsuya.foodorder.UserSession;
import com.foodorder.tatsuya.foodorder.adapter.OrderTrackingAdapter;
import com.foodorder.tatsuya.foodorder.model.orderpkg.FoodOrder;
import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;
import com.foodorder.tatsuya.foodorder.task.OrderGetter;

import java.util.ArrayList;
import java.util.List;

public class OrderTrackingActivity extends AppCompatActivity implements OnTaskCompleted<List<FoodOrder>> {
    private ListView listViewOrderTracking;
    private List<FoodOrder> foodOrderList = new ArrayList<>();

    @Override
    public void handle(List<FoodOrder> value) {
        Log.i("chay den handle", "OK");
        this.foodOrderList = value;
        OrderTrackingAdapter orderAdapter
                = new OrderTrackingAdapter(this, R.layout.activity_order_tracking_line, value);
        Log.i("chay", "OK");
        this.listViewOrderTracking.setAdapter(orderAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);
        this.listViewOrderTracking = super.findViewById(R.id.listViewOrderTracking);

        Account loggedAccount = UserSession.getInstance().getLoggedAccount();

        new OrderGetter(OrderTrackingActivity.this, OrderTrackingActivity.this)
                .execute(loggedAccount);
    }
}
