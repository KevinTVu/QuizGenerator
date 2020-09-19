package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.finalproject.ADMIN.Quizzes;
import com.example.finalproject.STUDENT.QuizAnswersUtil;
import com.example.finalproject.STUDENT.QuizUtil;
import com.example.finalproject.ADMIN.Username;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quiz_database.db";
    private static final String user = "user";
    private static final String quizInformation = "quizInformation";
    private static final String quiz = "quiz";
    private static final String assignedQuiz = "assignedQuiz";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    /**
     * onCreate method that will be creating the table. There will be a four tables, one for the user, one for quiz information, one for quiz questions, and one for assignment of quizzes
     */
    public void onCreate(SQLiteDatabase db) {
        String userTable = "CREATE TABLE " + user + " (username text primary key, password text, email text, phoneNumber text)";
        String quizInformationTable = "CREATE TABLE " + quizInformation + " (id integer primary key, topic text, amountOfQuestion integer, timeLimit integer)";
        String quizTable = "CREATE TABLE " + quiz + " (id integer, question text, q1 text, q2 text, q3 text, correctAnswer text, time integer, foreign key(id) references user(id), foreign key(time) references quizInformation(time), primary key(id, question))";
        String assignedQuizTable =  "CREATE TABLE " + assignedQuiz + " (username text, id integer, topic text, completionStatus integer default 0, gradePercentage integer, letterGrade text, foreign key(username) references user(username), foreign key(id) references quizInformation (id), foreign key(topic) references quizInformation(topic),primary key(username, id))";

        db.execSQL(userTable);
        db.execSQL(quizInformationTable);
        db.execSQL(quizTable);
        db.execSQL(assignedQuizTable);
    }

    @Override
    /**
     * onUpgrade method that will be exec SQL drop tables
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + user);
        db.execSQL("drop table if exists " + quizInformation);
        db.execSQL("drop table if exists " + quiz);
        onCreate(db);

    }

    /**
     * Insert function that will insert the username, password, email, and phone number into the user table
     * @param username
     * @param password
     * @param email
     * @param phoneNumber
     * @return
     */
    public boolean insert(String username, String password, String email, String phoneNumber)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);
        contentValues.put("phoneNumber", phoneNumber);
        long ins = db.insert("user", null, contentValues);
        if(ins == -1)
        {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * insertQuizRecord function that will insert the id, string, choice1, choice2, choice3, correct answer, and time into the Quiz table
     * @param id
     * @param question
     * @param q1
     * @param q2
     * @param q3
     * @param correctAnswer
     * @param time
     * @return
     */
    public boolean insertQuizRecord(int id, String question, String q1, String q2, String q3, String correctAnswer, int time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("question", question);
        contentValues.put("q1", q1);
        contentValues.put("q2", q2);
        contentValues.put("q3", q3);
        contentValues.put("correctAnswer", correctAnswer);
        contentValues.put("time", time);
        // insert data into database
        long ins = db.insert("quiz", null, contentValues);
        if(ins == -1)
        {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * InsertQuizInformation function that will insert the id, topic, amount of question, and timelimit into the QuizInformation table
     * @param id
     * @param topic
     * @param amountOfQuestion
     * @param timeLimit
     * @return
     */
    public boolean insertQuizInformation(int id, String topic, int amountOfQuestion, int timeLimit)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("topic", topic);
        contentValues.put("amountOfQuestion", amountOfQuestion);
        contentValues.put("timeLimit", timeLimit);
        long ins = db.insert("quizInformation", null, contentValues);
        if(ins == -1)
        {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * insertAssignQuiz function that will insert the specific username, quizID, and topic into the assigned quiz table
     * @param username
     * @param quizID
     * @param topic
     * @return
     */
    public boolean insertAssignQuiz(String username, int quizID, String topic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("id", quizID);
        contentValues.put("topic", topic);
        contentValues.put("completionStatus", "incomplete");

        long ins = db.insert(assignedQuiz, null, contentValues);
        if(ins == -1)
        {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * checkUsername function that will check if the username exist in the database
     * @param username
     * @return
     */
    public boolean checkUserName(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=?", new String[] {username});
        if(cursor.getCount() > 0)
        {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * checkLogin function that checks if the username and password exist in the user table
     * @param username
     * @param password
     * @return
     */
    public boolean checkLogin(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=? and password=? ", new String[] {username,password});
        if(cursor.getCount() > 0)
        {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkAccountExist(String username, String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=? and email=? ", new String[] {username,email});
        if(cursor.getCount() > 0)
        {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean updatePassword(String username, String newPassword)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", newPassword);
        db.update("user", contentValues, "username=?",new String[] {username} );
        return true;
    }


    /**
     * listContacts function that will return an arraylist of usernames, all the users the exist in the user table.
     * @return
     */
    public ArrayList<Username> listContacts(){
        String sql = "select * from user";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Username> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                //int id = Integer.parseInt(cursor.getString(0));
                String username = cursor.getString(0);
                String password = cursor.getString(1);
                String email = cursor.getString(2);
                String phone = cursor.getString(3);
                storeContacts.add(new Username(username, password, email, phone));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }

    /**
     * listQuizInformation function that will return an arraylist of quizzes, all the quizzes information in the quizInformation table
     * @return
     */
    public ArrayList<Quizzes> listQuizInformation(){
        String sql = "select * from quizInformation";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Quizzes> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                //int id = Integer.parseInt(cursor.getString(0));
                int id = cursor.getInt(0);
                String topic = cursor.getString(1);
                int amountOfQuestion = cursor.getInt(2);
                int timeLimit = cursor.getInt(3);
                storeContacts.add(new Quizzes(id, topic, amountOfQuestion, timeLimit));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }

    /**
     * listMyQuiz function that will be returning a specific quiz
     * @param username
     * @return
     */
    public ArrayList<QuizUtil> listMyQuiz(String username){
        String sql = "select * from " + assignedQuiz + " where username = \"" + username + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<QuizUtil> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                //int id = Integer.parseInt(cursor.getString(0));
                String uName = cursor.getString(0);
                int quizID = cursor.getInt(1);
                String topic = cursor.getString(2);
                storeContacts.add(new QuizUtil(uName, quizID, topic));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }

    /**
     * listIncompleteQuiz that will only be returning an arraylist of the quizzes that have the completionStatus as "incomplete"
     * @param username
     * @return
     */
    public ArrayList<QuizUtil> listIncompleteQuiz(String username){
        String incomplete = "incomplete";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<QuizUtil> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * from assignedQuiz where username=? and completionStatus=? order by topic ASC", new String[] {username,incomplete});

        if(cursor.moveToFirst()){
            do{
                //int id = Integer.parseInt(cursor.getString(0));
                String uName = cursor.getString(0);
                int quizID = cursor.getInt(1);
                String topic = cursor.getString(2);
                storeContacts.add(new QuizUtil(uName, quizID, topic));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }

    /**
     * listIncompleteQuiz that will only be returning an arraylist of the quizzes that have the completionStatus as "incomplete"
     * @param username
     * @return
     */
    public ArrayList<QuizUtil> listCompletedQuiz(String username){
        String completed = "completed";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<QuizUtil> storeContacts = new ArrayList<>();
        ArrayList<Integer> quizIDList = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * from assignedQuiz where username=? and completionStatus=? order by topic ASC", new String[] {username,completed});

        if(cursor.moveToFirst()){
            do{
                //int id = Integer.parseInt(cursor.getString(0));
                String uName = cursor.getString(0);
                int quizID = cursor.getInt(1);
                String topic = cursor.getString(2);
                storeContacts.add(new QuizUtil(uName, quizID, topic));
                quizIDList.add(quizID);
            }while (cursor.moveToNext());
        }
        QuizAnswersUtil.list = quizIDList;
        cursor.close();
        return storeContacts;
    }

    /**
     * updateGrades function that will update the grades of the student
     * @param username
     * @param id
     * @param percentage
     * @param letterGrade
     */
    public void updateGrades(String username, int id, double percentage, String letterGrade )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("completionStatus", "completed");
        contentValues.put("gradePercentage", percentage);
        contentValues.put("letterGrade", letterGrade);
        db.update(assignedQuiz, contentValues, "username=? AND id=?", new String[]{username, String.valueOf(id)}  );
    }


    /**
     * getAllQuestions function that will return an arraylist of questions for a specific quizID
     * @param quizID
     * @return
     */
    public ArrayList<QuizUtil.Question> getAllQuestions(int quizID) {
        String sql = "select question, q1, q2, q3, correctAnswer from " + quiz + " where id = " + quizID;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<QuizUtil.Question> allQuestions = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()){
            do{
                String q = cursor.getString(0);
                String c1 = cursor.getString(1);
                String c2 = cursor.getString(2);
                String c3 = cursor.getString(3);
                String answer = cursor.getString(4);
                allQuestions.add(new QuizUtil.Question(q, c1, c2, c3, answer));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return allQuestions;
    }

    /**
     * getGrades function that will return the list of grades for completed quizzes for a specific quiz ID and student
     * @param quizId
     * @param username
     * @return
     */
    public ArrayList<String> getGrades(int quizId, String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * from assignedQuiz where username=? and id=? ", new String[] {username, String.valueOf(quizId)});

        if(cursor.moveToFirst()){
            do{
                String topicQuiz = cursor.getString(2);
                String gradePercentageQuiz = cursor.getString(4);
                String letterGradeQuiz = cursor.getString(5);
                storeContacts.add(topicQuiz);
                storeContacts.add(gradePercentageQuiz);
                storeContacts.add(letterGradeQuiz);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }

    /**
     * getTopicTime that wlill return the time limit for a specific quiz
     * @param quizID
     * @return
     */
    public int getTopicTime(int quizID){
        String sql = "select timeLimit from " + quizInformation + " where id = " + quizID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int timeAssign = 0;
        if (cursor.moveToFirst()){
            timeAssign = cursor.getInt(0);
        }
        cursor.close();
        return timeAssign;
    }

    /**
     * getQuizSize will return the size of the quiz for a specific quiz
     * @param quizID
     * @return
     */
    public int getQuizSize(int quizID) {
        String sql = "select amountOfQuestion from " + quizInformation + " where id = " + quizID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int questionAMT = 0;
        if (cursor.moveToFirst()){
            questionAMT = cursor.getInt(0);
        }
        cursor.close();
        return questionAMT;
    }

}
