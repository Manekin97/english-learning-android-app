package com.example.rafik.englishcourse;

import java.util.ArrayList;

public abstract class Question {
    public String text;
    public ArrayList<ServerService.Pair> answers;
    public int correctAnswerIndex;
    protected QuizService service;

    public void onAnswerSelect(String answer, QuizService qService, Callback callback) {

        if (this.service == null) {
            this.service = qService;
        }

        if (answer.toLowerCase().equals(answers.get(correctAnswerIndex).value.toLowerCase())) {
            onCorrectAnswer(callback);
        } else {
            onWrongAnswer(callback);
        }
    }

    protected abstract void onCorrectAnswer(Callback callback);

    protected abstract void onWrongAnswer(Callback callback);
}
