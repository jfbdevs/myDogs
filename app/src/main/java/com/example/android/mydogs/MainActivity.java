package com.example.android.mydogs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Dog myDog = new Dog("Jago", 3);
        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.MyDogs", Context.MODE_PRIVATE);
        String savedName = prefs.getString("name", "empty");
        Button openNew = (Button)findViewById(R.id.openNew);



        String photoPath = Environment.getExternalStorageDirectory()+"/abc.jpg";


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);


        if(savedName.equals("empty")){
            assert openNew != null;
            openNew.setVisibility(View.VISIBLE);

        }
        else {
            TextView dogNameSaved = (TextView) findViewById(R.id.dogNameSaved);
            dogNameSaved.setText(savedName);
        }


// globally
    }

    public void openNew(View view) {
        Intent intent2 = new Intent(this, selectImage.class);
        startActivity(intent2);
    }




}

