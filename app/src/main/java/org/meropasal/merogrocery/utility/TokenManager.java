package org.meropasal.merogrocery.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.meropasal.merogrocery.model.VendorCustomerRecyclerModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TokenManager {
    private static final String PREF_NAME = "MyAppPrefs";
    private static final String TOKEN_KEY = "auth_token";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String ROLE = "role";

    private static final String CUSTOMERS = "customers";
    public static void saveToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    public static void clearToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TOKEN_KEY);
        editor.apply();
    }

    public static void savePhoneNumber(Context context, String phone_number) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHONE_NUMBER, phone_number);
        editor.apply();
    }

    public static String getPhoneNumber(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PHONE_NUMBER, null);
    }

    public static void clearPhoneNumber(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PHONE_NUMBER);
        editor.apply();
    }

    public static void saveRole(Context context, String role) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ROLE, role);
        editor.apply();
    }

    public static String getRole(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ROLE, null);
    }

    public static void clearRole(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ROLE);
        editor.apply();
    }


//    public static void saveCustomerArray(Context context, ArrayList<VendorCustomerRecyclerModel.Message.Customer> customers) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        Gson gson = new Gson();
//        String json = gson.toJson(customers);
//        editor.putString(CUSTOMERS, json);
//        editor.apply();
//    }
//
//    public static void UpdateCustomerArray(Context context, VendorCustomerRecyclerModel.Message.Customer customer) {
//        ArrayList<VendorCustomerRecyclerModel.Message.Customer> arrCustomer = TokenManager.getCustomerArray(context);
//        assert arrCustomer != null;
//        arrCustomer.add(customer);
//        saveCustomerArray(context, arrCustomer);
//    }
//
//    public static ArrayList<VendorCustomerRecyclerModel.Message.Customer> getCustomerArray(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString(CUSTOMERS, null);
//        if(json == null) {
//        return null;
//        }
//        Type type = new TypeToken<ArrayList<VendorCustomerRecyclerModel.Message.Customer>>() {}.getType();
//        return gson.fromJson(json, type);
//    }
//
//    public static void clearCustomerArray(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.remove(CUSTOMERS);
//        editor.apply();
//    }
}
