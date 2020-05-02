package com.example.assignment.Item;

import android.graphics.drawable.Drawable;
import android.view.View;
/***
 * 리스트 아이템 액티비티이다.
 */
public class MemoListItem {
    private Drawable icon;
    private String name;
    private String date;
    private int emoticon;
    private String content;

    private String week;
    private String day;
    private int _id;
    /** 클릭했을시 할당 **/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEmoticon() {
        return emoticon;
    }

    public void setEmoticon(int emoticon) {
        this.emoticon = emoticon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
