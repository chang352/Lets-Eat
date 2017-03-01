package cs307spring17team26.lets_eat_;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import android.content.Intent;

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
    private EditText passwordTextEdit;
    private EditText reenterTextEdit;
    private TextView existingAccountTextView;
    private Button createAccountButton;

    //checks if password has at least 5 characters, 1 capital letters, and 1 number
    public boolean isLegalPassword(String input) {
        if (input.length()<=5) return false; //length at least 5 characters
        if (!input.matches(".*[A-Z].*")) return false; //need at least 1 capital letter
        if (!input.matches(".*\\d+.*")) return false; //need at least 1 number
        return true;
    }

    //goes to different activity to show popup message of error
    private void popupActivity(String textEdit) {
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_creation);

        emailTextEdit = (EditText)findViewById(R.id.emailEditText);
        passwordTextEdit = (EditText)findViewById(R.id.passwordEditText);
        reenterTextEdit = (EditText)findViewById(R.id.reenterPasswordEditText);
        existingAccountTextView = (TextView)findViewById(R.id.existingAccountTextView);
        createAccountButton = (Button)findViewById(R.id.createAccountButton);

        //when user touches textEdit, keyboard will appear
        /*emailTextEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSoftKeyboard(emailTextEdit);
            }
        });
        passwordTextEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSoftKeyboard(passwordTextEdit);
            }
        });
        reenterTextEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSoftKeyboard(reenterTextEdit);
            }
        });*/

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
                String emailInput = emailTextEdit.getText().toString(); //user input for email
                String passwordInput = passwordTextEdit.getText().toString();
                String reenterInput = reenterTextEdit.getText().toString();
                //if user doesn't give valid email or is empty
                if (emailInput==null || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                    popupActivity("emailInput");
                }
                //check if email is already used
                //send request to the server to check database if email is already used or not
                /*else if () {
                    popupActivity("emailAlreadyUsed");
                }*/
                //if password is not has at least 5 characters, 1 capital letter, or 1 number, or is empty
                else if (passwordInput==null || !isLegalPassword(passwordInput)) {
                    popupActivity("passwordInput");
                }
                //else if email already used, or is empty
                else if (reenterInput==null || !passwordInput.equals(reenterInput)) {
                    popupActivity("reenterInput");
                }
                else { //data stored in database, go to login account UI page
                    //check database if email is already stored or not
                    //if not, create new entry in database for new account
                    finish();
                    //Intent intent = new Intent(ApplicationActivity.class);
                    //startActivity(intent);
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