package com.example.rafik.englishcourse;

import android.os.Handler;

public class TrainingQuestion extends Question {

    @Override
    protected void onCorrectAnswer(Callback callback) {
        callback.onCorrectAnswer();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                service.getNextQuestion(callback);
            }
        }, 1500);

        // set the score
    }

    @Override
    protected void onWrongAnswer(Callback callback) {
        callback.onWrongAnswer();
        //  set the score
    }
}
