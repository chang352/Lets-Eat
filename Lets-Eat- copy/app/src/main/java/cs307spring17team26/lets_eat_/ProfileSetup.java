package cs307spring17team26.lets_eat_;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.content.Intent;

public class ProfileSetup extends AppCompatActivity {

    private CharSequence email;
    private String emailString;
    private String nameString;
    private int age;
    private String locationString;
    private String genderString;
    private String bioString;
    private int maxDistance;
    private int minAge;
    private int maxAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setup);
        ImageView profilePic = (ImageView) findViewById(R.id.profilePic);
        ListView info = (ListView) findViewById(R.id.info);
        FloatingActionButton doneButton = (FloatingActionButton) findViewById(R.id.doneButton);

        Bundle account = getIntent().getExtras();
        if (account!=null) {
            email = account.getCharSequence("email");
        }

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileSetup.this, ApplicationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("email", email);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //just display the property, dialogsetup will use PUT to change info
        String[] list = {"'Name'", "'Age", "'Location'", "'Gender'", "'Bio'", "'Distance Range'", "'Age Range"};
        ArrayAdapter<String> infoListViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        info.setAdapter(infoListViewAdapter);
        info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newInstance(position);
            }
        });
    }

    public void showDialog(DialogFragment df) {
        FragmentManager fm = getSupportFragmentManager();
        df.show(fm, "Test");
    }
    public DialogSetup newInstance(int position) {
        DialogSetup dialogSetup = new DialogSetup();
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
            case 5:
                bundle.putInt("position", 5); bundle.putCharSequence("email", email); break;
            case 6:
                bundle.putInt("position", 6); bundle.putCharSequence("email", email); break;
            default: break;
        }
        dialogSetup.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        dialogSetup.show(fm, "");
        return dialogSetup;
    }
}
