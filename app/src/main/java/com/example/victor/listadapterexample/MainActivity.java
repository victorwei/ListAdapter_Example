package com.example.victor.listadapterexample;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends ListActivity {

    private static final int ADD_ASSIGNMENT = 0;



    AssignmentAdapter aAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aAdapter = new AssignmentAdapter(getApplicationContext());

        getListView().setHeaderDividersEnabled(true);




        RelativeLayout headerView = (RelativeLayout)getLayoutInflater().inflate(R.layout.activity_main, getListView(), false);

        getListView().addHeaderView(headerView);


        if (null == headerView){
            return;
        }


        final Button addButton = (Button)findViewById(R.id.AddItemButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start intent to AddAssignmentActivity
                Intent intent = new Intent(getApplicationContext(),AddAssignmentActivity.class);
                startActivityForResult(intent, ADD_ASSIGNMENT);

            }
        });

        final Button timerButton = (Button)findViewById(R.id.TimerButton);
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dialog to choose due date;
                Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
                startActivity(intent);

                ;

            }
        });


        setListAdapter(aAdapter);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Log.i(TAG, "Entered onActivityResult()");



        if (requestCode == ADD_ASSIGNMENT) {
            if (resultCode == RESULT_OK) {
                AssignmentRecord newdata = new AssignmentRecord(data);
                aAdapter.addItem(newdata);

            }
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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





}
