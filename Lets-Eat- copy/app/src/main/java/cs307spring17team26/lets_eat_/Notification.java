package com.example.android.testchat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by hareesh on 4/26/17.
 */

public class Notification {

    final private static String url = "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/users/";

    private static void sendChatNotification(Context c) {
        int m = (int) System.currentTimeMillis();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(c).setSmallIcon(R.drawable.sendbutton).setContentTitle("Let's Eat Notification").setContentText("You have a new chat notification!");
        Intent resultIntent = new Intent(c, TestActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(c);
        stackBuilder.addParentStack(TestActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(m, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(m, mBuilder.build());
    }

    private static void sendMeetingNotification(Context c) {
        int m = (int) System.currentTimeMillis();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(c).setSmallIcon(R.drawable.sendbutton).setContentTitle("Let's Eat Notification").setContentText("You have a new meeting request!");
        Intent resultIntent = new Intent(c, TestActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(c);
        stackBuilder.addParentStack(TestActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(m, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(m, mBuilder.build());
    }

    private static void sendMatchNotification(Context c) {
        int m = (int) System.currentTimeMillis();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(c).setSmallIcon(R.drawable.sendbutton).setContentTitle("Let's Eat Notification").setContentText("You have a new match request!");
        Intent resultIntent = new Intent(c, TestActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(c);
        stackBuilder.addParentStack(TestActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(m, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(m, mBuilder.build());
    }

    public static void sendNotifications(final Context c, final String email) {
        RequestQueue queue = Volley.newRequestQueue(c);
        JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET, url + email, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("chatNotification") != 0) {
                        sendChatNotification(c);
                        postNum(c, email, 0, "chatNotification");
                    }
                    if (response.getInt("meetingNotification") != 0) {
                        sendMeetingNotification(c);
                        postNum(c, email, 0, "meetingNotification");
                    }
                    if (response.getInt("matchNotification") != 0) {
                        sendMatchNotification(c);
                        postNum(c, email, 0, "matchNotification");
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
        queue.add(j);
    }

    public static void postNum(final Context c, String email, int num, String notificationType) {
        RequestQueue queue = Volley.newRequestQueue(c);
        JSONObject numObj = new JSONObject();
        try {
            numObj.put(notificationType, num);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest j = new JsonObjectRequest(Request.Method.PUT, url + email, numObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(j);
    }
}
