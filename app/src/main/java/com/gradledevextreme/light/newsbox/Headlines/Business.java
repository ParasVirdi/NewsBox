package com.gradledevextreme.light.newsbox.Headlines;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gradledevextreme.light.newsbox.Adapters.CustomAdapter;
import com.gradledevextreme.light.newsbox.Activities.LoginActivity;
import com.gradledevextreme.light.newsbox.AsyncTask.Function;
import com.gradledevextreme.light.newsbox.Models.NewsModel;
import com.gradledevextreme.light.newsbox.Activities.NavigationActivity;
import com.gradledevextreme.light.newsbox.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
   Required for Business news
 */
public class Business extends Fragment {


    private CustomAdapter adapter;
    private RecyclerView buisnessHeadlinesRecyclerView;
    private ArrayList<NewsModel> arrayList;
    private ProgressDialog progressDialog;
    private boolean value = true;
    String NEWS_SOURCE = "bbc-news";


    public Business() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        SharedPreferences settings = getActivity().getSharedPreferences(NavigationActivity.PREFS_NAME, 0);
        String location = settings.getString("location", "");


        DNews newsTask = new DNews();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Buffering data from servers...");
        if (value) {
            progressDialog.show();
        }


        //if we dont have any location india or world etc in locations
        if (location.equals("")) {
            Toast.makeText(getContext(), "Location not found", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        } else {
            arrayList = new ArrayList<>();
            adapter = new CustomAdapter(getContext(), arrayList);
            buisnessHeadlinesRecyclerView = (RecyclerView) view.findViewById(R.id.buisnessHeadlinesRecyclerView);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            buisnessHeadlinesRecyclerView.setLayoutManager(layoutManager);
            buisnessHeadlinesRecyclerView.setItemViewCacheSize(20);
            buisnessHeadlinesRecyclerView.setDrawingCacheEnabled(true);
            buisnessHeadlinesRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            buisnessHeadlinesRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();


            switch (location) {

                case "India":
                    NEWS_SOURCE = "financial-times";
                    newsTask.execute();
                    //  getStories("financial-times");
                    break;
                case "Australia":
                    NEWS_SOURCE = "abc-news-au";
                    newsTask.execute();
                    // getStories("abc-news-au");
                    break;
                case "USA":
                    NEWS_SOURCE = "the-wall-street-journal";
                    newsTask.execute("the-wall-street-journal");
                    // getStories("the-wall-street-journal");
                    break;
                case "UK":
                    NEWS_SOURCE = "business-insider-uk";
                    newsTask.execute("business-insider-uk");
                    //getStories("business-insider-uk");
                    break;
                default:
                    NEWS_SOURCE = "the-wall-street-journal";
                    newsTask.execute("the-wall-street-journal");
                    // getStories("the-wall-street-journal");
                    break;
            }
        }
        return view;
    }


    public class DNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(String xml) {
            try {
                JSONObject object = new JSONObject(xml);
                //get json array from it
                JSONArray array = object.getJSONArray("articles");
                //now get every title etc from that array
                JSONObject object1 = null;
                NewsModel model;
                for (int i = 0; i < array.length(); i++) {
                    model = new NewsModel();
                    object1 = array.getJSONObject(i);
                    model.setTitle(object1.getString("title"));
                    model.setDescription(object1.getString("description"));
                    model.setAuthor(object1.getString("author"));
                    model.setUrl(object1.getString("url"));
                    model.setUrlToImage(object1.getString("urlToImage"));
                    model.setPublishedAt(object1.getString("publishedAt"));
                    adapter.addItem(model);

                    if (value) {
                        progressDialog.dismiss();
                        value = false;
                    }
                }

            } catch (JSONException e) {

            }


        }

        @Override
        protected String doInBackground(String... urls) {
            String xml = "";

            String urlParameters = "";
            xml = Function.excuteGet("https://newsapi.org/v1/articles?source=" + NEWS_SOURCE + "&sortBy=top&apiKey=7eb605a354634012a3946004936e71cc", urlParameters);
            return xml;
        }
    }


/**
 public void getStories(String newspaper) {


 //our url for news
 String api = "https://newsapi.org/v1/articles?source=" + newspaper + "&sortBy=latest&apiKey=7eb605a354634012a3946004936e71cc";
 StringRequest request = new StringRequest(Request.Method.GET, api, new Response.Listener<String>() {
@Override public void onResponse(String response) {
//first get json object
try {
JSONObject object = new JSONObject(response);
//get json array from it
JSONArray array = object.getJSONArray("articles");
//now get every title etc from that array
JSONObject object1 = null;
NewsModel model;
for (int i = 0; i < array.length(); i++) {
model = new NewsModel();
object1 = array.getJSONObject(i);
model.setTitle(object1.getString("title"));
model.setDescription(object1.getString("description"));
model.setAuthor(object1.getString("author"));
model.setUrl(object1.getString("url"));
model.setUrlToImage(object1.getString("urlToImage"));
model.setPublishedAt(object1.getString("publishedAt"));
adapter.addItem(model);
progressDialog.dismiss();

if (value) {
progressDialog.dismiss();
value = false;
}
}
} catch (JSONException e) {
e.printStackTrace();
}
}
}, new Response.ErrorListener() {
@Override public void onErrorResponse(VolleyError error) {
}
});


 Volley.newRequestQueue(getContext()).add(request);


 }**/


}
