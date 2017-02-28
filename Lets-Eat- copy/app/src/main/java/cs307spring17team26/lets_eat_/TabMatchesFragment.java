package cs307spring17team26.lets_eat_;

/**
 * Created by nathanchang on 2/22/17.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class TabMatchesFragment extends Fragment {

    public TabMatchesFragment() {}

    private ListView matchesListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_matches, container, false);
        SearchView searchBarSearchView = (SearchView)rootView.findViewById(R.id.searchBarSearchView); // initialize a search view
        CharSequence query = searchBarSearchView.getQuery(); // get the query string currently in the text field
        String[] info = {"Nathan Chang\n20", "MJ\n50", "Bill Nye\n60"};
        matchesListView = (ListView)rootView.findViewById(R.id.matchesListView);
        ArrayAdapter<String> infoListViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, info);
        matchesListView.setAdapter(infoListViewAdapter);
        return rootView;
    }
}
