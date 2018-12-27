package com.example.thann.cryptochat;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by thann on 11/4/2017.
 */

public class ContactKeyList extends ArrayAdapter<Contacts> {

    private Activity context;
    List<Contacts> names;

    public ContactKeyList(Activity context, List<Contacts> names) {
        super(context, R.layout.layout_contactkey_list, names);
        this.context = context;
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_contactkey_list,null,true);

        TextView textViewFirst = (TextView) listViewItem.findViewById(R.id.nameforkey);

        textViewFirst.setGravity(Gravity.CENTER);
        textViewFirst.setPadding(16, 16, 16, 16);
        textViewFirst.setTextSize(14);
        textViewFirst.setTextColor(Color.parseColor("#000000"));
        textViewFirst.setSingleLine(true);
        textViewFirst.setEllipsize(TextUtils.TruncateAt.END);
        textViewFirst.setSingleLine(true);

        Contacts contact = names.get(position);
        textViewFirst.setText(String.valueOf(contact.getname()));

        return listViewItem;

    }

}