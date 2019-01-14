package com.example.rafik.englishcourse;

import android.os.Handler;

public class TestQuestion extends Question {
    @Override
    protected void onCorrectAnswer(Callback callback) {
        callback.onCorrectAnswer();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                service.getNextQuestion(callback);
                service.incrementCorrectAnswerCount();
                service.incrementCurrentQuestionIndex();
                service.incrementAnswersCount();
            }
        }, 1450);
    }

    @Override
    protected void onWrongAnswer(Callback callback) {
        callback.onWrongAnswer();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                service.getNextQuestion(callback);
                service.incrementCurrentQuestionIndex();
                service.incrementAnswersCount();
            }
        }, 1450);
    }
}
