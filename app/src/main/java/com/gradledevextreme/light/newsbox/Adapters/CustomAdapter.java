package com.gradledevextreme.light.newsbox.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gradledevextreme.light.newsbox.Models.NewsModel;
import com.gradledevextreme.light.newsbox.R;
import com.gradledevextreme.light.newsbox.WebView_y.WebVieww;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by anshuman on 07/10/17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {


    private static Context mContext;
    private static ArrayList<NewsModel> newsModelArrayList;
    private URL url;


    public CustomAdapter(Context context, ArrayList<NewsModel> newsModelArrayList) {
        this.mContext = context;
        this.newsModelArrayList = newsModelArrayList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
        return new CustomAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewsModel model = newsModelArrayList.get(position);
        if (model.getAuthor().equals("null")) {
        } else {
            holder.authorTextView.setText(model.getAuthor());
        }
        holder.titleTextView.setText(model.getTitle());
        holder.descriptionTextView.setText(model.getDescription());
        String mPublishedAt = model.getPublishedAt();
        String[] parts = mPublishedAt.split("T", 2);
        holder.publishedAtTextView.setText(parts[0]);


        //to load images in recycler view without piccaso
//        try {
//             url = new URL(model.getUrlToImage().toString());
//        } catch (MalformedURLException e) {
//            System.out.println("The URL is not valid.");
//            System.out.println(e.getMessage());
//        }
//
//        try {
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        }catch(IOException e)
//        {e.printStackTrace();}
//        holder.newsImage.setImageBitmap(bmp);


        Picasso.with(mContext).load(model.getUrlToImage()).fit()
                .centerCrop().into(holder.newsImage);

    }


    @Override
    public int getItemCount() {
        return newsModelArrayList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView titleTextView, authorTextView, descriptionTextView, publishedAtTextView;
        private ImageView newsImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            authorTextView = (TextView) itemView.findViewById(R.id.authorTextView);
            descriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
            publishedAtTextView = (TextView) itemView.findViewById(R.id.publishedAtTextView);
            newsImage = (ImageView) itemView.findViewById(R.id.newsImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            NewsModel model = newsModelArrayList.get(this.getPosition());
            Intent nextWebView = new Intent(mContext, WebVieww.class);
            WebVieww.mUrl = model.getUrl().toString();
            mContext.startActivity(nextWebView);

//            Intent nextWebView = new Intent(mContext, WebVieww.class);
//            WebView.mUrl = model.getUrl().toString();
//            mContext.startActivity(nextWebView);
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }


    public void addItem(NewsModel model) {
        newsModelArrayList.add(0, model);
        notifyItemRangeChanged(0, getItemCount());
    }
}
