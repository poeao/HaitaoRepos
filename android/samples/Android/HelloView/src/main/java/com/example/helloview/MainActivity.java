package com.example.helloview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity {

    String[] cities = {"上海","北京","珠海","承德"};
    ArrayAdapter<String> adapter = null;
    boolean display = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button );
        Button button2 = (Button)findViewById(R.id.button2 );
        RadioButton rb = (RadioButton)findViewById(R.id.radioButton1);
        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup);
        CheckBox cb = (CheckBox) findViewById(R.id.checkBox);
        Spinner spinner = (Spinner) findViewById(R.id.spinner );

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text = (EditText)findViewById(R.id.editText );
                Button button = (Button)view;
                button.setText("Clicked Button");
                if(text.getText() != null) {
                    show(text.getText().toString());
                }
                text.setText("Changed By Click Button");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressBar bar = (ProgressBar)findViewById(R.id.progressBar );
                if(display) {
                    bar.setVisibility(View.INVISIBLE);
                    display = false;
                } else {
                    bar.setVisibility(View.VISIBLE);
                    display = true;
                }

            }
        });

        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                   // RadioButton r = (RadioButton)compoundButton;
                    //show("Radio Button has been clicked."));
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

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String text = ((TextView)view).getText().toString() +"position : " + position +" with ID --> " + id;
                show(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                show("You 啥也没选啊!好赖选一个呗!");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.mainmenu, menu);

        menu.add("Main");
        return true;
    }

    private void show(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT ).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.exit) {
            finish();
        } else if(item.getItemId() == R.id.about) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Title");
            AlertDialog dialog = builder.create();
            dialog.setMessage("Hello, 欢迎点击菜单!");
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            dialog.show();;
        }

        return super.onOptionsItemSelected(item);
    }


}
