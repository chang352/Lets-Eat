package cs307spring17team26.lets_eat_;

/**
 * Created by nathanchang on 2/22/17.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.content.Intent;

public class TabProfileFragment extends Fragment {

    public TabProfileFragment() {}

    private ListView infoListView;
    private ImageButton profilePicButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_profile, container, false);

        profilePicButton = (ImageButton)rootView.findViewById(R.id.profilePicButton);
        profilePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchesProfile.class);
                startActivity(intent);
            }
        });

        String name = "'Name'";
        String age = "'Age'";
        String location = "'Location'";
        String favCuisine = "'Favorite Cuisine'";
        String school = "'School'";
        String aboutMe = "'About Me'";
        String[] info = {name, age, location, favCuisine, school, aboutMe};
        infoListView = (ListView)rootView.findViewById(R.id.infoListView);
        ArrayAdapter<String> infoListViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, info);
        infoListView.setAdapter(infoListViewAdapter);
        return rootView;
    }
}
