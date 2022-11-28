package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.widget.Toast;
import

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    final Context context = this;
    TextView userName;
    Button exit;
    Button deleteUser;
    Button changePassword;
    Button accept;
    EditText newPassword;
    Intent intent;
    Toast toast;
    DataBaseHandler db = new DataBaseHandler(this);
    User user = new User();
    final Handler handler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userName = findViewById(R.id.userName);
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            user = (User) arguments.getSerializable("user");
        }
        userName.setText(user.getLogin());

        exit = findViewById(R.id.Exit);
        exit.setOnClickListener(this);
        deleteUser = findViewById(R.id.deleteUser);
        deleteUser.setOnClickListener(this);
        changePassword = findViewById(R.id.changePass);
        changePassword.setOnClickListener(this);
        intent = new Intent(this, MainActivity.class);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Exit:
                startActivity(intent);
                break;

            case R.id.deleteUser:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        db.deleteUserFromDB(user);
                        db.close();
                    }
                }).start();
                startActivity(intent);
                break;

            case R.id.changePass:
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        LayoutInflater li = LayoutInflater.from(context);
                        View passView = li.inflate(R.layout.dialog_change_pass, null);
                        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);

                        mDialogBuilder.setView(passView);
                        final EditText userInput = (EditText) passView.findViewById(R.id.newPass);
                        mDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("Accept",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                String newPwd = userInput.getText().toString();
                                                System.out.println("new " + newPwd);
                                                System.out.println(user.getPass());
                                                if(newPwd.equals("")){

                                                }else if(newPwd.equals(user.getPass())) {
                                                    toast=Toast.makeText(ProfileActivity.this,"Password match",Toast.LENGTH_SHORT);
                                                    toast.show();
                                                } else {
                                                    toast=Toast.makeText(ProfileActivity.this,"Succes",Toast.LENGTH_SHORT);
                                                    toast.show();
                                                    db.updateUserPassword(user, newPwd);
                                                    db.close();
                                                }

                                            }
                                        })
                                .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = mDialogBuilder.create();
                        alertDialog.show();
                    }
                });

                break;
            default:
                break;
        }
    }
}