package com.example.android.meetingsetup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;


public class MeetingActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private DatePicker datePicker;
    private EditText editText;
    private int hour;
    private int minute;
    private int day;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        /*
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        */


        datePicker = (DatePicker)findViewById(R.id.datePicker);
        day = datePicker.getDayOfMonth();
        month = datePicker.getMonth();
        year = datePicker.getYear();
        printDate(day, month, year);

        editText = (EditText)findViewById(R.id.editText);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        timePicker = (TimePicker)findViewById(R.id.timePicker);
        hour = timePicker.getHour();
        minute = timePicker.getMinute();
        printTime(hour, minute);
    }

    public void printTime(int hour, int minute) {
        System.out.println("hour: " + hour);
        System.out.println("minute: " + minute);
    }

    public void printDate(int day, int month, int year) {
        System.out.println("day: " + day);
        System.out.println("month: " + month);
        System.out.println("year: " + year);
    }

    public void sendMeeeting() {

    }
}
