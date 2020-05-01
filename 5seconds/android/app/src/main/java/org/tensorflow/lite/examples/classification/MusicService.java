package org.tensorflow.lite.examples.classification;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MusicService extends Service {
    private static final String TAG = "MusicService";
    MediaPlayer player;
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");

//        player = MediaPlayer.create(this, R.raw.bubblefighter); // create(Context context, int resid)
//        player.setLooping(true); // 반복재생 여부 설정
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // intent: startService() 호출 시 넘기는 intent 객체
        // flags: service start 요청에 대한 부가 정보. 0, START_FLAG_REDELIVERY, START_FLAG_RETRY
        // startId: start 요청을 나타내는 unique integer id
        Log.d(TAG, "onStartCommand()");

        Integer param = intent.getIntExtra("index", 0);
        Toast.makeText(this, param+"", Toast.LENGTH_SHORT).show();
        if(param == 1)player = MediaPlayer.create(this, R.raw.song1);
        else if(param == 2)player = MediaPlayer.create(this, R.raw.song44);
        else if(param == 3)player = MediaPlayer.create(this, R.raw.song4);
        else if(param == 4)player = MediaPlayer.create(this, R.raw.song2);
        else if(param == 5)player = MediaPlayer.create(this, R.raw.song6);

        player.setLooping(true); // 반복재생 여부 설정
        player.start();

        return START_NOT_STICKY;
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        Toast.makeText(this, "MusicService 중지", Toast.LENGTH_SHORT).show();

        player.stop();
        player.release();
        player = null;
    }

    // 아래 onBind 메소드가 없으면 어떻게 될까?
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
