package com.example.android.udacity_nanoand_bakeit.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.udacity_nanoand_bakeit.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class PrefHandler {
    static SharedPreferences bakeItPref;

    public static JSONObject getCurrentRecipe(Context context){
        readPrefs(context);//make sure we've read prefs
        String jsonString = bakeItPref.getString("currentrecipe", "");
        JSONObject currRecipe = null;
        try {
            currRecipe = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return currRecipe;
    }
    public static int getCurrentRecipeOffset(Context context){
        readPrefs(context);//make sure we've read prefs
        int offset = bakeItPref.getInt("currentrecipeoffset",-1);
        return offset;
    }
    /*

     */
    public static void saveCurrentRecipeData(Context context, JSONObject recipe,  int recipeOffset){
        setCurrentRecipe( context,  recipe);
        setCurrentRecipeOffset( context,   recipeOffset);
    }
    public static void setCurrentRecipe(Context context, JSONObject recipe){
        readPrefs(context);
        // use Editor to update prefs
        String recipeString = recipe.toString();
        SharedPreferences.Editor editor = bakeItPref.edit();
        editor.putString("currentrecipe", recipeString);
        editor.commit();
    }
    public static void setCurrentRecipeOffset(Context context,  int recipeOffset){
        readPrefs(context);
        // use Editor to update prefs
        SharedPreferences.Editor editor = bakeItPref.edit();
        editor.putInt("currentrecipeoffset", recipeOffset);
        editor.commit();
    }

    private static void readPrefs(Context context){
        if (bakeItPref == null) bakeItPref= context.getSharedPreferences("BakeItPrefs", Context.MODE_PRIVATE);
    }
}
