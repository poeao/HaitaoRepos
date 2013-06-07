package com.me.helloandroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import com.me.configuration.CommonConstants;

/**
 * Created by Haitao Song on 13-6-6.
 */
public class BeginService  extends Service{
    private MyBinder myBinder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public void myMethod() {
        for(int i=0; i < 100; i++) {
            try {
                Thread.sleep(1000);
                Log.i(CommonConstants.LOG_TAG, "Begin Service mymethod() --- > " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Main thread
        for(int i=0; i < 100; i++) {
            try {
                Thread.sleep(1000);
                Log.i(CommonConstants.LOG_TAG, "Starting  BeginService --- > " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public class MyBinder extends Binder {
        public BeginService getService() {
            return  BeginService.this;
        }
    }

}
