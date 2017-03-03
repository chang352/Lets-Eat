package cs307spring17team26.lets_eat_;

/**
 * Created by nathanchang on 2/22/17.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.content.Intent;
import android.support.v4.app.DialogFragment;

public class TabProfileFragment extends Fragment{

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
        infoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //show input text to change info
                /*Bundle bundle = new Bundle();
                switch (position) {
                    case 0:
                        bundle.putCharSequence("name", "0"); break;
                    case 1:
                        bundle.putCharSequence("age", "1"); break;
                    case 2:
                        bundle.putCharSequence("location", "2"); break;
                    case 3:
                        bundle.putCharSequence("favoriteCuisine", "3"); break;
                    case 4:
                        bundle.putCharSequence("school", "4"); break;
                    case 5:
                        bundle.putCharSequence("aboutMe", "5"); break;
                    default:
                        return;
                }
                TabProfileFragment tab = new TabProfileFragment();
                tab.setArguments(bundle);*/
                showDialog(new MyDialogFragment());
            }
        });
        return rootView;
    }

    public void showDialog(DialogFragment df) {
        FragmentManager fm = getFragmentManager();
        df.show(fm, "Test");
    }
}

