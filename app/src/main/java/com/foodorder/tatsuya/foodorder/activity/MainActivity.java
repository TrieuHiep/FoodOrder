package com.foodorder.tatsuya.foodorder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;

import com.foodorder.tatsuya.foodorder.R;
import com.foodorder.tatsuya.foodorder.task.SearchTask;
import com.foodorder.tatsuya.foodorder.task.factory.FoodFactoryTask;
import com.foodorder.tatsuya.foodorder.utils.UserSession;
import com.foodorder.tatsuya.foodorder.adapter.FoodAdapter;
import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;
import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.task.BasicTask;
import com.foodorder.tatsuya.foodorder.task.factory.FoodLoader;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnTaskCompleted<List<Food>> {

    private GridView gridViewProduct;
//    private ImageView cartImage;
    private List<Food> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        process();
    }

    private void process() {
        Account loggedAccount = UserSession.getInstance().getLoggedAccount();
        System.out.println(loggedAccount.getUsername());
        System.out.println(loggedAccount.getPassword());

        this.gridViewProduct = super.findViewById(R.id.gridViewProduct);
//        this.cartImage = super.findViewById(R.id.cartImage);

        BasicTask<Void, Food, List<Food>> basicTask =
                new FoodLoader(MainActivity.this, MainActivity.this);
        basicTask.execute();

        this.gridViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ViewDetail.class);
                Food food = MainActivity.this.foodList.get(i);
                intent.putExtra("foodDetail", food);
                startActivity(intent);
            }
        });

//        this.cartImage.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, ViewMyMealActivity.class);
//            super.startActivity(intent);
//        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.searchMenu);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                if (s != null){
                    new SearchTask(MainActivity.this, MainActivity.this)
                            .execute(s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        if(id == R.id.cartImage){
            Intent intent = new Intent(MainActivity.this, ViewMyMealActivity.class);
            super.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_orderTracking) {
            // Handle the camera action
            super.startActivity(new Intent(MainActivity.this, OrderTrackingActivity.class));
        } else if (id == R.id.nav_logout) {
            UserSession.getInstance().putAccount(this, new Account());
            Intent intent = new Intent(super.getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        } else if (id == R.id.nav_Hamburger) {
            new FoodFactoryTask()
                    .getTask(this, this, "Hamburger")
                    .execute();
        } else if (id == R.id.nav_Chicken) {
            new FoodFactoryTask()
                    .getTask(this, this, "Chicken")
                    .execute();
        } else if (id == R.id.nav_Rice) {
            new FoodFactoryTask()
                    .getTask(this, this, "Rice")
                    .execute();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void handle(List<Food> value) {
        this.foodList = value;
        FoodAdapter foodAdapter
                = new FoodAdapter(this, R.layout.activity_food_line, value);
        this.gridViewProduct.setAdapter(foodAdapter);
    }
}
