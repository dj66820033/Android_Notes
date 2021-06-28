package com.dry.dingransnotes.beans;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ItemBean {
    public String name, description;
    public Boolean priority;
    public String date;

    public ItemBean(String name, String description) {
        this.name = name;
        this.description = description;
        this.priority = false;
        SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy",new Locale("en"));
        this.date = format.format(new Date());
    }
}
