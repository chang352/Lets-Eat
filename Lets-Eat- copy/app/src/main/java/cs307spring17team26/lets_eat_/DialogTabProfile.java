package cs307spring17team26.lets_eat_;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
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

public class DialogTabProfile extends DialogFragment {

    private EditText editInfo;
    private Button okButton;
    private Button cancelButton;
    private TextView changeinfoText;
    private TextView errorView;
    private String newText = "";
    private int position;
    private CharSequence email;

    public DialogTabProfile() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_tab_profile, container, false);
        editInfo = (EditText) rootView.findViewById(R.id.editInfo);
        okButton = (Button) rootView.findViewById(R.id.okButton);
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        changeinfoText = (TextView) rootView.findViewById(R.id.changeInfoText);
        errorView = (TextView) rootView.findViewById(R.id.errorView);

        final String message = "Invalid: Must be a number.";

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            position = bundle.getInt("position");
            email = bundle.getCharSequence("email");
        }

        switch (position) {
            case 0:
                changeinfoText.setText("Name");
                break;
            case 1:
                changeinfoText.setText("Age");
                break;
            case 2:
                changeinfoText.setText("Location");
                break;
            case 3:
                changeinfoText.setText("Gender");
                break;
            case 4:
                changeinfoText.setText("Bio");
                break;
            default:
                break;
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorView.setText("\n");
                newText = editInfo.getText().toString();
                //if (newText==null && newText.isEmpty()) {dismiss();}
                JSONObject ob = new JSONObject();
                switch (position) {
                    case 0:try {ob.put("name", newText);} catch (JSONException e) {e.printStackTrace();} break;
                    case 1:if(newText.matches("-?\\d+(\\.\\d+)?")) {try {ob.put("age", newText);} catch (JSONException e) {e.printStackTrace();} break;}
                    case 2:try {ob.put("location", newText);} catch (JSONException e) {e.printStackTrace();} break;
                    case 3:try {ob.put("gender", newText);} catch (JSONException e) {e.printStackTrace();} break;
                    case 4:try {ob.put("bio", newText);} catch (JSONException e) {e.printStackTrace();} break;
                    default: break;
                }
                Context c = getActivity().getApplication();
                RequestQueue queue = Volley.newRequestQueue(c);
                JsonObjectRequest j = new JsonObjectRequest(
                        Request.Method.PUT, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/users/" + email, ob,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (position==0) {
                                        response.put("name", newText);
                                    } else if (position==1) {
                                        if (newText.matches("-?\\d+(\\.\\d+)?")) {
                                            response.put("age", Integer.parseInt(newText));
                                        } else {
                                            //errorView.setText(message);
                                        }
                                    } else if (position==2) {response.put("location", newText);
                                    } else if (position==3) {response.put("gender", newText);
                                    } else if (position==4) {response.put("bio", newText);}
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
