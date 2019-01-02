package com.example.rafik.englishcourse;

import java.util.ArrayList;

public class TrainingQuestion extends Question {
    TrainingQuestion(){}

    TrainingQuestion(String text, ArrayList<String> answers, int correctAnswerIndex, QuestionType questionType){
        super();
        this.text = text;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
        this.type = questionType;
    }


    @Override
    protected void onCorrectAnswer() {

    }

    @Override
    protected void onWrongAnswer() {

    }
}
