package com.foodorder.tatsuya.foodorder.task;

/**
 * Created by hiennv on 10/04/2018.
 */

public interface OnTaskCompleted<T> {
    void handle(T value);
}
