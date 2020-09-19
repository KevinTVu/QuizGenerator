package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPassword extends AppCompatActivity {


    EditText username, password, email;

    private DatabaseHelper db;

    Button resetPasswordButton;

    //Context variable that will be the current context of Login activity
    Context context;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        username = findViewById(R.id.usernameVerify);
        email = findViewById(R.id.emailVerify);
        password = findViewById(R.id.newPassword);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        db = new DatabaseHelper(this);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameString = username.getText().toString();
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();

                boolean checkUsernameAndEmail = db.checkAccountExist(usernameString, emailString);
                if(checkUsernameAndEmail == true)
                {
                    boolean updatePassword = db.updatePassword(usernameString, passwordString);
                    if(true == updatePassword)
                    {
                        toast.makeText(context, "Password successfully changed", toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }

                }
                else
                {
                    username.setError("Invalid Username");
                    email.setError("Invalid Email");
                }

            }
        });

        //context is initialized to the current context
        context = getApplicationContext();




    }
}