package com.example.victor.listadapterexample;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.w3c.dom.Text;
import java.util.concurrent.TimeUnit;

public class TimerActivity extends Activity
{


    private Button btnStart, btnStop, btnFinish;
    private Uri breakRingtone, workRingtone;
    private TextView textViewTimer, textDialog;
    final static Integer studytime = 10000;        // 3 minutes
    final static Integer breaktime = 5000;
    final static Integer longbreaktime = 15000;
    final static Integer ticktime = 1000;         // 1 second interval
    Integer counter = 1;
    private CounterClass WorkTimer, ShortBreakTimer, LongBreakTimer;

    public void playRingtoneAlarm(){
        breakRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtoneSound = RingtoneManager.getRingtone(getApplicationContext(), breakRingtone);

        if (ringtoneSound != null) {
            ringtoneSound.play();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);




        btnStart = (Button)findViewById(R.id.btnStart);
        btnStop = (Button)findViewById(R.id.btnStop);
        btnFinish = (Button)findViewById(R.id.btnBack);
        textViewTimer = (TextView)findViewById(R.id.textViewTimer);
        textDialog = (TextView)findViewById(R.id.textDialog);

        textViewTimer.setText("00:03:00");



        startTimer(counter);

        //final CounterClass countertimer = new CounterClass(studytime,ticktime);


        //  Button Start

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((counter % 2) == 1) {
                    WorkTimer.start();
                } else if ((counter % 8) == 0 ) {
                    LongBreakTimer.start();
                } else if ((counter % 2) == 0 ){
                    ShortBreakTimer.start();
                }

            }
        });

        //Button Stop
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((counter % 2) == 1) {
                    WorkTimer.cancel();
                } else if ((counter % 8) == 0 ) {
                    LongBreakTimer.cancel();
                } else if ((counter % 2) == 0 ){
                    ShortBreakTimer.cancel();
                }
            }
        });


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }



    public void startTimer (int counterID) {

        if ((counterID % 2) == 1) {
            WorkTimer = new CounterClass(studytime, ticktime);

        } else if ((counterID % 8) == 0 ) {
            LongBreakTimer = new CounterClass(longbreaktime, ticktime);        //long break time

        } else if ((counterID % 2) == 0 ){
            ShortBreakTimer = new CounterClass(breaktime, ticktime);

        }



    }

    public void createDialog (){
        FragmentManager fm = getFragmentManager();
        breakTimeDialog breakDialog = new breakTimeDialog();
        breakDialog.show(fm, "breakdialog");

}


    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            long millis = millisUntilFinished;
            String hourminsec = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

            System.out.println(hourminsec);
            textViewTimer.setText(hourminsec);

        }

        @Override
        public void onFinish() {
            if (counter % 2 == 1){
                textViewTimer.setText("BREAK TIME FOOL!");
                playRingtoneAlarm();
                createDialog();
                counter += 1;
                startTimer(counter);



            } else if (counter % 8 == 0) {
                textViewTimer.setText("LONG BREAK TIME");
                counter += 1;
                startTimer(counter);
            } else if (counter % 2 == 0) {
                textViewTimer.setText("Time to get back to Studying!");
                counter += 1;
                startTimer(counter);
            }


            textDialog.setText("BREAK TIME FOOL");

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class breakTimeDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            String alertMessage = "Good work! Take a twix";
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(alertMessage)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

}
