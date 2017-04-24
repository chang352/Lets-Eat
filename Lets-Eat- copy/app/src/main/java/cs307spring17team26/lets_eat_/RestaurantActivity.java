package com.example.android.restaurantactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.ListActivity;
import android.view.View;
import android.widget.RatingBar;
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

public class RestaurantActivity extends ListActivity {

    String consumerKey = "jINYfs_pNzwGGFJJdVUF-g";
    String secretKey = "d0Z7lmAkmiSfP8uXFKPF8SUOuCk";
    String token = "PZy9JPPRb4RvqXSuFyJerK8xaYBYf6KH";
    String secret = "ONRsjq3_gxHWF7v5xergJO5r94M";

    YelpAPIFactory apiFactory = new YelpAPIFactory(consumerKey, secretKey, token, secret);
    YelpAPI yelpApi = apiFactory.createAPI();
    CoordinateOptions coordinates = CoordinateOptions.builder().longitude(-86.908066).latitude(40.425869).build();
    Map<String, String> params = new HashMap<>();

    private ArrayList<Business> restaurantList = new ArrayList<>();
    private ArrayList<String> restaurantNames = new ArrayList<>();
    private ArrayList<Double> restaurantRatings = new ArrayList<>();
    private TextView selection;
    private RatingBar ratingBar;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        params.put("terms", "restaurants");
        params.put("radius", "16090");
        Call<SearchResponse> call = yelpApi.search(coordinates, params);
        Callback<SearchResponse> callback = new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                restaurantList = searchResponse.businesses();
                for(int i = 0; i < restaurantList.size(); i++) {
                    restaurantNames.add(i, restaurantList.get(i).name());
                    restaurantRatings.add(i, restaurantList.get(i).rating());
                }
                //restaurantList now has all the names
                //display them and their ratings in rows
                list = (ListView)findViewById(android.R.id.list);
                setListAdapter(new IconicAdapter());
                selection = (TextView)findViewById(R.id.info);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                //t.toString() to show error
                System.out.println(t.toString());
            }
        };
        call.enqueue(callback);

        //OLD STUFF WITHOUT STAR RATINGS WORKING
        /*params.put("terms", "restaurants");
        params.put("radius", "16090");
        Call<SearchResponse> call = yelpApi.search(coordinates, params);
        Callback<SearchResponse> callback = new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                restaurantList = searchResponse.businesses();
                for(int i = 0; i < restaurantList.size(); i++) {
                    restaurantNames.add(i, restaurantList.get(i).name());
                    restaurantRatings.add(i, restaurantList.get(i).rating());
                }
                //restaurantList now has all the names
                //display them and their ratings in rows
                list = (ListView)findViewById(android.R.id.list);
                setListAdapter(new ArrayAdapter<>(RestaurantActivity.this, R.layout.row, R.id.label, restaurantNames));
                setListAdapter(new ArrayAdapter<>(RestaurantActivity.this, R.layout.row, R.id.rate, restaurantRatings));
                selection = (TextView)findViewById(R.id.info);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                //t.toString() to show error
                System.out.println(t.toString());
            }
        };
        call.enqueue(callback);*/
        //END OF OLD STUFF WITHOUT RATING STARS WORKING
        /*
        list = (ListView)findViewById(android.R.id.list);

        setListAdapter(new ArrayAdapter<>(this, R.layout.row, R.id.label, restaurantList));
        selection = (TextView)findViewById(R.id.info);
        */
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        //list.setVisibility(View.INVISIBLE);
        //selection.setVisibility(View.VISIBLE);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurantList.get(position).mobileUrl()));
        startActivity(browserIntent);
        //selection.setText(restaurantList.get(position).snippetText());
    }

    class IconicAdapter extends ArrayAdapter<Business> {
        IconicAdapter() {
            super(RestaurantActivity.this, R.layout.row, restaurantList);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            TextView label = (TextView)row.findViewById(R.id.label);
            label.setText(restaurantNames.get(position));
            RatingBar rating = (RatingBar)row.findViewById(R.id.rate);
            rating.setRating(restaurantRatings.get(position).intValue());
            System.out.println(restaurantNames.get(position) + " " + restaurantRatings.get(position).intValue() + " :)");
            return row;
        }
    }
}
