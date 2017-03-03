package cs307spring17team26.lets_eat_;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class AccountCreationActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;

    private View mContentView;
    private View mControlsView;
    private boolean mVisible;

    private EditText emailTextEdit;
    private TextView emailTextView;
    private EditText passwordTextEdit;
    private TextView passwordTextView;
    private EditText reenterTextEdit;
    private TextView reenterTextView;
    private TextView existingAccountTextView;
    private Button createAccountButton;
    private TextView errorTextView;

    //checks if password has at least 5 characters, 1 capital letters, and 1 number
    public boolean isLegalPassword(String input) {
        if (input.length()<=5) return false; //length at least 5 characters
        if (!input.matches(".*[A-Z].*")) return false; //need at least 1 capital letter
        if (!input.matches(".*\\d+.*")) return false; //need at least 1 number
        return true;
    }

    //goes to different activity to show popup message of error
    /*private void popupActivity(String textEdit) {
        if (textEdit.equals("emailInput")) { //invalid email popup
            Intent intent = new Intent(this, PopupInvalidEmail.class);
            startActivity(intent);
        }
        else if (textEdit.equals("emailAlreadyUsed")) {
            Intent intent = new Intent(this, PopupEmailAlreadyUsed.class);
            startActivity(intent);
        }
        else if (textEdit.equals("passwordInput")) { //invalid password popup
            Intent intent = new Intent(this, PopupInvalidPassword.class);
            startActivity(intent);
        }
        else if (textEdit.equals("reenterInput")) { //passwords don't match popup
            Intent intent = new Intent(this, PopupPasswordDontMatch.class);
            startActivity(intent);
        }
    }*/

View focusView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_creation);

        emailTextEdit = (EditText)findViewById(R.id.emailEditText);
        emailTextView = (TextView)findViewById(R.id.emailTextView);
        passwordTextEdit = (EditText)findViewById(R.id.passwordEditText);
        passwordTextView = (TextView)findViewById(R.id.passwordTextView);
        reenterTextEdit = (EditText)findViewById(R.id.reenterPasswordEditText);
        reenterTextView = (TextView)findViewById(R.id.reenterTextView);
        existingAccountTextView = (TextView)findViewById(R.id.existingAccountTextView);
        createAccountButton = (Button)findViewById(R.id.createAccountButton);
        errorTextView = (TextView)findViewById(R.id.errorTextView);

        //reset errors and messages
        emailTextView.setError(null);
        passwordTextView.setError(null);
        reenterTextView.setError(null);
        emailTextView.setText("");
        passwordTextView.setText("");
        reenterTextView.setText("");
        errorTextView.setText("");

        //if user has existing account, goes back to login account UI activity, login UI is main page
        existingAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(this, LoginAccountActivity.class);
                //startActivity(intent);
                finish();
            }
        });

        //when user clicks the create account button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailInput = emailTextEdit.getText().toString(); //user input for email
                final String passwordInput = passwordTextEdit.getText().toString();
                String reenterInput = reenterTextEdit.getText().toString();

                emailTextView.setError(null);
                passwordTextView.setError(null);
                reenterTextView.setError(null);
                emailTextView.setText("");
                passwordTextView.setText("");
                reenterTextView.setText("");

                //if user doesn't give valid email or is empty
                if (emailInput==null || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                    //popupActivity("emailInput");
                    emailTextView.setError(getString(R.string.error_invalid_email));
                    focusView = emailTextView;
                    emailTextView.setText("Invalid Email.");
                }
                //if password is not has at least 5 characters, 1 capital letter, or 1 number, or is empty
                else if (passwordInput==null || !isLegalPassword(passwordInput)) {
                    //popupActivity("passwordInput");
                    passwordTextView.setError(getString(R.string.error_invalid_password));
                    focusView = passwordTextView;
                    passwordTextView.setText("Invalid Password.");
                }
                //else if email already used, or is empty
                else if (reenterInput==null || !passwordInput.equals(reenterInput)) {
                    //popupActivity("reenterInput");
                    reenterTextView.setError(getString(R.string.error_invalid_password));
                    focusView = reenterTextView;
                    reenterTextView.setText("Passwords don't match.");
                }
                //check if email is already used, send request to server to check if email is already used
                else {
                    Context c  = getApplication();
                    RequestQueue queue = Volley.newRequestQueue(c);
                    JsonObjectRequest j = new JsonObjectRequest(
                            Request.Method.GET, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/users/" + emailInput, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    //if email already used, invalid email error message
                                    emailTextView.setError(getString(R.string.error_invalid_email));
                                    focusView = emailTextView;
                                    emailTextView.setText("Email already used.");
                                    //makeNewAccount(emailInput, passwordInput, errorTextView);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    /*emailTextView.setError(getString(R.string.error_invalid_email));
                                    focusView = emailTextView;
                                    emailTextView.setText("Email already used.");*/
                                    makeNewAccount(emailInput, passwordInput, errorTextView);
                                }
                    });
                    queue.add(j);
                }
            }
        });

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        /*mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });*/

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    public void makeNewAccount(String email, String password, final TextView errorTextView) {

        JSONObject object = new JSONObject();
        try {
            object.put("_id", email);
            object.put("password", password);
        } catch(JSONException e) {
            errorTextView.setText("Error occurred when creating your account. Please try again.");
            return;
        }
        Context c  = getApplication();
        RequestQueue queue = Volley.newRequestQueue(c);
        JsonObjectRequest j = new JsonObjectRequest(
                Request.Method.PUT, "http://ec2-52-24-61-118.us-west-2.compute.amazonaws.com/account_info/" + email, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent = new Intent(AccountCreationActivity.this, ApplicationActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //errorTextView.setText("Error occurred when creating your account. Please try again.");
                        Intent intent = new Intent(AccountCreationActivity.this, ApplicationActivity.class);
                        startActivity(intent);
                    }
        });
        queue.add(j);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };

    private final Handler mHideHandler = new Handler();
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
