package cs307spring17team26.lets_eat_;

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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    Map<String, String> params = new HashMap<>();

    private ArrayList<Business> restaurantList = new ArrayList<>();
    private ArrayList<String> restaurantNames = new ArrayList<>();
    private ArrayList<Double> restaurantRatings = new ArrayList<>();
    private TextView selection;
    private RatingBar ratingBar;
    private ListView list;
    public static double longitude;
    public static double latitude;
    public static int maxRange;

    private static final String url = "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/users/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        RequestQueue queue = Volley.newRequestQueue(getApplication());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + "sokola@purdue.edu", null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println("hi");
                    JSONArray location = response.getJSONArray("location");
                    RestaurantActivity.longitude = location.getDouble(0);
                    RestaurantActivity.latitude = location.getDouble(1);
                    RestaurantActivity.maxRange = response.getInt("maxRange");
                    CoordinateOptions coordinates = CoordinateOptions.builder().longitude(longitude).latitude(latitude).build();

                    params.put("terms", "restaurants");
                    params.put("radius", Integer.toString(maxRange*1609));

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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
        /*while (maxRange == 0);
        CoordinateOptions coordinates = CoordinateOptions.builder().longitude(longitude).latitude(latitude).build();

        params.put("terms", "restaurants");
        params.put("radius", Integer.toString(maxRange*1609));

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
        call.enqueue(callback);*/
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
            //System.out.println(restaurantNames.get(position) + " " + restaurantRatings.get(position).intValue() + " :)");
            return row;
        }
    }
}
