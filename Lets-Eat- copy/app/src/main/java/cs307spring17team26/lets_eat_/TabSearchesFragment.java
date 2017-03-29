package cs307spring17team26.lets_eat_;

/**
 * Created by nathanchang on 2/22/17.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.content.Intent;

public class TabSearchesFragment extends Fragment {

    private CharSequence email;
    private String emailString;


    public TabSearchesFragment() {
        emailString = "";
    }

    private ListView searchesListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_searches, container, false);

        Bundle account = this.getArguments();
        if (account!=null) {
            email = account.getCharSequence("email");
            emailString = email.toString();
        }

        String name = "Bob Smith\n18";
        String[] info = {name, "MJ\n19", "Nathan Chang\n20"};
        searchesListView = (ListView)rootView.findViewById(R.id.searchesListView);
        ArrayAdapter<String> searchesListViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, info);
        searchesListView.setAdapter(searchesListViewAdapter);
        searchesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //show input text to change info
                Intent intent = new Intent(getActivity(), ProfileSearches.class);
                getActivity().startActivity(intent);
            }
        });
        return rootView;
    }
}
