package com.foodorder.tatsuya.foodorder.task.factory;

import android.content.Context;

import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;
import com.foodorder.tatsuya.foodorder.task.BasicTask;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;

import java.util.List;

/**
 * Created by tatsuya on 22/04/2018.
 */

public class FoodFactoryTask {
    public BasicTask<Void, Food, List<Food>> getTask(Context context,
                                                     OnTaskCompleted<List<Food>> listener,
                                                     String type) {
        switch (type) {
            case "Hamburger": {
                return new HamburgerLoader(context, listener);
            }
            case "Rice": {
                return new RiceLoader(context, listener);
            }
            case "Chicken": {
                return new ChickenLoader(context, listener);
            }
            default:
                return new FoodLoader(context, listener);
        }
    }
}
