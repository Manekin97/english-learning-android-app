package com.example.rafik.englishcourse;

public class TestQuestionFactory extends QuestionFactory {
    @Override
    public Question getQuestion() {
        return new TestQuestion();
    }
}
