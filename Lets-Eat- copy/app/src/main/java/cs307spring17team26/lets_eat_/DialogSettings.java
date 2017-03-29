package cs307spring17team26.lets_eat_;

import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;
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
    private boolean dismiss;

    public DialogSettings() {
        position = -1;
        dismiss = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_settings, container, false);
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
                changeinfoText.setText("Distance Range"); break;
            case 1:
                changeinfoText.setText("Age Range"); break;
            case 2:
                changeinfoText.setText("Are you sure you want to log out?");
                okButton.setText("YES");
                cancelButton.setText("NO");
                break;
            default: break;
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position==2) {
                    //logout(), return back to login screen, logging out of account, clears all activities in the
                    Intent intent = new Intent(getActivity(), LoginAccountActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                errorView.setText("\n");
                newText = editInfo.getText().toString();
                Context c  = getActivity().getApplication();
                RequestQueue queue = Volley.newRequestQueue(c);
                JsonObjectRequest j = new JsonObjectRequest(
                        Request.Method.PUT, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/users/" + email, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (position==0) {
                                        if (newText.matches("-?\\d+(\\.\\d+)?")) {
                                            response.put("maxRange", Integer.parseInt(newText));
                                            changeDismiss(true);
                                        } else {
                                            errorView.setText(message);
                                        }
                                    } else if (position==1) {
                                        if (newText.matches("-?\\d+(\\.\\d+)?")) {

                                        } else {
                                            errorView.setText(message);
                                        }
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
                dismiss(getDismiss());
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
    public void changeDismiss(boolean d) {
        dismiss = d;
    }
    public boolean getDismiss() {
        return dismiss;
    }
    public void dismiss(boolean dismiss) {
        if (dismiss) {
            dismiss();
        }
    }
}
