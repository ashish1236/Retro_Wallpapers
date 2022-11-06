package com.menga.blackwallpapers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AbsListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.menga.blackwallpapers.databinding.ActivityMain2Binding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    ActivityMain2Binding binding;
ArrayList<model> list=new ArrayList<>();
    int pagenumber;
    Intent intent;
    Boolean isscroling=false;
    int currentitems,totalitems,scroloutitems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent=getIntent();
        String searchquery=intent.getStringExtra("searchkey");
Fetch(searchquery);
        GridLayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2);
        binding.rv.setLayoutManager(layoutManager);


        binding.rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
    isscroling=true;
}

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentitems= layoutManager.getChildCount();
                totalitems=layoutManager.getItemCount();
                scroloutitems=layoutManager.findFirstVisibleItemPosition();
                if (isscroling&&currentitems+scroloutitems==totalitems){
                    isscroling=false;
                    Fetch(searchquery);
                }
            }
        });






    }
    private void Fetch(String searchquery){
        StringRequest request=new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search?query="+searchquery+"/?page=\"+pagenumber+\"&per_page=80", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray= jsonObject.getJSONArray("photos");

                    Adapter adapter=new Adapter(list,getApplicationContext());

for (int i=1;i<=jsonArray.length();i++){
JSONObject imgobject=jsonArray.getJSONObject(i);
JSONObject jsonObject1=imgobject.getJSONObject("src");
String imgsrc=jsonObject1.getString("portrait");
    model models = new model();
    models.setMedium(imgsrc);
    list.add(models);
    adapter.notifyDataSetChanged();
    binding.rv.setAdapter(adapter);


}
                    pagenumber++;

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> hasmap=new HashMap();
                hasmap.put("Authorization","563492ad6f917000010000018a057bbfc50e4224b99b2282463e862f");
                return hasmap;


            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }
}