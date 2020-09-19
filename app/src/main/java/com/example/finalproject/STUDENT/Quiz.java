package com.example.finalproject.STUDENT;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.DatabaseHelper;
import com.example.finalproject.R;
import com.example.finalproject.Student;

import java.util.List;
import java.util.Locale;

public class Quiz extends AppCompatActivity {

    // global variables
    private Context context;    // this context
    private TextView question;  // question text view
    private Button b1, b2, b3;  // 3 buttons choices

    // list of object in question
    List<QuizUtil.Question> questionItems;
    private int currentQuestion = 0;    // current question

    // holders for correct and wrong number
    private int correct = 0;
    private int wrong = 0;

    private Handler handler = new Handler();    // handler to handle the timer
    private int seconds = 500;                  // time range to switch between each button

    // declare a database
    private DatabaseHelper db;

    // a variable to store the total questions
    private double totalQuestions;

    // countdown textview
    private TextView countDownText;
    // countdown timer variable
    private CountDownTimer countDownTimer;
    // this is 60 sec = 1 min
    private long time_in_millis = 60000;
    // variable to store the time count down in milliseconds
    private long time_countDown_inMillis;
    // variable to store the time in second
    private int countTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        context = getApplicationContext();

        // set all textviews and button to each key from xml file
        question = findViewById(R.id.question);
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        countDownText = findViewById(R.id.timer);
        // set the database to contain all of the system database data
        db = new DatabaseHelper(this);

        // get the quiz key id
        Intent intent = getIntent();
        int quizID = intent.getIntExtra("quizID", 0);

        // search quiz list from the database
        if (db.getAllQuestions(quizID).isEmpty()){
            Toast.makeText(context,"Quiz list has nothing", Toast.LENGTH_LONG).show();
        }
        else{
            // get the assign time from the quiz creation table
            int quizTime = db.getTopicTime(quizID);

            // get the quiz size like how many question does each topic has.
            totalQuestions = db.getQuizSize(quizID);

            // convert time minutes to milliseconds... + 1000 because we want to count down start at 60seconds
            time_countDown_inMillis = (quizTime * time_in_millis) + 1000;    // convert to milliseconds (i.e. 2min x 60000ms = 120000)

            // start the count down
            startCountDown();

            //Toast.makeText(context,"This quiz has timer in " + quizTime + " minutes", Toast.LENGTH_LONG).show();
            // get all of the questions base on the topic id
            questionItems = db.getAllQuestions(quizID);
            // populate them in display
            setQuestionScreen(currentQuestion);

            // listen for a response from the user if they click the first choice
            b1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    // check if the choice response is correct from each question
                    if (questionItems.get(currentQuestion).getAnswer1()
                            .equals(questionItems.get(currentQuestion).getCorrect())){
                        correct++;

                        // blinking color from white to green to indicate correct choice
                        b1.setBackgroundResource(R.drawable.correct_button);

                        //Toast.makeText(context, "Correct answer", Toast.LENGTH_SHORT).show();
                    }
                    // if wrong choice
                    else {
                        wrong++;

                        // blinking color from white to red to indicate wrong choice
                        b1.setBackgroundResource(R.drawable.wrong_button);

                        //Toast.makeText(context, "Wrong answer", Toast.LENGTH_SHORT).show();
                    }

                    // if the current question is less than the size of the topic then move to the next question
                    if (currentQuestion < questionItems.size() - 1){
                        currentQuestion++;

                        // delay the screen display in half second to show the blinking color
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                b1.setBackgroundResource(R.drawable.choice_button);
                                setQuestionScreen(currentQuestion);
                            }
                        }, seconds);

                    }
                    // Anytime when the quiz is done, immediately change the display to the result activity
                    else{
                        Toast.makeText(context, "Well Done", Toast.LENGTH_SHORT).show();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), Result.class);
                                intent.putExtra("Correct", correct);                        // send over the correct choices
                                intent.putExtra("Wrong", wrong);                            // send over the wrong choices
                                intent.putExtra("TotalQuestions", questionItems.size());    // send over total questions
                                intent.putExtra("TimeCompleted", countTime);        // send over the counted time
                                time_countDown_inMillis = 0;                               // reset time back to 0
                                startActivity(intent);
                                finish();
                            }
                        }, seconds);

                    }
                }
            });

            // listen for a response from the user if they click the second choice
            b2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    // check if the choice response is correct from each question
                    if (questionItems.get(currentQuestion).getAnswer2()
                            .equals(questionItems.get(currentQuestion).getCorrect())){
                        correct++;

                        // blinking color from white to green to indicate correct choice
                        b2.setBackgroundResource(R.drawable.correct_button);

                        //Toast.makeText(context, "Correct answer", Toast.LENGTH_SHORT).show();
                    }
                    // if wrong choice
                    else {
                        wrong++;

                        // blinking color from white to red to indicate wrong choice
                        b2.setBackgroundResource(R.drawable.wrong_button);

                        //Toast.makeText(context, "Wrong answer", Toast.LENGTH_SHORT).show();
                    }

                    // if the current question is less than the size of the topic then move to the next question
                    if (currentQuestion < questionItems.size() - 1){
                        currentQuestion++;

                        // delay the screen display in half second to show the blinking color
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                b2.setBackgroundResource(R.drawable.choice_button);
                                setQuestionScreen(currentQuestion);
                            }
                        }, seconds);

                    }
                    // Anytime when the quiz is done, immediately change the display to the result activity
                    else{
                        Toast.makeText(context, "Well Done", Toast.LENGTH_SHORT).show();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), Result.class);
                                intent.putExtra("Correct", correct);                        // send over the correct choices
                                intent.putExtra("Wrong", wrong);                            // send over the wrong choices
                                intent.putExtra("TotalQuestions", questionItems.size());    // send over total questions
                                intent.putExtra("TimeCompleted", countTime);          // send over the counted time
                                time_countDown_inMillis = 0;                                // reset time back to 0
                                startActivity(intent);
                                finish();
                            }
                        }, seconds);
                    }

                }
            });

            // listen for a response from the user if they click the third choice
            b3.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    // check if the choice response is correct from each question
                    if (questionItems.get(currentQuestion).getAnswer3()
                            .equals(questionItems.get(currentQuestion).getCorrect())){
                        correct++;

                        // blinking color from white to green to indicate correct choice
                        b3.setBackgroundResource(R.drawable.correct_button);

                        //Toast.makeText(context, "Correct answer", Toast.LENGTH_SHORT).show();
                    } else {
                        wrong++;

                        // blinking color from white to red to indicate wrong choice
                        b3.setBackgroundResource(R.drawable.wrong_button);

                        //Toast.makeText(context, "Wrong answer", Toast.LENGTH_SHORT).show();
                    }

                    // if the current question is less than the size of the topic then move to the next question
                    if (currentQuestion < questionItems.size() - 1){
                        currentQuestion++;

                        // delay the screen display in half second to show the blinking color
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                b3.setBackgroundResource(R.drawable.choice_button);
                                setQuestionScreen(currentQuestion);
                            }
                        }, seconds);

                    }
                    // Anytime when the quiz is done, immediately change the display to the result activity
                    else{
                        Toast.makeText(context, "Well Done", Toast.LENGTH_SHORT).show();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), Result.class);
                                intent.putExtra("Correct", correct);                        // send over the correct choices
                                intent.putExtra("Wrong", wrong);                            // send over the wrong choices
                                intent.putExtra("TotalQuestions", questionItems.size());    // send over total questions
                                intent.putExtra("TimeCompleted", countTime);        // send over the counted time
                                time_countDown_inMillis = 0;                               // reset time back to 0
                                startActivity(intent);
                                finish();
                            }
                        }, seconds); // half Second

                    }

                }
            });
        }

    }

    /**
     * Set each question and its answers to populate into the textview to be display
     * @param number
     */
    @SuppressLint("SetTextI18n")
    private void setQuestionScreen(int number){
        question.setText("Question " + (currentQuestion+1) + ".\n\n" + questionItems.get(number).getQuestion());
        b1.setText(questionItems.get(number).getAnswer1());
        b2.setText(questionItems.get(number).getAnswer2());
        b3.setText(questionItems.get(number).getAnswer3());
    }

    /**
     * Count down method
     */
    private void startCountDown() {
        // set the interval to start count down by 1 sec tick
        countDownTimer = new CountDownTimer(time_countDown_inMillis, 1000) {
            @Override
            public void onTick(long timeUp) {
                time_countDown_inMillis = timeUp;
                countTime++;            // count in each second run
                updateCountDownText();  // update the display context in each tick
            }

            /**
             * when done, send over the information to the result activity to be display
             */
            @Override
            public void onFinish() {
                time_countDown_inMillis = 0;    // rest timer to zero
                updateCountDownText();
                Intent intent = new Intent(getApplicationContext(), Result.class);
                intent.putExtra("Correct", correct);
                intent.putExtra("Wrong", wrong);
                intent.putExtra("TotalQuestions", questionItems.size());  // send over total questions
                intent.putExtra("TimeCompleted", countTime);        // send over the counted time
                startActivity(intent);
                finish();
            }
        }.start(); // begin
    }

    /**
     * An update method to update and the text view in each second
     * Also, convert millisecond to seconds
     */
    private void updateCountDownText(){
        int minutes = (int)(time_countDown_inMillis / 1000) / 60;
        int seconds = (int)(time_countDown_inMillis / 1000) % 60;

        String timeFormat = String.format(Locale.getDefault(),
                "%02d:%02d", minutes, seconds);
        countDownText.setText(timeFormat);

        // if timer hit last 10 sec, digit will turn to red color
        if (time_countDown_inMillis <= 11000) {
            countDownText.setTextColor(Color.RED);
        }
    }

    /**
     * A destroy method to cancel the timer
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    /**
     * Anytime the user pressed the device button during the quiz, everything will be lost
     * and the user has to restart the test again.
     */
    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent(getApplicationContext(), Student.class);
        returnIntent.putExtra("hasBackPressed",true);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

}