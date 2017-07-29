package vivz.newsapp;

/**
 * Created by user on 22-07-2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import vivz.newsapp.DbUtils.DbHelper;
import vivz.newsapp.Model.NewsDetails;

public class RefreshTasks {

    public static final String ACTION_REFRESH = "refresh";
    private static String TAG = "RefreshTasks";
   // SQLiteDatabase db;

    public static void refreshArticles(Context context) throws JSONException {
       // ArrayList<NewsDetails> newsDetailses =new ArrayList<NewsDetails> ;
       // URL url = NetworkUtils.makeURL();


        NetworkUtils utils = new NetworkUtils();
        URL url = utils.makeURL("the-next-web", "latest", "17212abb471447c1bc7bcb493fd44d8c");
        String data = utils.getReponseFromHttpUrl(url);


        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();
        ArrayList<NewsDetails> newsDetails=   NetworkUtils.parseJSON(data);
        DbHelper.deleteData(db);

        DbHelper.insertData(db, newsDetails);
        db.close();


//        try {
//            Log.e(TAG,"executing db transactions");
//
//         //   Cursor cursor=DbHelper.getAll(db);
//
//
//        } catch (Exception e) {
//            Log.e(TAG,"in catchtransactions",e);
//            e.printStackTrace();
//
//        }finally {
//            Log.e(TAG,"db closed");
//
//        }


    }
}