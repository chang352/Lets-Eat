package cs307spring17team26.lets_eat_;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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
                changeinfoText.setText("Age"); editInfo.setInputType(InputType.TYPE_CLASS_NUMBER); break;
            /*case 2:
                changeinfoText.setText("Location"); break;*/
            case 2:
                changeinfoText.setText("Gender"); break;
            case 3:
                changeinfoText.setText("Bio"); break;
            case 4:
                changeinfoText.setText("Distance Range"); editInfo.setInputType(InputType.TYPE_CLASS_NUMBER); break;
            /*case 5:
                changeinfoText.setText("Age Range"); break;*/
            default: break;
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorView.setText("\n");
                newText = editInfo.getText().toString();
                JSONObject ob = new JSONObject();
                switch (position) {
                    case 0:try {ob.put("name", newText);} catch (JSONException e) {e.printStackTrace();} break;
                    case 1:try {ob.put("age", newText);} catch (JSONException e) {e.printStackTrace();} break;
                    //case 2:try {ob.put("location", newText);} catch (JSONException e) {e.printStackTrace();} break;
                    case 2:try {ob.put("gender", newText);} catch (JSONException e) {e.printStackTrace();} break;
                    case 3:try {ob.put("bio", newText);} catch (JSONException e) {e.printStackTrace();} break;
                    case 4:try {ob.put("maxRange", newText);} catch (JSONException e) {e.printStackTrace();} break;
                    //case 5:try {ob.put("ageRange", newText);} catch (JSONException e) {e.printStackTrace();} break;
                    default: break;

                }
                Context c  = getActivity().getApplication();
                RequestQueue queue = Volley.newRequestQueue(c);
                JsonObjectRequest j = new JsonObjectRequest(
                        Request.Method.PUT, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/users/" + email, ob,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                errorView.setText("\n");
                                newText = editInfo.getText().toString();
                                if (newText.isEmpty()) {
                                    //dismiss();
                                }
                                switch (position) {
                                    case 0:try {response.put("name", newText);} catch (JSONException e) {e.printStackTrace();}break;
                                    case 1:try {response.put("age", newText);} catch (JSONException e) {e.printStackTrace();}break;
                                    //case 2:try {response.put("location", newText);} catch (JSONException e) {e.printStackTrace();}break;
                                    case 2:try {response.put("gender", newText);} catch (JSONException e) {e.printStackTrace();}break;
                                    case 3:try {response.put("bio", newText);} catch (JSONException e) {e.printStackTrace();}break;
                                    case 4:try {response.put("maxRange", newText);} catch (JSONException e) {e.printStackTrace();}break;
                                    //case 5:if(newText.matches("[-+]?\\d*\\.?\\d+")) {try {response.put("ageRange", newText);} catch (JSONException e) {e.printStackTrace();}break;}
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                queue.add(j);
                dismiss();
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
