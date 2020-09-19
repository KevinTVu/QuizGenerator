package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.auth.FirebaseAuth;

import com.example.finalproject.ADMIN.AdminActivity;
import com.example.finalproject.ADMIN.QuizLayoutActivity;
import com.example.finalproject.STUDENT.Quiz;
import com.example.finalproject.STUDENT.QuizAnswersUtil;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    //Integer variable that is the return code from the signup activity to the login activity
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;

    //HashMap with key String and value String that will store the users credentials
    HashMap<String,String> database = new HashMap<String,String>();

    //One private button variables that will be the Login and
    private Button m_btnLogin;

    //One textview that is clickable
    private TextView clickableRegisterTextView, forgotPasswordTextView;

    //Two private EditText variables that will be used for the username and password
    private EditText m_txtUsername, m_txtPassword;

    //One private Toast variable that will be used to display different types of text
    private Toast toast;

    //Database to access user information
    private DatabaseHelper db;



    //Context variable that will be the current context of Login activity
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Find the views from layout resource file that are attached to LoginActivity
        m_btnLogin =  findViewById(R.id.btnLogin);
        clickableRegisterTextView = findViewById(R.id.registerTextViewClickable);
        m_txtUsername = findViewById(R.id.txtUsername);
        m_txtPassword = findViewById(R.id.txtPassword);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextViewClickable);

        db = new DatabaseHelper(this);

        //context is initialized to the current context
        context = getApplicationContext();

        //If login key is clicked
        m_btnLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String username = m_txtUsername.getText().toString();
                String password = m_txtPassword.getText().toString();
                boolean checkUserAndPass = db.checkLogin(username, password);
                if(checkUserAndPass)
                {
                    //Intent intent = new Intent(context, Student.class);
                    Intent intent = new Intent(getApplicationContext(), BlankActivity.class);
                    QuizAnswersUtil.username = username;
                    //intent.putExtra("username", username);
                    startActivity(intent);
                    Toast.makeText(context,"Login Successful", Toast.LENGTH_LONG).show();
                }
                else if (username.equals("admin") && password.equals("admin")){
                    //Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                    Intent intent = new Intent(getApplicationContext(), BlankActivity.class);

                    Toast.makeText(context,"Administrator login successful", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(context,"Login Unsuccessful", Toast.LENGTH_LONG).show();
                    m_txtPassword.getText().clear();
                }

            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //clears the username EditText
                m_txtUsername.getText().clear();

                //clears the password EditText
                m_txtPassword.getText().clear();

                //Toast that displays that it is transitioning into the reset password
                toast = Toast.makeText(context, "Loading reset password screen", Toast.LENGTH_LONG);

                toast.show();

                Intent forgotPassword = new Intent(context, ResetPassword.class);

                forgotPassword.putExtra("hashMap", database);

                startActivityForResult(forgotPassword, SECOND_ACTIVITY_REQUEST_CODE);


            }
        });

                //Enters if register button is clicked
                clickableRegisterTextView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //clears the username EditText
                        m_txtUsername.getText().clear();

                        //clears the password EditText
                        m_txtPassword.getText().clear();

                        //Toast that displays that it is transitioning into the loading signup form
                        toast = Toast.makeText(context, "Loading signup form", Toast.LENGTH_LONG);

                        //Displays the toast
                        toast.show();

                        //Intent register that transitions to SignUpActivity class
                        Intent register = new Intent(context, RegisterActivity.class);

                        //Sends the database HashMap to the SignUpActivity
                        register.putExtra("hashMap", database);

                        //Starts the activity
                        startActivityForResult(register, SECOND_ACTIVITY_REQUEST_CODE);
                    }
                });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                HashMap<String, String> hashMapTemp = (HashMap<String, String>) data.getSerializableExtra("new user");
                for(String s: hashMapTemp.keySet())
                {
                    database.put(s, hashMapTemp.get(s));
                }
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        m_txtUsername.getText().clear();
        m_txtPassword.getText().clear();
    }
}
