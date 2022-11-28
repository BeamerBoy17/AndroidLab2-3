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


public class SignUpActivity extends AppCompatActivity {
    Button signUp;
    EditText username;
    EditText password;
    User user;
    TextView errorMessage;
    DataBaseHandler db = new DataBaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        final Context SignUp = SignUpActivity.this.getApplicationContext();
        errorMessage= findViewById(R.id.errorMessage);
        username = findViewById(R.id.Name);
        password = findViewById(R.id.Password);
        signUp = findViewById(R.id.signUp);

        Intent Intent = new Intent(SignUpActivity.this, ProfileActivity.class);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")) {
                    errorMessage.setText("Fill the form");
                    errorMessage.setVisibility(View.VISIBLE);
                } else {
                    user = new User(username.getText().toString().trim(), password.getText().toString().trim());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            boolean check = user.checkName(db);

                            try {
                                Thread.sleep(15000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if(check){
                                errorMessage.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        errorMessage.setText("Пользователь с таким именем уже существует!");
                                        errorMessage.setVisibility(View.VISIBLE);
                                    }
                                });

                            }else if(user.checkPass()){
                                errorMessage.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        errorMessage.setText("Пароль менее 3 символов!");
                                        errorMessage.setVisibility(View.VISIBLE);
                                    }
                                });

                            }else {
                                errorMessage.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        errorMessage.setText("Регистрация прошла успешно!");
                                        errorMessage.setVisibility(View.VISIBLE);
                                    }
                                });
                                db.addUser(user);
                                db.close();

                               Intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                               Intent.putExtra("user", user);
                                SignUp.startActivity(Intent);

                            }
                        }
                    }).start();
                }
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}