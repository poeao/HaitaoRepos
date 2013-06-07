package com.me.helloandroid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.app.Activity;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.me.configuration.CommonConstants;
import com.me.helloandroid.service.BeginService;
import com.me.helloandroid.service.MusicService;

public class MainActivity extends Activity implements View.OnClickListener {

    Intent intentS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Log.i(CommonConstants.LOG_TAG, "----OnCreate();");
        Log.v("myTag", "Hello world log message");
        Log.d("myTag", "Debug info message");
        Log.i("myTag", "Info message");
        Log.w("myTag", "Warning info message");
        Log.e("myTag", "Error info message");

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
        Button buttonM = (Button)findViewById(R.id.music);
        buttonM.setOnClickListener(this);
        Button buttonB = (Button)findViewById(R.id.bindButton);
        buttonB.setOnClickListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 1234) {
            Toast.makeText(this, "Return value ---> " + data.getStringExtra("key"), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                intent.putExtra("myintent", "Hello, intent extra");
                startActivityForResult(intent, 1234);
                break;
            case R.id.music:
                if(intentS == null) {
                    intentS = new Intent(MainActivity.this, MusicService.class);
                }
                Button bm = (Button)view;
                if(bm.getText().toString().equals("StartMusic")) {
                    bm.setText("StopMusic");
                    startService(intentS);

                } else {
                    //intent should be the member variable, or it won't be stopped.
                    bm.setText("StartMusic");
                    stopService(intentS);
                }
                break;
            case R.id.bindButton:
                Intent intentb = new Intent(MainActivity.this, BeginService.class);
                //Call bind service
                bindService(intentb, conn, Context.BIND_AUTO_CREATE );
                break;
            default:
                break;
        }
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BeginService.MyBinder binder = (BeginService.MyBinder)iBinder;
            BeginService bindService = binder.getService();
            //running on server side
            bindService.myMethod();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(CommonConstants.LOG_TAG, "----OnStart();");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(CommonConstants.LOG_TAG, "----OnPause();");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(CommonConstants.LOG_TAG, "----OnResume();");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(CommonConstants.LOG_TAG, "----OnStop);");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(CommonConstants.LOG_TAG, "----OnDestroy();");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(CommonConstants.LOG_TAG, "----OnRestart();");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
