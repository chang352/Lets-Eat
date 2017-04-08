package cs307spring17team26.lets_eat_;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by hareesh on 2/28/17.
 */

public class ChatMessage {
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private static final String url = "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/chat/";

    private String messageText;
    private String messageUser;
    private String messageTime;

    public ChatMessage(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;

        // Initialize to current time
        Calendar cal = Calendar.getInstance();
        this.messageTime = dateFormat.format(cal.getTime());
    }

    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public void putChat(final String user1, final String user2, JSONObject response, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url + user1, response, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("PUT error");
                error.printStackTrace();
            }
        });

        queue.add(jsonObjectRequest);
    }
}
