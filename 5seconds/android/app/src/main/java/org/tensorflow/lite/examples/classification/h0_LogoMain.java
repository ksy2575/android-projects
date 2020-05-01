package org.tensorflow.lite.examples.classification;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class h0_LogoMain extends AppCompatActivity {

    final String PREFERENCE = "isFirst";

    final int datsbaseSize = 63;
    int backMusic, sfx2;
    int[] answerList = new int[datsbaseSize]; //초기값 0, 배열 크기 60 + 1(newStage 때문) + 1(backMusic) + 1 (sfx)
    DBhelper dbHelper;

    ImageView logo;
    SoundPool sfx;
    int logoSfx;

    private View 	decorView;
    private int	uiOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h0_logo_activity);

        dbHelper = new DBhelper(getApplicationContext(), "CLEARLIST.db", null, 1);

        //dbHelper.allClear();
        SharedPreferences pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String result = pref.getString("c", "0");

        if (result.equals("0")) { // 처음 접속했다면
            dbHelper.allClear();
            dbHelper.insert();

            SharedPreferences.Editor editor = pref.edit();
            editor.putString("c", "1");
            editor.commit();
        }

        answerList = dbHelper.getStageResult();
        /* 음악 환경 설정 부분 0 : 꺼짐, 1 : 켜짐 ///////////////////////////////////////////////// */
        backMusic = answerList[61];
        sfx2 = answerList[62];

        decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;


        logo = findViewById(R.id.logo);
        sfx = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
        logoSfx = sfx.load(this, R.raw.logo, 1);

        Animation fadein, fadeout;
        fadein = AnimationUtils.loadAnimation(this, R.anim.long_fadein);
        fadeout = AnimationUtils.loadAnimation(this, R.anim.fadeout);

        logo.setImageAlpha(0);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                logo.setImageAlpha(255);
                logo.startAnimation(fadein);

            }
        }, 1000);


        fadein.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                    }
                }, 500);
                sfx.play(logoSfx, 1.0f, 1.0f, 0, 0, 1);
            }
            @Override
            public void onAnimationEnd(Animation animation) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {


                        logo.startAnimation(fadeout);
                    }
                }, 3000);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        fadeout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logo.setImageAlpha(0);
                Intent intent = new Intent(h0_LogoMain.this, h1_StartMain.class);
                startActivity(intent);
                overridePendingTransition(R.anim.short_fadein, R.anim.fadeout);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        // super.onWindowFocusChanged(hasFocus);

        if( hasFocus ) {
            decorView.setSystemUiVisibility( uiOption );
        }
    }

}
