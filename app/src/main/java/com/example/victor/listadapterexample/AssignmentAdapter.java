package com.example.victor.listadapterexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by victor on 9/8/15.
 */
public class AssignmentAdapter extends BaseAdapter {

    private ArrayList<AssignmentRecord> alist = new ArrayList<AssignmentRecord>();

    private final Context aContext;

    public AssignmentAdapter (Context context){
        aContext = context;
    }


    public void addItem (AssignmentRecord item) {
        alist.add(item);
        notifyDataSetChanged();
    }

    public void deleteItem (AssignmentRecord item) {
        alist.remove(item);
    }

    @Override
    public int getCount() {
        return alist.size();
    }

    @Override
    public Object getItem(int position) {
        return alist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get current item
        final AssignmentRecord assignmentRecord = alist.get(position);

        RelativeLayout itemlayoutview = null;

        if (convertView == null){
            convertView = ((LayoutInflater)aContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.assignment_item, null);
        }
        itemlayoutview = (RelativeLayout)convertView;

        final TextView assignmentTitle = (TextView)convertView.findViewById(R.id.adapter_item_title);
        assignmentTitle.setText(assignmentRecord.getItemName());

        final TextView dueDateText = (TextView)convertView.findViewById(R.id.adapter_dueDate);
        dueDateText.setText(AssignmentRecord.standardDateformat.format(assignmentRecord.getDueDate()));

        final CheckBox statusBox = (CheckBox)convertView.findViewById(R.id.adapter_finish_checkbox);
        if (assignmentRecord.getCurrentStatus()== AssignmentRecord.Status.NOTFINISHED){
            statusBox.setChecked(false);
        } else {
            statusBox.setChecked(true);
        }

        statusBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //prompt to delete record;
                    assignmentRecord.setStatus(AssignmentRecord.Status.FINISHED);

                    // cancel intent for alarm manager


                } else {
                    // do nothing
                }

            }
        });




        return itemlayoutview;
    }
}
