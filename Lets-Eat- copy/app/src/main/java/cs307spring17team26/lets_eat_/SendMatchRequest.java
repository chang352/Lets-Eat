package cs307spring17team26.lets_eat_;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by hareesh on 3/1/17.
 */

public class SendMatchRequest extends Fragment {
    private static final String url = "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/matches/";
    public static JSONObject obj;
    public static JSONObject potentialMatches;

    //TEST FUNCTIONS
    /*
    public static void main(String[] args) {
        String user1 = "sokola@purdue.edu";
        String user2 = "user1@purdue.edu";
        SendMatchRequest sendMatchRequest = new SendMatchRequest();
    }
    */

    public void testSend(String user1Period, String user2Period, String user1Und, String user2Und, SendMatchRequest sendMatchRequest, Context context) {
        sendMatchRequest.sendMatchRequest(user1Period, user2Period, user1Und, user2Und, context);
        System.out.println("Match request sent");
    }

    public void testChatFail(String user1, String user2, SendMatchRequest sendMatchRequest, Context context) {
        System.out.println(sendMatchRequest.canChat(user1, user2, context));
    }

    public void testAccept(String user1Period, String user2Period, String user1Und, String user2Und, SendMatchRequest sendMatchRequest, Context context) {
        sendMatchRequest.acceptMatch(user1Period, user2Period, user1Und, user2Und, context);
        System.out.println("sokola accepts user1");
    }

    public void testChatSuccess(String user1, String user2, SendMatchRequest sendMatchRequest, Context context) {
        System.out.println(sendMatchRequest.canChat(user1, user2, context) + " :)");
    }

    public void testDeny(String user1Period, String user2Period, String user1Und, String user2Und, SendMatchRequest sendMatchRequest, Context context) {
        sendMatchRequest.denyMatch(user1Period, user2Period, user1Und, user2Und, context);
        sendMatchRequest.denyMatch(user2Period, user1Period, user2Und, user1Und, context);
        System.out.println("Match denied");
    }

    public void testRetrieveMatches(String user1, SendMatchRequest sendMatchRequest, Context context) {
        JSONObject testObj = sendMatchRequest.retrievePotentialMatches(user1, context);
        System.out.println(testObj.toString());
    }

    //USEFUL FUNCTIONS
    public void putData(final String user1, final String user2, JSONObject data, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url + user1, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }

    public void sendMatchRequest(final String user1Period, final String user2Period, final String user1Und, final String user2Und, final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + user1Period, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    //if (response.getInt(user2) <= 0) {

                        JSONObject newObject = new JSONObject();
                        newObject.put(user2Und, 1);
                        putData(user1Period, user2Und, newObject, context);

                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonObjectRequest);
        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url + user2Period, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    //if (response.getInt(user2) <= 0) {

                    JSONObject newObject = new JSONObject();
                    newObject.put(user1Und, 0);
                    putData(user2Period, user1Und, newObject, context);

                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest1);
    }

    public void acceptMatch(final String user1Period, final String user2Period, final String user1Und, final String user2Und, final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + user1Period, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject newObject = new JSONObject();
                    newObject.put(user2Und, 2);
                    putData(user1Period, user2Und, newObject, context);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonObjectRequest);
        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url + user2Period, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject newObject = new JSONObject();
                    newObject.put(user1Und, 2);
                    putData(user2Period, user1Und, newObject, context);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonObjectRequest1);
    }

    public void denyMatch(final String user1Period, final String user2Period, final String user1Und, final String user2Und, final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + user1Period, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response.put(user2Und, -1);
                    JSONObject newObject = new JSONObject();
                    newObject.put(user2Und, -1);
                    putData(user1Period, user2Und, newObject, context);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonObjectRequest);
        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url + user2Period, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response.put(user1Und, -1);
                    JSONObject newObject = new JSONObject();
                    newObject.put(user1Und, -1);
                    putData(user2Period, user1Und, newObject, context);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonObjectRequest1);
    }

    public JSONObject retrievePotentialMatches(final String user1, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + user1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    potentialMatches = new JSONObject();
                    Iterator<String> users = response.keys();
                    while (users.hasNext()) {
                        potentialMatches.put(users.next(), 0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonObjectRequest);

        return potentialMatches;
    }

    public boolean canChat(final String user1, final String user2, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final JSONObject canChatObj = new JSONObject();
        boolean canChat = false;
        SendMatchRequest.obj = new JSONObject();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + user1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt(user2) == 2) {
                        SendMatchRequest.obj.put(user2, true);
                        System.out.println("true!");
                    } else {
                        SendMatchRequest.obj.put(user2, false);
                        System.out.println("false!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonObjectRequest);
        try {
            if (SendMatchRequest.obj != null) {
                canChat = SendMatchRequest.obj.getBoolean(user2);
            } else {
                System.out.println("it's null :(");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return canChat;
    }
}
