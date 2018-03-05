package com.gradledevextreme.light.newsbox.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gradledevextreme.light.newsbox.Models.NewsModel;
import com.gradledevextreme.light.newsbox.R;

import java.util.ArrayList;

/**
 * Created by anshuman on 07/10/17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {




    private Context context;
    private ArrayList<NewsModel> newsModelArrayList;





    public CustomAdapter(Context context, ArrayList<NewsModel> newsModelArrayList) {
        this.context = context;
        this.newsModelArrayList = newsModelArrayList;
    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row,parent,false);

        return new CustomAdapter.MyViewHolder(itemView);
    }




    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewsModel model = newsModelArrayList.get(position);
        if(model.getAuthor().equals("null")){
        }else{
            holder.authorTextView.setText(model.getAuthor());
        }
        holder.titleTextView.setText(model.getTitle());
        holder.descriptionTextView.setText(model.getDescription());
        holder.publishedAtTextView.setText(model.getPublishedAt());
    }




    @Override
    public int getItemCount() {
        return newsModelArrayList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTextView,authorTextView,descriptionTextView,publishedAtTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView)itemView.findViewById(R.id.titleTextView);
            authorTextView = (TextView)itemView.findViewById(R.id.authorTextView);
            descriptionTextView = (TextView)itemView.findViewById(R.id.descriptionTextView);
            publishedAtTextView = (TextView)itemView.findViewById(R.id.publishedAtTextView);
        }
    }




    public void addItem(NewsModel model){
        newsModelArrayList.add(0,model);
        notifyItemRangeChanged(0,getItemCount());
    }
}
