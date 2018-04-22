package com.foodorder.tatsuya.foodorder.utils;

import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;

/**
 * Created by tatsuya on 17/04/2018.
 */

public class EndPoint<T> implements OnTaskCompleted<T> {
    //for getting result from server but not display in any activity
    @Override
    public void handle(T value) {
    }
}
