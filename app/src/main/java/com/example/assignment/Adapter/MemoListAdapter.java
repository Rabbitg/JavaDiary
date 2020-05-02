package com.example.assignment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.assignment.Item.MemoListItem;
import com.example.assignment.R;

import java.util.ArrayList;
/***
 * 리스트뷰에서 위젯 불러오는 액티비티이다.
 */
public class MemoListAdapter extends BaseAdapter {

    private ArrayList<MemoListItem> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public MemoListItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        /** 'listview_custom' Layout을 inflate하여 convertView 참조 획득 **/
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_custom, parent, false);
        }

        /** 'listview_custom'에 정의된 위젯에 대한 참조 획득 **/
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name) ;
        TextView tv_week = (TextView) convertView.findViewById(R.id.tv_week) ;
        TextView tv_day = (TextView) convertView.findViewById(R.id.tv_day) ;
        ImageView iv_emoticon = (ImageView) convertView.findViewById(R.id.basic_emoticon_selected);

        /** 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 **/
        MemoListItem myItem = getItem(position);

        /** 각 위젯에 세팅된 아이템을 뿌려준다 **/
        tv_name.setText(myItem.getName());
        tv_week.setText(myItem.getWeek());
        tv_day.setText(myItem.getDay());
        iv_emoticon.setImageResource(myItem.getEmoticon());


        return convertView;
    }

    /** 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 **/
    public void addItem(String name, String week, String day, int emoticon) {

        MemoListItem mItem = new MemoListItem();
        /** MyItem에 아이템을 setting한다. **/
        mItem.setName(name);
        mItem.setWeek(week);
        mItem.setDay(day);
        mItem.setEmoticon(emoticon);

        /** mItems에 MyItem을 추가한다. **/
        mItems.add(mItem);

    }
}
