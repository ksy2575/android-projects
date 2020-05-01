package com.example.rhythmnoodle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;


public class NoodleLibraryActivity extends AppCompatActivity implements SensorEventListener {
    RecipeFileManager mFileMgr = new RecipeFileManager(this);//레시피 파일
    ListView m_ListView;
    String noodleName = "";
    static final int [][] loop = new int[4][3];
    static final int [][] index = new int[4][3];
    static int j=4;
    String chefno = "1";
    int playsw = 0;
    Thread w;

    //흔들기 관련
    SensorManager mSensorManager;
    Sensor mAccelerometer;
    private long mShakeTime;
    private static final int SHAKE_SKIP_TIME = 2000;
    private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
    SoundPool cool;

    boolean isPlay = false;

    public int loopcnt,empty;
    int chord1, chord2, chord3, drum1, drum2, drum3, melody1, melody2, melody3, pepper;
    ImageButton LibraryPlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noodle_library);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        m_ListView = findViewById(R.id.list);
        LibraryPlay = (ImageButton) findViewById(R.id.LibraryPlay);

        makeList();

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++){
                loop[i][j] = 0;
            }
        }

    }
    @Override
    protected  void onRestart(){
        super.onRestart();
        LibraryPlay.setVisibility(View.INVISIBLE);
        LibraryPlay.setImageResource(R.drawable.playnoodle);
        makeList();
    }

    @Override
    protected  void onResume(){
        super.onResume();
        mSensorManager.registerListener(this,mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    public void onSensorChanged(SensorEvent event){

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];

            float gravityX = axisX / SensorManager.GRAVITY_EARTH;
            float gravityY = axisY / SensorManager.GRAVITY_EARTH;
            float gravityZ = axisZ / SensorManager.GRAVITY_EARTH;

            Float f = gravityX * gravityX + gravityY * gravityY + gravityZ * gravityZ;
            double squaredD = Math.sqrt(f.doubleValue());
            float gForce = (float) squaredD;
            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                long currentTime = System.currentTimeMillis();
                if (mShakeTime + SHAKE_SKIP_TIME > currentTime) {
                    return;
                }

                mShakeTime = currentTime;

                if(isPlay){
                    Toast.makeText(getApplicationContext(), "shake!!", Toast.LENGTH_SHORT).show();
                    cool.play(pepper, 10f, 10f, 0, 0, 1);
                    reLoadPepper();
                }
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int a){

    }
    void reLoadPepper(){
        cool.stop(pepper);
        if(chefno.equals("1")) {
            Toast.makeText(getApplicationContext(), "reload!!", Toast.LENGTH_SHORT).show();
            pepper = cool.load(this, R.raw.edm_pepper1, 1);
        }else if(chefno.equals("2")) {
            pepper = cool.load(this, R.raw.hiphop_pepper1, 1);
            Toast.makeText(getApplicationContext(), "reload2!!", Toast.LENGTH_SHORT).show();
        }else if(chefno.equals("3")) {
            pepper = cool.load(this, R.raw.rock_pepper1, 1);
        }
    }

    private void makeList(){
        File Dir = new File("/data/data/com.example.rhythmnoodle/files");
        if(Dir.exists()==false)
        {
            return;
        }
        File[] files = Dir.listFiles();

        ArrayList<String> arrayList = new ArrayList<String>();

        for(int i = 0; i < files.length; i++) {
            if(!files[i].isHidden() && files[i].isFile()){
                String song = files[i].getName();
                int txt = song.lastIndexOf(".");
                String musictitle = song.substring(0, txt);
                arrayList.add(musictitle);
            }
        }

        ArrayAdapter<String> arrayAdt = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);

        m_ListView.setAdapter(arrayAdt);

        // ListView 아이템 터치 시 이벤트를 처리할 리스너 설정
        m_ListView.setOnItemClickListener(onClickListItem);

        // ListView 아이템 롱 터치 시 플로팅컨텍스트 메뉴가 나타남
        registerForContextMenu(m_ListView);

    }

    // 아이템 터치 이벤트 리스너 구현
    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            noodleName = (String) parent.getItemAtPosition(position);
            String loadedData = mFileMgr.load(noodleName);
            Toast.makeText(getApplicationContext(),loadedData,Toast.LENGTH_SHORT);


            if(cool != null){
                cool.autoPause();
                LibraryPlay.setImageResource(R.drawable.playnoodle);
            }

            if(playsw == 1) w.interrupt();
            if(cool != null) cool.release();
            isPlay = false;


            cool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
            chefno = loadedData.substring(0,1);
            setChefNo(chefno);
            loadmusic(loadedData);//불러오기 모드

            LibraryPlay.setVisibility(View.VISIBLE);

            for (int i = 0; i < m_ListView.getChildCount(); i++) {
                if(position == i ){
                    m_ListView.getChildAt(i).setBackgroundColor(Color.GRAY);
                }else{
                    m_ListView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
            }

            loopcnt = 0;
            empty = 0;
            for(int i=0; i<4; i++){//모든 우드와 우드의 악기를 메뉴배열에 추가시킴
                for(int j=0; j<3; j++) {
                    if(loop[i][j] == 0) loopcnt++;
                    else loopcnt = 0;
                }
                if(loopcnt == 3) empty = i+1;
            }
        }
    };


    public void LibraryPlay(View view){
        if (playsw == 0) {//멈춰져있는 상태
            LibraryPlay.setImageResource(R.drawable.stopnoodle);
            w = new NoodleLibraryActivity.PlayMusic();
            w.start();
        } else {    //켜져있는 상태
            LibraryPlay.setImageResource(R.drawable.playnoodle);
            w.interrupt();
            w = new NoodleLibraryActivity.PlayMusic();
            playsw = 0;
        }
        isPlay = true;
    }



    // 국수 수정
    public void onUpdateNoodle(String data){
        Intent intent = new Intent(this, UpdateNoodle.class);
        intent.putExtra("data", data);
        intent.putExtra("name", noodleName);
        startActivity(intent);
    }

    //플로팅 컨텍스트메뉴 항목
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //메뉴 생성
        menu.setHeaderTitle("가락국수");
        menu.add(0, 1, 0, "수정");
        menu.add(0, 2, 0, "삭제");
    }
    //컨텍스트메뉴 선택 이벤트
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        noodleName = ((TextView) info.targetView).getText().toString();

        switch (item.getItemId()) {
            case 1:
                //수정
                onUpdateNoodle(mFileMgr.load(noodleName));
                return true;

            case 2:
                //삭제
                mFileMgr.delete(noodleName);
                //삭제가 이루어지면 onRestart()를 실행함으로써 리스트뷰를 최신화해준다.
                this.onRestart();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void setChefNo(String ChefNo){
        if(ChefNo.equals("1")) {
            chord1 = cool.load(this, R.raw.edm_chord1, 1);
            chord2 = cool.load(this, R.raw.edm_chord2, 1);
            chord3 = cool.load(this, R.raw.edm_chord3, 1);

            drum1 = cool.load(this, R.raw.edm_drum1, 1);
            drum2 = cool.load(this, R.raw.edm_drum2, 1);
            drum3 = cool.load(this, R.raw.edm_drum3, 1);

            melody1 = cool.load(this, R.raw.edm_melody1, 1);
            melody2 = cool.load(this, R.raw.edm_melody2, 1);
            melody3 = cool.load(this, R.raw.edm_melody3, 1);

            pepper = cool.load(this, R.raw.edm_pepper1, 1);
        }else if(ChefNo.equals("2")) {
            chord1 = cool.load(this, R.raw.hiphop_bass1, 1);
            chord2 = cool.load(this, R.raw.hiphop_bass2, 1);
            chord3 = cool.load(this, R.raw.hiphop_bass3, 1);

            drum1 = cool.load(this, R.raw.hiphop_drum1, 1);
            drum2 = cool.load(this, R.raw.hiphop_drum2, 1);
            drum3 = cool.load(this, R.raw.hiphop_drum3, 1);

            melody1 = cool.load(this, R.raw.hiphop_melody1, 1);
            melody2 = cool.load(this, R.raw.hiphop_melody2, 1);
            melody3 = cool.load(this, R.raw.hiphop_melody3, 1);

            pepper = cool.load(this, R.raw.hiphop_pepper1, 1);
        }else if(ChefNo.equals("3")) {
            chord1 = cool.load(this, R.raw.rock_bass1, 1);
            chord2 = cool.load(this, R.raw.rock_bass2, 1);
            chord3 = cool.load(this, R.raw.rock_bass3, 1);

            drum1 = cool.load(this, R.raw.rock_drum1, 1);
            drum2 = cool.load(this, R.raw.rock_drum2, 1);
            drum3 = cool.load(this, R.raw.rock_drum3, 1);

            melody1 = cool.load(this, R.raw.rock_guitar1, 1);
            melody2 = cool.load(this, R.raw.rock_guitar2, 1);
            melody3 = cool.load(this, R.raw.rock_guitar3, 1);

            pepper = cool.load(this, R.raw.rock_pepper1, 1);
        }
    }

    public void loadmusic(String data){
        for (int i = 0, k = 1; i < 4; i++){
            for (int j = 0; j < 3; j++, k++){
                index[i][j] = Integer.parseInt(data.substring(k,k+1));
            }
            switch (index[i][0]) {
                case 0:
                    loop[i][0] = chord1;
                    break;
                case 1:
                    loop[i][0] = chord2;
                    break;
                case 2:
                    loop[i][0] = chord3;
                    break;
                case 3:
                    loop[i][0] = 0;
                    break;
            }
            switch (index[i][1]) {
                case 0:
                    loop[i][1] = drum1;
                    break;
                case 1:
                    loop[i][1] = drum2;
                    break;
                case 2:
                    loop[i][1] = drum3;
                    break;
                case 3:
                    loop[i][1] = 0;
                    break;
            }
            switch (index[i][2]) {
                case 0:
                    loop[i][2] = melody1;
                    break;
                case 1:
                    loop[i][2] = melody2;
                    break;
                case 2:
                    loop[i][2] = melody3;
                    break;
                case 3:
                    loop[i][2] = 0;
                    break;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(cool != null) cool.autoPause();
        if(playsw == 1) w.interrupt();
        if(cool != null) cool.release();
        isPlay = false;
    }
    public void onBackPressed() {
        super.onBackPressed();

        StartNoodleActivity.fa.finish();

        if(cool != null) cool.autoPause();
        if(playsw == 1) w.interrupt();
        if(cool != null) cool.release();
        isPlay = false;
        Intent intent = new Intent(this,StartNoodleActivity.class);
        startActivity(intent);
    }

    class PlayMusic extends Thread {
        public void run() {
            playsw = 1;
            for(int j=0; j<4; j++) {
                for (int i = 0; i < 3; i++) {
                    if(loop[j][i]!=0) cool.play(loop[j][i], 10f, 10f, 0, 0, 1);
                }
                try {
                    if(chefno.equals("2")) Thread.sleep(7385);
                    else Thread.sleep(8000);
                    cool.autoPause();
                }
                catch(InterruptedException e){
                    cool.autoPause();
                    playsw=0;
                    return;
                }
            }
            playsw = 0;
            isPlay = true;
        }
    }
}