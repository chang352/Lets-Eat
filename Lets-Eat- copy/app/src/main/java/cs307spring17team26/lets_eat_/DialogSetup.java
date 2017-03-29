package cs307spring17team26.lets_eat_;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by nathanchang on 2/28/17.
 */

public class DialogSetup extends DialogFragment {

    private EditText editInfo;
    private Button okButton;
    private Button cancelButton;
    private String newText = "";
    private TextView changeinfoText;
    private TextView errorView;
    private int position;
    private CharSequence email;

    public DialogSetup() {
        position = -1;
        email = "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dialog_settings, container, false);
        editInfo = (EditText) rootView.findViewById(R.id.editInfo);
        okButton = (Button) rootView.findViewById(R.id.okButton);
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        changeinfoText = (TextView) rootView.findViewById(R.id.changeInfoText);
        errorView = (TextView) rootView.findViewById(R.id.errorView);
        final String message = "Invalid: Must be a number.";

        Bundle bundle = this.getArguments();
        if (bundle!=null) {
            position = bundle.getInt("position");
            email = bundle.getCharSequence("email");
        }

        switch (position) {
            case 0:
                changeinfoText.setText("Name"); break;
            case 1:
                changeinfoText.setText("Age"); break;
            case 2:
                changeinfoText.setText("Location"); break;
            case 3:
                changeinfoText.setText("Gender"); break;
            case 4:
                changeinfoText.setText("Bio"); break;
            case 5:
                changeinfoText.setText("Distance Range"); break;
            case 6:
                changeinfoText.setText("Age Range"); break;
            default: break;
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorView.setText("\n");
                newText = editInfo.getText().toString();
                JSONObject ob = new JSONObject();
                switch (position) {
                    case 0:try {ob.put("name", "");} catch (JSONException e) {e.printStackTrace();} break;
                    case 1:try {ob.put("age", "");} catch (JSONException e) {e.printStackTrace();} break;
                    case 2:try {ob.put("location", "");} catch (JSONException e) {e.printStackTrace();} break;
                    case 3:try {ob.put("gender", "");} catch (JSONException e) {e.printStackTrace();} break;
                    case 4:try {ob.put("bio", "");} catch (JSONException e) {e.printStackTrace();} break;
                    case 5:try {ob.put("maxRange", "");} catch (JSONException e) {e.printStackTrace();} break;
                    case 6:try {ob.put("aegRange", "");} catch (JSONException e) {e.printStackTrace();} break;
                    default: break;

                }
                Context c  = getActivity().getApplication();
                RequestQueue queue = Volley.newRequestQueue(c);
                JsonObjectRequest j = new JsonObjectRequest(
                        Request.Method.PUT, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/account_info/" + email, ob,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                errorView.setText("\n");
                                newText = editInfo.getText().toString();
                                if (newText.isEmpty()) {
                                    //dismiss();
                                }
                                if ((position==1 || position==5 || position==6) && !newText.matches("[-+]?\\d*\\.?\\d+")) {
                                    errorView.setText(message);
                                } else {
                                    try {
                                        switch (position) {
                                            case 0:response.put("name", newText); break; //dismiss();
                                            case 1:response.put("age", newText); break;// dismiss();
                                            case 2:response.put("location", newText); break; //dismiss();
                                            case 3:response.put("gender", newText); break;// dismiss();
                                            case 4:response.put("bio", newText); break; //dismiss();
                                            case 5:response.put("maxRange", newText); break; //dismiss();
                                            case 6:response.put("ageRange", newText); break; //dismiss();
                                            default: break;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                queue.add(j);
                dismiss();
                //code for updating info in database
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return rootView;
    }
}
