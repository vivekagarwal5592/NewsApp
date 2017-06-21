package vivz.newsapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 16-06-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    ArrayList<NewsDetails> newsDetails = new ArrayList<>();
    public static final String class_name = "Recycler Adapter";
//    public RecyclerAdapter(NewsDetails newsDetails) {
//
//    }


    public RecyclerAdapter(ArrayList<NewsDetails> newsDetails) {
        this.newsDetails = newsDetails;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_news_box, parent, false);
        Log.e(class_name, "In onCreateViewHolder");
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;


//        recyclerView  = (RecyclerView)layout.findViewById(R.id.container);
//
//        return layout;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Log.e(class_name, "onBindViewHolder");
        holder.description.setText(newsDetails.get(position).description);
        holder.author.setText(newsDetails.get(position).author);
        holder.title.setText(newsDetails.get(position).title);
        holder.url.setText(newsDetails.get(position).url);
    }

    @Override
    public int getItemCount() {
        Log.e(class_name, "getItemCount");
        return newsDetails.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView title, author, description, url;

        public RecyclerViewHolder(View view) {
            super(view);
            Log.e(class_name, "RecyclerViewHolder");
            title = (TextView) view.findViewById(R.id.news_title);
            url = (TextView) view.findViewById(R.id.url);
            author = (TextView) view.findViewById(R.id.author);
            description = (TextView) view.findViewById((R.id.description));
        }


    }


}
