package com.example.adams.gymh;

/**
 * Created by adams on 09.01.2017.
 */

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.model.CompactRecipe;
import com.fatsecret.platform.model.Food;
import com.fatsecret.platform.services.FoodService;
import com.fatsecret.platform.services.RecipeService;
import com.fatsecret.platform.services.Response;

import java.util.List;

public class FoodSearch {
    String key = "3b7865bd623149fba79edc7687e07a0e";
    String secret = "6f148652181b4a11aeaa2d3bb5a608dc";

    public FoodSearch() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    public List<CompactFood> searchForFoods(String q) {

        FoodService foodService;
        Response<CompactFood> response=null;

        foodService = new FoodService(key, secret);
response=foodService.searchFoods(q);
        return response.getResults();

    }

    public Food getFood(Long id) {
        FoodService foodService = new FoodService(key, secret);
        return foodService.getFood(id);
    }

}