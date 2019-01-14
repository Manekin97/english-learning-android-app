package com.example.rafik.englishcourse;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LanguageSelectionActivity extends AppCompatActivity {
    private Button polEngButton;
    private Button engPolButton;

    QuizService quizService;
    boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection_activity);

        polEngButton = (Button) findViewById(R.id.pl_eng_btn);
        engPolButton = (Button) findViewById(R.id.eng_pl_btn);

        polEngButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizService.setLanguageMode(LanguageMode.POLISH_ENGLISH);
                Intent intent = new Intent(getBaseContext(), quizService.getDifficulty() == Difficulty.EXPERT ? OpenQuestionActivity.class : QuestionActivity.class);
                startActivity(intent);
            }
        });

        engPolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizService.setLanguageMode(LanguageMode.ENGLISH_POLISH);
                Intent intent = new Intent(getBaseContext(), quizService.getDifficulty() == Difficulty.EXPERT ? OpenQuestionActivity.class : QuestionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, QuizService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
        isBound = false;
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            QuizService.QuizServiceBinder binder = (QuizService.QuizServiceBinder) service;
            quizService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }
    };
}
