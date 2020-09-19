package com.example.finalproject.STUDENT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.DatabaseHelper;
import com.example.finalproject.R;
import com.example.finalproject.Student;

import java.util.ArrayList;

public class QuizAnswersActivity extends AppCompatActivity {

    // declare database
    private DatabaseHelper db;
    // recycler view variable
    private RecyclerView quizAnswersRecyclerView;
    // list of object in question
    private ArrayList<QuizUtil.Question> quizAnswers;
    // previous button
    private Button previousBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_answers);

        // set all textviews, recyclerview and button to each key from xml file
        quizAnswersRecyclerView = findViewById(R.id.answersQuizRecyclerView);
        previousBtn = findViewById(R.id.previous2);
        // set the database to contain all of the system database data
        db = new DatabaseHelper(this);
        // get all the quiz questions based on the topic id
        quizAnswers = db.getAllQuestions(QuizAnswersUtil.quizID);

        // set linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // set the recyclerview to linear layout manager
        quizAnswersRecyclerView.setLayoutManager(linearLayoutManager);

        // set the adapter to recyclerView quiz listing
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(this, quizAnswers);
        quizAnswersRecyclerView.setAdapter(adapter);

        // a finish button to previous activity
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * A recycler viw adapter that will manage all the data from the recyclerview
     */
    public static class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {

        // quiz answer list
        private final ArrayList<QuizUtil.Question> quizAnswers;
        Context context;

        // overload constructor to set all the data application
        public RecyclerviewAdapter(Context context, ArrayList<QuizUtil.Question> quizAnswers) {

            this.context = context;
            this.quizAnswers = quizAnswers;
        }

        /**
         * A holder that will hold all of the data from their parent
         * @param parent
         * @param viewType
         * @return
         */
        @NonNull
        @Override
        public RecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.test_history, parent, false);
            return new RecyclerviewAdapter.ViewHolder(view);
        }

        /**
         * Populate each item into the recycler view holder
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(final RecyclerviewAdapter.ViewHolder holder, int position) {
            //final QuizUtil mQ = quizAnswers.get(position);

            // set the test number
            holder.viewID.setText(String.valueOf(position + 1));

            // set the name of the exam to content
            //holder.contentView.setText(mQ.getTopic());
            holder.contentView.setText(quizAnswers.get(position).getQuestion() + " " + quizAnswers.get(position).getCorrect());  // set the topic

            holder.view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // empty
                }
            });
        }

        @Override
        public int getItemCount() {
            return quizAnswers.size();
        }

        // an extends View holder
        static class ViewHolder extends RecyclerView.ViewHolder {

            final View view;
            final TextView viewID;      // TextView that hold the id
            final TextView contentView; // TextView that hold the content

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.view = itemView;
                viewID = view.findViewById(R.id.id);
                contentView = view.findViewById(R.id.contents);
            }
        }
    }
}