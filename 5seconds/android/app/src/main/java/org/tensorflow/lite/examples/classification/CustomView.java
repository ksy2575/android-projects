package org.tensorflow.lite.examples.classification;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class CustomView extends View {

    String s = "";
    Boolean isShadow = false;
    public CustomView(Context context, String s, Boolean isShadow) {
        super(context);
        this.s = s;
        this.isShadow = isShadow;
    }

    public void onDraw(Canvas canvas) {

        if(isShadow){
            int j = 400;
            //int x = canvas.getWidth() / 2 - s.length() * j / 2;
            int y = canvas.getHeight() / 2 - j / 2;

            Log.d("ooro", canvas.getWidth() + "mm");
            Log.d("ooro", canvas.getHeight() + "mm");
            Log.d("ooro", y + "mm");
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
        else{
            int j = 200;
            int x = canvas.getWidth() / 2 - s.length() * j / 2;
            int y = canvas.getHeight() / 2 - j / 2;

            Log.d("ooro",s);
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