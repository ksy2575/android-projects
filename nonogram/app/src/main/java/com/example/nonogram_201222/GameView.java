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

import java.util.Collections;

public class GameView extends View {

    private Paint paint = new Paint();
    private Path path = new Path();
    private int x,y;
    int numberLength, squareLength;

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
        numberLength = (int)(Math.min(w,h)*0.25);
        squareLength = (int)(Math.min(w,h)*0.75);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        paint.setColor(Color.BLACK);

        //STROKE속성을 이용하여 테두리...선...
        paint.setStyle(Paint.Style.STROKE);

        //두께
        paint.setStrokeWidth(3);


        //path객체가 가지고 있는 경로를 화면에 그린다...
        canvas.drawPath(path,paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = (int)event.getX();
        y = (int)event.getY();

//        switch(event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                path.moveTo(x,y);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                x = (int)event.getX();
//                y = (int)event.getY();
//
//                path.lineTo(x,y);
//                break;
//        }

        //View의 onDraw()를 호출하는 메소드...
        invalidate();

        return true;
    }
}
