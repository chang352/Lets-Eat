package cs307spring17team26.lets_eat_;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileSearches extends AppCompatActivity {

    private CharSequence email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile_searches);

        Bundle account = getIntent().getExtras();
        if (account!=null) {
            email = account.getCharSequence("email");
        }

        ImageView imageView = (ImageView) findViewById(R.id.searchProfilePic);
        final ListView infoListView = (ListView) findViewById(R.id.searchInfo);
        FloatingActionButton deleteButton = (FloatingActionButton) findViewById(R.id.deleteButton);
        FloatingActionButton closeProfileButton = (FloatingActionButton) findViewById(R.id.closeProfileButton);
        FloatingActionButton acceptButton = (FloatingActionButton) findViewById(R.id.acceptButton);

        //removing the profile as potential match, closing the activity and going back to searches UI page
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code for to remove profile as potential match
                finish();
            }
        });

        //closing the activity, closing the potential match profile and going back to searches UI page
        closeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //accepting th profile as potential match, closing the activity and going back to searches UI page
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code for to accept profile as potential match
                finish();
            }
        });

        Context c = getApplication();
        RequestQueue queue = Volley.newRequestQueue(c);
        JsonObjectRequest j = new JsonObjectRequest(
                Request.Method.GET, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/users/" + email, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String[] info = {response.getString("name"), Integer.toString(response.getInt("age")), response.getString("gender"), response.getString("bio")};
                            ArrayAdapter<String> infoListViewAdapter = new ArrayAdapter<String>(ProfileSearches.this, android.R.layout.simple_list_item_1, info);
                            infoListView.setAdapter(infoListViewAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(j);
        // Set up the user interaction to manually show or hide the system UI.
        /*mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });*/

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }
}
