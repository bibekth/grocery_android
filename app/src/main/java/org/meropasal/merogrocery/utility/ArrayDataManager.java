package org.meropasal.merogrocery.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.meropasal.merogrocery.model.ProductPriceModel;

import java.util.ArrayList;

public class ArrayDataManager {
    private static final String PREF_NAME = "MyAppPrefs";
    private static final String PRODUCT_PRICE_KEY = "productPrices";

    public static void saveProductPrices(Context context, ArrayList<ProductPriceModel.Message.ProductPrices> arrProductPrices) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String arrProductPrice = gson.toJson(arrProductPrices);
        editor.putString(PRODUCT_PRICE_KEY, arrProductPrice);
        editor.apply();
    }

}
