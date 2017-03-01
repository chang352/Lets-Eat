package cs307spring17team26.lets_eat_;

import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by nathanchang on 2/28/17.
 */

public class MyDialogFragment extends DialogFragment {

    private EditText editInfo;
    private Button okButton;
    private Button cancelButton;
    private String newText = "";

    public MyDialogFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sample_dialog, container, false);
        editInfo = (EditText) rootView.findViewById(R.id.editInfo);
        okButton = (Button) rootView.findViewById(R.id.okButton);
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newText = editInfo.getText().toString();
                //code for updating info in database
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
