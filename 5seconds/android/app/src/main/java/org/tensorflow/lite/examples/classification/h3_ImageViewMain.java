package org.tensorflow.lite.examples.classification;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class h3_ImageViewMain extends AppCompatActivity {

    final int datsbaseSize = 63;
    int backMusic, sfx;

    ArrayList<Integer> imageList;

    DBhelper dbHelper;

    int[] answerList = new int[datsbaseSize]; //초기값 0, 배열 크기 60 + 1(newStage 때문) + 1(backMusic) + 1 (sfx)
    int[] answerList1 = new int[10];
    int[] standardOfbonus = {1, 4, 6, 9}; //2, 3, 4, 5단계에서 보너스 문제 오픈 기준

    private static final int DP = 24;

    RelativeLayout sayingLayout;
    Button prev, next, back, ok, exitButton;
    TextView conv, name;
    ImageView select, suc, gif, floatingCharacter;
    boolean isFirst = false;
    boolean isInflated = false;
    boolean isFloating = false;

    NonSwipeViewPager viewPager;

    int currentpoistion = 0; //현재 이미지 위치
    int answerposition = 0;
    int count = 0;

    String level = ""; //stage level

    int newStage = 0;
    private View decorView;
    private int uiOption;

    DataSet d;
    final String PREFERENCE = "isFirst";

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        // super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(uiOption);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h3_imageview_main);

        //시스템 관리 코드는 클래스에서 조정 안 됨
        decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        //시스템 관리 코드는 클래스에서 조정 안 됨
        //인플레이션으로 겹치는 레이아웃을 깐다
        LayoutInflater inflater = (LayoutInflater) getSystemService(

                Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout linear = (LinearLayout) inflater.inflate(R.layout.gif, null);
        LinearLayout.LayoutParams paramlinear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addContentView(linear, paramlinear);//이 부분이 레이아웃을 겹치는 부분
        //add는 기존의 레이아웃에 겹쳐서 배치하라는 뜻이다.

        SharedPreferences pref = getSharedPreferences(PREFERENCE, Activity.MODE_PRIVATE);
        isFirst = pref.getBoolean(PREFERENCE, true);

        select = findViewById(R.id.sel);
        back = findViewById(R.id.back);
        suc = findViewById(R.id.suc);
        viewPager = findViewById(R.id.viewPager);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        gif = (ImageView) findViewById(R.id.gif_image);
        exitButton = (Button) findViewById(R.id.back2);

        imageList = new ArrayList();
        d = new DataSet(h3_ImageViewMain.this, imageList, exitButton);

        dbHelper = new DBhelper(getApplicationContext(), "CLEARLIST.db", null, 1);

        d.setExit();
    }

    @Override
    public void onResume() {
        super.onResume();

        //음악 재생은 이 안의 구문을 다 갖다 쓰면 됨
        DBhelper dbHelper = new DBhelper(getApplicationContext(), "CLEARLIST.db", null, 1);
        if (dbHelper.getStageResult()[61] == 1) {
            Intent intent = new Intent(getApplicationContext(), MusicService.class);
            intent.putExtra("index", 3);//몇번째 노래를 재생할 것인지 MusicService에 전달
            startService(intent);
        }
        //음악 재생은 이 안의 구문을 다 갖다 쓰면 됨

        //시스템 관리 코드는 클래스에서 조정 안 됨
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(gif);
        Interpolator sInterpolator = new AccelerateInterpolator();

        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ScrollerCustomDuration scroller = new ScrollerCustomDuration(viewPager.getContext(), sInterpolator);
            mScroller.set(viewPager, scroller);
            viewPager.setCurrentItem(currentpoistion + 1);

        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }

        //시스템 관리 코드는 클래스에서 조정 안 됨
        // 200404 튜토리얼 대화창
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout t_linear = (LinearLayout) inflater.inflate(R.layout.conversation, null);
        LinearLayout.LayoutParams t_paramlinear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //처음, 재 접속인 경우
        Intent h2intent = getIntent();
        if (h2intent.getStringExtra("sToI") != null) {
            level = h2intent.getStringExtra("sToI");
            setResult(level);
        }

        //게임 중 뒤로가기 하고 온 경우
        Intent intent = getIntent();
        if (intent.getStringExtra("wToI1") != null && intent.getIntExtra("wToI4", -1) != -1) {
            level = intent.getStringExtra("wToI1");
            setResult(level);
        }

        //게임하고 온 경우
        Intent h2intent2 = getIntent();
        if (h2intent2.getStringExtra("wToI3") != null && h2intent2.getIntExtra("wToI2", -1) != -1) {
            answerposition = h2intent2.getIntExtra("wToI2", 0);
            level = h2intent2.getStringExtra("wToI3");
            setResult(level);
        }

        viewPager.setClipToPadding(false);
        float density = getResources().getDisplayMetrics().density;
        int margin = (int) (DP * density);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin / 2);

        Glide.with(h3_ImageViewMain.this).load(R.raw.cow).into(gifImage);
        gifImage.onStart();
        Handler delayHandler = new Handler();
        delayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO
                gifImage.onStop();

            }
        }, 930);

        if (level.equals("st1") == true) {
            d.initializeData(level);
            viewPager.setAdapter(new h3_ViewPagerAdapter(h3_ImageViewMain.this, imageList, answerList1));
        } else if (level.equals("st2") == true) {
            d.initializeData(level);
            viewPager.setAdapter(new h3_ViewPagerAdapter(this, imageList, answerList1));
        } else if (level.equals("st3") == true) {
            d.initializeData(level);
            viewPager.setAdapter(new h3_ViewPagerAdapter(this, imageList, answerList1));
        } else if (level.equals("st4") == true) {
            d.initializeData(level);
            viewPager.setAdapter(new h3_ViewPagerAdapter(this, imageList, answerList1));
        } else if (level.equals("st5") == true) {
            d.initializeData(level);
            viewPager.setAdapter(new h3_ViewPagerAdapter(this, imageList, answerList1));
        } else if (level.equals("b") == true) {
            d.initializeData(level);
            viewPager.setAdapter(new h3_ViewPagerAdapter(this, imageList, answerList1));
        } else{
            d.initializeData("t");
            viewPager.setAdapter(new h3_ViewPagerAdapter(h3_ImageViewMain.this, imageList, null));

        }

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (level.equals("b")) {
                    int check = 0;
                    if (newStage == 2) {
                        if ((currentpoistion - 1) <= standardOfbonus[0]) check = 1;
                    } else if (newStage == 3) {
                        if ((currentpoistion - 1) <= standardOfbonus[1]) check = 1;
                    } else if (newStage == 4) {
                        if ((currentpoistion - 1) <= standardOfbonus[2]) check = 1;
                    } else if (newStage == 5) {
                        if ((currentpoistion - 1) <= standardOfbonus[3]) check = 1;
                    }
                    if (check == 1) {
                        viewPager.setCurrentItem(currentpoistion - 1);

                        Glide.with(h3_ImageViewMain.this).load(R.raw.cow).into(gifImage);
                        gifImage.onStart();
                        Handler delayHandler = new Handler();
                        delayHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // TODO
                                gifImage.onStop();
                            }
                        }, 1500);
                        check = 0;
                    }
                } else {
                    viewPager.setCurrentItem(currentpoistion - 1);

                    Glide.with(h3_ImageViewMain.this).load(R.raw.cow).into(gifImage);
                    gifImage.onStart();
                    Handler delayHandler = new Handler();
                    delayHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // TODO
                            gifImage.onStop();
                        }
                    }, 1500);
                }
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (level.equals("b")) {
                    int check = 0;
                    if (newStage == 2) {
                        if ((currentpoistion + 1) <= standardOfbonus[0]) check = 1;
                        else
                            Toast.makeText(getApplicationContext(), "레벨이 낮아 접근할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    } else if (newStage == 3) {
                        if ((currentpoistion + 1) <= standardOfbonus[1]) check = 1;
                        else
                            Toast.makeText(getApplicationContext(), "레벨이 낮아 접근할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    } else if (newStage == 4) {
                        if ((currentpoistion + 1) <= standardOfbonus[2]) check = 1;
                        else
                            Toast.makeText(getApplicationContext(), "레벨이 낮아 접근할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    } else if (newStage == 5) {
                        if ((currentpoistion + 1) <= standardOfbonus[3]) check = 1;
                        else
                            Toast.makeText(getApplicationContext(), "레벨이 낮아 접근할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    if (check == 1) {
                        viewPager.setCurrentItem(currentpoistion + 1);

                        Glide.with(h3_ImageViewMain.this).load(R.raw.cow).into(gifImage);
                        gifImage.onStart();
                        Handler delayHandler = new Handler();
                        delayHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // TODO
                                gifImage.onStop();
                            }
                        }, 1500);
                        check = 0;
                    }
                } else { //b가 아닌 나머지 단계
                    viewPager.setCurrentItem(currentpoistion + 1);

                    Glide.with(h3_ImageViewMain.this).load(R.raw.cow).into(gifImage);
                    gifImage.onStart();
                    Handler delayHandler = new Handler();
                    delayHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // TODO
                            gifImage.onStop();
                        }
                    }, 1500);
                }
                if (isFirst && count == 2) {
                    addContentView(t_linear, t_paramlinear);
                    t_linear.setBackgroundColor(Color.parseColor("#99000000"));
                    conv.setText("잘했어!!\n이제 사진기 버튼을 눌러 물건을 찾아보자.");
                    count++;

//                    select.setClickable(false);
                    next.setClickable(false);
//                    back.setClickable(false);
//                    exitButton.setClickable(false);

                }
            }
        });

        if (isFirst && !isInflated) {
            level = "t";
//            d.initializeData("t");
            t_linear.setBackgroundColor(Color.parseColor("#99000000")); // 배경 어둡게

            //인플레이션으로 겹치는 레이아웃을 깐다
            addContentView(t_linear, t_paramlinear);//이 부분이 레이아웃을 겹치는 부분
            isInflated = true;
            isFloating = true;

            sayingLayout = findViewById(R.id.sayingLayout);
            sayingLayout.setVisibility(View.VISIBLE);
            ok = findViewById(R.id.conv_ok);
            conv = findViewById(R.id.conv);
            name = findViewById(R.id.name);
            floatingCharacter = findViewById(R.id.floatingCharacter);

            ok.setVisibility(View.VISIBLE);
            floatingCharacter.setVisibility(View.VISIBLE);

            //인플레이터 뒷부분 다 클릭 안되게 하는 부분
//            select.setClickable(false);
            prev.setClickable(false);
            next.setClickable(false);
            back.setClickable(false);
            exitButton.setClickable(false);
            gif.setVisibility(View.GONE);

            back.setVisibility(View.GONE);
            exitButton.setVisibility(View.GONE);

            name.setText("앨리스");
            conv.setText("시작해볼까?");
//
//            viewPager.setAdapter(new h3_ViewPagerAdapter(h3_ImageViewMain.this, imageList, null));
//            LayerDrawable d= (LayerDrawable)(viewPager.getForeground());


            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("asdfasdf", "count = " + count);
                    if (count == 0) {
                        conv.setText("화살표 버튼을 눌러서 사진을 바꿀 수 있어.");

                    } else if (count == 3) {
                        isFloating = false;
//                        select.setClickable(true);
                        prev.setClickable(true);
                        next.setClickable(true);
//                        back.setClickable(true);
//                        exitButton.setClickable(true);
                        ((ViewGroup) t_linear.getParent()).removeView(t_linear);
                        t_linear.setBackgroundColor(Color.parseColor("#00ff0000"));


                    } else {//원래 화면 터치되게
                        ((ViewGroup) t_linear.getParent()).removeView(t_linear);
                        t_linear.setBackgroundColor(Color.parseColor("#00ff0000"));
                        next.setClickable(true);
//                        back.setClickable(true);
//                        exitButton.setClickable(true);
                        gif.setVisibility(View.VISIBLE);//할까말까
                    }
                    count++;
                }
            });
        }

        prev.setVisibility(View.INVISIBLE);

        if (answerList1[currentpoistion] == 1) {
            suc.setVisibility(View.VISIBLE);
        } else {
            suc.setVisibility(View.INVISIBLE);
        }

        viewPager.addOnPageChangeListener(new NonSwipeViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.d("ITPANGPANG","onPageScrolled : "+position);
            }

            @Override
            public void onPageSelected(int position) {
                currentpoistion = position;

                if (answerList1[currentpoistion] == 1) {
                    suc.setVisibility(View.VISIBLE);
                } else {
                    suc.setVisibility(View.INVISIBLE);
                }

                if (currentpoistion == 0) prev.setVisibility(View.INVISIBLE);
                else prev.setVisibility(View.VISIBLE);
                if (currentpoistion == imageList.size() - 1) next.setVisibility(View.INVISIBLE);
                else next.setVisibility(View.VISIBLE);

//                Log.d("imageviewmain","액자크기 : " + ld.getLayerWidth(0));

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Log.d("ITPANGPANG","onPageScrollStateChanged : "+state);
            }
        });


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isFloating){

                    //중복 방지
                    d.imageList.clear();
                    for (int i = 0; i < 60; i++) {
                        answerList[i] = 0;
                    }

                /*
//                // 카메라 인식 안하고 넘길 때
                Intent intent = new Intent(h3_ImageViewMain.this, h3_WriteAction.class);//글쓰기로 가는 부분
                intent.putExtra("cToW1", currentpoistion);
                intent.putExtra("cToW2", level);

                startActivity(intent);
                finish();

                Log.d("uuuuuu","아아아아");
*/
                    Intent intent = new Intent(h3_ImageViewMain.this, ClassifierActivity.class);//글쓰기로 가는 부분
                    intent.putExtra("iToC1", currentpoistion);
                    intent.putExtra("iToC2", level);

                    startActivity(intent);
                    finish();

                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(h3_ImageViewMain.this, h2_TreeActivityMain.class);//트리로 가는 부분
                startActivity(intent);
                finish();
            }
        });


/*
        if (isFirst) {
            t_linear.setBackgroundColor(Color.parseColor("#99000000")); // 배경 어둡게

            //인플레이션으로 겹치는 레이아웃을 깐다
            addContentView(t_linear, t_paramlinear);//이 부분이 레이아웃을 겹치는 부분

            sayingLayout =  findViewById(R.id.sayingLayout);
            sayingLayout.setVisibility(View.VISIBLE);
            ok = findViewById(R.id.conv_ok);
            conv = findViewById(R.id.conv);
            name = findViewById(R.id.name);
            floatingCharacter = findViewById(R.id.floatingCharacter);

            ok.setVisibility(View.VISIBLE);
            floatingCharacter.setVisibility(View.VISIBLE);

            //인플레이터 뒷부분 다 클릭 안되게 하는 부분
            select.setClickable(false);
            prev.setClickable(false);
            next.setClickable(false);
            back.setClickable(false);
            exitButton.setClickable(false);
            gif.setVisibility(View.GONE);

            back.setVisibility(View.GONE);
            exitButton.setVisibility(View.GONE);

            name.setText("앨리스");
            conv.setText("시작해볼까?");

            level = "t";
            currentpoistion = 1;

            viewPager.setAdapter(new h3_ViewPagerAdapter(h3_ImageViewMain.this, imageList, null));

            ok.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Log.d("asdfasdf", "count = " + count );
                    if(count == 0){
                        conv.setText("화살표 버튼을 눌러서 사진을 바꿀 수 있어.");

                    }else if(count == 2){
                        select.setClickable(true);
                        prev.setClickable(true);
                        next.setClickable(true);
//                        back.setClickable(true);
//                        exitButton.setClickable(true);
                    }
                    else{//원래 화면 터치되게
                        ((ViewGroup)t_linear.getParent()).removeView(t_linear);
                        t_linear.setBackgroundColor(Color.parseColor("#00ff0000"));

                        prev.setClickable(true);
                        next.setClickable(true);
//                        back.setClickable(true);
//                        exitButton.setClickable(true);
                        gif.setVisibility(View.VISIBLE);//할까말까
                    }
                    count++;
                }
            });
        }*/
    }


    /*
    20.01.10 다같이 모여서 의논을 하였으나
    예지의 핸드폰과 태블릿에서 오류가 발생하였음
    카메라로 인텐트 후 onActivityResult로 돌아오는 도중
    imageFilePath가 null값으로 바뀌어 앱이 강제 종료됨
    여러가지 실험을 해본 결과, 안드로이드 버전 9(pie)에서만 그런 것일 것이라는 주장이 가장 유력함
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//카메라를 켜고 확인 버튼을 눌러 돌아왔을 때 실행되는 함수임. 나중에 글씨 쓰기로 들어갔을 때의 예외처리 필요할 수 있음
        super.onActivityResult(requestCode, resultCode, data);
    }

/*
    class thread_sleep extends Thread {
        Activity thisAct;
        thread_sleep(Activity theAct) {
            thisAct = theAct;
        }

        public void run() {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
*/

    public void setResult(String level) {
        answerList = dbHelper.getStageResult();

        /* 음악 환경 설정 부분 0 : 꺼짐, 1 : 켜짐 ///////////////////////////////////////////////// */
        backMusic = answerList[61];
        sfx = answerList[62];

        if (level.equals("st1")) {
            for (int i = 0; i < 10; i++) {
                answerList1[i] = answerList[i];
            }
        } else if (level.equals("st2")) {
            for (int i = 10; i < 20; i++) {
                answerList1[i - 10] = answerList[i];
            }
        } else if (level.equals("st3")) {
            for (int i = 20; i < 30; i++) {
                answerList1[i - 20] = answerList[i];
            }
        } else if (level.equals("st4")) {
            for (int i = 30; i < 40; i++) {
                answerList1[i - 30] = answerList[i];
            }
        } else if (level.equals("st5")) {
            for (int i = 40; i < 50; i++) {
                answerList1[i - 40] = answerList[i];
            }
        } else if (level.equals("b")) {
            for (int i = 50; i < 60; i++) {
                answerList1[i - 50] = answerList[i];
            }
            newStage = answerList[60];
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(h3_ImageViewMain.this, MusicService.class));
    }

    public float calcWidth(Drawable d){
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        float height = dm.heightPixels;
        float h_weight = 0.7f; // height weight

        return d.getIntrinsicWidth() * height * h_weight / d.getIntrinsicHeight();
    }
}