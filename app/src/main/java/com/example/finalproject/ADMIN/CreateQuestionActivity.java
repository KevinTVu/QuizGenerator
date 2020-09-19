package com.example.finalproject.ADMIN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.finalproject.DatabaseHelper;
import com.example.finalproject.R;

import java.util.HashMap;

public class CreateQuestionActivity extends AppCompatActivity {
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private EditText questionEditText;
    private EditText choiceOneEditText;
    private EditText choiceTwoEditText;
    private EditText choiceThreeEditText;
    private EditText correctChoiceEditText;
    private Context context;
    private Button submitQuestionButton;
    private TextView questionNumTextView;
    private DatabaseHelper db;
    private String topic;
    private int timeLimit;
    private int amountOfQuestions;
    private int temp = 1;
    private int quizID = 1;
    private String question;
    private String choiceOne;
    private String choiceTwo;
    private String choiceThree;
    private String correctChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        questionEditText = findViewById(R.id.questionEditText);
        choiceOneEditText =  findViewById(R.id.choice1EditText);
        choiceTwoEditText = findViewById(R.id.choice2EditText);
        choiceThreeEditText = findViewById(R.id.choice3EditText);
        submitQuestionButton = findViewById(R.id.submitQuestionButton);
        questionNumTextView = findViewById(R.id.questionNumTextView);
        correctChoiceEditText = findViewById(R.id.correctChoiceEditText);
        context = getApplicationContext();

        //Declare Intent variable that will grab the intent from the QuizLayoutActivity
        Intent getIntent = getIntent();

        //Grabs the topic, time limit, amount of questions, and a random generated ID from QuizLayOutActivity
        topic = getIntent.getStringExtra("topic");
        timeLimit = getIntent.getIntExtra("timeLimit", 0);
        amountOfQuestions = getIntent.getIntExtra("amountOfQuestions", 0);
        quizID = getIntent.getIntExtra("randomQuizID", quizID);

        //Creates a new DatabaseHelper object
        db = new DatabaseHelper(this);

        //Listener that will listen to the response of the submit button
        submitQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validations conditions that need to be met in order to successfully add the question into the database
                if(isEmpty(questionEditText))
                {
                    questionEditText.setError("Question cannot be empty!");
                }
                else if(isEmpty(choiceOneEditText))
                {
                    choiceOneEditText.setError("Choice 1 cannot be empty!");
                }
                else if(isEmpty(choiceTwoEditText))
                {
                    choiceTwoEditText.setError("Choice 2 cannot be empty");
                }
                else if(isEmpty(choiceThreeEditText))
                {
                    choiceThreeEditText.setError("Choice 3 cannot be empty");
                }
                else
                {
                    //Temp integer variable is incremented every time a question is added, if temp is equal to amount of questions, it will enter this if statement
                    if(temp == amountOfQuestions)
                    {
                        //Displays a toast message that all questions have been added into the database
                        Toast.makeText(getApplicationContext(), "All questions have been added to the database", Toast.LENGTH_LONG).show();

                        //Moves back to the AdminActivity
                        Intent adminIntent = new Intent(context, AdminActivity.class);

                        //Starts the activity
                        startActivity(adminIntent);
                    }

                    //Converts the information in the textView layout into a string
                    question = questionEditText.getText().toString();
                    choiceOne = choiceOneEditText.getText().toString();
                    choiceTwo = choiceTwoEditText.getText().toString();
                    choiceThree = choiceThreeEditText.getText().toString();
                    correctChoice = correctChoiceEditText.getText().toString();

                    boolean check = db.insertQuizRecord(quizID, question, choiceOne, choiceTwo, choiceThree, correctChoice, timeLimit);
                    if(check == true)
                    {
                        //Displays a toast message that it was successful
                        Toast.makeText(getApplicationContext(), "Question " + temp + " added to the quiz database", Toast.LENGTH_LONG).show();

                        //Increments the temp variable
                        temp++;

                        //This is to change the text view to the correct question title
                        if (temp <= amountOfQuestions){
                            questionNumTextView.setText("Question  " + temp);
                        }
                    }
                    else
                    {
                        //If it does not meet any conditions above, it will display a toast message saying that the questions were not added
                        Toast.makeText(getApplicationContext(), "Question was not added into the database", Toast.LENGTH_LONG).show();
                    }
                    //clears everything in the editText
                    questionEditText.getText().clear();
                    choiceOneEditText.getText().clear();
                    choiceTwoEditText.getText().clear();
                    choiceThreeEditText.getText().clear();
                    correctChoiceEditText.getText().clear();

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