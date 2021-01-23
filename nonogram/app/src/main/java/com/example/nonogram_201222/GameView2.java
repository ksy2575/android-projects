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
    private float x,y;
    private int xToGrid,yToGrid, tempX=-1, tempY=-1;
    float numberLength, squareLength, pointLength;

    int[][] userTable = { {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
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
    public GameView2(Context context, AttributeSet attrs, int defStyle) {super(context, attrs, defStyle);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("asdf2", w + ", " + h);
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
//        canvas.drawPath(path, paint);
        if(x != 0 && y != 0){
            float gridToPx = xToGrid*pointLength+numberLength, gridToPy = yToGrid*pointLength+numberLength;
            canvas.drawRect(gridToPx, gridToPy, gridToPx+pointLength, gridToPy+pointLength, paint);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        int x = (int)event.getX();
//        int y = (int)event.getY();
        x = event.getX();
        y = event.getY();
        //21.01.19 canvas to array
        xToGrid = (int)Math.floor((x-numberLength)/pointLength);
        yToGrid = (int)Math.floor((y-numberLength)/pointLength);
        if(tempX != xToGrid || tempY != yToGrid){
            tempX = xToGrid;
            tempY = yToGrid;
            if(xToGrid >= 0 && xToGrid <= 9 && yToGrid >= 0 && yToGrid <= 9){
//                Log.d("asdf", xToGrid + " , " + yToGrid);

                //21.01.23 case 0 > 빈칸, 1 > 색칠, 2 > 엑스
                switch (toArray(xToGrid, yToGrid)){
                    case 0:
                        break;
                    case 1:
                        path.moveTo(xToGrid,yToGrid);
                        break;
                    case 2:
                        break;
                }
                //View의 onDraw()를 호출하는 메소드...
                invalidate();
//                switch(event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        path.moveTo(x,y);
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        x = event.getX();
//                        y = event.getY();
//                        path.lineTo(x,y);
//                        break;
//                }
            }
        }


        return true;
    }

    //21.01.22 터치 좌표를 배열에 업데이트하는 메소드
    private int toArray(int xToGrid, int yToGrid) {
        //0이면 1, 1이면 0
        userTable[xToGrid][yToGrid] = (userTable[xToGrid][yToGrid] + 1)%3;
        return userTable[xToGrid][yToGrid];
    }
    //21.01.22 주어진 배열을 분석해 로직을 반환하는 메소드(게임 초기 구성 시 필요)
    private int[][] arrayToLogic(int[][] array){
        return house;
    }
}