package vivz.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.net.URL;
import java.util.List;

/**
 * Created by user on 22-07-2017.
 */

public class AsyncResponse extends AsyncTaskLoader<String> {


    Context context;
    AsyncData asyncData;

    public AsyncResponse(Context context, AsyncData asyncData) {
        super(context);
        this.context = context;
        this.asyncData = asyncData;
    }


    @Override
    protected void onStartLoading() {

    }

    @Override
    public String loadInBackground() {
        NetworkUtils utils = new NetworkUtils();
        URL url = utils.makeURL("the-next-web", "latest", "17212abb471447c1bc7bcb493fd44d8c");
        String res = utils.getReponseFromHttpUrl(url);
        return res;
    }


    @Override
    public void deliverResult(String data) {

        asyncData.getData(data);
    }
}
