package com.example.assignment.Item;

import android.graphics.drawable.Drawable;
import android.view.View;

public class MyItem {
    private Drawable icon;
    private String name;
    private String week;
    private String day;
    private int emoticon;
    // 클릭했을시 할당
    public View.OnClickListener onClickListener;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getEmoticon() {
        return emoticon;
    }

    public void setEmoticon(int emoticon) {
        this.emoticon = emoticon;
    }
}
