package cs307spring17team26.lets_eat_;

/**
 * Created by nathanchang on 2/22/17.
 */

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import android.widget.TextView;

public class TabProfileFragment extends Fragment{

    public TabProfileFragment() {
        //emailString = "";
        nameString = "";
        age = -1;
        locationString = "";
        genderString = "";
        bioString = "";
    }

    private ListView infoListView;
    private ImageButton profilePicButton;
    private FloatingActionButton settingsButton;
    private CharSequence email;
    //private String emailString;
    private String nameString;
    private int age;
    private String locationString;
    private String genderString;
    private String bioString;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.tab_profile, container, false);

        Bundle account = this.getArguments();
        if (account!=null) {
            email = account.getCharSequence("email");
            //emailString = email.toString();
        }

        Notification.sendNotifications(getActivity().getApplication(), email.toString());

        profilePicButton = (ImageButton)rootView.findViewById(R.id.profilePicButton);
        profilePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //final TextView test = (TextView)rootView.findViewById(R.id.textView3);
        Context c  = getActivity().getApplicationContext();
        RequestQueue queue = Volley.newRequestQueue(c);
        JsonObjectRequest j = new JsonObjectRequest(
                Request.Method.GET, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/users/" + email, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //test.setText("RESPONSE" + response.toString());
                        try {
                            //test.setText("TRY" + response.toString());
                            nameString = response.getString("name");
                            age = response.getInt("age");
                            //locationString = response.getString("location");
                            genderString = response.getString("gender");
                            bioString = response.getString("bio");
                        } catch (JSONException e) {
                            //test.setText("CATCH" + response.toString());
                            e.printStackTrace();
                        }
                        String[] info = {nameString, Integer.toString(age), genderString, bioString};
                        infoListView = (ListView)rootView.findViewById(R.id.infoListView);
                        final ArrayAdapter<String> infoListViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, info);
                        infoListView.setAdapter(infoListViewAdapter);
                        infoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //show input text to change info
                                newInstance(position);
                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //test.setText("ERROR" + error.toString());
            }
        });
        queue.add(j);

        settingsButton = (FloatingActionButton)rootView.findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileSettings.class);
                Bundle account = new Bundle();
                account.putCharSequence("email", email);
                intent.putExtras(account);
                startActivity(intent);
            }
        });
        return rootView;
    }

    public DialogTabProfile newInstance(int position) {
        DialogTabProfile dialogTabProfile = new DialogTabProfile();
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putInt("position", 0); bundle.putCharSequence("email", email); break;
            case 1:
                bundle.putInt("position", 1); bundle.putCharSequence("email", email); break;
            case 2:
                bundle.putInt("position", 2); bundle.putCharSequence("email", email); break;
            case 3:
                bundle.putInt("position", 3); bundle.putCharSequence("email", email); break;
            case 4:
                bundle.putInt("position", 4); bundle.putCharSequence("email", email); break;
            default: break;
        }
        dialogTabProfile.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        dialogTabProfile.show(fm, "");
        return dialogTabProfile;
    }
}
