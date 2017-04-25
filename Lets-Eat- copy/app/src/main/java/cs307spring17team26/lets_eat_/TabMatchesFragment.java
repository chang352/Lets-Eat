package cs307spring17team26.lets_eat_;

/**
 * Created by nathanchang on 2/22/17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TabMatchesFragment extends Fragment {

    public TabMatchesFragment() {}

    private CharSequence email;
    private ListView matchesListView;
    private ListView listView1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_matches, container, false);

        Bundle account = this.getArguments();
        if (account!=null) {
            email = account.getCharSequence("email");
        }

        matchesListView = (ListView) rootView.findViewById(R.id.matchesListView);
        listView1 = (ListView) rootView.findViewById(R.id.listView1);

        final Context c = getActivity().getApplication();
        final RequestQueue queue = Volley.newRequestQueue(c);
        JsonObjectRequest j = new JsonObjectRequest(
                Request.Method.GET, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/matches/" + email, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray array = response.names();
                        final String[] emailList = new String[array.length()];
                        int j = 0;
                        for (int i = 1; i < array.length(); i++) {
                            try {
                                if (response.getInt(array.getString(i)) == 2) {
                                    emailList[j] = array.getString(i);
                                    emailList[j] = emailList[j].replace("_", ".");
                                    j++;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        final String[] nameList = new String[emailList.length];
                        getName(emailList, nameList, matchesListView, 2);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(j);

        JsonObjectRequest j1 = new JsonObjectRequest(
                Request.Method.GET, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/matches/" + email, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray array = response.names();
                        final String[] emailList = new String[array.length()];
                        int j = 0;
                        for (int i = 1; i < array.length(); i++) {
                            try {
                                if (response.getInt(array.getString(i)) == 0) {
                                    emailList[j] = array.getString(i);
                                    emailList[j] = emailList[j].replace("_", ".");
                                    j++;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        final String[] nameList = new String[emailList.length];
                        getName(emailList, nameList, listView1, 1);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(j1);
        return rootView;
    }

    public void getName(final String[] emailArray, final String[] nameArray, final ListView matchesListView, final int num) {
        Context c = getActivity().getApplication();
        final RequestQueue queue1 = Volley.newRequestQueue(c);
        for (int k = 0; k < emailArray.length; k++) {
            if (emailArray[k] == null) break;
            final int finalK = k;
            final JsonObjectRequest r = new JsonObjectRequest(
                    Request.Method.GET, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/users/" + emailArray[k], null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            getNameInRequest(response, nameArray, finalK);
                            System.out.println(response);
                            //nameArray[finalK] = response.getString("name");
                            //getNameInRequest(response, nameArray, finalK);
                            int l = 0;
                            while (nameArray[l] != null) {
                                l++;
                            }
                            String[] correct = new String[l];
                            for (int m = 0; m < l; m++) {
                                correct[m] = nameArray[m];
                            }
                            //searchesListView = (ListView)findViewById(R.id.searchesListView);
                            final ArrayAdapter<String> matchListsViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, correct);
                            //System.out.println(correct[0]);
                            if (num==2) {
                                matchesListView.setAdapter(matchListsViewAdapter);
                                matchesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(getActivity(), ProfileMatches.class);
                                        intent.putExtra("emailMatch", emailArray[position]);
                                        intent.putExtra("emailUser", email);
                                        getActivity().startActivity(intent);
                                    }
                                });
                            } else if (num==1) {
                                listView1.setAdapter(matchListsViewAdapter);
                                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(getActivity(), ProfileSearches.class);
                                        intent.putExtra("emailPotential", emailArray[position]);
                                        intent.putExtra("emailUser", email);
                                        intent.putExtra("status", "pending");
                                        getActivity().startActivity(intent);
                                    }
                                });
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue1.add(r);
        }
    }

    public void getNameInRequest(JSONObject response, String[] nameChange, int num) {
        try {
            nameChange[num] = response.getString("name");
            System.out.println(nameChange[num]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
