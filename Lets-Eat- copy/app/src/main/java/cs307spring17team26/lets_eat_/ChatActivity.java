package cs307spring17team26.lets_eat_;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.w3c.dom.Text;

import static java.lang.Character.isDigit;

/**
 * Created by hareesh on 3/1/17.
 */

public class ChatActivity extends AppCompatActivity{
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

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
        return (message.getMessageTime() + message.getMessageUser() + ":" + message.getMessageText());
    }

    private ChatMessage parseDBChatMessage(String message) {
        //NEED TO FIGURE OUT WHAT CHARACTER TO USE TO SEPARATE USER FROM MESSAGE
        //SHOULD USE A CHARACTER THAT CAN'T BE IN AN EMAIL STRING
        ChatMessage newMessage;
        String messageUser = "";
        String messageText = "";

        int i = 17;
        for (; i < message.length(); i++) {
            if (message.charAt(i) == ':') {
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
