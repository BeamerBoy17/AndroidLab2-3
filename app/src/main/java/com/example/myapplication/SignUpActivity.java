package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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
                    if (user.checkName(db)) {
                        errorMessage.setText("User already exists");
                        errorMessage.setVisibility(View.VISIBLE);
                    } else if (user.checkPass()) {
                        errorMessage.setText("Minimum 1 symbols!");
                        errorMessage.setVisibility(View.VISIBLE);
                    } else {
                        errorMessage.setText("Succes!");
                        errorMessage.setVisibility(View.VISIBLE);
                        Log.i("MESSAGE", "onClick: USER ADD");
                        db.addUser(user);
                        db.close();
                        Intent.putExtra("user", user);
                        startActivity(Intent);
                    }
                }
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}