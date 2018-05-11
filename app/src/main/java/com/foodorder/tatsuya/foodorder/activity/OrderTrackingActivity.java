package com.foodorder.tatsuya.foodorder.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.foodorder.tatsuya.foodorder.R;
import com.foodorder.tatsuya.foodorder.adapter.OrderAdapter;
import com.foodorder.tatsuya.foodorder.model.orderpkg.FoodOrder;
import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;
import com.foodorder.tatsuya.foodorder.task.OrderGetter;
import com.foodorder.tatsuya.foodorder.task.SearchTask;
import com.foodorder.tatsuya.foodorder.utils.UserSession;

import java.util.ArrayList;
import java.util.List;

public class OrderTrackingActivity extends AppCompatActivity
        implements OnTaskCompleted<List<FoodOrder>>

{
    private ListView listViewOrderTracking;
    private List<FoodOrder> foodOrderList = new ArrayList<>();
//    private Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);
        this.listViewOrderTracking = super.findViewById(R.id.listViewOrderTracking);
//        this.btnRefresh = super.findViewById(R.id.btnRefresh);

        Account loggedAccount = UserSession.getInstance().getLoggedAccount();

        new OrderGetter(OrderTrackingActivity.this, OrderTrackingActivity.this)
                .execute(loggedAccount);
//        btnRefresh.setOnClickListener(view -> {
//            new OrderGetter(OrderTrackingActivity.this, OrderTrackingActivity.this)
//                    .execute(loggedAccount);
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ordertrackingmenu, menu);
        MenuItem menuItem = menu.findItem(R.id.refreshImage);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.refreshImage) {
            Account loggedAccount = UserSession.getInstance().getLoggedAccount();
            new OrderGetter(OrderTrackingActivity.this, OrderTrackingActivity.this)
                    .execute(loggedAccount);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void handle(List<FoodOrder> value) {
        this.foodOrderList = value;
        OrderAdapter orderAdapter
                = new OrderAdapter(this, R.layout.order_line, value);
        this.listViewOrderTracking.setAdapter(orderAdapter);
    }
}
