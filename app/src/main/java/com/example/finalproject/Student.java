package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.STUDENT.GradesActivity;
import com.example.finalproject.STUDENT.Quiz;
import com.example.finalproject.STUDENT.QuizAnswersUtil;
import com.example.finalproject.STUDENT.QuizUtil;
import com.example.finalproject.ADMIN.Username;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Student extends AppCompatActivity {

    Context context;

    private RecyclerView recyclerCompleteQuizList;
    private RecyclerView recyclerIncompleteQuizList;

    // list of quiz
    private ArrayList<QuizUtil> myQuizList = new ArrayList<>();
    private ArrayList<QuizUtil> completedQuizList = new ArrayList<>();

    private Button previousBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        context = getApplicationContext();
        previousBtn = findViewById(R.id.previous4);

        // data base helper
        DatabaseHelper db = new DatabaseHelper(this);
        // set keys to each View
        TextView listTitle = findViewById(R.id.my_exam);
        recyclerIncompleteQuizList = findViewById(R.id.quiz_list);
        recyclerCompleteQuizList = findViewById(R.id.completedQuizzesRecyclerView);

        // get all the quiz into list of ArrayList
        if (db.listIncompleteQuiz(QuizAnswersUtil.username).isEmpty() && db.listCompletedQuiz(QuizAnswersUtil.username).isEmpty()){
            Toast.makeText(context,"Quiz list has nothing", Toast.LENGTH_LONG).show();
        }

        myQuizList = db.listIncompleteQuiz(QuizAnswersUtil.username);
        completedQuizList = db.listCompletedQuiz(QuizAnswersUtil.username);

        // set the layout manager to the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerIncompleteQuizList.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        recyclerCompleteQuizList.setLayoutManager(linearLayoutManager2);


        // set the adapter to recyclerView quiz listing
        StudentRecyclerviewAdapter adapter = new StudentRecyclerviewAdapter(this, myQuizList, true);
        recyclerIncompleteQuizList.setAdapter(adapter);

        StudentRecyclerviewAdapter completedAdapter = new StudentRecyclerviewAdapter(this, completedQuizList, false);
        recyclerCompleteQuizList.setAdapter(completedAdapter);
        //}

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                 startActivity(logout);
            }
        });

    }

    public class StudentRecyclerviewAdapter extends RecyclerView.Adapter <StudentRecyclerviewAdapter.ViewHolder> {

        private final ArrayList<QuizUtil> myQuiz;
        private Boolean completeOrIncomplete;
        Context context;



        public StudentRecyclerviewAdapter(Context context, ArrayList<QuizUtil> users, Boolean completeOrIncomplete) {

            this.context = context;
            this.myQuiz = users;
            this.completeOrIncomplete = completeOrIncomplete;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_quiz_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final QuizUtil mQ = myQuiz.get(position);

            // set the test number
            holder.viewID.setText(String.valueOf(position + 1));

            // set the name of the exam to content
            //holder.contentView.setText(mQ.getTopic());
            holder.contentView.setText(mQ.getTopic());  // set the topic

            holder.view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(completeOrIncomplete) {
                        Context con = v.getContext();
                        // set the next window class to be perform
                        Intent intent = new Intent(con, Quiz.class);
                        intent.putExtra("quizID", mQ.getQuizID());  // send in the quizID to new intent
                        QuizAnswersUtil.quizID = mQ.getQuizID();
                        // set the id of the TextView
                        intent.putExtra(QuizUtil.SCORE_RESULT, holder.getAdapterPosition());
                        // start the Result class
                        context.startActivities(new Intent[]{intent});
                    }
                    else
                    {
                        Context con = v.getContext();
                        Intent intent = new Intent(con, GradesActivity.class);
                        intent.putExtra("quizID", mQ.getQuizID());
                        QuizAnswersUtil.quizID = mQ.getQuizID();
                        con.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return myQuiz.size();
        }

        // an extends View holder
        class ViewHolder extends  RecyclerView.ViewHolder {

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
