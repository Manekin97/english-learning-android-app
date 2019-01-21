package com.example.rafik.englishcourse;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.HashMap;

public class MainMenuActivity extends AppCompatActivity {
    private Button libraryButton;
    private Button startTestButton;
    private Button startTrainingButton;

    QuizService quizService;
    boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        this.libraryButton = (Button) findViewById(R.id.library_base_button);
        this.startTestButton = (Button) findViewById(R.id.test_btn);
        this.startTrainingButton = (Button) findViewById(R.id.training_btn);

        this.libraryButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, DictionaryActivity.class);
            startActivity(intent);
        });

        this.startTestButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, DifficultySelectionActivity.class);
            quizService.setQuizMode(Mode.TEST);
            startActivity(intent);
        });

        this.startTrainingButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, DifficultySelectionActivity.class);
            quizService.setQuizMode(Mode.TRAINING);
            startActivity(intent);
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
