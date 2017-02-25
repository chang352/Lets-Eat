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

public class TabProfileFragment extends Fragment {

    public TabProfileFragment() {}

    private ListView infoListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_profile, container, false);
        String name = "Name";
        String[] info = {name, "Age", "Location", "Gender", "Favorite Cuisine", "Occupation", "\nHi\nHi\nHi\nHi\nHi\nHi\nHi\nHi\nHi\n"};
        infoListView = (ListView)rootView.findViewById(R.id.infoListView);
        ArrayAdapter<String> infoListViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, info);
        infoListView.setAdapter(infoListViewAdapter);
        return rootView;
    }
}
