package cs307spring17team26.lets_eat_;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static java.lang.Character.isDigit;

/**
 * Created by hareesh on 3/1/17.
 */

public class ChatActivity extends AppCompatActivity{
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private static final String url = "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/chat/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chat_main);

        displayChatMessages();

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);

                //push input to database, perhaps as a ChatMessage?
                //ChatMessage message = new ChatMessage(input, getUser());
                RequestQueue queue = Volley.newRequestQueue(getApplication());

                String inputText = input.getText().toString();

                String currentUser = "";
                Bundle account = getIntent().getExtras();
                if (account != null) {
                    currentUser = account.getCharSequence("email").toString();
                }
                final ChatMessage message = new ChatMessage(inputText, currentUser);
                JSONObject JSONChatMessage = new JSONObject();
                try {
                    JSONChatMessage.put("user1@purdue.edu", message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final JSONObject messagesObj = new JSONObject();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + currentUser, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            response.accumulate("user1@purdue.edu", parseChatMessage(message));
                            messagesObj.put("newArray", response);
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

                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.PUT, url + currentUser, messagesObj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                queue.add(jsonObjectRequest1);

                input.setText("");
            }
        });
    }

    private void displayChatMessages() {
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);
        TextView messageText = (TextView)findViewById(R.id.message_text);
        TextView messageUser = (TextView)findViewById(R.id.message_user);
        TextView messageTime = (TextView)findViewById(R.id.message_time);

        //This is where I pull the text from the database I think
        //parseDBChatMessage(message);
        //messageText.setText(text);
        //messageUser.setText(user);
        Calendar cal = Calendar.getInstance();
        messageTime.setText(dateFormat.format(cal.getTime()));
    }

    private String parseChatMessage(ChatMessage message) {
        //format chat message as a string: time + userid + message
        return (message.getMessageTime() + message.getMessageUser() + ".." + message.getMessageText());
    }

    private ChatMessage parseDBChatMessage(String message) {
        ChatMessage newMessage;
        String messageUser = "";
        String messageText = "";

        int i = 17;
	//if we find "..", we know its the end of the email address
        for (; i < message.length(); i++) {
            if (message.substring(i, i+1) == "..") {
                break;
            }
            messageUser += message.charAt(i);
        }

        for (; i < message.length(); i++) {
            messageText += message.charAt(i);
        }

        newMessage = new ChatMessage(messageText, messageUser);

        return newMessage;
    }
}
