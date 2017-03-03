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

    public void sendMatchRequest(final String user1, final String user2) {
        //Get user2's _matches array
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url + user2 + "/", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //do something
                try {
                    boolean user1Exists = false;
                    JSONArray user2_matches = response.getJSONArray("matches");
                    int i = 0;
                    for (; i < user2_matches.length(); i++) {
                        if (user2_matches.getString(i).equals(user1)) {
                            user1Exists = true;
                        }
                    }

                    if (user1Exists) {
                        //call method to set user2's valid to 1
                        changeUser2Valid(i, user2);
                        //call method to get user1's matches, which will then set user1's valid to 1
                        changeUser1Matches(user1, user2);
                    } else {
                        //call method to get user1's valid, set to 0
                        changeUser1Valid(i, user1);
                    }
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

    public void changeUser2Valid(final int index, final String user2) {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + user2 + "/", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray user2_valid = response.getJSONArray("valid");
                    user2_valid.put(index, 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });
    }

    public void changeUser1Valid(final int index, final String user1) {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + user1 + "/", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray user1_valid = response.getJSONArray("valid");
                    user1_valid.put(index, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });
    }

    public void changeUser1Matches(final String user1, final String user2) {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + user1 + "/", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray user1_matches = response.getJSONArray("matches");
                    int i = 0;
                    for (; i < user1_matches.length(); i++) {
                        if (user1_matches.getString(i).equals(user2)) {
                            changeUser1Valid(i, user1);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });
    }
}
