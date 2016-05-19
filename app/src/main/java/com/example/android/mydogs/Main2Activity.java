package com.example.android.mydogs;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        String savedName = prefs.getString("name", "empty");
        TextView dogNameSaved = (TextView)findViewById(R.id.dogNameSaved);
        dogNameSaved.setText(savedName);
    }
}
