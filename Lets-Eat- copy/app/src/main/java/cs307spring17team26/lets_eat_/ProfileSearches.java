package cs307spring17team26.lets_eat_;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class ProfileSearches extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile_searches);

        ImageView imageView = (ImageView) findViewById(R.id.searchProfilePic);
        ListView infoListView = (ListView) findViewById(R.id.searchInfo);
        FloatingActionButton deleteButton = (FloatingActionButton) findViewById(R.id.deleteButton);
        FloatingActionButton closeProfileButton = (FloatingActionButton) findViewById(R.id.closeProfileButton);
        FloatingActionButton acceptButton = (FloatingActionButton) findViewById(R.id.acceptButton);

        //removing the profile as potential match, closing the activity and going back to searches UI page
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code for to remove profile as potential match
                finish();
            }
        });

        //closing the activity, closing the potential match profile and going back to searches UI page
        closeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //accepting th profile as potential match, closing the activity and going back to searches UI page
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code for to accept profile as potential match
                finish();
            }
        });

        String name = "'Name'";
        String age = "'Age'";
        String location = "'Location'";
        String favCuisine = "'Favorite Cuisine'";
        String school = "'School'";
        String aboutMe = "'About Me'";
        String[] info = {name, age, location, favCuisine, school, aboutMe};
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

