package com.example.rafik.englishcourse;

import java.util.ArrayList;

public abstract class Question {
    public String text;
    public ArrayList<String> answers;
    public int correctAnswerIndex;
    public QuestionType type;

    public void onAnswerSelect(){}
    protected abstract void onCorrectAnswer();
    protected abstract void onWrongAnswer();
}
