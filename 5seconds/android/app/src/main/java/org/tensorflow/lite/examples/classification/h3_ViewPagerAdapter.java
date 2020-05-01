package org.tensorflow.lite.examples.classification;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class h3_ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<Integer> imageList;
    private int[] answerList;
    private Drawable drawable;


    public h3_ViewPagerAdapter(Context context, ArrayList<Integer> imageList, int[] answerList) {
        this.mContext = context;
        this.imageList = imageList;
        this.answerList = answerList;
        this.drawable = context.getResources().getDrawable(R.drawable.frame);

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.h3_pagerview, null);

        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(imageList.get(position));

        // padding 적용
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{drawable});
        layerDrawable.setLayerWidth(0,(int)calcWidth(mContext.getResources().getDrawable(imageList.get(position))));
        layerDrawable.setLayerGravity(0, Gravity.CENTER);

        imageView.setForeground(layerDrawable);
//        int x_padding = (int)calcPadding(mContext.getResources().getDrawable(imageList.get(position)));

        if (answerList!=null && answerList[position] == 0) {
            imageView.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
        }
//        layerDrawable.setLayerInset(0,x_padding,0,0,0);

        container.addView(view);

        return view;
    }
    public LayerDrawable layer(){
        return null;
    }
    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (View)o);
    }


    public float calcWidth(Drawable d){
        DisplayMetrics dm = mContext.getApplicationContext().getResources().getDisplayMetrics();
        float height = dm.heightPixels;
        float h_weight = 0.7f; // height weight

        return d.getIntrinsicWidth() * height * h_weight / d.getIntrinsicHeight();
    }

//    public float calcPadding(Drawable d){
//        int DP = 24;
//        float density = mContext.getResources().getDisplayMetrics().density;
//        int margin = (int) (DP * density);
//
//        DisplayMetrics dm = mContext.getApplicationContext().getResources().getDisplayMetrics();
//        float width = dm.widthPixels;
//        float height = dm.heightPixels;
//        float h_weight = 0.7f; // height weight
//
//        float imageWidth = d.getIntrinsicWidth() * height * h_weight / d.getIntrinsicHeight();
//
//        Log.d("imagepadding","이미지가로 : "+imageWidth);
//        Log.d("imagepadding","기기 가로 : "+width);
//        Log.d("imagepadding","패딩 : "+ ((width-imageWidth)/2-margin) );
//        Log.d("imagepadding","마진 : "+margin);
//        Log.d("imagepadding","원래이미지가로 : "+d.getIntrinsicWidth());
//        Log.d("imagepadding","원래이미지세로 : "+d.getIntrinsicHeight());
//        Log.d("imagepadding","기기세로 : "+height);
//        Log.d("imagepadding","뷰 세로 : "+height*h_weight);
//
//
////        return (width-imageWidth)/2-margin;
//        return (width-imageWidth)/2;
//    }

    //리스트가 변경 될 때 무조건 notifyDataSetChanged 호출 해주자.
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
