package com.example.customadapterdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

public class UserListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<User> userList;
    private int highlightedItemIndex = -1; // Изменено название переменной

    public UserListAdapter(Context context, ArrayList<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User currentUser = userList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.useritem, parent, false);
        }

        ImageView userImageView = convertView.findViewById(R.id.userpic);
        TextView userNameView = convertView.findViewById(R.id.name);
        TextView userPhoneView = convertView.findViewById(R.id.phone);

        userNameView.setText(currentUser.getName());
        userPhoneView.setText(currentUser.getPhoneNumber());

        userImageView.setImageResource(getImageResource(currentUser.getSex()));

        convertView.setBackgroundColor(context.getResources().getColor(position == highlightedItemIndex ? R.color.selected_item_color : R.color.default_item_color));

        return convertView;
    }

    private int getImageResource(Sex sex) {
        switch (sex) {
            case MAN: return R.drawable.user_man;
            case WOMAN: return R.drawable.user_woman;
            case UNKNOWN: return R.drawable.user_unknown;
            case OTHER: return R.drawable.user_other;
            default: return R.drawable.user_not_specified;
        }
    }

    public void setHighlightedItemIndex(int index) {
        highlightedItemIndex = index;
        notifyDataSetChanged();
    }

    public void addNewUser(User user) {
        userList.add(user);
        notifyDataSetChanged();
    }

    // Refactored sorting methods using lambda expressions
    public void sortUsersByName() {
        Collections.sort(userList, (u1, u2) -> u1.getName().compareTo(u2.getName()));
        notifyDataSetChanged();
    }

    public void sortUsersByPhoneNumber() {
        Collections.sort(userList, (u1, u2) -> u1.getPhoneNumber().compareTo(u2.getPhoneNumber()));
        notifyDataSetChanged();
    }

    public void sortUsersBySex() {
        Collections.sort(userList, (u1, u2) -> u1.getSex().compareTo(u2.getSex()));
        notifyDataSetChanged();
    }
}
