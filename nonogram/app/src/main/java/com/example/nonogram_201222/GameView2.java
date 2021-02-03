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
    private Paint paint2 = new Paint();
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
    int[] dragArray = {-1, -1, -1, -1};

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
//        Log.d("asdf2", w + ", " + h);
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

        paint2.setColor(Color.LTGRAY);
        //STROKE속성을 이용하여 테두리...선...
        paint2.setStyle(Paint.Style.FILL);
        //두께
        paint2.setStrokeWidth(1);
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
        int dragFlag = whereTowhere();
        if(dragFlag == 1){
            for(int i = Math.min(dragArray[0], dragArray[2]); i <= Math.max(dragArray[0], dragArray[2]); i++){
                float gridToPx = i*pointLength+numberLength, gridToPy = dragArray[1]*pointLength+numberLength;
                canvas.drawRect(gridToPx, gridToPy, gridToPx+pointLength, gridToPy+pointLength, paint2);
                Log.d("asdf2", "가로");
            }
        }
        else if(dragFlag == 2){
            for(int i = Math.min(dragArray[1], dragArray[3]); i <= Math.max(dragArray[1], dragArray[3]); i++){
                float gridToPx = dragArray[0]*pointLength+numberLength, gridToPy = i*pointLength+numberLength;
                canvas.drawRect(gridToPx, gridToPy, gridToPx+pointLength, gridToPy+pointLength, paint2);
                Log.d("asdf2", "세로");
            }
        }
//        if((0<=dragArray[2] && dragArray[2]<10) && (0<=dragArray[3] && dragArray[3]<10)){
//            //tempX, tempY, GridX, GridY
////            Log.d("asdf2", dragArray[0] + ", " + dragArray[1] + ", " + dragArray[2] + ", " + dragArray[3]);
//            if(dragArray[0] != dragArray[2] && Math.abs(dragArray[1] - dragArray[3]) <= 1){//x축 상의 친구들을 tempX,tempY의 색으로 변경
//                for(int i = Math.min(dragArray[0], dragArray[2]); i <= Math.max(dragArray[0], dragArray[2]); i++){
//                    float gridToPx = i*pointLength+numberLength, gridToPy = dragArray[1]*pointLength+numberLength;
//                    canvas.drawRect(gridToPx, gridToPy, gridToPx+pointLength, gridToPy+pointLength, paint2);
//                    Log.d("asdf2", "가로");
//                }
//
//            }
//            else if(Math.abs(dragArray[0] - dragArray[2]) <= 1){//y축 상의 친구들을 tempX,tempY의 색으로 변경
//                for(int i = Math.min(dragArray[1], dragArray[3]); i <= Math.max(dragArray[1], dragArray[3]); i++){
//                    float gridToPx = dragArray[0]*pointLength+numberLength, gridToPy = i*pointLength+numberLength;
//                    canvas.drawRect(gridToPx, gridToPy, gridToPx+pointLength, gridToPy+pointLength, paint2);
//                    Log.d("asdf2", "세로");
//                }
//            }
//        }
    }

    //21.02.01 드래그의 방향을 결정하는 메서드. 정확도 처리를 하였다.
    private int whereTowhere() {
        if((0<=dragArray[2] && dragArray[2]<10) && (0<=dragArray[3] && dragArray[3]<10)){
            //tempX, tempY, GridX, GridY
//            Log.d("asdf2", dragArray[0] + ", " + dragArray[1] + ", " + dragArray[2] + ", " + dragArray[3]);
            if(dragArray[0] != dragArray[2] && Math.abs(dragArray[1] - dragArray[3]) <= 1){//x축 상의 친구들을 tempX,tempY의 색으로 변경
                return 1;

            }
            else if(Math.abs(dragArray[0] - dragArray[2]) <= 1){//y축 상의 친구들을 tempX,tempY의 색으로 변경
                return 2;
            }
        }
        return -1;
    }

    private void compareTable(int[][] userTable, int[][] house) {
        if(userTable == house){
            Log.d("asdf", "어예에");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        //21.01.19 canvas to array
        xToGrid = (int)Math.floor((x-numberLength)/pointLength);
        yToGrid = (int)Math.floor((y-numberLength)/pointLength);
//        Log.d("asdf", ""+tempX+ ""+tempY + ""+touchFlag);

        //터치
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(!touchFlag){
                tempX = xToGrid;
                tempY = yToGrid;
                if(xToGrid >= 0 && xToGrid <= 9 && yToGrid >= 0 && yToGrid <= 9){
//                Log.d("asdf", xToGrid + " , " + yToGrid);
                    toArray(xToGrid, yToGrid,-1);//반환값 필요 없을듯
                }
            }
            touchFlag = true;
        }
        //드래그
        else if(event.getAction() == MotionEvent.ACTION_MOVE){
            dragFunc(tempX, tempY, xToGrid, yToGrid);
        }
        //떼기
        else{
            //21.02.01 드래그 좌표 저장 메서드 추가 필요
            if(dragArray[0] != -1){
                dragSave();
                dragArray[0] = -1;
                dragArray[1] = -1;
                dragArray[2] = -1;
                dragArray[3] = -1;
            }
            touchFlag = false;
        }
//        if(event.getAction() == MotionEvent.ACTION_DOWN && !touchFlag){
//            touchFlag = true;
//            x = event.getX();
//            y = event.getY();
//            //21.01.19 canvas to array
//            xToGrid = (int)Math.floor((x-numberLength)/pointLength);
//            yToGrid = (int)Math.floor((y-numberLength)/pointLength);
//            if(tempX != xToGrid || tempY != yToGrid){
//                tempX = xToGrid;
//                tempY = yToGrid;
//                if(xToGrid >= 0 && xToGrid <= 9 && yToGrid >= 0 && yToGrid <= 9){
////                Log.d("asdf", xToGrid + " , " + yToGrid);
//                    int currValue = toArray(xToGrid, yToGrid);//반환값 필요 없을듯
//                    //21.01.23 case 0 > 빈칸, 1 > 색칠, 2 > 엑스
//                    switch (currValue){
//                        case 0:
//                            break;
//                        case 1:
//                            break;
//                        case 2:
//                            break;
//                    }
//
//                    //View의 onDraw()를 호출하는 메소드
//                    invalidate();
////                if(event.getAction() == MotionEvent.ACTION_UP){
////                    Log.d("asdf","up");
////                    touchFlag = false;
////                }
////                switch(event.getAction()){
////                    case MotionEvent.ACTION_DOWN:
////                        path.moveTo(x,y);
////                        break;
////                    case MotionEvent.ACTION_MOVE:
////                        x = event.getX();
////                        y = event.getY();
////                        path.lineTo(x,y);
////                        break;
////                }
//                }
//            }
//        }
//        else{
//            touchFlag = false;
//        }

        invalidate();
        return true;
    }

    private void dragFunc(int tX, int tY, int xG, int yG) {
        dragArray[0] = tX;
        dragArray[1] = tY;
        dragArray[2] = xG;
        dragArray[3] = yG;

        dragLimit();
    }
    //21.02.01 드래그 시 인덱스 초과 문제를 방지하는 메서드
    private void dragLimit() {
        if(dragArray[0] < 0) dragArray[0] = 0;
        if(dragArray[1] < 0) dragArray[1] = 0;
        if(dragArray[2] < 0) dragArray[2] = 0;
        if(dragArray[3] < 0) dragArray[3] = 0;
        if(dragArray[0] >= 10) dragArray[0] =9;
        if(dragArray[1] >= 10) dragArray[1] =9;
        if(dragArray[2] >= 10) dragArray[2] =9;
        if(dragArray[3] >= 10) dragArray[3] =9;
    }

    //21.02.01 드래그한 좌표들을 배열에 추가하는 메서드
    private void dragSave() {
        dragLimit();
        int dragFlag = whereTowhere();
//        Log.d("asdf2", tempX +", " + tempY +" : "+ userTable[tempX][tempY]);
        int defaultVal = userTable[dragArray[0]][dragArray[1]];
        if(dragFlag == 1){
            for(int i = Math.min(dragArray[0], dragArray[2]); i <= Math.max(dragArray[0], dragArray[2]); i++){
                toArray(i, dragArray[1], defaultVal);
            }
        }
        else if(dragFlag == 2){
            for(int i = Math.min(dragArray[1], dragArray[3]); i <= Math.max(dragArray[1], dragArray[3]); i++){
                toArray(dragArray[0], i, defaultVal);
            }
        }

    }

    //21.01.23 터치 좌표를 배열에 업데이트하는 메소드(※ 배열의 i,j가 아닌 좌표의 x,y축을 기준으로 삼음 - 가로 : x, 세로 : y)
    private int toArray(int xToGrid, int yToGrid, int defaultValue) {
        //0이면 1, 1이면 0, defaultValue != -1이면 드래그처리
        if(defaultValue == -1){
            userTable[yToGrid][xToGrid] = (userTable[yToGrid][xToGrid] + 1)%2;
            for(int i=0; i<10;i++){
                Log.d("asdf", Arrays.toString(userTable[i]) + " : " + i + "defval = -1");
            }
            return userTable[yToGrid][xToGrid];
        }
        else{
            userTable[yToGrid][xToGrid] = defaultValue;
            for(int i=0; i<10;i++){
                Log.d("asdf", Arrays.toString(userTable[i]) + " : " + i + " : " + defaultValue);
            }
            return userTable[yToGrid][xToGrid];
        }
    }
    //21.01.22 주어진 배열을 분석해 로직을 반환하는 메소드(게임 초기 구성 시 필요)
    private int[][] arrayToLogic(int[][] array){
        return house;
    }
}