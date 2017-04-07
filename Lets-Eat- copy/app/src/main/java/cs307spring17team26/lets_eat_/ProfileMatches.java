package cs307spring17team26.lets_eat_;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class ProfileMatches extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile_matches);

        ImageView imageView = (ImageView)findViewById(R.id.matchProfilePic);
        ListView infoListView = (ListView)findViewById(R.id.matchInfo);
        FloatingActionButton deleteButton = (FloatingActionButton)findViewById(R.id.deleteButton);
        FloatingActionButton closeProfileButton = (FloatingActionButton)findViewById(R.id.closeProfileButton);
        FloatingActionButton chatButton = (FloatingActionButton)findViewById(R.id.chatButton);
        FloatingActionButton restaurantButton = (FloatingActionButton)findViewById(R.id.restaurantButton);

        //removing the match, closing the activity and going back to match UI page
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code for to remove match
                finish();
            }
        });

        //closing the activity, closing the match profile and going back to matches UI page
        closeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //open restaurant UI page
        restaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Restaurants r = new Restaurants();
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

        String name = "'Name'";
        String age = "'Age'";
        //String location = "'Location'";
        //String favCuisine = "'Favorite Cuisine'";
        //String school = "'School'";
        String gender = "'Gender'";
        String aboutMe = "'About Me'";
        String[] info = {name, age, gender, aboutMe};
        ArrayAdapter<String> infoListViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, info);
        infoListView.setAdapter(infoListViewAdapter);

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
