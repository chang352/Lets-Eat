package com.example.android.meetingrequest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.Date;

public class ScheduleActivity extends AppCompatActivity {
    private DatePicker datePicker;
    private EditText editText;
    private TimePicker timePicker;
    private Button sendButton;
    private Button deleteButton;
    private Button cancelButton;
    private Button searchButton;
    final private String url = "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/meeting/user/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        datePicker = (DatePicker)findViewById(R.id.datePicker);
        editText = (EditText)findViewById(R.id.editText);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setSingleLine(true);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        sendButton = (Button)findViewById(R.id.sendButton);
        deleteButton = (Button)findViewById(R.id.deleteButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        searchButton = (Button)findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to Search Activity
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle account = getIntent().getExtras();
                String user1 = "";
                if (account != null) {
                    user1 = account.getCharSequence("email").toString();
                }
                sendMeeting("sokola@purdue.edu", "user1@purdue.edu", editText.getText().toString(), timeToString(timePicker.getHour(), timePicker.getMinute(), datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear()), 0, getApplication());
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle account = getIntent().getExtras();
                String user1 = "";
                if (account != null) {
                    user1 = account.getCharSequence("email").toString();
                }
                deleteMeeting("sokola@purdue.edu", "user1@purdue.edu", getApplication());
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to previous Activity
            }
        });
    }

    public void sendMeeting(final String user1, final String user2, String name, String time, int isGood, Context context) {
        JSONObject meeting = new JSONObject();
        try {
            meeting.put("restaurant", name);
            meeting.put("time", time);
            meeting.put("isGood", isGood);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(context);

        //first check that user2 isn't already there
        //use url + user1 to get all the users with underscores

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url + user1 + "/match/" + user2, meeting, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }

    public void deleteMeeting(final String user1, final String user2, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url + user1 + "/match/" + user2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }

    public String timeToString(int hour, int minute, int day, int month, int year) {
        return hour + ":" + minute + " " + day + "/" + month + "/" + year;
    }

    //functions to parse time from database
    public int returnHour(String timeAndDate) {
        String time = "";
        return 0;
    }

    //add button to go to Search Activity
    //add delete button as well
}
