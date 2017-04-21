package cs307spring17team26.lets_eat_;

/**
 * Created by nathanchang on 2/22/17.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class TabSearchesFragment extends Fragment {

    public TabSearchesFragment() {
    }

    private CharSequence email;
    private ListView searchesListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_searches, container, false);

        Bundle account = this.getArguments();
        if (account != null) {
            email = account.getCharSequence("email");
        }

        searchesListView = (ListView) rootView.findViewById(R.id.searchesListView);

        final Context c = getActivity().getApplication();
        final RequestQueue queue = Volley.newRequestQueue(c);
        JsonObjectRequest j = new JsonObjectRequest(
                Request.Method.GET, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/users/" + email, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println(response);
                            int age = response.getInt("age");
                            //int[] location = (int[]) response.get("location");
                            RequestQueue q1 = Volley.newRequestQueue(c);
                            JsonObjectRequest j1 = new JsonObjectRequest(
                                    Request.Method.GET, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/search/user/"
                                    +email+"/age/"+age, null, new Response.Listener<JSONObject>() {
                                        @Override
                                public void onResponse(JSONObject response) {
                                            System.out.println(response);
                                            final JSONArray array = response.names();
                                            final String[] names = new String[response.length()];
                                            final String[]namesEmail = new String[response.length()];
                                            int namesCount = 0;
                                            for (int i = 0; i<response.length(); i++) {
                                                try {
                                                    JSONObject temp = response.getJSONObject(array.getString(i));
                                                    if (!temp.getString("_id").equals(email)) {
                                                        names[namesCount] = temp.getString("name");
                                                        namesEmail[namesCount] = temp.getString("_id");
                                                        namesCount++;
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            final String[] namesFinal = new String[namesCount];
                                            for (int i = 0; i<namesCount; i++) {
                                                namesFinal[i] = names[i];
                                            }
                                            final String[] namesEmailFinal = new String[namesCount];
                                            for (int i = 0; i<namesCount; i++) {
                                                namesEmailFinal[i] = namesEmail[i];
                                            }
                                            //System.out.println(names[0]);
                                            ArrayAdapter<String> searchListsViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, namesFinal);
                                            //System.out.println(names[0]);
                                            searchesListView.setAdapter(searchListsViewAdapter);
                                            searchesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    Intent intent = new Intent(getActivity(), ProfileSearches.class);
                                                    intent.putExtra("emailUser", email);
                                                    intent.putExtra("emailPotential", namesEmailFinal[position]);
                                                    intent.putExtra("status", "request");
                                                    getActivity().startActivity(intent);
                                                }
                                            });
                                        }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                            q1.add(j1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(j);
        return rootView;
    }
}
