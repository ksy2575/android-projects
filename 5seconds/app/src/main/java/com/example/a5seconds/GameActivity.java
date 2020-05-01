package com.example.a5seconds;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/*
* 더 할일
*
* 음악, 효과음 넣기(사운드풀)
* 승리 조건, 달성 시 이벤트
* 시계 수정
* 다이얼로그 커스텀
*
* Toast, Log 다 빼기
* 나중에 사진 찍기 기능
*
* */
public class GameActivity extends AppCompatActivity {

    Button card;
    Button clock;
    TextView matchAmount;
    Button plus, minus, trashbin;
    TextView score1, score2, score3, score4;
    TextView player1, player2, player3, player4;
    MyTimer myTimer;
    LinearLayout totalLayout;

    SoundPool sfx;
    private int touchSfx1, touchSfx2, drawSfx1, drawSfx2, drawSfx3, startSfx1, startSfx2, timerSfx1, timerSfx2, timerSfx3, timerSfx4, timerSfx5, winSfx, plusminusSfx, wrongSfx;


    List<String> list = new ArrayList<>();
    String question = "";

    int match = 1;//판 수
    int focusing = 0;//플레이어 1~4
    int sound = 0;
    String roleOnLoad ="";


    Boolean flag = false;
    boolean isTimerRunning = false;
    boolean isWinning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);

        card = findViewById(R.id.card);
        clock = findViewById(R.id.clock);
        matchAmount = findViewById(R.id.matchAmount);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        trashbin = findViewById(R.id.trashbin);
        score1 = findViewById(R.id.score1);
        score2 = findViewById(R.id.score2);
        score3 = findViewById(R.id.score3);
        score4 = findViewById(R.id.score4);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player3 = findViewById(R.id.player3);
        player4 = findViewById(R.id.player4);

        totalLayout = (LinearLayout)findViewById(R.id.totalLayout);

        myTimer = new MyTimer(5000, 10);

        soundLoad();

        AssetManager assets = getResources().getAssets() ;
        InputStream file = null ;

        byte buf[] = new byte[4096] ;
        String text = "" ;

        try {
            file = assets.open("questions.txt") ;
            if (file.read(buf) > 0) {//한 문자씩 쫙 읽는 구문
                text = new String(buf) ;
            }
            file.close() ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (file != null) {
            try {
                file.close() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
        String[] array = text.split("\n");

        for(int i = 0; i<array.length-1;i++){

            list.add(array[i]);//0~128까지 잘 들어감, 이제 섞자
            //Log.d("array", i +", "+ array[i]);
        }

        Collections.shuffle(list);

        for(int i = 0; i<array.length-1;i++){
            Log.d("list", i +", "+ list.get(i));//잘 섞었다.
        }
    }

    private void soundLoad() {
        //효과음 사운드풀
        sfx = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);

        Log.d("role", "3");
        sfx.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                if(!roleOnLoad.equals("")){
                    Thread thread = new Thread();
                    try {
                        thread.sleep(300);
                    }catch (InterruptedException e){
                    }//인터럽트는 나중에
                    Log.d("role", roleOnLoad);
                    soundPlay(roleOnLoad);
                    roleOnLoad = "";
                    Log.d("role", "4");
                }
            }
        });
        touchSfx1 = sfx.load(this, R.raw.click, 1);
        touchSfx2 = sfx.load(this, R.raw.jump, 1);
        drawSfx1 = sfx.load(this, R.raw.yes, 1);//이거 랜덤으로 여러개ㄱㄱ
        drawSfx2 = sfx.load(this, R.raw.question, 1);
        timerSfx1 = sfx.load(this, R.raw.bding, 1);//
        timerSfx2 = sfx.load(this, R.raw.bbbding, 1);//
        Log.d("role", "5");
//        timerSfx2 = sfx.load(this, R.raw.click, 1);//
        winSfx = sfx.load(this, R.raw.winning1, 1);
        plusminusSfx = sfx.load(this, R.raw.plusminus, 1);
        wrongSfx = sfx.load(this, R.raw.wrong, 1);
        Log.d("role", "6");
    }

    @Override
    public void onResume(){
        super.onResume();

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!flag&&!isWinning){

                    flag = true;                                    //flag를 다이얼로그가 끝나야 false로 만드는 걸로 찾기ㅇㅇ함

                    if(match < list.size()){

                        soundPlay("draw");

                        question = list.get(match);

                        Toast.makeText(getApplicationContext(), Integer.toString(question.length()), Toast.LENGTH_SHORT).show();

                        if(question.length()>=10){
                            question = question.substring(0, question.length()/2) + "\n" + question.substring(question.length()/2);
                        }

                        View dlgView = View.inflate(GameActivity.this, R.layout.picture, null);
                        AlertDialog.Builder dlg = new AlertDialog.Builder(GameActivity.this);
                        Button pic = (Button) dlgView.findViewById(R.id.newimage);
                        dlg.setTitle(match + "번 문제!");
                        pic.setBackgroundResource(R.drawable.cardlayout);
                        pic.setText(question);
                        dlg.setView(dlgView);
                        dlg.setPositiveButton("시작!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                isTimerRunning = true;
                                isWinning = true;
                                myTimer.start();
                                soundPlay("timer");
                                card.setBackgroundResource(R.drawable.cardlayout);
                                card.setText(question);
                                card.setTextSize(20);
                                card.setScaleX((float)1);
                            }
                        });
                        dlg.setCancelable(false);
                        dlg.show();


                    }
                    else{
                        soundPlay("touch");

//                        int winnerArray[] = {0,0,0,0};//승리 플레이어 추려내는 부분 생략..
//                        winnerArray[1] = Integer.parseInt(score1.getText().toString());
//                        winnerArray[2]= Integer.parseInt(score2.getText().toString());
//                        winnerArray[3]= Integer.parseInt(score3.getText().toString());
//                        winnerArray[4]= Integer.parseInt(score4.getText().toString());
//                        String winner = "";
//                        winner = 0;

                        Toast.makeText(getApplicationContext(), "게임이 끝났습니다.", Toast.LENGTH_SHORT).show();
                        Collections.shuffle(list);
                        match = 0;
                        matchAmount.setText(match + "번째 판");

                    }
                }
                else{
                    soundPlay("wrong");
                }

            }
        });
        card.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {//카드뭉치가 아닐 때 온터치리스너로 드래그 동작
                focusPlayer(0);
                if(isWinning){
                    trashbin.setVisibility(View.VISIBLE);
                    ClipData data = null;
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(card);
                    view.startDrag(data, shadowBuilder, view, 0);
                    return true;
                }
                return false;
            }
        });

        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focusPlayer(0);
                if(!isTimerRunning){
                    isTimerRunning = true;
                    soundPlay("timer");
                    myTimer.start();
                    Toast.makeText(getApplicationContext(), "start", Toast.LENGTH_SHORT).show();

                }
                else{//running중일 때
                    sfx.autoPause();
                    myTimer.cancel();
                    clock.setText("5.00");
                    isTimerRunning = false;
                    flag = false;
                    Toast.makeText(getApplicationContext(), "stop", Toast.LENGTH_SHORT).show();
                }
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                soundPlay("plus");

                switch (focusing){

                    case 1:
                        score1.setText(String.valueOf(Integer.parseInt(score1.getText().toString())+1));
                        break;
                    case 2:
                        score2.setText(String.valueOf(Integer.parseInt(score2.getText().toString())+1));
                        break;
                    case 3:
                        score3.setText(String.valueOf(Integer.parseInt(score3.getText().toString())+1));
                        break;
                    case 4:
                        score4.setText(String.valueOf(Integer.parseInt(score4.getText().toString())+1));
                        break;
                }
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                soundPlay("minus");

                switch (focusing){

                    case 1:
                        if(Integer.parseInt(score1.getText().toString())!=0)
                            score1.setText(String.valueOf(Integer.parseInt(score1.getText().toString())-1));
                        break;
                    case 2:
                        if(Integer.parseInt(score2.getText().toString())!=0)
                            score2.setText(String.valueOf(Integer.parseInt(score2.getText().toString())-1));
                        break;
                    case 3:
                        if(Integer.parseInt(score3.getText().toString())!=0)
                            score3.setText(String.valueOf(Integer.parseInt(score3.getText().toString())-1));
                        break;
                    case 4:
                        if(Integer.parseInt(score4.getText().toString())!=0)
                            score4.setText(String.valueOf(Integer.parseInt(score4.getText().toString())-1));
                        break;
                }
            }
        });

        score1.setOnDragListener(new DragListener());
        score2.setOnDragListener(new DragListener());
        score3.setOnDragListener(new DragListener());
        score4.setOnDragListener(new DragListener());
        player1.setOnDragListener(new DragListener());
        player2.setOnDragListener(new DragListener());
        player3.setOnDragListener(new DragListener());
        player4.setOnDragListener(new DragListener());
        trashbin.setOnDragListener(new DragListener());

        score1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPlay("score");
                focusPlayer(1);
            }
        });
        score2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPlay("score");
                focusPlayer(2);
            }
        });
        score3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPlay("score");
                focusPlayer(3);
            }
        });
        score4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPlay("score");
                focusPlayer(4);
            }
        });
        player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPlay("score");
                focusPlayer(1);
            }
        });
        player2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPlay("score");
                focusPlayer(2);
            }
        });
        player3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPlay("score");
                focusPlayer(3);
            }
        });
        player4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPlay("score");
                focusPlayer(4);
            }
        });

        totalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focusPlayer(0);
            }
        });
    }

    @Override
    public void onStop(){
        super.onStop();

        sfx.release();
        sfx = null;
    }

    public void focusPlayer(int i) {

        if(focusing !=0){
            switch (focusing){
                case 1:
                    score1.setTextSize(40);
                    score1.setTextColor(Color.parseColor("#000000"));
                    break;
                case 2:
                    score2.setTextSize(40);
                    score2.setTextColor(Color.parseColor("#000000"));
                    break;
                case 3:
                    score3.setTextSize(40);
                    score3.setTextColor(Color.parseColor("#000000"));
                    break;
                case 4:
                    score4.setTextSize(40);
                    score4.setTextColor(Color.parseColor("#000000"));
                    break;
            }
        }

        focusing = i;

        switch (i){
            case 1:
                score1.setTextSize(42);
                score1.setTextColor(Color.parseColor("#fc9b90"));
                break;
            case 2:
                score2.setTextSize(42);
                score2.setTextColor(Color.parseColor("#fc9b90"));
                break;
            case 3:
                score3.setTextSize(42);
                score3.setTextColor(Color.parseColor("#fc9b90"));
                break;
            case 4:
                score4.setTextSize(42);
                score4.setTextColor(Color.parseColor("#fc9b90"));
                break;
        }

        if(focusing!=0){
            plus.setVisibility(View.VISIBLE);
            minus.setVisibility(View.VISIBLE);
        }
        else{
            plus.setVisibility(View.INVISIBLE);
            minus.setVisibility(View.INVISIBLE);
        }

    }


    class DragListener implements View.OnDragListener{//dragListener는 받는 친구들이 되어야 함

        public boolean onDrag(View view, DragEvent event) {


            switch (event.getAction()){

                case DragEvent.ACTION_DRAG_STARTED:

                    card.setVisibility(View.INVISIBLE);

                    Log.d("drag", "start");

                    break;

                case DragEvent.ACTION_DRAG_ENTERED:

                    Log.d("drag", "ACTION_DRAG_ENTERED");

                    if(view == findViewById(R.id.score1)||view == findViewById(R.id.player1)){
                        player1.setTextColor(Color.parseColor("#FF5897DB"));
                        player1.setTypeface(Typeface.DEFAULT_BOLD);
                        soundPlay("touch");
                    }
                    else if(view == findViewById(R.id.score2)||view == findViewById(R.id.player2)){
                        player2.setTextColor(Color.parseColor("#FF5897DB"));
                        player2.setTypeface(Typeface.DEFAULT_BOLD);
                        soundPlay("touch");
                    }
                    else if(view == findViewById(R.id.score3)||view == findViewById(R.id.player3)){
                        player3.setTextColor(Color.parseColor("#FF5897DB"));
                        player3.setTypeface(Typeface.DEFAULT_BOLD);
                        soundPlay("touch");
                    }
                    else if(view == findViewById(R.id.score4)||view == findViewById(R.id.player4)){
                        player4.setTextColor(Color.parseColor("#FF5897DB"));
                        player4.setTypeface(Typeface.DEFAULT_BOLD);
                        soundPlay("touch");
                    }
                    else if(view == findViewById(R.id.trashbin)){
                        trashbin.setScaleX((float)1.4);
                        trashbin.setScaleY((float)1.4);
                    }
                    //Animation animation = new ScaleAnimation(1,2,1,2);
                    //view.startAnimation(animation);
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("drag", "ACTION_DRAG_EXITED");

                    if(view == findViewById(R.id.score1)||view == findViewById(R.id.player1)){
                        player1.setTextColor(Color.parseColor("#FFF0E6"));
                        player1.setTypeface(Typeface.DEFAULT);
                    }
                    else if(view == findViewById(R.id.score2)||view == findViewById(R.id.player2)){
                        player2.setTextColor(Color.parseColor("#FFF0E6"));
                        player2.setTypeface(Typeface.DEFAULT);
                    }
                    else if(view == findViewById(R.id.score3)||view == findViewById(R.id.player3)){
                        player3.setTextColor(Color.parseColor("#FFF0E6"));
                        player3.setTypeface(Typeface.DEFAULT);
                    }
                    else if(view == findViewById(R.id.score4)||view == findViewById(R.id.player4)){
                        player4.setTextColor(Color.parseColor("#FFF0E6"));
                        player4.setTypeface(Typeface.DEFAULT);
                    }
                    else if(view == findViewById(R.id.trashbin)){
                        trashbin.setScaleX((float)1);
                        trashbin.setScaleY((float)1);
                    }

                    break;

                case DragEvent.ACTION_DROP://쓰레기통을 만들어서 뽑을 때와 드래그할 때를 구분할지
                    Log.d("drag", "ACTION_DROP");
                    if(view == findViewById(R.id.score1)||view == findViewById(R.id.player1)){
                        score1.setText(String.valueOf(Integer.parseInt(score1.getText().toString())+1));
                        player1.setTextColor(Color.parseColor("#FFF0E6"));
                        player1.setTypeface(Typeface.DEFAULT);
                        soundPlay("win");
                    }
                    else if(view == findViewById(R.id.score2)||view == findViewById(R.id.player2)){
                        score2.setText(String.valueOf(Integer.parseInt(score2.getText().toString())+1));
                        player2.setTextColor(Color.parseColor("#FFF0E6"));
                        player2.setTypeface(Typeface.DEFAULT);
                        soundPlay("win");
                    }
                    else if(view == findViewById(R.id.score3)||view == findViewById(R.id.player3)){
                        score3.setText(String.valueOf(Integer.parseInt(score3.getText().toString())+1));
                        player3.setTextColor(Color.parseColor("#FFF0E6"));
                        player3.setTypeface(Typeface.DEFAULT);
                        soundPlay("win");
                    }
                    else if(view == findViewById(R.id.score4)||view == findViewById(R.id.player4)){
                        score4.setText(String.valueOf(Integer.parseInt(score4.getText().toString())+1));
                        player4.setTextColor(Color.parseColor("#FFF0E6"));
                        player4.setTypeface(Typeface.DEFAULT);
                        soundPlay("win");
                    }
                    else if(view == findViewById(R.id.trashbin)){
                        Toast.makeText(getApplicationContext(), "통과", Toast.LENGTH_SHORT).show();
                        trashbin.setVisibility(View.GONE);
                        soundPlay("wrong");
                    }

                    card.setBackgroundResource(R.drawable.card);
                    card.setText("");
                    card.setScaleX((float) 1.2);
                    isWinning = false;
                    match++;
                    matchAmount.setText(match + "번째 판");//이거 흐름 다시 생각-게임이 끝나야 다음 번째 판으로 넘어가도록 ->ㅇㅇ함
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("drag", "ACTION_DRAG_ENDED");
                    card.setVisibility(View.VISIBLE);
                    trashbin.setVisibility(View.GONE);
                    break;

            }
            return true;
        }
    }


    class MyTimer extends CountDownTimer{

        public MyTimer(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            if(millisUntilFinished/1000.0>0.04){

                clock.setText(String.format("%.2f", millisUntilFinished/1000.0));
            }
            else{
                clock.setText("0.00");
            }
        }

        @Override
        public void onFinish() {
            clock.setText("0.00");
            flag = false;
            isTimerRunning = false;
            Thread thread = new Thread();
            try {
                thread.sleep(1000);
                clock.setText("5.00");
            }catch (InterruptedException e){
                Log.d("sleep", "catch");
                Toast.makeText(getApplicationContext(), "catch", Toast.LENGTH_SHORT).show();
            }//인터럽트는 나중에
        }
    }

    private void soundPlay(String role) {

        Log.d("role", "1");
        if(sound>=12){//주기적으로 릴리즈해주는 구문
            sfx.release();
            sfx = null;
            roleOnLoad = role;
            Log.d("role", "2");
            soundLoad();
            Log.d("role", "7");
            sound = 0;
            return;
        }
        Log.d("role", role);
        sound++;

        if(role.equals("touch")){
            sfx.play(touchSfx1, 1.0f, 1.0f, 0, 0, 1.5f);
        }
        else if(role.equals("draw")){
            if((match/5)%2==0)
                sfx.play(drawSfx1, 1.0f, 1.0f, 0, 0, 1.0f);
            else
                sfx.play(drawSfx2, 0.7f, 0.7f, 0, 0, 1.0f);

        }
        else if(role.equals("timer")){
            if(Math.random()>=0.2)
                sfx.play(timerSfx1, 0.7f, 0.7f, 0, 0, 1.0f);
            else
                sfx.play(timerSfx2, 0.7f, 0.7f, 0, 0, 1.0f);
        }
        else if(role.equals("win")){
            sfx.play(winSfx, 1.0f, 1.0f, 0, 0, 1.0f);

        }
        else if(role.equals("plus")){
            sfx.play(plusminusSfx, 1.0f, 1.0f, 0, 0, 1.5f);

        }
        else if(role=="minus"){
            sfx.play(plusminusSfx, 1.0f, 1.0f, 0, 0, 1.0f);
            Log.d("role", "1234");
        }
        else if(role.equals("wrong")){
            sfx.play(wrongSfx, 0.7f, 0.7f, 0, 0, 1.5f);

        }
        else if(role.equals("score")){
            sfx.play(touchSfx2, 1.0f, 1.0f, 0, 0, 1.5f);

        }
        /*
        switch (role){
            case "touch":
                sfx.play(touchSfx1, 1.0f, 1.0f, 0, 0, 1.5f);
                break;
            case "draw"://여기 두개ㄱㄱ
                if((match/5)%2==0)
                    sfx.play(drawSfx1, 1.0f, 1.0f, 0, 0, 1.0f);
                else
                    sfx.play(drawSfx2, 0.7f, 0.7f, 0, 0, 1.0f);
                break;
            case "start":
                if(Math.random()>=0.2)
                    sfx.play(startSfx1, 0.8f, 0.8f, 0, 0, 1.0f);
                else
                    sfx.play(startSfx2, 0.8f, 0.8f, 0, 0, 1.0f);
                break;
            case "timer"://여기 여러개 넣기 근데 판마다 같은걸로
                sfx.play(timerSfx1, 1.0f, 1.0f, 0, 0, 1.0f);
                break;
            case "win":
                sfx.play(winSfx, 1.0f, 1.0f, 0, 0, 1.0f);
                break;
            case "plus":
                sfx.play(plusminusSfx, 1.0f, 1.0f, 0, 0, 1.5f);
                break;
            case "minus":
                sfx.play(plusminusSfx, 1.0f, 1.0f, 0, 0, 1.0f);
                break;
            case "wrong":
                sfx.play(wrongSfx, 0.7f, 0.7f, 0, 0, 1.5f);
                break;
            case "score":
                sfx.play(touchSfx2, 1.0f, 1.0f, 0, 0, 1.5f);
                break;

         }
        */



    }
}