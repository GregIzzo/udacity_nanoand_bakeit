package com.example.android.udacity_nanoand_bakeit.utilities;

/**
 * Created by gizzo_000 on 7/12/2018.
 */
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class NetworkUtils {
    private static  String APIKEY = "";

    private static final String APIKEY_PARAM = "api_key";
    private static final String LANGUAGE_PARAM = "language";
    private static final String LANGUAGE_VALUE = "en-US";
    //private static final String PAGE_PARAM = "page";
    private static final String REGION_PARAM = "region";
    private static final String REGION_VALUE = "US";//Must be Upper Case

    private static String DATAURL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static URL buildDataURL(){
        Uri builtUri = Uri.parse(DATAURL).buildUpon()
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        //based on NetworkUtils.java from Udacity Google Challenge S05.01
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            Log.d("GGG", "getResponseFromHttpUrl:********** ");
            InputStream inStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inStream);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d("GGG", "NetworkUtils. getResponseFromHttpUrl: EXCEPTION:" + e.getMessage());
            return null;
        } finally {

            urlConnection.disconnect();
        }
    }


}
