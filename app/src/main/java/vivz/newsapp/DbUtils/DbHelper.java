package vivz.newsapp.DbUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import vivz.newsapp.Model.NewsDetails;


/**
 * Created by user on 22-07-2017.
 */

public class DbHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "news.db";
    private static final String TAG = "dbhelper";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "Table created");
        String queryString = "CREATE TABLE " + Contract.TABLE_NewsApp.TABLE_NAME + " (" +
                Contract.TABLE_NewsApp.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.TABLE_NewsApp.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                Contract.TABLE_NewsApp.COLUMN_NAME_AUTHOR + " DATE, " +

                //added two new columns for storing category and done/not_done
                Contract.TABLE_NewsApp.COLUMN_NAME_IMAGE + " TEXT, " +
                Contract.TABLE_NewsApp.COLUMN_NAME_URL + " TEXT, " +
                Contract.TABLE_NewsApp.COLUMN_NAME_TITLE + " TEXT" +
                "); ";

        Log.e(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public static void insertData(SQLiteDatabase db, ArrayList<NewsDetails> newsDetails) {
        Log.e(TAG, "in insert");
        db.beginTransaction();
        try {
            for (NewsDetails a : newsDetails) {
                ContentValues cv = new ContentValues();
                cv.put(Contract.TABLE_NewsApp.COLUMN_NAME_TITLE, a.getTitle());
                cv.put(Contract.TABLE_NewsApp.COLUMN_NAME_AUTHOR, a.getAuthor());
                cv.put(Contract.TABLE_NewsApp.COLUMN_NAME_DESCRIPTION, a.getDescription());
                cv.put(Contract.TABLE_NewsApp.COLUMN_NAME_IMAGE, a.getImageUrl());
                cv.put(Contract.TABLE_NewsApp.COLUMN_NAME_URL, a.getUrl());
                db.insert(Contract.TABLE_NewsApp.TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "error" + e);
        } finally {
            db.endTransaction();
            //          db.close();
        }
        Log.e(TAG, "after insert");

    }

    public static void deleteData(SQLiteDatabase db) {

          db.beginTransaction();
        Log.e(TAG, "in delete");

        db.delete(Contract.TABLE_NewsApp.TABLE_NAME, null, null);
        Log.e(TAG, "after delete");
        db.setTransactionSuccessful();
        db.endTransaction();
        //   db.close();

        Log.e(TAG,"getting all data");
       Cursor c= getAll(db);

        while(c.moveToNext()){
            Log.e(TAG,"inside");
           Log.e(TAG,c.getString(1));
            Log.e(TAG,c.getString(2));
        }
        Log.e(TAG,"after getting all data");
    }


    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                Contract.TABLE_NewsApp.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
//        db.setTransactionSuccessful();
//        db.endTransaction();
        //  db.close();
        return cursor;

    }


}
