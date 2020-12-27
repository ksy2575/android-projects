package com.example.nonogram_201222;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

public class CustomAdapter  extends ArrayAdapter<HashMap<Integer, String>> {
    private HashMap<Integer, String> items;
    Context context;

    public CustomAdapter(@NonNull Context context, int resource, HashMap<Integer, String> items){
        super(context, resource);
        this.items = items;
        this.context = context;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item_view, null, true);

        //title
        TextView title = convertView.findViewById(R.id.title_view);
        title.setText(items.get(position).toString());

        //icon
        ImageView icon = convertView.findViewById(R.id.icon_view1);
        Context context = icon.getContext();
        int id = context.getResources().getIdentifier("a" + position, "drawable",
                context.getPackageName());
        icon.setImageResource(id);

        return convertView;
    }


}