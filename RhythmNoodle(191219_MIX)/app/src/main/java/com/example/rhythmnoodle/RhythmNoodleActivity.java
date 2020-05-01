package com.example.rhythmnoodle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static com.example.rhythmnoodle.ChefSelect.ChefNo;

public class RhythmNoodleActivity extends AppCompatActivity{
    static final int [][] loop = new int[4][3];
    static final int [][] index = new int[4][3];
    static int j=4;
    String track = "";
    String chefno = "1";
    int playsw = 0;
    Thread w;
    int sw = 0;


    SoundPool cool;
    public int loopcnt,empty;
    int chord1, chord2, chord3, drum1, drum2, drum3, melody1, melody2, melody3;
    RecipeFileManager mFileMgr = new RecipeFileManager(this);//레시피 파일
    Drawable noodle1Click, noodle2Click,noodle3Click,noodle4Click,egg1Click,egg2Click,egg3Click,egg4Click,
            bowl1Click,bowl2Click,bowl3Click,bowl4Click,wood1Click, wood2Click, wood3Click, wood4Click, playClick;

    public void cleanBowl(){
        bowl1Click.setAlpha(255);
        bowl2Click.setAlpha(255);
        bowl3Click.setAlpha(255);
        bowl4Click.setAlpha(255);
    }

    public void cleanEgg(){
        egg1Click.setAlpha(255);
        egg2Click.setAlpha(255);
        egg3Click.setAlpha(255);
        egg4Click.setAlpha(255);
    }

    public void cleanNoodle(){
        noodle1Click.setAlpha(255);
        noodle2Click.setAlpha(255);
        noodle3Click.setAlpha(255);
        noodle4Click.setAlpha(255);
    }

    public void cleanWood(){
        wood1Click.setAlpha(255);
        wood2Click.setAlpha(255);
        wood3Click.setAlpha(255);
        wood4Click.setAlpha(255);
    }

    public void cleanAll(){
        cleanBowl();
        cleanEgg();
        cleanWood();
        cleanNoodle();
        playClick.setAlpha(255);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.rhythm_noodle);

        chefno = Integer.toString(ChefNo);
        if(ChefNo==1){
            Toast.makeText(getApplicationContext(), "edm 주방장", Toast.LENGTH_SHORT).show();
        }
        else if(ChefNo==2){
            Toast.makeText(getApplicationContext(), "힙합 주방장", Toast.LENGTH_SHORT).show();
        }
        else if(ChefNo==3)
        {
            Toast.makeText(getApplicationContext(), "락 주방장", Toast.LENGTH_SHORT).show();
        }

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++){
                loop[i][j] = 0;
            }
        }
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++){
                index[i][j] = 3;
            }
        }

        noodle1Click = ((ImageView)findViewById(R.id.noodle1)).getBackground();
        noodle2Click = ((ImageView)findViewById(R.id.noodle2)).getBackground();
        noodle3Click = ((ImageView)findViewById(R.id.noodle3)).getBackground();
        noodle4Click = ((ImageView)findViewById(R.id.noodle4)).getBackground();
        egg1Click = ((ImageView)findViewById(R.id.egg1)).getBackground();
        egg2Click = ((ImageView)findViewById(R.id.egg2)).getBackground();
        egg3Click = ((ImageView)findViewById(R.id.egg3)).getBackground();
        egg4Click = ((ImageView)findViewById(R.id.egg4)).getBackground();
        bowl1Click = ((ImageView)findViewById(R.id.bowl1)).getBackground();
        bowl2Click = ((ImageView)findViewById(R.id.bowl2)).getBackground();
        bowl3Click = ((ImageView)findViewById(R.id.bowl3)).getBackground();
        bowl4Click = ((ImageView)findViewById(R.id.bowl4)).getBackground();
        wood1Click = ((ImageView)findViewById(R.id.wood1)).getBackground();
        wood2Click = ((ImageView)findViewById(R.id.wood2)).getBackground();
        wood3Click = ((ImageView)findViewById(R.id.wood3)).getBackground();
        wood4Click = ((ImageView)findViewById(R.id.wood4)).getBackground();
        playClick = ((ImageView)findViewById(R.id.play)).getBackground();

        cleanAll();

        findViewById(R.id.wood1).setOnLongClickListener(mLongClickListener);
        findViewById(R.id.wood2).setOnLongClickListener(mLongClickListener);
        findViewById(R.id.wood3).setOnLongClickListener(mLongClickListener);
        findViewById(R.id.wood4).setOnLongClickListener(mLongClickListener);
        findViewById(R.id.bowl1).setOnLongClickListener(mLongClickListener);
        findViewById(R.id.bowl2).setOnLongClickListener(mLongClickListener);
        findViewById(R.id.bowl3).setOnLongClickListener(mLongClickListener);
        findViewById(R.id.bowl4).setOnLongClickListener(mLongClickListener);
        findViewById(R.id.egg1).setOnLongClickListener(mLongClickListener);
        findViewById(R.id.egg2).setOnLongClickListener(mLongClickListener);
        findViewById(R.id.egg3).setOnLongClickListener(mLongClickListener);
        findViewById(R.id.egg4).setOnLongClickListener(mLongClickListener);
        findViewById(R.id.noodle1).setOnLongClickListener(mLongClickListener);
        findViewById(R.id.noodle2).setOnLongClickListener(mLongClickListener);
        findViewById(R.id.noodle3).setOnLongClickListener(mLongClickListener);
        findViewById(R.id.noodle4).setOnLongClickListener(mLongClickListener);

        cool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);

        if(ChefNo ==1) {
            chord1 = cool.load(this, R.raw.edm_chord1, 1);
            chord2 = cool.load(this, R.raw.edm_chord2, 1);
            chord3 = cool.load(this, R.raw.edm_chord3, 1);

            drum1 = cool.load(this, R.raw.edm_drum1, 1);
            drum2 = cool.load(this, R.raw.edm_drum2, 1);
            drum3 = cool.load(this, R.raw.edm_drum3, 1);

            melody1 = cool.load(this, R.raw.edm_melody1, 1);
            melody2 = cool.load(this, R.raw.edm_melody2, 1);
            melody3 = cool.load(this, R.raw.edm_melody3, 1);
        }else if(ChefNo ==2) {
            chord1 = cool.load(this, R.raw.hiphop_bass1, 1);
            chord2 = cool.load(this, R.raw.hiphop_bass2, 1);
            chord3 = cool.load(this, R.raw.hiphop_bass3, 1);

            drum1 = cool.load(this, R.raw.hiphop_drum1, 1);
            drum2 = cool.load(this, R.raw.hiphop_drum2, 1);
            drum3 = cool.load(this, R.raw.hiphop_drum3, 1);

            melody1 = cool.load(this, R.raw.hiphop_melody1, 1);
            melody2 = cool.load(this, R.raw.hiphop_melody2, 1);
            melody3 = cool.load(this, R.raw.hiphop_melody3, 1);
        }else if(ChefNo ==3) {
            chord1 = cool.load(this, R.raw.rock_bass1, 1);
            chord2 = cool.load(this, R.raw.rock_bass2, 1);
            chord3 = cool.load(this, R.raw.rock_bass3, 1);

            drum1 = cool.load(this, R.raw.rock_drum1, 1);
            drum2 = cool.load(this, R.raw.rock_drum2, 1);
            drum3 = cool.load(this, R.raw.rock_drum3, 1);

            melody1 = cool.load(this, R.raw.rock_guitar1, 1);
            melody2 = cool.load(this, R.raw.rock_guitar2, 1);
            melody3 = cool.load(this, R.raw.rock_guitar3, 1);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        cool.autoPause();
        if(playsw == 1) w.interrupt();
        cool.release();
    }

    Button.OnLongClickListener mLongClickListener = new Button.OnLongClickListener(){

        public boolean onLongClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.wood1:
                    cool.autoPause();
                    playClick.setAlpha(255);
                    for (int i = 0; i < 3; i++) {
                        if(loop[0][i]!=0) cool.play(loop[j][i], 10f, 10f, 0, 0, 1);
                        Toast.makeText(getApplicationContext(), String.valueOf(loop[j][i]), Toast.LENGTH_SHORT).show();
                    }

                    return true;
                case R.id.wood2:
                    cool.autoPause();
                    playClick.setAlpha(255);
                    for (int i = 0; i < 3; i++) {
                        if(loop[1][i]!=0) cool.play(loop[j][i], 10f, 10f, 0, 0, 1);

                    }
                    return true;
                case R.id.wood3:
                    cool.autoPause();
                    playClick.setAlpha(255);
                    for (int i = 0; i < 3; i++) {
                        if(loop[2][i]!=0) cool.play(loop[j][i], 10f, 10f, 0, 0, 1);
                    }
                    return true;
                case R.id.wood4:
                    cool.autoPause();
                    playClick.setAlpha(255);
                    for (int i = 0; i < 3; i++) {
                        if(loop[3][i]!=0) cool.play(loop[j][i], 10f, 10f, 0, 0, 1);
                    }
                    return true;
                case R.id.bowl1:
                    cool.autoPause();
                    playClick.setAlpha(255);
                    cool.play(chord1, 10f, 10f, 0, 0, 1);
                    // 롱클릭을 완전히 처리하면, 논리적 참을 반환한다.
                    return true;
                case R.id.bowl2:
                    cool.autoPause();
                    playClick.setAlpha(255);
                    cool.play(chord2, 10f, 10f, 0, 0, 1);
                    // 롱클릭을 완전히 처리하면, 논리적 참을 반환한다.
                    return true;
                case R.id.bowl3:
                    cool.autoPause();
                    playClick.setAlpha(255);
                    cool.play(chord3, 10f, 10f, 0, 0, 1);
                    // 롱클릭을 완전히 처리하면, 논리적 참을 반환한다.
                    return true;
                case R.id.egg1:
                    cool.autoPause();
                    playClick.setAlpha(255);
                    cool.play(drum1, 10f, 10f, 0, 0, 1);
                    // 롱클릭을 완전히 처리하면, 논리적 참을 반환한다.
                    return true;
                case R.id.egg2:
                    cool.autoPause();
                    playClick.setAlpha(255);
                    cool.play(drum2, 10f, 10f, 0, 0, 1);
                    // 롱클릭을 완전히 처리하면, 논리적 참을 반환한다.
                    return true;
                case R.id.egg3:
                    cool.autoPause();
                    playClick.setAlpha(255);
                    cool.play(drum3, 10f, 10f, 0, 0, 1);
                    // 롱클릭을 완전히 처리하면, 논리적 참을 반환한다.
                    return true;
                case R.id.noodle1:
                    cool.autoPause();
                    playClick.setAlpha(255);
                    cool.play(melody1, 10f, 10f, 0, 0, 1);
                    // 롱클릭을 완전히 처리하면, 논리적 참을 반환한다.
                    return true;
                case R.id.noodle2:
                    cool.autoPause();
                    playClick.setAlpha(255);
                    cool.play(melody2, 10f, 10f, 0, 0, 1);
                    // 롱클릭을 완전히 처리하면, 논리적 참을 반환한다.
                    return true;
                case R.id.noodle3:
                    cool.autoPause();
                    playClick.setAlpha(255);
                    cool.play(melody3, 10f, 10f, 0, 0, 1);
                    // 롱클릭을 완전히 처리하면, 논리적 참을 반환한다.
                    return true;
            }
            return false;
        } // end onLongClick
    }; // end inner class


    public void setSelect(int i){
        if(loop[i][0] == chord1) bowl1Click.setAlpha(70);
        if(loop[i][0] == chord2) bowl2Click.setAlpha(70);
        if(loop[i][0] == chord3) bowl3Click.setAlpha(70);
        if(loop[i][1] == drum1) egg1Click.setAlpha(70);
        if(loop[i][1] == drum2) egg2Click.setAlpha(70);
        if(loop[i][1] == drum3) egg3Click.setAlpha(70);
        if(loop[i][2] == melody1) noodle1Click.setAlpha(70);
        if(loop[i][2] == melody2) noodle2Click.setAlpha(70);
        if(loop[i][2] == melody3) noodle3Click.setAlpha(70);
    }
    public void onClick(View view) {
        //온클릭 ID에 따라 재생, 정지
        ImageView imageview1 = findViewById(R.id.imageView);
        ImageView imageview2 = findViewById(R.id.imageView2);
        ImageView imageview3 = findViewById(R.id.imageView3);
        switch(view.getId()) {
            case R.id.wood1:
                cleanAll();
                wood1Click.setAlpha(70);
                setSelect(0);
                j=0;
                break;
            case R.id.wood2:
                cleanAll();
                wood2Click.setAlpha(70);
                setSelect(1);
                j=1;
                break;
            case R.id.wood3:
                cleanAll();
                wood3Click.setAlpha(70);
                setSelect(2);
                j=2;
                break;
            case R.id.wood4:
                cleanAll();
                wood4Click.setAlpha(70);
                setSelect(3);
                j=3;
                break;

            case R.id.bowl1:
                track = "bowl1";
                if(j==4) Toast.makeText(getApplicationContext(), "먼저 숫자패널을 선택하세요", Toast.LENGTH_LONG).show();
                else{
                    loop[j][0] = chord1;
                    index[j][0] = 0;
                    cleanBowl();
                    bowl1Click.setAlpha(70);
                    if(j==0) imageview1.setImageResource(R.drawable.bowlslice1);
                }
                break;
            case R.id.bowl2:
                track = "bowl2";
                if(j==4) Toast.makeText(getApplicationContext(), "먼저 숫자패널을 선택하세요", Toast.LENGTH_LONG).show();
                else {
                    loop[j][0] = chord2;
                    index[j][0] = 1;
                    cleanBowl();
                    bowl2Click.setAlpha(70);
                    if (j == 0) imageview1.setImageResource(R.drawable.bowlslice2);
                }
                break;
            case R.id.bowl3:
                track = "bow31";
                if(j==4) Toast.makeText(getApplicationContext(), "먼저 숫자패널을 선택하세요", Toast.LENGTH_LONG).show();
                else {
                    loop[j][0] = chord3;
                    index[j][0] = 2;
                    cleanBowl();
                    bowl3Click.setAlpha(70);
                    if (j == 0) imageview1.setImageResource(R.drawable.bowlslice3);
                }
                break;
            case R.id.bowl4:
                track = "bowl4";
                if(j==4) Toast.makeText(getApplicationContext(), "먼저 숫자패널을 선택하세요", Toast.LENGTH_LONG).show();
                else {
                    loop[j][0] = 0;
                    index[j][0] = 3;
                    cleanBowl();
                    bowl4Click.setAlpha(70);
                    if (j == 0) imageview1.setImageResource(R.drawable.bowlslice4);
                }
                break;

            case R.id.egg1:
                track = "egg1";
                if(j==4) Toast.makeText(getApplicationContext(), "먼저 숫자패널을 선택하세요", Toast.LENGTH_LONG).show();
                else {
                    loop[j][1] = drum1;
                    index[j][1] = 0;
                    cleanEgg();
                    egg1Click.setAlpha(70);
                    if (j == 0) imageview2.setImageResource(R.drawable.eggslice1);
                }
                break;
            case R.id.egg2:
                track = "egg2";
                if(j==4) Toast.makeText(getApplicationContext(), "먼저 숫자패널을 선택하세요", Toast.LENGTH_LONG).show();
                else {
                    loop[j][1] = drum2;
                    index[j][1] = 1;
                    cleanEgg();
                    egg2Click.setAlpha(70);
                    if (j == 0) imageview2.setImageResource(R.drawable.eggslice2);
                }
                break;
            case R.id.egg3:
                track = "egg3";
                if(j==4) Toast.makeText(getApplicationContext(), "먼저 숫자패널을 선택하세요", Toast.LENGTH_LONG).show();
                else {
                    loop[j][1] = drum3;
                    index[j][1] = 2;
                    cleanEgg();
                    egg3Click.setAlpha(70);
                    if (j == 0) imageview2.setImageResource(R.drawable.eggslice3);
                }
                break;
            case R.id.egg4:
                track = "egg4";
                if(j==4) Toast.makeText(getApplicationContext(), "먼저 숫자패널을 선택하세요", Toast.LENGTH_LONG).show();
                else {
                    loop[j][1] = 0;
                    index[j][1] = 3;
                    cleanEgg();
                    egg4Click.setAlpha(70);
                    if (j == 0) imageview2.setImageResource(R.drawable.eggslice4);
                }
                break;
            case R.id.noodle1:
                track = "noodle1";
                if(j==4) Toast.makeText(getApplicationContext(), "먼저 숫자패널을 선택하세요", Toast.LENGTH_LONG).show();
                else {
                    loop[j][2] = melody1;
                    index[j][2] = 0;
                    cleanNoodle();
                    noodle1Click.setAlpha(70);
                    if (j == 0) imageview3.setImageResource(R.drawable.noodle1);
                }
                break;
            case R.id.noodle2:
                track = "noodle2";
                if(j==4) Toast.makeText(getApplicationContext(), "먼저 숫자패널을 선택하세요", Toast.LENGTH_LONG).show();
                else {
                    loop[j][2] = melody2;
                    index[j][2] = 1;
                    cleanNoodle();
                    noodle2Click.setAlpha(70);
                    if (j ==0) imageview3.setImageResource(R.drawable.noodle2);
                }
                break;
            case R.id.noodle3:
                track = "noodle3";
                if(j==4) Toast.makeText(getApplicationContext(), "먼저 숫자패널을 선택하세요", Toast.LENGTH_LONG).show();
                else {
                    loop[j][2] = melody3;
                    index[j][2] = 2;
                    cleanNoodle();
                    noodle3Click.setAlpha(70);
                    if (j == 0) imageview3.setImageResource(R.drawable.noodle3);
                }
                break;
            case R.id.noodle4:
                track = "noodle4";
                if(j==4) Toast.makeText(getApplicationContext(), "먼저 숫자패널을 선택하세요", Toast.LENGTH_LONG).show();
                else {
                    loop[j][2] = 0;
                    index[j][2] = 3;
                    cleanNoodle();
                    noodle4Click.setAlpha(70);
                    if (j == 0) imageview3.setImageResource(R.drawable.noodle4);
                }
                break;
            case R.id.play:
                loopcnt = 0;
                empty = 0;
                for(int i=0; i<4; i++){//모든 우드와 우드의 악기를 메뉴배열에 추가시킴
                    for(int j=0; j<3; j++) {
                        if(loop[i][j] == 0) loopcnt++;
                        else loopcnt = 0;
                    }
                    if(loopcnt == 3) empty = i+1;
                    loopcnt = 0;
                }
                if(empty != 0) Toast.makeText(getApplicationContext(),"빈 루프가 존재합니다",Toast.LENGTH_SHORT).show();
                else {
                    if (sw == 0) {
                        playClick.setAlpha(70);
                        sw = 1;
                        w = new PlayMusic();
                        w.start();
                    } else {
                        playClick.setAlpha(255);
                        w.interrupt();
                        w = new PlayMusic();
                        sw = 0;
                    }
                }
                break;
            case R.id.save:
                loopcnt = 0;
                empty = 0;
                for(int i=0; i<4; i++){//모든 우드와 우드의 악기를 메뉴배열에 추가시킴
                    for(int j=0; j<3; j++) {
                        if(loop[i][j] == 0) loopcnt++;
                        else loopcnt = 0;
                    }
                    if(loopcnt == 3) empty = i+1;
                    loopcnt = 0;
                }
                if(empty != 0) Toast.makeText(getApplicationContext(),empty+"번째 루프가 비었습니다.",Toast.LENGTH_SHORT).show();
                else {
                    String menu[] = {"", "", "", ""};//메뉴 초기화
                    String data = "";   //파일에 넣어줄 데이터
                    EditText NoodleName = (EditText) findViewById(R.id.noodleName);//파일 입출력

                    String noodlename = NoodleName.getText().toString();//String타입 국수이름
                    if(noodlename.equals("")){
                        noodlename = "국수이름";
                    }

                    for (int i = 0; i < 4; i++)//모든 우드와 우드의 악기를 메뉴배열에 추가시킴
                        for (int j = 0; j < 3; j++)
                            menu[i] += String.valueOf(index[i][j]);

                    //ex) 데이터 = 1100101121220
                    data = chefno + "" + menu[0] + "" + menu[1] + "" + menu[2] + "" + menu[3] + "\n";
                    mFileMgr.save(data, noodlename);//데이터 저장

                    Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(this,NoodleLibraryActivity.class);
                    startActivity(intent);

                    break;
                }
        }
    }
    class PlayMusic extends Thread {
        public void run() {
            playsw = 1;
            for(int j=0; j<4; j++) {
                for (int i = 0; i < 3; i++) {
                    if(loop[j][i]!=0) cool.play(loop[j][i], 10f, 10f, 0, 0, 1);
                }
                try {
                    if(ChefNo == 2) Thread.sleep(7385);
                    else Thread.sleep(8000);
                }
                catch(InterruptedException e){
                    cool.autoPause();
                    playsw=0;
                    return;
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    playClick.setAlpha(255);
                }
            });
            playsw = 0;
        }
    }





}