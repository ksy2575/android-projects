package com.example.nonogram_201222;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        ImageView icon1 = convertView.findViewById(R.id.icon_view1);
        ImageView icon2 = convertView.findViewById(R.id.icon_view2);
        ImageView icon3 = convertView.findViewById(R.id.icon_view3);
        ImageView icon4 = convertView.findViewById(R.id.icon_view4);
        ImageView icon5 = convertView.findViewById(R.id.icon_view5);
        ImageView icon6 = convertView.findViewById(R.id.icon_view6);


        int id;
        int i = 0;
        Context context;

        //st1_1 형식으로 저장된 사진 파일 불러오기 - 나중에 DB와 연동
        context = icon1.getContext();
        if(true){
            id = context.getResources().getIdentifier("st" + position + "_" + i++, "drawable",
                    context.getPackageName());
        }else{
            id = context.getResources().getIdentifier("aaa", "drawable",
                    context.getPackageName());
        }
        icon1.setImageResource(id);


        context = icon2.getContext();
        if(false){
            id = context.getResources().getIdentifier("st" + position + "_" + i++, "drawable",
                    context.getPackageName());
        }else{
            id = context.getResources().getIdentifier("aaa", "drawable",
                    context.getPackageName());
        }
        icon2.setImageResource(id);

        context = icon3.getContext();
        if(true){
            id = context.getResources().getIdentifier("st" + position + "_" + i++, "drawable",
                    context.getPackageName());
        }else{
            id = context.getResources().getIdentifier("aaa", "drawable",
                    context.getPackageName());
        }
        icon3.setImageResource(id);

        context = icon4.getContext();
        if(true){
            id = context.getResources().getIdentifier("st" + position + "_" + i++, "drawable",
                    context.getPackageName());
        }else{
            id = context.getResources().getIdentifier("aaa", "drawable",
                    context.getPackageName());
        }
        icon4.setImageResource(id);

        context = icon5.getContext();
        if(true){
            id = context.getResources().getIdentifier("st" + position + "_" + i++, "drawable",
                    context.getPackageName());
        }else{
            id = context.getResources().getIdentifier("aaa", "drawable",
                    context.getPackageName());
        }
        icon5.setImageResource(id);

        context = icon6.getContext();
        if(true){
            id = context.getResources().getIdentifier("st" + position + "_" + i++, "drawable",
                    context.getPackageName());
        }else{
            id = context.getResources().getIdentifier("aaa", "drawable",
                    context.getPackageName());
        }
        icon6.setImageResource(id);


        final LinearLayout stageGrid = convertView.findViewById(R.id.stageGrid);

        //201219 펼쳐지는 리스트뷰 구현 - 다른 리스트를 접는 기능은 고민 중
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stageGrid.getVisibility() == View.GONE){
                    stageGrid.setVisibility(View.VISIBLE);
                }
                else{
                    stageGrid.setVisibility(View.GONE);
                }
            }
        });


        Log.d("asdf", position + " view");
        return convertView;
    }


//    @NonNull
//    public View foldView(int position, @Nullable View convertView){
////        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////        convertView = inflater.inflate(R.layout.list_item_view, null, true);
//
//
//        Log.d("asdf", position + " foldView");
//        LinearLayout stageGrid = convertView.findViewById(R.id.stageGrid);
//
//        if(stageGrid.getVisibility() == View.GONE){
//            stageGrid.setVisibility(View.VISIBLE);
//            Log.d("asdf", position + " Gone");
//        }
//
//        return convertView;
//    }

//    public void folding(int i) {
////            stageGrid.setVisibility(View.VISIBLE);
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        foldView(i, inflater.inflate(R.layout.list_item_view, null, true));
//
//    }
}