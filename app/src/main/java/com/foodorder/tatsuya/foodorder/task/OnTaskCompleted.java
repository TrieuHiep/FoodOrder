package com.foodorder.tatsuya.foodorder.task;

/**
 * Created by tatsuya on 21/03/2018.
 */

public interface OnTaskCompleted<T> {
    void handle(T value);
}
