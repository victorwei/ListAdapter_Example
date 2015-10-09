package com.example.victor.listadapterexample;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by victor on 9/8/15.
 */
public class AssignmentRecord {




    public enum Status {
        NOTFINISHED, FINISHED
    };

    public final static String ITEM = "item";
    public final static String STATUS = "status";
    public final static String DDATE = "due_date";
    public final static String RDATE = "reminder_date";
    public final static String REMINDER = "reminder";
    public final static String FILE = "filename";


    private String itemName = new String();
    //private String itemType = new String();
    private Date dueDate = new Date();
    private Date reminderDate = new Date();
    private Status defaultStatus = Status.NOTFINISHED;

    private AlarmManager alarmManager;
    private Intent notifcationReceiverIntent, notificationIntent;
    private PendingIntent notificationReceiverPendingIntent, notificationPendingIntent;


    public final static SimpleDateFormat standardDateformat = new SimpleDateFormat("mm-dd-yyyy", Locale.US);



    AssignmentRecord(String item, Date duedate, Date reminderdate) {
        this.itemName = item;
        this.dueDate = duedate;
        this.reminderDate = reminderdate;
        //this.currentStatus = status;

    }

    AssignmentRecord (Intent intent){
        itemName = intent.getStringExtra(AssignmentRecord.ITEM);
        //defaultStatus = Status.valueOf(intent.getStringExtra(AssignmentRecord.STATUS));

        try {
            dueDate = AssignmentRecord.standardDateformat.parse(intent.getStringExtra(AssignmentRecord.DDATE));
        } catch (ParseException e) {
            dueDate = new Date();
        }
        try {
            reminderDate = AssignmentRecord.standardDateformat.parse(intent.getStringExtra(AssignmentRecord.RDATE));
        } catch (ParseException e) {
            reminderDate = new Date();
        }

    }

    public void setReminderNotification (Context context) {

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        notifcationReceiverIntent = new Intent(context, alarmNotificationReceiver.class);
        notificationReceiverPendingIntent = PendingIntent.getBroadcast(context, 0, notifcationReceiverIntent, 0);


        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getRemindDayLong(), AlarmManager.INTERVAL_DAY, notificationReceiverPendingIntent);

    }

    public void cancelNotification ()
    {
        alarmManager.cancel(notificationReceiverPendingIntent);
    }


    public class alarmNotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){

            notificationIntent = new Intent (context, MainActivity.class);

            notificationPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

            Notification.Builder notificationBuilder = new Notification.Builder(context).setTicker("test");



        }
    }






    // use String values and create Intent

    public static void sendIntent(Intent intent, String itemtitle, String duedate, String reminddate) {

        intent.putExtra(AssignmentRecord.ITEM, itemtitle);
        intent.putExtra(AssignmentRecord.DDATE, duedate);
        intent.putExtra(AssignmentRecord.RDATE, reminddate);
        //intent.putExtra(AssignmentRecord.STATUS, status);

    }

    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemname) {
        itemName = itemname;
    }
    public Status getCurrentStatus() {
        return defaultStatus;
    }
    public void setStatus(Status status){
        defaultStatus = status;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate ( Date dDate) {
        dueDate = dDate;
    }
    public Date getReminderDate() {
        return reminderDate;
    }
    public void setReminderDate (Date rDate){
        reminderDate = rDate;
    }
    public Long getRemindDayLong() {
        Calendar targetdate = Calendar.getInstance();
        targetdate.setTime (reminderDate);
        int month = targetdate.get(Calendar.MONTH);
        int day = targetdate.get(Calendar.DAY_OF_MONTH);
        int year = targetdate.get(Calendar.YEAR);

        return targetdate.getTimeInMillis();

    }


}
