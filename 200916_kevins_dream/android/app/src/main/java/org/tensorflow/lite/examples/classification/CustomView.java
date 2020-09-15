package org.tensorflow.lite.examples.classification;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class CustomView extends View {


    String s = "";
    Boolean isShadow = false;
    int questionWidth = 0;
    int questionHeight = 0;
    int writeWidth = 0;
    int writeHeight = 0;
    public CustomView(Context context, String s, Boolean isShadow, int deviceWidth, int deviceHeight) {
        super(context);
        this.s = s;
        this.isShadow = isShadow;
        questionWidth = (int)(deviceWidth / 6) * 8;
        questionHeight = (int)(deviceHeight / 7) * 13;
        writeWidth = (int)(deviceWidth / 6) * 8;//전체 10 중 4.875
        writeHeight = (int)(deviceHeight / 7) * 13;
    }

    public void onDraw(Canvas canvas) {

        if(isShadow){//가이드 음영 부분
            int j = 0;

            if(s.length()>8){//제시어가 길면 긴만큼 줄이기
                j = 400 - 10*s.length();

            }else{
                j = 400;
            }
            //int x = canvas.getWidth() / 2 - s.length() * j / 2;
            int y = canvas.getHeight() / 2 - j / 2;

            String[] arr = s.split("");

            canvas.drawColor(Color.WHITE);

            Paint MyPaint = new Paint();
            MyPaint.setStrokeWidth(8f);
            MyPaint.setStyle(Paint.Style.STROKE);
            MyPaint.setColor(Color.BLACK);
            MyPaint.setStyle(Paint.Style.FILL);
            MyPaint.setTextSize(j);
            MyPaint.setTextAlign(Paint.Align.CENTER);
            MyPaint.setTextScaleX(1.1f);
            canvas.drawText(s, canvas.getWidth() / 2, y + 3 * j / 4 - 10, MyPaint);
        }
        else{//제시어 부분
            int j = 0;
            if(questionWidth <= questionHeight){//그럴 리 없겠지만 가로보다 세로 길이가 더 길 때
                //가로에 맞춰
                j = questionWidth / s.length();

            }
            else if(questionWidth / questionHeight > s.length()){//글자 수로 쪼갰을 때 가로가 더 길 때
                j = (int)(questionHeight * 0.8);
            }
            else{                               //글자 수로 쪼갰을 때 세로가 더 길 때
                j = (int)(questionWidth /s.length());
            }
//            if(s.length()>8){//제시어가 길면 긴만큼 줄이기
//                j = 200 - 5*s.length();
//
//            }else{
//                j = 200;
//            }
            int x = canvas.getWidth() / 2 - s.length() * j / 2;
            int y = canvas.getHeight() / 2 - j / 2;

            String[] arr = s.split("");

            canvas.drawColor(Color.WHITE);

            Paint MyPaint = new Paint();

            for (int i = 0; i < s.length(); i++) {
                MyPaint.setStrokeWidth(5f);
                MyPaint.setStyle(Paint.Style.STROKE);
                MyPaint.setColor(Color.BLACK);

//                canvas.drawRect(x+i*j,y,j,j,MyPaint);
                Path path = new Path();
                path.moveTo(x + i * j, y);
                path.lineTo(x + i * j, y);
                path.lineTo(x + i * j, y + j);
                path.lineTo(x + (i + 1) * j, y + j);
                path.lineTo(x + (i + 1) * j, y);
                path.lineTo(x + i * j, y);
                canvas.drawPath(path, MyPaint);

                MyPaint.setStyle(Paint.Style.FILL);
                MyPaint.setTextSize(j);
                MyPaint.setTextAlign(Paint.Align.CENTER);

                if(arr[0].equals("") == true) {
                    canvas.drawText(arr[i+1], x + j * 0.5f + i * j, y + j - 10, MyPaint);
                }
                else {
                    canvas.drawText(arr[i], x + j * 0.5f + i * j, y + j - 10, MyPaint);
                }
            }
        }
    }
}