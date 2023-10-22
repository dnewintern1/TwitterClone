package com.base.twitterclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends ArrayAdapter<UserDetails> {
    private int layoutResource;

    public UserAdapter(Context context, int resource, List<UserDetails> objects) {
        super(context, resource, objects);
        layoutResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(layoutResource, null);
        }

        UserDetails user = getItem(position);
        if (user != null) {
            TextView nameTextView = view.findViewById(R.id.userNameTextView);
            TextView checkBox = view.findViewById(R.id.checkBox);
            TextView checkText = view.findViewById(R.id.checkText);


            nameTextView.setText(user.getProfileName());
        }

        return view;
    }
}