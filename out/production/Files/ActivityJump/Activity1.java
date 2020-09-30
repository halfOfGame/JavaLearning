package com.example.activitytest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Activity1 extends AppCompatActivity {


    TextView textView2, textView3;
    EditText editText1, editText2;
    Button button1, button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);


        textView2 = findViewById(R.id.textView4);
        textView3 = findViewById(R.id.textView6);
        editText1 = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView2.setText(editText1.getText());
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity1.this, Activity2.class);
                Bundle bundle = new Bundle();
                bundle.putString("key1", editText2.getText().toString());
                intent.putExtras(bundle);
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            String value2 = data.getStringExtra("key2");
            textView3.setText(value2);
        }
    }
}
