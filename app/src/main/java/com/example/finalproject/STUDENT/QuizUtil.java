package com.example.finalproject.STUDENT;

public class QuizUtil {

    private String username;
    private int quizID;
    private String topic;

    public static final String SCORE_RESULT = "score_result";

    public QuizUtil(String username, int quizID, String topic) {
        this.username = username;
        this.quizID = quizID;
        this.topic = topic;
    }

    public String getUserName() {
        return username;
    }

    public int getQuizID() {
        return quizID;
    }

    public String getTopic() {
        return topic;
    }

    public static class Question {
        private String question, answer1, answer2, answer3, correct;

        public Question(String question, String answer1, String answer2, String answer3, String correct) {
            this.question = question;
            this.answer1 = answer1;
            this.answer2 = answer2;
            this.answer3 = answer3;
            this.correct = correct;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer1() {
            return answer1;
        }

        public String getAnswer2() {
            return answer2;
        }

        public String getAnswer3() {
            return answer3;
        }

        public String getCorrect() {
            return correct;
        }
    }


}
