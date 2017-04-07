package com.example.android.yelpapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YelpAPI yelpApi;
        Map<String, String> params;

        final String consumerKey = "jINYfs_pNzwGGFJJdVUF-g";
        final String consumerSecret = "d0Z7lmAkmiSfP8uXFKPF8SUOuCk";
        final String token = "PZy9JPPRb4RvqXSuFyJerK8xaYBYf6KH";
        final String tokenSecret = "ONRsjq3_gxHWF7v5xergJO5r94M";
        YelpAPIFactory apiFactory = new YelpAPIFactory(consumerKey, consumerSecret, token, tokenSecret);
        yelpApi = apiFactory.createAPI();

        params = new HashMap<String, String>();
        params.put("term", "restaurants");



        params.put("radius", Double.toString(100 * 1609.34));

        CoordinateOptions coordinates = CoordinateOptions.builder().longitude(122.4194).latitude(37.7722).build();

        Call<SearchResponse> call = yelpApi.search(coordinates, params);

        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                ArrayList<Business> businesses = response.body().businesses();
                TextView restaurants = (TextView)findViewById(R.id.restaurants);
                restaurants.setText(response.body().businesses().get(0).name());
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                TextView restaurants = (TextView)findViewById(R.id.restaurants);
                restaurants.setText(t.toString());
            }
        });


    }
}
