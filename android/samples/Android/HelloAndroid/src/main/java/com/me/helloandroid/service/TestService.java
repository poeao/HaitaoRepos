package com.me.helloandroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.me.configuration.CommonConstants;

/**
 * Created by Haitao Song on 13-6-7.
 */
public class TestService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        for(int i = 0; i < 100; i ++) {
            Log.i(CommonConstants.LOG_TAG, "Test Service --- > " + i);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(CommonConstants.LOG_TAG, "Stop test service ......");
        super.onDestroy();
    }
}
