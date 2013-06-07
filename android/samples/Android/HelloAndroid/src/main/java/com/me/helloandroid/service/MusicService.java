package com.me.helloandroid.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import com.me.configuration.CommonConstants;
import com.me.helloandroid.R;

/**
 * Created by Haitao Song on 13-6-7.
 */
public class MusicService extends Service {
    MediaPlayer mp;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp = MediaPlayer.create(this, R.raw.ichego);
        mp.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(CommonConstants.LOG_TAG, "Stop Music service ......");
        mp.stop();
        super.onDestroy();
    }


}
