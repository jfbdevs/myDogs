package com.example.android.mydogs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public final static String EXTRA_MESSAGE2 = "com.mycompany.myfirstapp.MESSAGE2";
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


    public void sendMessage(View view) {
        Intent intent = new Intent(this, SetName.class);
        // get the name and append to the intent
        EditText editText = (EditText) findViewById(R.id.inputName);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        // get the age and append to the intent
        EditText editText2 = (EditText) findViewById(R.id.insertAge);
        String messageAge = editText2.getText().toString();
        intent.putExtra(EXTRA_MESSAGE2, messageAge);
        startActivity(intent);
    }

}
