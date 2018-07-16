package com.example.android.udacity_nanoand_bakeit.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecipeJSON {
    private static JSONArray dataJSONArray = null;
    private static String TAG = "RecipeJSON";

    public static JSONArray getData() {
        return dataJSONArray;
    }
    public static void setDataString(String jsonString) {
        //Expect jsonString to be a JSONArray:
        //  [ {JsonObject}, {JsonObject},....]
        // where each JsonObject is a recipe Data object
        dataJSONArray = null;
         try {

            dataJSONArray = new JSONArray(jsonString);
        } catch (JSONException e) {
               Log.i(TAG, "setDataString: ^^^^^^^^^ ERROR json array could not be created from incoming data");
            e.printStackTrace();
        }

    }
    public static JSONObject getRecipe(int position){
        if (dataJSONArray == null) return null;
        try {
            return dataJSONArray.getJSONObject(position);
        } catch (JSONException e){
            Log.i(TAG, "getRecipe: failed to get JSONObject from position:" + position);
            return null;
        }
    }
    public static String getRecipeId(int position){
        //get ID from object in JSONArray at position
        return getStringProperty(position, "id");
    }
    public static String getRecipeName(int position){
        //get ID from object in JSONArray at position
        return getStringProperty(position, "name");
    }
    public static String getRecipeImage(int position) {
        return getStringProperty(position,"image");
    }
    public static int getRecipeServings(int position){
        return getIntProperty(position, "servings");
    }
    private static String getStringProperty(int position, String sName){
        if (dataJSONArray == null) return "";
        if (position >= dataJSONArray.length()) return "";
        try {
            JSONObject jobj =  dataJSONArray.getJSONObject(position);
            return jobj.getString(sName);
        } catch (JSONException e){
            Log.i(TAG, "getRecipe: failed to get string ("+sName+")  from JSONObject at position:" + position);
            return "";
        }
    }
    private static int getIntProperty(int position, String sName){
        if (dataJSONArray == null) return -1;
        if (position >= dataJSONArray.length()) return -1;
        try {
            JSONObject jobj =  dataJSONArray.getJSONObject(position);
            return jobj.getInt(sName);
        } catch (JSONException e){
            Log.i(TAG, "getRecipe: failed to get string ("+sName+")  from JSONObject at position:" + position);
            return -1;
        }
    }
    public static int size(){
        if (dataJSONArray == null) return 0;
        return dataJSONArray.length();
    }

}
