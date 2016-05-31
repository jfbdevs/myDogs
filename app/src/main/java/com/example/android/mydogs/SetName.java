package com.example.android.mydogs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SetName extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public final static String EXTRA_MESSAGE2 = "com.mycompany.myfirstapp.MESSAGE2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_name);
        Intent intent = getIntent();
        String message = intent.getStringExtra(SetName.EXTRA_MESSAGE);
        String messageAge = intent.getStringExtra(SetName.EXTRA_MESSAGE2);
        float age = Float.parseFloat(messageAge);
        Dog myDog=new Dog(message, age);

        TextView dogName = (TextView)findViewById(R.id.name);
        dogName.setText(myDog.getName());

        TextView dogAge = (TextView)findViewById(R.id.age);
        dogAge.setText(String.valueOf(myDog.getAge()));

        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.MyDogs", Context.MODE_PRIVATE);

       // SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", myDog.getName());
        editor.commit();


        //int defaultValue = getResources().getInteger(R.string.saved_high_score_default);
        String savedName = prefs.getString("name", "empty");
        TextView dogNameSaved = (TextView)findViewById(R.id.dogNameSaved);
        dogNameSaved.setText(savedName);
/** Called when the user clicks the Send button */
    }
}
