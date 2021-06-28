package com.dry.dingransnotes.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dry.dingransnotes.R;
import com.dry.dingransnotes.beans.ItemBean;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    private final List<ItemBean> listdata;
    private final Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public ListAdapter(List<ItemBean> listdata, Context context,SharedPreferences.Editor editor){
        this.listdata = listdata;
        this.context = context;
        this.editor = editor;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {

        holder.txt_notename.setText(listdata.get(position).name);
        holder.txt_description.setText(listdata.get(position).description);
        holder.txt_date.setText(listdata.get(position).date);
        holder.ckb_priority.setChecked(listdata.get(position).priority);

//        editor = sharedPreferences.edit();
//        editor.putString(String.valueOf(position),name+"::::"+description+"::::"+date+"::::"+priority);
//        editor.putString("name:"+position,holder.txt_notename.getText().toString());
//        editor.putString("description:"+position,holder.txt_description.getText().toString());
//        editor.putString("date:"+position,holder.txt_date.getText().toString());
//        editor.putBoolean("priority:"+position,holder.ckb_priority.isChecked());
//        editor.commit();

        holder.ckb_priority.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String name = listdata.get(position).name;
                if (isChecked){
                    listdata.get(position).priority = true;
                    setSharedPreference();
                    Toast.makeText(context,name + " is high priority", Toast.LENGTH_LONG).show();
                }else{
                    listdata.get(position).priority = false;
                    setSharedPreference();
                    Toast.makeText(context,name + " is low priority", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void setSharedPreference(){
        Gson gson = new Gson();
        String json = gson.toJson(listdata);
        editor.putString("notes",json);
        editor.commit();
    }

    @Override
    public int getItemCount() {
        if (listdata != null){
            return listdata.size();
        }
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_notename,txt_description,txt_date;
        private CheckBox ckb_priority;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txt_notename = itemView.findViewById(R.id.txt_notename);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_date = itemView.findViewById(R.id.txt_date);
            ckb_priority = itemView.findViewById(R.id.ckb_priority);
        }

        public void setData(ItemBean itemBean){
            txt_notename.setText(itemBean.name);
            txt_description.setText(itemBean.description);

            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy ");
            String dateString = format.format( itemBean.date );
            txt_date.setText(dateString);

            ckb_priority.setChecked(itemBean.priority);
        }
    }
}
