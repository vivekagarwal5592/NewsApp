package vivz.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.util.ArrayList;

import vivz.newsapp.DbUtils.Contract;
import vivz.newsapp.DbUtils.DbHelper;
import vivz.newsapp.Model.NewsDetails;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    //  private ArrayList<NewsDetails> newsdetails = new ArrayList<NewsDetails>();
    private ArrayList<NewsDetails> newsDetails = new ArrayList<NewsDetails>();
    private final Context context = this;

    private ProgressBar progressBar;
    private AsyncTask asyncTask;
    private static String TAG = "MainActivity";
    private Cursor cursor;
    private SQLiteDatabase db;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        sharedPreferences = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean("first_install", true)) {
            Log.e(TAG,"First install");
            refresh_data();
            editor.putBoolean("first_install", false);
            editor.commit();
        } else {
            Log.e(TAG,"Not First install");
            refreshUI();
        }


        ScheduleUtilities.scheduleRefresh(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button_brown, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.search:
                refresh_data();
                adapter.swapCursor(newsDetails);


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public void refreshUI() {


        db = new DbHelper(MainActivity.this).getReadableDatabase();
        cursor = DbHelper.getAll(db);

        newsDetails = new ArrayList<>();

        //  ArrayList<NewsDetails> newsDetail= new  ArrayList<NewsDetails>();
        while (cursor.moveToNext()) {
            //   Log.e(TAG,"in cursor");
            newsDetails.add(new NewsDetails(
                    cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsApp.COLUMN_NAME_TITLE)),
                    cursor.getInt(cursor.getColumnIndex(Contract.TABLE_NewsApp.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsApp.COLUMN_NAME_AUTHOR)),
                    cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsApp.COLUMN_NAME_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsApp.COLUMN_NAME_URL)),
                    cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsApp.COLUMN_NAME_IMAGE))
            ));
        }

        db.close();

        //   Log.e(TAG,"before");
        for (NewsDetails n : newsDetails) {
            //   Log.e(TAG,"in");
            //     Log.e(TAG,n.getAuthor());
        }

        adapter = new RecyclerAdapter(newsDetails, new RecyclerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int clickedItemIndex) {
                String url = newsDetails.get(clickedItemIndex).getUrl();
                //     Log.e(TAG, String.format("Url %s", url));

                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.container);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


    }

    public void refresh_data() {

        AsyncResponse asyncResponse = new AsyncResponse(context, new AsyncData() {
            @Override
            public void getData(String data) {

                //   Log.e(TAG,data);
                newsDetails = null;
                try {
                    newsDetails = NetworkUtils.parseJSON(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                db = new DbHelper(MainActivity.this).getWritableDatabase();
                DbHelper.deleteData(db);
                DbHelper.insertData(db, newsDetails);
                db.close();
                refreshUI();
            }
        });

        asyncResponse.forceLoad();
        //   asyncResponse.execute();


    }

}
