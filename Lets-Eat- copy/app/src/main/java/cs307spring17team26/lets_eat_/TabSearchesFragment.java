package cs307spring17team26.lets_eat_;

/**
 * Created by nathanchang on 2/22/17.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ArrayAdapter;

public class TabSearchesFragment extends Fragment {

    public TabSearchesFragment() {}

    private ListView searchesListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_searches, container, false);
        String name = "Bob Smith\n18";
        String[] info = {name, "MJ\n19", "Nathan Chang\n20"};
        searchesListView = (ListView)rootView.findViewById(R.id.searchesListView);
        ArrayAdapter<String> searchesListViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, info);
        searchesListView.setAdapter(searchesListViewAdapter);
        return rootView;
    }
}
