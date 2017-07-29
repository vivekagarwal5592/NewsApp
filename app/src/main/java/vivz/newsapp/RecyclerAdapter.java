package vivz.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vivz.newsapp.Model.NewsDetails;

/**
 * Created by user on 16-06-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    ArrayList<NewsDetails> newsDetails = new ArrayList<>();
    public static final String class_name = "Recycler Adapter";
    ItemClickListener listener;
    Context context;
//    public RecyclerAdapter(NewsDetails newsDetails) {
//
//    }

    public interface ItemClickListener {
        public void onItemClick(int clickedItemIndex);
    }


    public RecyclerAdapter(ArrayList<NewsDetails> newsDetails, ItemClickListener listener) {

        this.newsDetails = newsDetails;
        this.listener = listener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_news_box, parent, false);
   //     Log.e(class_name, "In onCreateViewHolder");
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;


//        recyclerView  = (RecyclerView)layout.findViewById(R.id.container);
//
//        return layout;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
     //   Log.e(class_name, "onBindViewHolder");
        holder.description.setText(newsDetails.get(position).getDescription());
        holder.author.setText(newsDetails.get(position).getAuthor());
        holder.title.setText(newsDetails.get(position).getTitle());


        String imageUrl = (newsDetails.get(position).getImageUrl());

        if (imageUrl != null) {
            Picasso.with(context)
                    .load(imageUrl)
                    .into(holder.imageView);
        }
    }

    public void swapCursor(ArrayList<NewsDetails> newsDetails) {

        this.newsDetails = null;
        this.newsDetails = newsDetails;

        // Force the RecyclerView to refresh
        this.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
   //     Log.e(class_name, "getItemCount");
        return newsDetails.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, author, description;
        ImageView imageView;

        public RecyclerViewHolder(View view) {
            super(view);
      //      Log.e(class_name, "RecyclerViewHolder");
            title = (TextView) view.findViewById(R.id.news_title);

            author = (TextView) view.findViewById(R.id.author);
            description = (TextView) view.findViewById((R.id.description));
            imageView = (ImageView) view.findViewById(R.id.articleImage);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
       //     Log.e(class_name, "inOnClick");
            listener.onItemClick(pos);
        }
    }

    public void abc() {
    }


}
