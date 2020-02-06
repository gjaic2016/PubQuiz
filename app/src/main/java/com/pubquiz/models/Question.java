package com.pubquiz.models;

import java.util.List;

public class Question {

    private String question;
    private String correct_answer;
    private List<String> incorrect_answers;

    public Question() {
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correct_answer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrect_answers;
    }
}
