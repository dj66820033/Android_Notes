package com.dry.dingransnotes;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.dry.dingransnotes.adapters.ListAdapter;
import com.dry.dingransnotes.beans.ItemBean;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btn_add;
    RecyclerView recyclerview;
    List<ItemBean> listdata = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        recyclerview = findViewById(R.id.recyclerview);

        try {
            initialization();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            getData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initialization() throws ParseException {
        ItemBean data1 = new ItemBean();
        data1.name = "Note 1";
        data1.description = "Description Note 1";
        data1.date = new Date();
        data1.priority = false;
        listdata.add(data1);

        ItemBean data2 = new ItemBean();
        data2.name = "Note 2";
        data2.description = "Dingran Yang \nN01349848";
        data2.date = new Date();
        data2.priority = true;
        listdata.add(data2);

        for(int i=3; i<16; i++){
            ItemBean data = new ItemBean();
            data.name = "Note " + i;
            data.description = "Description Note " + i;
            data.date = new Date();
            data.priority = false;
            listdata.add(data);
        }

        ListAdapter listAdapter = new ListAdapter(listdata, getApplicationContext());
        recyclerview.setAdapter(listAdapter);
    }

    public void add_clicked(View view) {
        EditText edit_descr = new EditText(this);
        AlertDialog.Builder inputDialog = new AlertDialog.Builder(this);
        inputDialog.setTitle("Enter description")
                .setView(edit_descr);
        inputDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ItemBean data = new ItemBean();

                int i = recyclerview.getAdapter().getItemCount()+1;
                data.name = "Note " + i;
                data.description = edit_descr.getText().toString();
                data.date = new Date();
                data.priority = false;
                listdata.add(data);

                ListAdapter listAdapter = new ListAdapter(listdata, getApplicationContext());
                recyclerview.setAdapter(listAdapter);
            }
        });
        inputDialog.setNegativeButton("Cancel",null);
        inputDialog.show();
    }

    private void getData() throws ParseException {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(!sharedPreferences.equals(null)){
            String name = "";
            String description = "";
            Date date = null;
            Boolean priority;
            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy ");
            Map<String, ?> allContent = sharedPreferences.getAll();
            for(Map.Entry<String, ?>  entry : allContent.entrySet()){
                String key = entry.getKey();
                Log.e("key",key);
//            String[] keys = key.split(":");
//            String keyname = keys[0];
//            //int position = Integer.parseInt(keys[1]);
//            if (keyname.equals("name")){
//                name = (String) entry.getValue();
//            }else if (keyname.equals("description")){
//                description = (String) entry.getValue();
//            }else if (keyname.equals("date")){
//                SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy ");
//                date = format.parse((String) entry.getValue());
//            }else if (keyname.equals("priority")){
//                priority = (Boolean) entry.getValue();
//                ItemBean data = new ItemBean();
//                data.name = name;
//                data.description = description;
//                data.date = date;
//                data.priority = priority;
//                listdata.add(data);
//            }
                //int position = Integer.parseInt(key);
                String values = (String) entry.getValue();
                String[] value = values.split("::::");
                Log.e("Description",value[1]);
                name=value[0];
                description = value[1];
                date = format.parse(value[2]);
                priority = Boolean.parseBoolean(value[3]);
                ItemBean data = new ItemBean();
                data.name = name;
                data.description = description;
                data.date = date;
                data.priority = priority;
                listdata.add(data);
            }
            ListAdapter listAdapter = new ListAdapter(listdata, getApplicationContext());
            recyclerview.setAdapter(listAdapter);
        }
    }
}