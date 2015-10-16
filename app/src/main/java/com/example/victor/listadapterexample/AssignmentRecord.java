package com.example.victor.listadapterexample;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
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
    private String notificationTitle = new String();
    private String notificationText = new String();
    private String dueDateString = new String();
    private Date dueDate = new Date();
    private Date reminderDate = new Date();
    private Status defaultStatus = Status.NOTFINISHED;

    private AlarmManager alarmManager;
    private Intent notificationReceiverIntent, notificationIntent;
    private PendingIntent notificationReceiverPendingIntent, notificationPendingIntent;
    private static final int mID = 1;


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
        dueDateString = intent.getStringExtra(AssignmentRecord.DDATE);

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

        notificationReceiverIntent = new Intent(context, alarmNotificationReceiver.class);
        notificationReceiverPendingIntent = PendingIntent.getBroadcast(context, 0, notificationReceiverIntent, 0);


        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getRemindDayLong(), AlarmManager.INTERVAL_DAY, notificationReceiverPendingIntent);

    }

    public void cancelNotification ()
    {
        alarmManager.cancel(notificationReceiverPendingIntent);
    }


    public class alarmNotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){

            notificationTitle = itemName + " due: " + dueDateString;
            notificationText = "Stop procrastinating and get to work!";


            notificationIntent = new Intent (context, MainActivity.class);
            notificationPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder notificationBuilder = new Notification.Builder(context).setTicker("test").setContentTitle(notificationTitle)
                    .setContentText(notificationText).setSmallIcon(R.drawable.notification_template_icon_bg);

            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(mID, notificationBuilder.build());



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
