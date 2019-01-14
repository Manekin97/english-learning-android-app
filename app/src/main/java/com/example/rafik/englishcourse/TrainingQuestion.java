package com.example.rafik.englishcourse;

import android.os.Handler;
import android.util.Log;

public class TrainingQuestion extends Question {

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
        service.incrementAnswersCount();
        callback.onWrongAnswer();
    }
}
