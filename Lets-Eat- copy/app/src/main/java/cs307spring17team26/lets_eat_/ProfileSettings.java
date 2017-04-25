package cs307spring17team26.lets_eat_;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import android.content.Context;
import org.json.JSONObject;
import org.json.JSONException;

public class ProfileSettings extends AppCompatActivity {

    private ListView settingsList;
    private FloatingActionButton closeButton;
    private CharSequence email;
    private int maxRange;
    private int ageRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Bundle account = getIntent().getExtras();
        if (account!=null) {
            email = account.getCharSequence("email");
        }

        Context c  = getApplication();
        RequestQueue queue = Volley.newRequestQueue(c);
        JsonObjectRequest j = new JsonObjectRequest(
                Request.Method.GET, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/users/" + email, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            maxRange = response.getInt("maxRange");
                            //ageRange = response.getInt("ageRange");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String distance = "Distance Range\n" + maxRange;
                        //String age = "Age Range\n" + Integer.toString(ageRange);
                        String feedback = "Send a Feedback\n";
                        String logout = "Log Out\n";
                        String[] info = {distance, feedback, logout};
                        settingsList = (ListView)findViewById(R.id.settingsList);
                        final ArrayAdapter<String> infoListViewAdapter = new ArrayAdapter<String>(ProfileSettings.this, android.R.layout.simple_list_item_1, info);
                        settingsList.setAdapter(infoListViewAdapter);
                        settingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                newInstance(position);
                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(j);

        closeButton = (FloatingActionButton)findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public DialogSettings newInstance(int position) {
        DialogSettings dialogSettings = new DialogSettings();
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putInt("position", 0); bundle.putCharSequence("email", email); break;
            case 1:
                bundle.putInt("position", 1); bundle.putCharSequence("email", email); break;
            case 2:
                bundle.putInt("position", 2); bundle.putCharSequence("email", email); break;
            default: break;
        }
        dialogSettings.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        dialogSettings.show(fm, "");
        return dialogSettings;
    }
}
