package cs307spring17team26.lets_eat_;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Button;

//import java.util.ArrayList;
//import com.yelp.clientlib.entities.Business;

public class ProfileMatches extends AppCompatActivity {

    private CharSequence email;
    private CharSequence emailMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile_matches);

        Bundle account = getIntent().getExtras();
        if (account!=null) {
            email = account.getCharSequence("emailUser");
            emailMatch = account.getCharSequence("emailMatch");
        }

        final SendMatchRequest smr = new SendMatchRequest();
        ImageView imageView = (ImageView)findViewById(R.id.matchProfilePic);
        final ListView infoListView = (ListView)findViewById(R.id.matchInfo);
        FloatingActionButton closeProfileButton = (FloatingActionButton)findViewById(R.id.closeProfileButton);
        FloatingActionButton chatButton = (FloatingActionButton)findViewById(R.id.chatButton);
        FloatingActionButton restaurantButton = (FloatingActionButton)findViewById(R.id.restaurantButton);
        FloatingActionButton acceptMeeting = (FloatingActionButton)findViewById(R.id.acceptMeeting);
        final FrameLayout layout = (FrameLayout)findViewById(R.id.layout);
        final TextView text = (TextView)findViewById(R.id.text);
        final TextView meeting = (TextView)findViewById(R.id.meeting);
        final Button accept = (Button)findViewById(R.id.acceptButton);
        final Button delete = (Button)findViewById(R.id.deleteButton);
        final Button back = (Button)findViewById(R.id.back);
        //TextView test = (TextView)findViewById(R.id.test);

        //closing the activity, closing the match profile and going back to matches UI page
        closeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //go to restaurants UI
        restaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileMatches.this, ScheduleActivity.class);
                intent.putExtra("emailUser", email);
                intent.putExtra("emailMatch", emailMatch);
                startActivity(intent);
            }
        });

        //chat with match
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code for to chat with match
                Intent intent = new Intent(ProfileMatches.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        acceptMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.INVISIBLE);
                text.setVisibility(View.INVISIBLE);
                meeting.setVisibility(View.VISIBLE);
                accept.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                Context c = getApplication();
                RequestQueue queue = Volley.newRequestQueue(c);
                JsonObjectRequest j = new JsonObjectRequest(
                        Request.Method.GET, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/meeting/user/" + email + "/match/" + emailMatch, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.has("message")) {
                                        meeting.setText("No meetings scheduled.");
                                        accept.setVisibility(View.INVISIBLE);
                                        delete.setVisibility(View.INVISIBLE);
                                    } else {
                                        meeting.setText(response.getString("restaurant") + "\n" + response.getString("time"));
                                        if (response.getInt("isGood")==1 || response.getInt("isGood")==2) {accept.setVisibility(View.INVISIBLE);}
                                    }
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
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("isGood", 2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Context c = getApplication();
                        RequestQueue queue = Volley.newRequestQueue(c);
                        JsonObjectRequest j = new JsonObjectRequest(
                                Request.Method.PUT, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/meeting/user/" + email + "/match/" + emailMatch, obj,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            response.put("isGood", 2);
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
                        JsonObjectRequest j1 = new JsonObjectRequest(
                                Request.Method.PUT, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/meeting/user/" + emailMatch + "/match/" + email, obj,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            response.put("isGood", 2);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        queue.add(j1);
                        meeting.setVisibility(View.INVISIBLE);
                        accept.setVisibility(View.INVISIBLE);
                        delete.setVisibility(View.INVISIBLE);
                        back.setVisibility(View.INVISIBLE);
                        layout.setVisibility(View.VISIBLE);
                        text.setVisibility(View.VISIBLE);
                    }
                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context c = getApplication();
                        RequestQueue queue = Volley.newRequestQueue(c);
                        JsonObjectRequest j = new JsonObjectRequest(
                                Request.Method.DELETE, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/meeting/user/" + email + "/match/" + emailMatch, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        queue.add(j);
                        JsonObjectRequest j1 = new JsonObjectRequest(
                                Request.Method.DELETE, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/meeting/user/" + emailMatch + "/match/" + email, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        queue.add(j1);
                        meeting.setVisibility(View.INVISIBLE);
                        accept.setVisibility(View.INVISIBLE);
                        delete.setVisibility(View.INVISIBLE);
                        back.setVisibility(View.INVISIBLE);
                        layout.setVisibility(View.VISIBLE);
                        text.setVisibility(View.VISIBLE);
                    }
                });
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        meeting.setVisibility(View.INVISIBLE);
                        accept.setVisibility(View.INVISIBLE);
                        delete.setVisibility(View.INVISIBLE);
                        back.setVisibility(View.INVISIBLE);
                        layout.setVisibility(View.VISIBLE);
                        text.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        Context c = getApplication();
        RequestQueue queue = Volley.newRequestQueue(c);
        JsonObjectRequest j = new JsonObjectRequest(
                Request.Method.GET, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/users/" + emailMatch, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String[] info = {response.getString("name"), Integer.toString(response.getInt("age")), response.getString("gender"), response.getString("bio")};
                            ArrayAdapter<String> infoListViewAdapter = new ArrayAdapter<String>(ProfileMatches.this, android.R.layout.simple_list_item_1, info);
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
