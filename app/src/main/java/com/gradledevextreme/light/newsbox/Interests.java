package com.gradledevextreme.light.newsbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import java.util.ArrayList;

public class Interests extends AppCompatActivity {


    private EditText mKeywords;
    public static ArrayList<String> mArr;
    private Button mAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);


        mArr = new ArrayList<>();
        mAdd = (Button) findViewById(R.id.add);


        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mKeywords = (EditText) findViewById(R.id.keywords);
                if (mKeywords.getText() != null) {
                    mArr.add(mKeywords.getText().toString());
                }
            }
        });
    }
}

