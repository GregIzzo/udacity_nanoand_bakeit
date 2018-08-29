package com.example.android.udacity_nanoand_bakeit.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeJSON {
    private static JSONArray dataJSONArray = null;//array of recipe objects
    private static JSONObject currentRecipe = null;// currently selected recipe
    private static String rawDataString = null;
    private static boolean showingIngredients = false;
    private static int currentRecipeStepNum =0;
    private static int currentRecipeListPosition =0;//offset into data for current recipe
    private static String TAG = "RecipeJSON";

    private static String INGREDIENT_QTY = "quantity";
    private static String INGREDIENT_MEASURE = "measure";
    private static String INGREDIENT_NAME = "ingredient";

    public static JSONArray getData() {
        return dataJSONArray;
    }

    public static void setDataString(String jsonString) {
        //Expect jsonString to be a JSONArray:
        //  [ {JsonObject}, {JsonObject},....]
        // where each JsonObject is a recipe Data object
        dataJSONArray = null;
        //FORCE INTERNAL DATA TO BE USED -- FOR TESTING
        //Log.d(TAG, "setDataString: =============== INTERNAL DATA IS BEING USED BY FORCE ==========");
        //jsonString = null;
        // END FORCE INTERNAL DATA
        if (jsonString == null){
            Log.d(TAG, "!!!!! !!!!! !!!!! setDataString: USING STATIC DATA !!!!!!!!!!");
            jsonString = staticData;
            rawDataString = jsonString;
        }
         try {

            dataJSONArray = new JSONArray(jsonString);
             rawDataString = jsonString;
             Log.d(TAG, "setDataString: ARRAY SIZE=" + dataJSONArray.length());
        } catch (JSONException e) {
               Log.i(TAG, "setDataString: ^^^^^^^^^ ERROR json array could not be created from incoming data");
            e.printStackTrace();
        }

    }
    public static String getRawDataString(){
       return rawDataString;
    }
    public static int getCurrentRecipeListPosition(){
        return currentRecipeListPosition;
    }
    public static int setCurrentRecipe(int position){
        // pass in list offset to pick the current recipe
        // Return value: 1=okay/done, 0=not done/error
        currentRecipeListPosition = position;
        if (dataJSONArray == null) return 0 ;
        if (position >= dataJSONArray.length()) return 0;
        try {
            currentRecipe = dataJSONArray.getJSONObject(position);
            currentRecipeStepNum = 0;
            setShowingIngredients(true);
            return 1;
        } catch (JSONException e){
            //a problem happened
            Log.d(TAG, "setCurrentRecipe: problem:"+ e.getLocalizedMessage());
            return 0;
        }
    }
    public static int setCurrentRecipe(JSONObject jsonObject){
        //ignore dataJSONArray. This routine is intended to setup the current recipe from
        // a jsonobject built from json string data stored in SharedPreferences
        if (jsonObject == null) return 0;

        currentRecipe = jsonObject;
        currentRecipeStepNum = 0;
        setShowingIngredients(true);
        try {
            currentRecipeListPosition = findRecipeOffset(jsonObject.getInt("id"));
        } catch (JSONException e) {
            e.printStackTrace();
            currentRecipeListPosition =-1;
        }

        return 1;
    }
    public static int findRecipeOffset(int recipeID){
        if (dataJSONArray == null) return -1;
        int rcount = dataJSONArray.length();
        for (int i=0; i<rcount; i++){
            JSONObject jsonObject = null;
            try {
                jsonObject = dataJSONArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jsonObject != null){
                if (jsonObject.has("id")){
                    try {
                        if (recipeID == jsonObject.getInt("id")){
                            //got it
                            return i;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return -1;
    }
    public static JSONObject getCurrentRecipe(){
        return currentRecipe;
    }
    public static void setCurrentRecipeStep(int whatStep) {
        currentRecipeStepNum =0;
        if (dataJSONArray != null && currentRecipe != null){
            if (whatStep < getCurrRecipeStepCount()){
                currentRecipeStepNum = whatStep;
            }
        }
    }
    public static int getCurrentRecipeStepNum(){
        return currentRecipeStepNum;
    }
    public static boolean isShowingIngredients() { return showingIngredients;}
    public static void setShowingIngredients(boolean showingIngredientsState) { showingIngredients = showingIngredientsState;}
    public static ArrayList<String> getCurrentRecipeIngredientsArray (){
        if (currentRecipe == null) return null;
       return getIngredientsArray(currentRecipe);
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
    /* ******************************************************************************* */
    private static String getCurrStringProperty( String sName){
        if (dataJSONArray == null) return "";
        if (currentRecipe == null) return "";
        try {
            return currentRecipe.getString(sName);
        } catch (JSONException e){
            Log.i(TAG, "getCurrStringProperty: failed to get string ("+sName+")  from currentRecipe");
            return "";
        }
    }
    private static int getCurrIntProperty( String sName){
        if (dataJSONArray == null) return -1;
        if (currentRecipe == null) return -1;
        try {
            return currentRecipe.getInt(sName);
        } catch (JSONException e){
            Log.i(TAG, "getCurrIntProperty: failed to get int ("+sName+")   from currentRecipe");
            return -1;
        }
    }
    public static String getCurrIngredientsString(){
        if (dataJSONArray == null) return "";
        if (currentRecipe == null) return  "";
        return getIngredientsString(currentRecipe);
    }
    public static String getCurrRecipeId(){
        //get ID from CurrentRecipe
        return getCurrStringProperty("id");
    }
    public static String getCurrRecipeName(){
        //get Name from currentRecipe
        return getCurrStringProperty("name");
    }
    public static String getCurrRecipeImage() {
        return getCurrStringProperty("image");
    }
    public static int getCurrRecipeServings(){
        return getCurrIntProperty( "servings");
    }
    public static int getCurrRecipeStepCount(){
        if (dataJSONArray == null) return 0;
        if (currentRecipe == null) return  0 ;
        JSONArray recipeSteps = null;
        try{
            recipeSteps = currentRecipe.getJSONArray("steps");
            return recipeSteps.length();
        } catch (JSONException e){
            Log.d(TAG, "getCurrRecipeStepCount: JSON ERROR:" + e.getLocalizedMessage());
            return 0;
        }

    }
    public static JSONArray getCurrRecipeSteps(){
        if (dataJSONArray == null) return new JSONArray();
        if (currentRecipe == null) return  new JSONArray();
        try{
            return currentRecipe.getJSONArray("steps");
        } catch (JSONException e){
            Log.d(TAG, "getCurrRecipeSteps: JSON ERROR:" + e.getLocalizedMessage());
            return new JSONArray();
        }
    }
    public static int getCurrRecipeStepId(int stepNum){
        Log.d(TAG, "getCurrRecipeStepId: stepnum=" + stepNum+" ["+getCurrRecipeStepInt(stepNum, "id")+"]");
        return getCurrRecipeStepInt(stepNum, "id");
     }
    public static String getCurrRecipeStepShortDescription(int stepNum){
        return getCurrRecipeStepString(stepNum, "shortDescription");
    }
    public static String getCurrRecipeStepDescription(int stepNum){
        return getCurrRecipeStepString(stepNum, "description");
    }
    public static String getCurrRecipeStepVideoURL(int stepNum){
        return getCurrRecipeStepString(stepNum, "videoURL");
    }
    public static String getCurrRecipeStepThumbnailURL(int stepNum){
        return getCurrRecipeStepString(stepNum, "thumbnailURL");
    }

    public static String getCurrRecipeStepString(int stepNum, String key){
        if (dataJSONArray == null) return "";
        if (currentRecipe == null) return "";
        try{
            JSONArray steps = currentRecipe.getJSONArray("steps");
            if (stepNum <= steps.length()){
                return steps.getJSONObject(stepNum).getString(key);
            } else {
                return "";
            }
        } catch (JSONException e){
            Log.d(TAG, "getCurrRecipeStepString: stepnum:"+stepNum+" key:"+key+" JSON ERROR:" + e.getLocalizedMessage());
            return "";
        }

    }
    public static int getCurrRecipeStepInt(int stepNum, String key){
        if (dataJSONArray == null) return -1;
        if (currentRecipe == null) return -1;
        try{
            JSONArray steps = currentRecipe.getJSONArray("steps");
            if (stepNum <= steps.length()){
                return steps.getJSONObject(stepNum).getInt(key);
            } else {
                return -1;
            }
        } catch (JSONException e){
            Log.d(TAG, "getCurrRecipeStepInt: stepnum:"+stepNum+" key:"+key+" JSON ERROR:" + e.getLocalizedMessage());
            return -1;
        }

    }
    public static String getCurrRecipeIngredientCompleteString(int offset){
        if (dataJSONArray == null) return "";
        if (currentRecipe == null) return "";
        try{
            JSONArray steps = currentRecipe.getJSONArray("ingredients");
            if (offset <= steps.length()){
                //Compose output string from parts

                return steps.getJSONObject(offset).getString(INGREDIENT_QTY) + " " +
                        steps.getJSONObject(offset).getString(INGREDIENT_MEASURE) + " " +
                        steps.getJSONObject(offset).getString(INGREDIENT_NAME) ;
            } else {
                return "";
            }
        } catch (JSONException e){
            Log.d(TAG, "getCurrRecipeIngredientCompleteString: offset:"+offset+"  JSON ERROR:" + e.getLocalizedMessage());
            return "";
        }

    }

    public static String getCurrRecipeIngredientString(int offset, String key){
        if (dataJSONArray == null) return "";
        if (currentRecipe == null) return "";
        try{
            JSONArray steps = currentRecipe.getJSONArray("ingredients");
            if (offset <= steps.length()){
                return steps.getJSONObject(offset).getString(key);
            } else {
                return "";
            }
        } catch (JSONException e){
            Log.d(TAG, "getCurrRecipeIngredientString: offset:"+offset+" key:"+key+" JSON ERROR:" + e.getLocalizedMessage());
            return "";
        }

    }

    public static int getCurrRecipeIngredientInt(int offset, String key){
        if (dataJSONArray == null) return -1;
        if (currentRecipe == null) return -1;
        try {
            JSONArray ingredientList = currentRecipe.getJSONArray("ingredients");
            if (offset <= ingredientList.length()){
                return ingredientList.getJSONObject(offset).getInt(key);
            } else {
                return -1;
            }
        } catch (JSONException e){
            Log.d(TAG, "getCurrRecipeIngredientInt: offset:"+offset+" key:"+key+" JSON ERROR:" + e.getLocalizedMessage());
            return -1;
        }
    }
    public static int getCurrRecipeIngredientsCount(){
        if (dataJSONArray == null) return 0;
        if (currentRecipe == null) return 0;
        try {
            JSONArray ingredientList = currentRecipe.getJSONArray("ingredients");
           return ingredientList.length();
        } catch (JSONException e){
            Log.d(TAG, "getCurrRecipeIngredientsCount:  JSON ERROR:" + e.getLocalizedMessage());
            return 0;
        }

    }
    /* ******************************************************************************* */

    public static int size(){
        if (dataJSONArray == null) return 0;
        return dataJSONArray.length();
    }
    public static String getIngredientsString(int position){
        if (dataJSONArray == null) return "No Data";
        if (position >= dataJSONArray.length()) return "No Data";
        try {
            JSONObject jobj =  dataJSONArray.getJSONObject(position);
           return getIngredientsString(jobj);
        } catch (JSONException e){
            Log.d(TAG, "getIngredientsString: failed to create ingredient string");
            return "No Data";
        }
    }
    public static String getIngredientsString(JSONObject rObj){
        try {
            JSONArray ingList = rObj.getJSONArray("ingredients");
            int len = ingList.length();
            String ingrString = "";
            for (int i = 0; i < len; i++) {
                JSONObject ingObj = ingList.getJSONObject(i);
                if (i == 0) {
                    ingrString = ingObj.getString("ingredient");
                } else {
                    ingrString = ingrString + ", " + ingObj.getString("ingredient");
                }
            }
            return ingrString;
        } catch (JSONException e){
            Log.d(TAG, "getIngredientsString: failed to create ingredient string");
            return "No Data";

        }
    }
    public static ArrayList<String> getIngredientsArray(JSONObject rObj){
        ArrayList<String> myArrayList;
        myArrayList = new ArrayList<>();
        try {
            JSONArray ingList = rObj.getJSONArray("ingredients");
            int len = ingList.length();
            String ingrString = "";
            for (int i = 0; i < len; i++) {
                JSONObject ingObj = ingList.getJSONObject(i);
                myArrayList.add(ingObj.getString("ingredient"));
                }

            return myArrayList;
        } catch (JSONException e){
            Log.d(TAG, "getIngredientsString: failed to create ingredient string");
            return null;

        }
    }
    private static String staticData = "[\n" +
            "  {\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"Nutella Pie\",\n" +
            "    \"ingredients\": [\n" +
            "      {\n" +
            "        \"quantity\": 2,\n" +
            "        \"measure\": \"CUP\",\n" +
            "        \"ingredient\": \"Graham Cracker crumbs\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 6,\n" +
            "        \"measure\": \"TBLSP\",\n" +
            "        \"ingredient\": \"unsalted butter, melted\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 0.5,\n" +
            "        \"measure\": \"CUP\",\n" +
            "        \"ingredient\": \"granulated sugar\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 1.5,\n" +
            "        \"measure\": \"TSP\",\n" +
            "        \"ingredient\": \"salt\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 5,\n" +
            "        \"measure\": \"TBLSP\",\n" +
            "        \"ingredient\": \"vanilla\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 1,\n" +
            "        \"measure\": \"K\",\n" +
            "        \"ingredient\": \"Nutella or other chocolate-hazelnut spread\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 500,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"Mascapone Cheese(room temperature)\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 1,\n" +
            "        \"measure\": \"CUP\",\n" +
            "        \"ingredient\": \"heavy cream(cold)\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 4,\n" +
            "        \"measure\": \"OZ\",\n" +
            "        \"ingredient\": \"cream cheese(softened)\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"steps\": [\n" +
            "      {\n" +
            "        \"id\": 0,\n" +
            "        \"shortDescription\": \"Recipe Introduction\",\n" +
            "        \"description\": \"Recipe Introduction\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4\",\n" +
            "        \"thumbnailURL\": \"https://www.recipeboy.com/wp-content/uploads/2016/09/No-Bake-Nutella-Pie.jpg\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 1,\n" +
            "        \"shortDescription\": \"Starting prep\",\n" +
            "        \"description\": \"1. Preheat the oven to 350°F. Butter a 9\\\" deep dish pie pan.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 2,\n" +
            "        \"shortDescription\": \"Prep the cookie crust.\",\n" +
            "        \"description\": \"2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 3,\n" +
            "        \"shortDescription\": \"Press the crust into baking form.\",\n" +
            "        \"description\": \"3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 4,\n" +
            "        \"shortDescription\": \"Start filling prep\",\n" +
            "        \"description\": \"4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd97a_1-mix-marscapone-nutella-creampie/1-mix-marscapone-nutella-creampie.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 5,\n" +
            "        \"shortDescription\": \"Finish filling prep\",\n" +
            "        \"description\": \"5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 6,\n" +
            "        \"shortDescription\": \"Finishing Steps\",\n" +
            "        \"description\": \"6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"servings\": 8,\n" +
            "    \"image\": \"https://www.recipeboy.com/wp-content/uploads/2016/09/No-Bake-Nutella-Pie.jpg\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 2,\n" +
            "    \"name\": \"Brownies\",\n" +
            "    \"ingredients\": [\n" +
            "      {\n" +
            "        \"quantity\": 350,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"Bittersweet chocolate (60-70% cacao)\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 226,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"unsalted butter\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 300,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"granulated sugar\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 100,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"light brown sugar\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 5,\n" +
            "        \"measure\": \"UNIT\",\n" +
            "        \"ingredient\": \"large eggs\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 1,\n" +
            "        \"measure\": \"TBLSP\",\n" +
            "        \"ingredient\": \"vanilla extract\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 140,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"all purpose flour\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 40,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"cocoa powder\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 1.5,\n" +
            "        \"measure\": \"TSP\",\n" +
            "        \"ingredient\": \"salt\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 350,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"semisweet chocolate chips\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"steps\": [\n" +
            "      {\n" +
            "        \"id\": 0,\n" +
            "        \"shortDescription\": \"Recipe Introduction\",\n" +
            "        \"description\": \"Recipe Introduction\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc33_-intro-brownies/-intro-brownies.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 1,\n" +
            "        \"shortDescription\": \"Starting prep\",\n" +
            "        \"description\": \"1. Preheat the oven to 350?F. Butter the bottom and sides of a 9\\\"x13\\\" pan.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 2,\n" +
            "        \"shortDescription\": \"Melt butter and bittersweet chocolate.\",\n" +
            "        \"description\": \"2. Melt the butter and bittersweet chocolate together in a microwave or a double boiler. If microwaving, heat for 30 seconds at a time, removing bowl and stirring ingredients in between.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc43_1-melt-choclate-chips-and-butter-brownies/1-melt-choclate-chips-and-butter-brownies.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 3,\n" +
            "        \"shortDescription\": \"Add sugars to wet mixture.\",\n" +
            "        \"description\": \"3. Mix both sugars into the melted chocolate in a large mixing bowl until mixture is smooth and uniform.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 4,\n" +
            "        \"shortDescription\": \"Mix together dry ingredients.\",\n" +
            "        \"description\": \"4. Sift together the flour, cocoa, and salt in a small bowl and whisk until mixture is uniform and no clumps remain. \",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc9e_4-sift-flower-add-coco-powder-salt-brownies/4-sift-flower-add-coco-powder-salt-brownies.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 5,\n" +
            "        \"shortDescription\": \"Add eggs.\",\n" +
            "        \"description\": \"5. Crack 3 eggs into the chocolate mixture and carefully fold them in. Crack the other 2 eggs in and carefully fold them in. Fold in the vanilla.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc62_2-mix-egss-with-choclate-butter-brownies/2-mix-egss-with-choclate-butter-brownies.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 6,\n" +
            "        \"shortDescription\": \"Add dry mixture to wet mixture.\",\n" +
            "        \"description\": \"6. Dump half of flour mixture into chocolate mixture and carefully fold in, just until no streaks remain. Repeat with the rest of the flour mixture. Fold in the chocolate chips.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdcc8_5-mix-wet-and-cry-batter-together-brownies/5-mix-wet-and-cry-batter-together-brownies.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 7,\n" +
            "        \"shortDescription\": \"Add batter to pan.\",\n" +
            "        \"description\": \"7. Pour the batter into the prepared pan and bake for 30 minutes.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdcf4_8-put-brownies-in-oven-to-bake-brownies/8-put-brownies-in-oven-to-bake-brownies.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 8,\n" +
            "        \"shortDescription\": \"Remove pan from oven.\",\n" +
            "        \"description\": \"8. Remove the pan from the oven and let cool until room temperature. If you want to speed this up, you can feel free to put the pan in a freezer for a bit.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 9,\n" +
            "        \"shortDescription\": \"Cut and serve.\",\n" +
            "        \"description\": \"9. Cut and serve.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdcf9_9-final-product-brownies/9-final-product-brownies.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"servings\": 8,\n" +
            "    \"image\": \"https://images.media-allrecipes.com/userphotos/560x315/3850414.jpg\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 3,\n" +
            "    \"name\": \"Yellow Cake\",\n" +
            "    \"ingredients\": [\n" +
            "      {\n" +
            "        \"quantity\": 400,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"sifted cake flour\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 700,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"granulated sugar\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 4,\n" +
            "        \"measure\": \"TSP\",\n" +
            "        \"ingredient\": \"baking powder\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 1.5,\n" +
            "        \"measure\": \"TSP\",\n" +
            "        \"ingredient\": \"salt\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 2,\n" +
            "        \"measure\": \"TBLSP\",\n" +
            "        \"ingredient\": \"vanilla extract, divided\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 8,\n" +
            "        \"measure\": \"UNIT\",\n" +
            "        \"ingredient\": \"egg yolks\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 323,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"whole milk\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 961,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"unsalted butter, softened and cut into 1 in. cubes\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 6,\n" +
            "        \"measure\": \"UNIT\",\n" +
            "        \"ingredient\": \"egg whites\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 283,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"melted and cooled bittersweet or semisweet chocolate\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"steps\": [\n" +
            "      {\n" +
            "        \"id\": 0,\n" +
            "        \"shortDescription\": \"Recipe Introduction\",\n" +
            "        \"description\": \"Recipe Introduction\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffddf0_-intro-yellow-cake/-intro-yellow-cake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 1,\n" +
            "        \"shortDescription\": \"Starting prep\",\n" +
            "        \"description\": \"1. Preheat the oven to 350°F. Butter the bottoms and sides of two 9\\\" round pans with 2\\\"-high sides. Cover the bottoms of the pans with rounds of parchment paper, and butter the paper as well.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 2,\n" +
            "        \"shortDescription\": \"Combine dry ingredients.\",\n" +
            "        \"description\": \"2. Combine the cake flour, 400 grams (2 cups) of sugar, baking powder, and 1 teaspoon of salt in the bowl of a stand mixer. Using the paddle attachment, beat at low speed until the dry ingredients are mixed together, about one minute\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffde28_1-mix-all-dry-ingredients-yellow-cake/1-mix-all-dry-ingredients-yellow-cake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 3,\n" +
            "        \"shortDescription\": \"Prepare wet ingredients.\",\n" +
            "        \"description\": \"3. Lightly beat together the egg yolks, 1 tablespoon of vanilla, and 80 grams (1/3 cup) of the milk in a small bowl.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffde36_2-mix-all-wet-ingredients-yellow-cake/2-mix-all-wet-ingredients-yellow-cake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 4,\n" +
            "        \"shortDescription\": \"Add butter and milk to dry ingredients.\",\n" +
            "        \"description\": \"4. Add 283 grams (20 tablespoons) of butter and 243 grams (1 cup) of milk to the dry ingredients. Beat at low speed until the dry ingredients are fully moistened, using a spatula to help with the incorporation if necessary. Then beat at medium speed for 90 seconds.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 5,\n" +
            "        \"shortDescription\": \"Add egg mixture to batter.\",\n" +
            "        \"description\": \"5. Scrape down the sides of the bowl. Add the egg mixture to the batter in three batches, beating for 20 seconds each time and then scraping down the sides.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffde36_2-mix-all-wet-ingredients-yellow-cake/2-mix-all-wet-ingredients-yellow-cake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 6,\n" +
            "        \"shortDescription\": \"Pour batter into pans.\",\n" +
            "        \"description\": \"6. Pour the mixture in two even batches into the prepared pans. Bake for 25 minutes or until a tester comes out of the cake clean. The cake should only start to shrink away from the sides of the pan after it comes out of the oven.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffde43_5-add-mixed-batter-to-baking-pans-yellow-cake/5-add-mixed-batter-to-baking-pans-yellow-cake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 8,\n" +
            "        \"shortDescription\": \"Begin making buttercream.\",\n" +
            "        \"description\": \"8. Once the cake is cool, it's time to make the buttercream. You'll start by bringing an inch of water to a boil in a small saucepan. You'll want to use a saucepan that is small enough that when you set the bowl of your stand mixer in it, the bowl does not touch the bottom of the pot.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 9,\n" +
            "        \"shortDescription\": \"Prepare egg whites.\",\n" +
            "        \"description\": \"9. Whisk together the egg whites and remaining 300 grams (1.5 cups) of sugar in the bowl of a stand mixer until combined. Set the bowl over the top of the boiling water and continue whisking the egg white mixture until it feels hot to the touch and the sugar is totally dissolved (if you have a reliable thermometer, it should read 150°F). \",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/5901299d_6-srir-egg-whites-for-frosting-yellow-cake/6-srir-egg-whites-for-frosting-yellow-cake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 10,\n" +
            "        \"shortDescription\": \"Beat egg whites to stiff peaks.\",\n" +
            "        \"description\": \"10. Remove the bowl from the pot, and using the whisk attachment of your stand mixer, beat the egg white mixture on medium-high speed until stiff peaks form and the outside of the bowl reaches room temperature.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 11,\n" +
            "        \"shortDescription\": \"Add butter to egg white mixture.\",\n" +
            "        \"description\": \"11. Keeping the mixer at medium speed, add the butter one piece at a time to the egg white mixture, waiting 5 to 10 seconds between additions. If the mixture starts to look curdled, just keep beating it! It will come together once it has been mixed enough and has enough butter added. \",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/590129a3_9-mix-in-butter-for-frosting-yellow-cake/9-mix-in-butter-for-frosting-yellow-cake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 12,\n" +
            "        \"shortDescription\": \"Finish buttercream icing.\",\n" +
            "        \"description\": \"12. With the mixer still running, pour the melted chocolate into the buttercream. Then add the remaining tablespoon of vanilla and 1/2 teaspoon of salt. Beat at high speed for 30 seconds to ensure the buttercream is well-mixed.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/590129a5_10-mix-in-melted-chocolate-for-frosting-yellow-cake/10-mix-in-melted-chocolate-for-frosting-yellow-cake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 13,\n" +
            "        \"shortDescription\": \"Frost cakes.\",\n" +
            "        \"description\": \"13. Frost your cake! Use a serrated knife to cut each cooled cake layer in half (so that you have 4 cake layers). Frost in between the layers, the sides of the cake, and the top of the cake. Then eat it!\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/590129ad_17-frost-all-around-cake-yellow-cake/17-frost-all-around-cake-yellow-cake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"servings\": 8,\n" +
            "    \"image\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 4,\n" +
            "    \"name\": \"Cheesecake\",\n" +
            "    \"ingredients\": [\n" +
            "      {\n" +
            "        \"quantity\": 2,\n" +
            "        \"measure\": \"CUP\",\n" +
            "        \"ingredient\": \"Graham Cracker crumbs\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 6,\n" +
            "        \"measure\": \"TBLSP\",\n" +
            "        \"ingredient\": \"unsalted butter, melted\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 250,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"granulated sugar\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 1,\n" +
            "        \"measure\": \"TSP\",\n" +
            "        \"ingredient\": \"salt\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 4,\n" +
            "        \"measure\": \"TSP\",\n" +
            "        \"ingredient\": \"vanilla,divided\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 680,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"cream cheese, softened\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 3,\n" +
            "        \"measure\": \"UNIT\",\n" +
            "        \"ingredient\": \"large whole eggs\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 2,\n" +
            "        \"measure\": \"UNIT\",\n" +
            "        \"ingredient\": \"large egg yolks\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 250,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"heavy cream\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"steps\": [\n" +
            "      {\n" +
            "        \"id\": 0,\n" +
            "        \"shortDescription\": \"Recipe Introduction\",\n" +
            "        \"description\": \"Recipe Introduction\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdae8_-intro-cheesecake/-intro-cheesecake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 1,\n" +
            "        \"shortDescription\": \"Starting prep.\",\n" +
            "        \"description\": \"1. Preheat the oven to 350°F. Grease the bottom of a 9-inch round springform pan with butter. \",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 2,\n" +
            "        \"shortDescription\": \"Prep the cookie crust.\",\n" +
            "        \"description\": \"2. To assemble the crust, whisk together the cookie crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt for the crust in a medium bowl. Stir in the melted butter and 1 teaspoon of vanilla extract until uniform. \",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdb1d_2-form-crust-to-bottom-of-pan-cheesecake/2-form-crust-to-bottom-of-pan-cheesecake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 3,\n" +
            "        \"shortDescription\": \"Start water bath.\",\n" +
            "        \"description\": \"3. Fill a large roasting pan with a few inches of hot water and place it on the bottom rack of the oven.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 4,\n" +
            "        \"shortDescription\": \"Prebake cookie crust. \",\n" +
            "        \"description\": \"4. Press the cookie mixture into the bottom and slightly up the sides of the prepared pan. Bake for 11 minutes and then let cool.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 5,\n" +
            "        \"shortDescription\": \"Mix cream cheese and dry ingredients.\",\n" +
            "        \"description\": \"5. Beat the cream cheese, remaining 200 grams (1 cup) of sugar, and remaining 1/2 teaspoon salt on medium speed in a stand mixer with the paddle attachment for 3 minutes (or high speed if using a hand mixer). \",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdb3a_3-mix-sugar-salt-together-cheesecake/3-mix-sugar-salt-together-cheesecake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 6,\n" +
            "        \"shortDescription\": \"Add eggs.\",\n" +
            "        \"description\": \"6. Scrape down the sides of the pan. Add in the eggs one at a time, beating each one on medium-low speed just until incorporated. Scrape down the sides and bottom of the bowl. Add in both egg yolks and beat until just incorporated. \",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdb55_4-add-eggs-mix-cheesecake/4-add-eggs-mix-cheesecake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 7,\n" +
            "        \"shortDescription\": \"Add heavy cream and vanilla.\",\n" +
            "        \"description\": \"7. Add the cream and remaining tablespoon of vanilla to the batter and beat on medium-low speed until just incorporated. \",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdb72_5-mix-vanilla-cream-together-cheesecake/5-mix-vanilla-cream-together-cheesecake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 8,\n" +
            "        \"shortDescription\": \"Pour batter in pan.\",\n" +
            "        \"description\": \"8. Pour the batter into the cooled cookie crust. Bang the pan on a counter or sturdy table a few times to release air bubbles from the batter.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdb88_6-add-the-batter-to-the-pan-w-the-crumbs-cheesecake/6-add-the-batter-to-the-pan-w-the-crumbs-cheesecake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 9,\n" +
            "        \"shortDescription\": \"Bake the cheesecake.\",\n" +
            "        \"description\": \"9. Bake the cheesecake on a middle rack of the oven above the roasting pan full of water for 50 minutes. \",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 10,\n" +
            "        \"shortDescription\": \"Turn off oven and leave cake in.\",\n" +
            "        \"description\": \"10. Turn off the oven but keep the cheesecake in the oven with the door closed for 50 more minutes.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 11,\n" +
            "        \"shortDescription\": \"Remove from oven and cool at room temperature.\",\n" +
            "        \"description\": \"11. Take the cheesecake out of the oven. It should look pale yellow or golden on top and be set but still slightly jiggly. Let it cool to room temperature. \",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 12,\n" +
            "        \"shortDescription\": \"Final cooling and set.\",\n" +
            "        \"description\": \"12. Cover the cheesecake with plastic wrap, not allowing the plastic to touch the top of the cake, and refrigerate it for at least 8 hours. Then it's ready to serve!\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdbac_9-finished-product-cheesecake/9-finished-product-cheesecake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"servings\": 8,\n" +
            "    \"image\": \"https://food.fnr.sndimg.com/content/dam/images/food/fullset/2008/9/8/1/Cheesecake_Classic.jpg.rend.hgtvcom.616.462.suffix/1371587222109.jpeg\"\n" +
            "  }\n" +
            "]\n" +
            "\n";

}
