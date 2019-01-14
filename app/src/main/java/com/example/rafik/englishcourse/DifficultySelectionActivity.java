package com.example.rafik.englishcourse;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class DifficultySelectionActivity extends AppCompatActivity {

    private Button beginnerDiffButton;
    private Button elementaryDiffButton;
    private Button intermediateDiffButton;
    private Button expertDiffButton;

    QuizService quizService;
    boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_selection);

        beginnerDiffButton = (Button) findViewById(R.id.easy_lvl_btn);
        elementaryDiffButton = (Button) findViewById(R.id.normal_lvl_btn);
        intermediateDiffButton = (Button) findViewById(R.id.hard_lvl_btn);
        expertDiffButton = (Button) findViewById(R.id.expert_lvl_btn);

        beginnerDiffButton.setOnClickListener(v -> {
                Intent intent = new Intent(this, LanguageSelectionActivity.class);
                quizService.setDifficulty(Difficulty.BEGINNER);
                startActivity(intent);
            }
        );

        elementaryDiffButton.setOnClickListener(v -> {
                    Intent intent = new Intent(this, LanguageSelectionActivity.class);
                    quizService.setDifficulty(Difficulty.ELEMENTARY);
                    startActivity(intent);
                }
        );

        intermediateDiffButton.setOnClickListener(v -> {
                    Intent intent = new Intent(this, LanguageSelectionActivity.class);
                    quizService.setDifficulty(Difficulty.INTERMEDIATE);
                    startActivity(intent);
                }
        );

        expertDiffButton.setOnClickListener(v -> {
                    Intent intent = new Intent(this, LanguageSelectionActivity.class);
                    quizService.setDifficulty(Difficulty.EXPERT);
                    quizService.setQuestionType(QuestionType.OPEN);
                    startActivity(intent);
                }
        );

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
