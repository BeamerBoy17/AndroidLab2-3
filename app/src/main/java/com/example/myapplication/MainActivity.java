package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sPref;
    EditText nameText;
    final String SAVED_TEXT = "saved_text";

    private final static String TAG= "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "OnCreate");
        nameText = (EditText) findViewById(R.id.Name);
        loadText();
        Button enter =  findViewById(R.id.button1);
        enter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                Intent intent= new Intent(MainActivity.this,AdapterActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

    }

    void saveText(){
        sPref = getPreferences(MODE_PRIVATE);
        Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, nameText.getText().toString());
        ed.commit();

    }
    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        Editor ed = sPref.edit();
        String savedText = sPref.getString(SAVED_TEXT, "");
        nameText.setText(savedText);

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        saveText();
        Log.d(TAG, "onDestroy");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "onStop");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
}