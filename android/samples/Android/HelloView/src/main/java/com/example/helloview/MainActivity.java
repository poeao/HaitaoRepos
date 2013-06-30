package com.example.helloview;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button );
        RadioButton rb = (RadioButton)findViewById(R.id.radioButton1);
        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup);
        CheckBox cb = (CheckBox) findViewById(R.id.checkBox);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text = (EditText)findViewById(R.id.editText );
                Button button = (Button)view;
                button.setText("Clicked Button");
                show(text.getText().toString());
                text.setText("Changed By Click Button");
            }
        });

        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    RadioButton r = (RadioButton)compoundButton;
                    show("Radio Button has been clicked." + r.getText().toString());
                }
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.radioButton1) {
                    show("You have selected a woman.");
                } else if(i == R.id.radioButton2) {
                    show("You have selected a man. ");
                }
            }
        });

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox)view;
                if(cb.isChecked()) {
                    show("You checked zhuhai");
                } else {
                    show("You unchecked Zhuhai");
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void show(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT ).show();
    }
}
