package vivz.newsapp;

import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import vivz.newsapp.DbUtils.Contract;
import vivz.newsapp.Model.NewsDetails;


/**
 * Created by user on 12-06-2017.
 */

public class NetworkUtils {

    public static final String TAG = "";
    public static final String GITHUB_BASE_URL = "https://newsapi.org/v1/articles";
    public static final String PARAM_QUERY = "source";
    public static final String PARAM_SORT = "sortBy";
    public static final String API_KEY = "apikey";

    public static URL makeURL(String searchQuery, String sortBy, String api_key) {
        Uri uri = Uri.parse(GITHUB_BASE_URL).buildUpon().appendQueryParameter(PARAM_QUERY, searchQuery)
                .appendQueryParameter(PARAM_SORT, sortBy).appendQueryParameter(API_KEY, api_key).build();

        URL url = null;

        try {
            String urlString = uri.toString();
            Log.d(TAG, "URL:" + urlString);
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getReponseFromHttpUrl(URL url) {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            InputStream in = urlConnection.getInputStream();
            Scanner input = new Scanner(in);

            input.useDelimiter("\\A");
            return (input.hasNext()) ? input.next() : null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return null;
    }


    public static ArrayList<NewsDetails> parseJSON(String data) throws JSONException {


        ArrayList<NewsDetails> newsDetails = new  ArrayList<NewsDetails>();


            Log.e(TAG, data);
       //     progressBar.setVisibility(View.INVISIBLE);
            try {

                JSONObject main = new JSONObject(data);

                JSONArray items = main.getJSONArray("articles");

                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    String author = item.getString("author");
                    String article_url = item.getString("url");
                    String title = item.getString("title");
                    String description = item.getString("description");
                    String image_url = item.getString("urlToImage");
                    //       Log.e(class_name, name);
                    //      result.add(new Repository(name,url,owner));
                    newsDetails.add(new NewsDetails(title, author, description, article_url, image_url));
                }


            } catch (JSONException e) {
                Log.e(TAG, "error", e);
                e.printStackTrace();
            }


        return newsDetails;





        }

    public static ArrayList<NewsDetails> parseJSON(Cursor cursor) throws JSONException {


        ArrayList<NewsDetails> newsDetails = new  ArrayList<NewsDetails>();


        while (cursor.moveToNext()) {
            newsDetails.add(new NewsDetails(
                    cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsApp.COLUMN_NAME_TITLE)),
                    cursor.getInt(cursor.getColumnIndex(Contract.TABLE_NewsApp.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsApp.COLUMN_NAME_AUTHOR)),
                    cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsApp.COLUMN_NAME_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsApp.COLUMN_NAME_IMAGE)),
                    cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsApp.COLUMN_NAME_URL))
            ));
        }
        return newsDetails;





    }


}
