package com.example.rafik.englishcourse;

public class TrainingQuestionFactory extends QuestionFactory {
    @Override
    public Question getQuestion() {
        return new TrainingQuestion();
    }
}
