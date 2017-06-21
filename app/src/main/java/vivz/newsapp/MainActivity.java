package vivz.newsapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<NewsDetails> newsdetails = new ArrayList<NewsDetails>();
    private final Context context = this;
    private Background background;
private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        execute_background();


    }

    public void execute_background() {
        background = new Background();
        background.execute();
    }


    public class Background extends AsyncTask<URL, Void, String> {

        Context context;

        public Background(Context context) {
            this.context = context;
        }

        public Background() {
        }

        public static final String class_name = "Background";

        @Override
        protected void onPreExecute() {

            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            NetworkUtils utils = new NetworkUtils();
            URL url = utils.makeURL("the-next-web", "latest", "17212abb471447c1bc7bcb493fd44d8c");
            String res = utils.getReponseFromHttpUrl(url);


//
            return res;

        }

        @Override
        protected void onPostExecute(String searchresult) {
            Log.e(class_name, searchresult);
            progressBar.setVisibility(View.INVISIBLE);
            try {

                JSONObject main = new JSONObject(searchresult);

                JSONArray items = main.getJSONArray("articles");

                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    String name = item.getString("author");
                    String article_url = item.getString("url");
                    String title = item.getString("title");
                    String description = item.getString("description");
                    Log.e(class_name, name);
                    //      result.add(new Repository(name,url,owner));
                    newsdetails.add(new NewsDetails(name, article_url, title, description));
                }


                recyclerView = (RecyclerView) findViewById(R.id.container);
                adapter = new RecyclerAdapter(newsdetails);
                layoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                Log.e(class_name, "error", e);
                e.printStackTrace();
            }


            //  TextView displayJSON = (TextView) findViewById(R.id.displayJSON);
            // displayJSON.setText(searchresult);

        }
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
                execute_background();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

}
