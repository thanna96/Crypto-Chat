package com.example.thann.cryptochat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by thann on 11/4/2017.
 */

public class ContactList extends ArrayAdapter<Contacts> {

    private Activity context;
    List<Contacts> names;

    public ContactList(Activity context, List<Contacts> names) {
        super(context, R.layout.layout_contact_list, names);
        this.context = context;
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_contact_list,null,true);

        TextView textViewFirst = (TextView) listViewItem.findViewById(R.id.firstName);

        Contacts contact = names.get(position);
        textViewFirst.setText(String.valueOf(contact.getname()));

        return listViewItem;

    }

}
