package com.dry.dingransnotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.dry.dingransnotes.adapters.ListAdapter;
import com.dry.dingransnotes.beans.ItemBean;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
    List<ItemBean> listdata;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ListAdapter listAdapter;
    private static final String MY_PREFERENCES = "MY_NOTES";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        recyclerview = findViewById(R.id.recyclerview);
        sharedPreferences = MainActivity.this.getApplicationContext().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Gson gson =new Gson();
        try {
            String getNotes = sharedPreferences.getString("notes",null);
            listdata = gson.fromJson(getNotes,new TypeToken<List<ItemBean>>(){}.getType());
        }catch (Exception e1){
            try {
                initialization();
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }

        listAdapter = new ListAdapter(listdata, getApplicationContext(),editor);
        recyclerview.setLayoutManager(new GridLayoutManager(this,2));
        recyclerview.setAdapter(listAdapter);

    }

    private void initialization() throws ParseException {
        listdata = new ArrayList<ItemBean>();
        for(int i=1; i<=16; i++){
            listdata.add(new ItemBean("Note "+i,"Description "+i));
        }
        listdata.get(1).description = "Dingran Yang \nN01349848";

    }

    public void add_clicked(View view) {
        EditText edit_descr = new EditText(this);
        AlertDialog.Builder inputDialog = new AlertDialog.Builder(this);
        inputDialog.setTitle("Enter description")
                .setView(edit_descr);
        inputDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String description = edit_descr.getText().toString();
                int noteNo = listdata.size()+1;
                ItemBean itemBean = new ItemBean("Note "+noteNo,description);
                listdata.add(itemBean);
                listAdapter.notifyItemInserted(listdata.size()-1);
                Gson gson = new Gson();
                String json = gson.toJson(listdata);
                editor.putString("notes",json);
                editor.commit();

            }
        });
        inputDialog.setNegativeButton("Cancel",null);
        inputDialog.show();
    }

/*    private void getData() throws ParseException {
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
    }*/
}