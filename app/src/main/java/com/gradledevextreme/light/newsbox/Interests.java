package com.gradledevextreme.light.newsbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import java.util.ArrayList;

public class Interests extends AppCompatActivity {


    private EditText mKeywords;
    public static String mArr;
    private Button mAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);


        mKeywords = (EditText) findViewById(R.id.keywords);
        mAdd = (Button) findViewById(R.id.add);


        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mArr = mKeywords.getText().toString();
            }
        });
    }
}

