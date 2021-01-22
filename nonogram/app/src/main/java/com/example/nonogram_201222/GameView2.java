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

public class GameView2 extends View {

    private Paint paint = new Paint();
    private Path path = new Path();
    private int x,y;
    private int xToGrid,yToGrid, tempX=-1, tempY=-1;
    float numberLength, squareLength, pointLength;

    int[][] table = { {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

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


    float[] pts = new float[44];

    public GameView2(Context context) {
        super(context);
    }
    public GameView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public GameView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
//    @Override
//    protected void onMeasure(int widthMeasureSpec , int heightMeasureSpec){
////        Log.d("asdf", widthMeasureSpec + ", " + heightMeasureSpec);
//    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("asdf2", w + ", " + h);
        numberLength = (float)(Math.min(w,h)*0.334);
        squareLength = (float)(Math.min(w,h)*0.666);
        pointLength = squareLength/10;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        //STROKE속성을 이용하여 테두리...선...
        paint.setStyle(Paint.Style.STROKE);
        //두께
        paint.setStrokeWidth(3);
        canvas.drawPath(path, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        int x = (int)event.getX();
//        int y = (int)event.getY();
        x = (int)event.getX();
        y = (int)event.getY();
        //21.01.19 canvas to array
        xToGrid = (int)((x-numberLength)/pointLength);
        yToGrid = (int)((y-numberLength)/pointLength);
        if(tempX != xToGrid || tempY != yToGrid){
            tempX = xToGrid;
            tempY = yToGrid;
            if(xToGrid >= 0 && xToGrid <= 9 && yToGrid >= 0 && yToGrid <= 9){
                Log.d("asdf", xToGrid + " , " + yToGrid);
                toArray(xToGrid, yToGrid);
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        path.moveTo(x,y);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x = (int)event.getX();
                        y = (int)event.getY();
                        path.lineTo(x,y);
                        break;
                }
            }
        }
        //View의 onDraw()를 호출하는 메소드...
        invalidate();

        return true;
    }

    //21.01.22 터치 좌표를 배열에 업데이트하는 메소드
    private int[][] toArray(int xToGrid, int yToGrid) {
        return house;
    }
    //21.01.22 주어진 배열을 분석해 로직을 반환하는 메소드
    private int[][] arrayToLogic(int[][] array){
        return house;
    }
}