package com.gradledevextreme.light.newsbox.Headlines;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.gradledevextreme.light.newsbox.Models.NewsModel;
import com.gradledevextreme.light.newsbox.Activities.NavigationActivity;
import com.gradledevextreme.light.newsbox.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
   Required for Science News
 */


public class Science extends Fragment {


    private CustomAdapter adapter;
    private RecyclerView scienceHeadlinesRecyclerView;
    private ArrayList<NewsModel> arrayList;
    private ProgressDialog progressDialog;
    private boolean value = true;


    public Science() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_science, container, false);
        SharedPreferences settings = getActivity().getSharedPreferences(NavigationActivity.PREFS_NAME, 0);
        String location = settings.getString("location", "");


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Buffering data from servers...");
        if(value)
        {
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
            scienceHeadlinesRecyclerView = (RecyclerView) view.findViewById(R.id.scienceHeadlinesRecyclerView);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            scienceHeadlinesRecyclerView.setLayoutManager(layoutManager);
            scienceHeadlinesRecyclerView.setItemViewCacheSize(20);
            scienceHeadlinesRecyclerView.setDrawingCacheEnabled(true);
            scienceHeadlinesRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            scienceHeadlinesRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();


            switch (location) {

                case "India":
                    getStories("national-geographic");
                    break;
                case "Australia":
                    getStories("national-geographic");
                    break;
                case "USA":
                    getStories("national-geographic");
                    break;
                case "UK":
                    getStories("national-geographic");
                    break;
                default:
                    getStories("national-geographic");
                    break;
            }
        }


        return view;


    }


    public void getStories(String newspaper) {


        //our url for news
        String api = "https://newsapi.org/v1/articles?source=" + newspaper + "&sortBy=latest&apiKey=7eb605a354634012a3946004936e71cc";
        StringRequest request = new StringRequest(Request.Method.GET, api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                        if(value)
                        {
                            progressDialog.dismiss();
                            value = false;
                        }                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        Volley.newRequestQueue(getContext()).add(request);


    }
}
