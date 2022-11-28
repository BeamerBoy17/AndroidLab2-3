package com.example.myapplication;


import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    Button signUp;
    EditText username;
    EditText password;
    TextView errorMessage;
    DataBaseHandler db = new DataBaseHandler(this);

    private final static String TAG= "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final Context Enter = MainActivity.this.getApplicationContext();
        username = findViewById(R.id.Name);
        password = findViewById(R.id.Password);
        signUp = findViewById(R.id.signUp);

        errorMessage = findViewById(R.id.errorMessage);
        Log.d(TAG, "OnCreate");
        Button button1 = findViewById(R.id.button1);
        Button signUp = findViewById(R.id.signUp);
        Intent Intent = new Intent(this, ProfileActivity.class);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User logUser = new User(username.getText().toString().trim(), password.getText().toString().trim());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(logUser.getLogin().equals("") || logUser.getPass().equals("")) {
                            errorMessage.post(new Runnable() {
                                @Override
                                public void run() {
                                    errorMessage.setText("Заполните поля!");
                                    errorMessage.setVisibility(View.VISIBLE);
                                }
                            });
                        }else if(CheckUser(logUser)){
                            db.close();
                            Intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                            Intent.putExtra("user", logUser);
                            Enter.startActivity(Intent);

                        }else{
                            errorMessage.post(new Runnable() {
                                @Override
                                public void run() {
                                    errorMessage.setText("Неверный логин или пароль!");
                                    errorMessage.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    }
                }).start();

            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
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
    public boolean CheckUser(User user) {
        return db.selectUserData(user);
    }
}