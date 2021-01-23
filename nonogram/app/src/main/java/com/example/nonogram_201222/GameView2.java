package com.example.nonogram_201222;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Array;
import java.util.Arrays;

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
    private boolean touchFlag;

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
            for(int i = 0;i<10;i++){
                for(int j = 0;j<10;j++){
                    if(userTable[i][j] == 1){
                        float gridToPx = j*pointLength+numberLength, gridToPy = i*pointLength+numberLength;
                        canvas.drawRect(gridToPx, gridToPy, gridToPx+pointLength, gridToPy+pointLength, paint);
                    }
                }
            }
            compareTable(userTable, house);

        }


    }

    private void compareTable(int[][] userTable, int[][] house) {
        if(userTable == house){
            Log.d("asdf", "어예에");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        int x = (int)event.getX();
//        int y = (int)event.getY();
        touchFlag = true;
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
                int currValue = toArray(xToGrid, yToGrid);//반환값 필요 없을듯
                //21.01.23 case 0 > 빈칸, 1 > 색칠, 2 > 엑스
                switch (currValue){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }

                //View의 onDraw()를 호출하는 메소드
                invalidate();
//                if(event.getAction() == MotionEvent.ACTION_UP){
//                    Log.d("asdf","up");
//                    touchFlag = false;
//                }
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

    //21.01.23 이거는 키보드용이고 터치를 떼었을 때 플래그를 작동시킬 방법 찾기
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    //21.01.23 터치 좌표를 배열에 업데이트하는 메소드(※ 배열의 i,j가 아닌 좌표의 x,y축을 기준으로 삼음 - 가로 : x, 세로 : y)
    private int toArray(int xToGrid, int yToGrid) {
        //0이면 1, 1이면 0
        userTable[yToGrid][xToGrid] = (userTable[yToGrid][xToGrid] + 1)%2;
        Log.d("asdf", xToGrid +","+ yToGrid);
        for(int i=0; i<10;i++){
//            Log.d("asdf", Arrays.toString(userTable[i]));
        }

        return userTable[yToGrid][xToGrid];
    }
    //21.01.22 주어진 배열을 분석해 로직을 반환하는 메소드(게임 초기 구성 시 필요)
    private int[][] arrayToLogic(int[][] array){
        return house;
    }
}