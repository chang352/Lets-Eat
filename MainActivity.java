package letseat.letseat;

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

        String consumerKey = "jINYfs_pNzwGGFJJdVUF-g";
        String consumerSecret = "d0Z7lmAkmiSfP8uXFKPF8SUOuCk";
        String token = "PZy9JPPRb4RvqXSuFyJerK8xaYBYf6KH";
        String tokenSecret = "ONRsjq3_gxHWF7v5xergJO5r94M";
        YelpAPIFactory apiFactory = new YelpAPIFactory(consumerKey, consumerSecret, token,
                tokenSecret);
        YelpAPI yelpApi = apiFactory.createAPI();
        CoordinateOptions coordinates = CoordinateOptions.builder().longitude(-86.908066).
                latitude(40.425869).build();
        Map<String, String> params = new HashMap<>();
        params.put("term", "restaurants");
        params.put("radius", "16090");
        Call<SearchResponse> call = yelpApi.search(coordinates, params);
        Callback<SearchResponse> callback = new Callback<SearchResponse>() {

            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {

                SearchResponse searchResponse = response.body();
                ArrayList<Business> businesses = searchResponse.businesses();
                TextView test = (TextView) findViewById(R.id.test);
                test.setText(businesses.get(0).name());

            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

                TextView test = (TextView) findViewById(R.id.test);
                test.setText(t.toString());

            }

        };
        call.enqueue(callback);

        /* Test that the yelpApi is called and succeeded. */
        Yelp yelpTest = new Yelp(10, -86.908066, 40.425869);
        ArrayList<Business> restaurantTest = yelpTest.getRecommendations();
        TextView testTextView = (TextView) findViewById(R.id.test);
        String onFailed = "Error: YelpApi call failed.";
        String onSuccess = "YelpApi call succeed.";
        if (restaurantTest.size() == 0) {
            testTextView.setText(onFailed);
        } else {
            testTextView.setText(onSuccess);
        }

    }

}
