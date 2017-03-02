package cs307spring17team26.lets_eat_;

import android.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hareesh on 3/1/17.
 */

public class SendMatchRequest extends Fragment {
    private static final String url = "ec2-52-24-61-118.us-west-2.compute.amazonaws.com/users/";

    /*
    Step 1: Get requested user's _matches array
    Step 2: Search _matches for original user
    Step 3: If original user is in _matches, set both _valid array indexes to 1. Else, set original user's _valid array index to 0
     */

    public void sendMatchRequest(String user1, String user2) {
        //Get user2's _matches array
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url + user2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //do something
                try {
                    boolean user1Exists = false;
                    JSONArray user2_matches = response.getJSONArray("matches");

                    //need to get the _valid array from user2 and user1, and user1's _matches array
                    //if user1Exists = true, set both valid array indexes to 1
                    //else, set user1's _valid to 0 at the index that represents user2
                } catch (JSONException e) {
                    e.printStackTrace();;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });

        queue.add(jsObjRequest);

    }
}
