package com.foodorder.tatsuya.foodorder.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.foodorder.tatsuya.foodorder.model.personpkg.Account;

/**
 * Created by hiennv on 4/14/18.
 */

public class UserSession {
    private static final UserSession instance = new UserSession();

    public static UserSession getInstance() {
        return instance;
    }

    private static SharedPreferences sharedPreferences;

    public void putAccount(Context context, Account account) {
        sharedPreferences = context.getSharedPreferences("LoggedAccount",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", account.getUsername());
        editor.putString("password", account.getPassword());
        editor.apply();
    }

    public Account getLoggedAccount() {
        return new Account(sharedPreferences.getString("username", null),
                sharedPreferences.getString("password", null));
    }

    private UserSession() {
    }
}
