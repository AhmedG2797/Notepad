package com.example.android.notepad;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    Context context;

    public ItemAdapter(@NonNull Context context, @NonNull List<Item> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        Item currentItem = getItem(position);

        TextView textView = listItemView.findViewById(R.id.t_title);
        textView.setText(currentItem.getTitle());

        return listItemView;
    }
}
