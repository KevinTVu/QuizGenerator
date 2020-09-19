package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    //The view objects
    private EditText editTextUsername, editTextPassword, editTextRePassword, editTextPhone, editTextEmail;

    private Button buttonSubmit;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    private DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Creates a new awesomeValidation object
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        String regexPhone = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
        String regexUsername = "^\\s(?:\\S\\s*){4,20}$";
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";

        //Find the views from layout resource file that are attached to SignUp activity
        editTextUsername = (EditText) findViewById(R.id.editUsername);
        editTextPassword = (EditText) findViewById(R.id.editPassword);
        editTextRePassword = (EditText) findViewById(R.id.editRetypePass);
        editTextPhone = (EditText) findViewById(R.id.editPhone);
        editTextEmail = (EditText) findViewById(R.id.editEmail);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

        //Validation of the username, email, phone. Password will be checked in the submit form stage
        awesomeValidation.addValidation(this, R.id.editUsername, RegexTemplate.NOT_EMPTY, R.string.name_error);
        awesomeValidation.addValidation(this, R.id.editEmail, Patterns.EMAIL_ADDRESS, R.string.email_error);
        awesomeValidation.addValidation(this, R.id.editPhone, regexPhone, R.string.mobile_error);
        awesomeValidation.addValidation(this, R.id.editPassword, RegexTemplate.NOT_EMPTY, R.string.pass_invalid);
        awesomeValidation.addValidation(this, R.id.editRetypePass, RegexTemplate.NOT_EMPTY, R.string.pass_invalid);
        //awesomeValidation.addValidation(this, R.id.editPassword, R.id.editRetypePass, R.string.pass_error);

        //onClickListener on the submit button
        buttonSubmit.setOnClickListener((View.OnClickListener) this);
    }

    private void submitForm() {
        if (awesomeValidation.validate()) {

            //Stores the content in the username text box into a username String
            String username = editTextUsername.getText().toString();

            //Stores the content in the password text box into a password String
            String password = editTextPassword.getText().toString();

            //Stores the content in the email text box into a email String
            String email = editTextEmail.getText().toString();

            //Stores the content in the phoneNumber text box into a phoneNumber string
            String phoneNumber = editTextPhone.getText().toString();



            Boolean checkUsername = db.checkUserName(username);
            if(checkUsername == true)
            {
                Boolean insert = db.insert(username, password, email, phoneNumber);
                if(insert == true)
                {
                   Toast.makeText(this, "User added to the database", Toast.LENGTH_LONG).show();
                   finish();
                }
            }
            else
            {
                editTextUsername.setError("Username already exist");
            }
        }

    }

    //If the user clicks the submit button, it will check if the two passwords match. If it matches, it calls the submitForm function
    public void onClick(View view) {
        //If the password is not equal to the reenter password, it sets an error message
        if(!editTextPassword.getText().toString().equals(editTextRePassword.getText().toString()))
        {
            editTextPassword.setError("Password do not match");
            editTextRePassword.setError("Password do not match");
        }
        //else no errors are set and submitForm function is called
        else {
            editTextPassword.setError(null);
            editTextRePassword.setError(null);
            submitForm();
        }
    }
}
