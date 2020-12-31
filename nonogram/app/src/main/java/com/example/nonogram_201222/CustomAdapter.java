package com.example.nonogram_201222;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;



public class CustomAdapter  extends ArrayAdapter<HashMap<Integer, String>> {
    private HashMap<Integer, String> items;
    Context context;
    ViewHolder viewHolder;
    class ViewHolder{
        TextView title;
        ImageView icon1;
        ImageView icon2;
        ImageView icon3;
        ImageView icon4;
        ImageView icon5;
        ImageView icon6;
        LinearLayout stageGrid;
    }

    public CustomAdapter(@NonNull Context context, int resource, HashMap<Integer, String> items){
        super(context, resource);
        this.items = items;
        this.context = context;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_view, null, true);

            viewHolder = new ViewHolder();

            //title
            viewHolder.title = convertView.findViewById(R.id.title_view);
            //icon
            viewHolder.icon1 = convertView.findViewById(R.id.icon_view1);
            viewHolder.icon2 = convertView.findViewById(R.id.icon_view2);
            viewHolder.icon3 = convertView.findViewById(R.id.icon_view3);
            viewHolder.icon4 = convertView.findViewById(R.id.icon_view4);
            viewHolder.icon5 = convertView.findViewById(R.id.icon_view5);
            viewHolder.icon6 = convertView.findViewById(R.id.icon_view6);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }



        viewHolder.title.setText(items.get(position).toString());

        int id;
        int i = 0;
        Context context;

        //st1_1 형식으로 저장된 사진 파일 불러오기 - 나중에 DB와 연동
        context = viewHolder.icon1.getContext();
        if(true){
            id = context.getResources().getIdentifier("st" + position + "_" + i++, "drawable",
                    context.getPackageName());
        }else{
            id = context.getResources().getIdentifier("aaa", "drawable",
                    context.getPackageName());
        }
        viewHolder.icon1.setImageResource(id);


        context = viewHolder.icon2.getContext();
        if(false){
            id = context.getResources().getIdentifier("st" + position + "_" + i++, "drawable",
                    context.getPackageName());
        }else{
            id = context.getResources().getIdentifier("aaa", "drawable",
                    context.getPackageName());
        }
        viewHolder.icon2.setImageResource(id);

        context = viewHolder.icon3.getContext();
        if(true){
            id = context.getResources().getIdentifier("st" + position + "_" + i++, "drawable",
                    context.getPackageName());
        }else{
            id = context.getResources().getIdentifier("aaa", "drawable",
                    context.getPackageName());
        }
        viewHolder.icon3.setImageResource(id);

        context = viewHolder.icon4.getContext();
        if(true){
            id = context.getResources().getIdentifier("st" + position + "_" + i++, "drawable",
                    context.getPackageName());
        }else{
            id = context.getResources().getIdentifier("aaa", "drawable",
                    context.getPackageName());
        }
        viewHolder.icon4.setImageResource(id);

        context = viewHolder.icon5.getContext();
        if(true){
            id = context.getResources().getIdentifier("st" + position + "_" + i++, "drawable",
                    context.getPackageName());
        }else{
            id = context.getResources().getIdentifier("aaa", "drawable",
                    context.getPackageName());
        }
        viewHolder.icon5.setImageResource(id);

        context = viewHolder.icon6.getContext();
        if(true){
            id = context.getResources().getIdentifier("st" + position + "_" + i++, "drawable",
                    context.getPackageName());
        }else{
            id = context.getResources().getIdentifier("aaa", "drawable",
                    context.getPackageName());
        }
        viewHolder.icon6.setImageResource(id);


        viewHolder.stageGrid = convertView.findViewById(R.id.stageGrid);

        //201229 펼쳐지는 리스트뷰 구현 - 다른 리스트를 접는 기능은 고민 중
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewHolder.stageGrid.getVisibility() == View.GONE){
                    viewHolder.stageGrid.setVisibility(View.VISIBLE);
                }
                else{
                    viewHolder.stageGrid.setVisibility(View.GONE);
                }
                Log.d("asdf", " view");
            }
        });


        Log.d("asdf", position + " view");
        return convertView;
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState){
//
//    }
}