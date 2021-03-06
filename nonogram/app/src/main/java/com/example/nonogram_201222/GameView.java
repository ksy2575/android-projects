package com.example.nonogram_201222;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameView extends View {

    private Paint paint = new Paint();
    private Path path = new Path();
    private int x,y;
    float numberLength, squareLength, pointLength;

    float[] pts = new float[44];

    int[][] house = { {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
            {0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 0, 0, 1, 1, 1, 1},
            {1, 1, 1, 1, 0, 0, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},};

    int[] numArray = new int[20];
    ArrayList<Integer[]> list2 = new ArrayList<Integer[]>();




    public GameView(Context context) {
        super(context);
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
//    @Override
//    protected void onMeasure(int widthMeasureSpec , int heightMeasureSpec){
////        Log.d("asdf", widthMeasureSpec + ", " + heightMeasureSpec);
//    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("asdf", w + ", " + h);
        numberLength = (float)(Math.min(w,h)*0.33);
        squareLength = (float)(Math.min(w,h)*0.66);
        pointLength = squareLength/10;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        //STROKE속성을 이용하여 테두리...선...
        paint.setStyle(Paint.Style.FILL);
        //두께
        paint.setStrokeWidth(3);
        //path객체가 가지고 있는 경로를 화면에 그린다...
//        canvas.drawPath(path,paint);

        //기본 구조 그리기

        //가로줄 11개 {x, 0, x, 1560}
        for(int i=0;i<44;i+=4){
            //21.01.19 외곽선 강조하기 나중에 코딩
//            if(i == 0 || i == 20 || i == 40){
//                paint.setStrokeWidth(10);
//                pts[i] = numberLength+(float)(pointLength*i);
//                pts[i+1] = 0;
//                pts[i+2] = numberLength+(float)(pointLength*i);
//                pts[i+3] = numberLength+(float)(squareLength);
//                paint.setStrokeWidth(3);
//                continue;
//            }
            pts[i] = numberLength+(float)(pointLength*i/4);
            pts[i+1] = 0;
            pts[i+2] = numberLength+(float)(pointLength*i/4);
            pts[i+3] = numberLength+(float)(squareLength);
//            Log.d("asdf", pts[i] + " , " + pts[i+1]);
        }
        canvas.drawLines(pts, paint);

        //세로줄 11개 {0, y, 1560, y}
        for(int i=0;i<44;i+=4){
            pts[i] = 0;
            pts[i+1] = numberLength+(float)(pointLength*i/4);
            pts[i+2] = numberLength+(float)(squareLength);
            pts[i+3] = numberLength+(float)(pointLength*i/4);
//            Log.d("asdf", pts[i] + " , " + pts[i+1]);
        }
        canvas.drawLines(pts, paint);
        paint.setTextSize((float)100);
        canvas.drawText("1", (float)numberLength, (float)numberLength, paint);
        arrayToNum(house);
        Log.d("asdf", Arrays.toString(numArray));
        for(Integer[] i:list2){
            Log.d("asdf", Arrays.toString(i));
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        x = (int)event.getX();
//        y = (int)event.getY();
//
//
//
//
////        if(x >= numberLength && y >= numberLength){
////            Log.d("asdf", x + " , " + y);
////            switch(event.getAction()){
////                case MotionEvent.ACTION_DOWN:
////                    path.moveTo(x,y);
////                    break;
////                case MotionEvent.ACTION_MOVE:
////                    x = (int)event.getX();
////                    y = (int)event.getY();
////
////                    path.lineTo(x,y);
////                    break;
////            }
////        }
//
//        //View의 onDraw()를 호출하는 메소드...
//        invalidate();

        return true;
    }

    //int[][] house = { {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
    //                    {0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
    //                    {0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
    //                    {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
    //                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    //                    {1, 1, 1, 1, 0, 0, 1, 1, 1, 1},
    //                    {1, 1, 1, 1, 0, 0, 1, 1, 1, 1},
    //                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    //                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    //                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},};
    //21.01.26 numArray[0] = 가로, numArray[1] = 세로
    private int[][] arrayToNum(int[][] table){

        //가로
        for(int i=0;i<table.length;i++){
            int cnt = 0;
            ArrayList<Integer> list = new ArrayList<Integer>();
            for(int j=0;j<table[0].length;j++){
                if(table[i][j]==1){
                    cnt++;
                }
                else if(cnt != 0){
                    list.add(cnt);
                    cnt = 0;
                }
            }
            if(list.size() == 0){
                list.add(cnt);
            }
//            list2.add(list);
        }
        return house;
    }
}
