package cs307spring17team26.lets_eat_;

import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;

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

public class DialogSettings extends DialogFragment {

    private EditText editInfo;
    private Button okButton;
    private Button cancelButton;
    private String newText = "";
    private TextView changeinfoText;
    private TextView errorView;
    private int position;
    private CharSequence email;

    public DialogSettings() {
        position = -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_settings, container, false);
        editInfo = (EditText) rootView.findViewById(R.id.editInfo);
        okButton = (Button) rootView.findViewById(R.id.okButton);
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        changeinfoText = (TextView) rootView.findViewById(R.id.changeInfoText);
        errorView = (TextView) rootView.findViewById(R.id.errorView);

        Bundle bundle = this.getArguments();
        if (bundle!=null) {
            position = bundle.getInt("position");
            email = bundle.getCharSequence("email");
        }

        editInfo.setFocusableInTouchMode(true);
        switch (position) {
            case 0:
                changeinfoText.setText("Distance Range"); editInfo.setInputType(InputType.TYPE_CLASS_NUMBER); break;
            case 1:
                changeinfoText.setText("Feedback"); break;
            case 2:
                changeinfoText.setText("Are you sure you want to log out?");
                editInfo.setFocusable(false);
                okButton.setText("YES");
                cancelButton.setText("NO");
                break;
            default: break;
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position==1) {
                    //send feedback to message
                }
                else if (position==2) {
                    //logout(), return back to login screen, logging out of account, clears all activities in the
                    Intent intent = new Intent(getActivity(), LoginAccountActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                errorView.setText("\n");
                newText = editInfo.getText().toString();
                JSONObject ob = new JSONObject();
                switch (position) {
                    case 0:try {ob.put("maxRange", newText);} catch (JSONException e) {e.printStackTrace();} break;
                    //case 1:try {ob.put("ageRange", newText);} catch (JSONException e) {e.printStackTrace();} break;
                    default: break;
                }
                Context c  = getActivity().getApplication();
                RequestQueue queue = Volley.newRequestQueue(c);
                JsonObjectRequest j = new JsonObjectRequest(
                        Request.Method.PUT, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/users/" + email, ob,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (position==0) {
                                        response.put("maxRange", Integer.parseInt(newText));
                                    }
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
