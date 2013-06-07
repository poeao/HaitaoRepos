package com.me.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Haitao Song on 13-6-1.
 */
public class FirstActivity extends Activity {
    private Intent getintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.first);

        Button close = (Button) findViewById(R.id.button);
        final EditText etext = (EditText) findViewById(R.id.editText);
        //get the intent transferred from main activity.
        getintent = getIntent();

        etext.setText(getintent.getStringExtra("myintent"));

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getintent.putExtra("key", etext.getText().toString());

                setResult(1234, getintent);
                finish();
            }
        });
    }
}
