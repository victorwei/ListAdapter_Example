package com.example.victor.listadapterexample;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

import com.example.victor.listadapterexample.AssignmentRecord.Status;


/**
 * Created by victor on 9/14/15.
 */
public class AddAssignmentActivity extends Activity {

    //set notification variables
    private AlarmManager alarmManager;
    private Intent notifcationReceiverIntent;
    private PendingIntent notificationReceiverPendingIntent;


    //variables
    private static String dueDateString;
    private static String reminderDateString;
    private static TextView dueDateView;
    private static TextView reminderDateView;

    // variables for
    private Date aDate;
    private EditText aAssignmentText;
    private RadioButton aDefaultRbutton;
    private RadioGroup aStatusRGroup;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_assignment);



        // SET UP VIEW
        //aDefaultRbutton = (RadioButton)findViewById(R.id.statusNo);
        //aStatusRGroup = (RadioGroup)findViewById(R.id.statusGroup);
        aAssignmentText = (EditText)findViewById(R.id.assignment_title);
        dueDateView = (TextView)findViewById(R.id.duedate);
        reminderDateView = (TextView)findViewById(R.id.reminder_date);




        // button to choose due date

        final Button dueDatePicker = (Button)findViewById(R.id.datebutton);
        dueDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dialog to choose due date;
                dueDateDialog();

            }
        });


        // button to choose when to start receiving reminders for assignment

        final Button remindAssignment = (Button)findViewById(R.id.reminderbutton);
        remindAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog to choose reminder date;
                reminderDateDialog();
            }
        });


        // button for cancel button

        final Button cancelButton = (Button)findViewById(R.id.cancelbutton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        // button for submit button
        final Button submitButton = (Button)findViewById(R.id.submitbutton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String assignmentTitle = aAssignmentText.getText().toString();

                //Status status = Status.NOTFINISHED;

                String duedate = dueDateString;
                String reminderdate = reminderDateString;

                //set up new intent and package data and send

                Intent intent= new Intent();

                AssignmentRecord.sendIntent(intent, assignmentTitle, duedate, reminderdate);

                setResult(RESULT_OK, intent);
                finish();

            }
        });

    }





    // set default date


    public void setDefaultDate() {

        aDate = new Date();

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        setDueDateString(month, day, year);
        setReminderDateString(month, day, year);


    }


    // date picker dialog fragment

    public static class dueDatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker

            final Calendar currentdate = Calendar.getInstance();

            int month = currentdate.get(Calendar.MONTH);
            int day = currentdate.get(Calendar.DAY_OF_MONTH);
            int year = currentdate.get(Calendar.YEAR);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            setDueDateString(monthOfYear, dayOfMonth, year);

            dueDateView.setText(dueDateString);
        }
    }

    public static class reminderDatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker

            final Calendar currentdate = Calendar.getInstance();

            int month = currentdate.get(Calendar.MONTH);
            int day = currentdate.get(Calendar.DAY_OF_MONTH);
            int year = currentdate.get(Calendar.YEAR);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            setReminderDateString(monthOfYear, dayOfMonth, year);

            reminderDateView.setText(reminderDateString);
        }
    }



    private void dueDateDialog() {
        DialogFragment dialogFragment = new dueDatePickerFragment();
        dialogFragment.show(getFragmentManager(), "dueDatePicker");
    }

    private void reminderDateDialog() {
        DialogFragment dialogFragment = new reminderDatePickerFragment();
        dialogFragment.show(getFragmentManager(), "reminderDatePicker");
    }

    private static void setDueDateString (int Month, int Day, int Year ) {

        String monthString = Integer.toString(Month);
        String dayString = Integer.toString(Day);
        String yearString = Integer.toString(Year);

        if (Month < 10) {
            monthString = "0"+monthString;
        }
        if (Day < 10) {
            dayString = "0"+dayString;
        }

        dueDateString = monthString + "-" + dayString + "-" + yearString;
    }

    private static void setReminderDateString (int Month, int Day, int Year ) {

        String monthString = Integer.toString(Month);
        String dayString = Integer.toString(Day);
        String yearString = Integer.toString(Year);

        if (Month < 10) {
            monthString = "0"+monthString;
        }
        if (Day < 10) {
            dayString = "0"+dayString;
        }

        reminderDateString = monthString + "-" + dayString + "-" + yearString;
    }





}
