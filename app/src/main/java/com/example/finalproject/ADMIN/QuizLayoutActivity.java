package com.example.finalproject.ADMIN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.finalproject.DatabaseHelper;
import com.example.finalproject.R;

import java.util.Random;

public class QuizLayoutActivity extends AppCompatActivity {

    private EditText topicEditText;
    private EditText amtOfQuestionsEditText;
    private EditText timeLimitEditText;
    private Button submitButton;
    AwesomeValidation awesomeValidation;
    private int random;

    private String topic;
    private int timeLimit;
    private int amtOfQuestions;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_layout);

        topicEditText = findViewById(R.id.topicEditText);
        amtOfQuestionsEditText = findViewById(R.id.amtOfQuestionEditText);
        timeLimitEditText = findViewById(R.id.timeLimitEditText);
        submitButton = findViewById(R.id.submitActivityQuizLayoutButton);
        db = new DatabaseHelper(this);

        String regex = "^[1-9][0-9]*$";

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Validations to make sure that the amtOfQuestionEditText and timeLimit only contain integers and not characters
        awesomeValidation.addValidation(this, R.id.amtOfQuestionEditText, regex, R.string.invalid_number);
        awesomeValidation.addValidation(this, R.id.timeLimitEditText, regex, R.string.invalid_number);


        //Submit button listener that will listen to see if the user clicks on the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Extra validations that checks if the editText boxes are empty
                if(isEmpty(topicEditText))
                {
                    topicEditText.setError("Topic cannot be empty!");
                }
                if(isEmpty(amtOfQuestionsEditText))
                {
                    amtOfQuestionsEditText.setError("Amount cannot be empty!");
                }
                else if(isEmpty(timeLimitEditText))
                {
                    timeLimitEditText.setError("Time limit cannot be empty");
                }
                else {
                    //If validations were good then it will enter this if statement
                    if (awesomeValidation.validate()) {
                        random = new Random().nextInt(1000) + 0;

                        topic = topicEditText.getText().toString();
                        timeLimit = Integer.parseInt(timeLimitEditText.getText().toString());
                        amtOfQuestions = Integer.parseInt(amtOfQuestionsEditText.getText().toString());

                        //Intent that will be used to send the topic, timelimit, amountofquestions, and random generated quiz id to the CreateQuestionActivity
                        Intent intent = new Intent(getApplicationContext(), CreateQuestionActivity.class);
                        intent.putExtra("topic", topic);
                        intent.putExtra("timeLimit", timeLimit);
                        intent.putExtra("amountOfQuestions", amtOfQuestions);
                        intent.putExtra("randomQuizID", random);

                        //Uses the databasehelper to insert the randomly generated number, topic, amount of questions, and time limit into the QuizInformation table
                        boolean check = db.insertQuizInformation(random, topic, amtOfQuestions, timeLimit);     // This has to add
                        if (check == true) {
                            Toast.makeText(getApplicationContext(), "Successfully added into database", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_LONG).show();
                        }
                        startActivity(intent);
                    }
                }
            }
        });


    }
    //isEmpty function that will check if the EditText is empty
    private Boolean isEmpty(EditText eText)
    {
        if(eText.getText().toString().isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}