package com.example.rafik.englishcourse;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {

    private TextView questionTextView;
    private Button answerAButton;
    private Button answerBButton;
    private Button answerCButton;
    private Button answerDButton;
    private Chronometer chronometer;
    private int defulatButtonColor = Color.parseColor("#be8fe2");

    QuizService quizService;
    boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        this.questionTextView = (TextView) findViewById(R.id.text_question);
        this.answerAButton = (Button) findViewById(R.id.first_answer);
        this.answerBButton = (Button) findViewById(R.id.second_answer);
        this.answerCButton = (Button) findViewById(R.id.third_answer);
        this.answerDButton = (Button) findViewById(R.id.fourth_answer);
        this.chronometer = (Chronometer) findViewById(R.id.chronometer);

        this.answerAButton.setOnClickListener(v -> {
            String answer = this.answerAButton.getText().toString();

            this.quizService.onAnswerSelect(answer, new Callback() {
                @Override
                void onQuestionReady(Question question) {
                    SetQuestionText(question);
                }

                @Override
                void onWrongAnswer(){
                    answerAButton.getBackground().setTint(Color.RED);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            answerAButton.getBackground().setTint(defulatButtonColor);
                        }
                    }, 1500);
                }

                @Override
                void onCorrectAnswer() {
                    answerAButton.getBackground().setTint(Color.GREEN);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            answerAButton.getBackground().setTint(defulatButtonColor);
                        }
                    }, 1500);
                }
            });
        });

        this.answerBButton.setOnClickListener(v -> {
            String answer = this.answerBButton.getText().toString();

            this.quizService.onAnswerSelect(answer, new Callback() {
                @Override
                void onQuestionReady(Question question) {
                    SetQuestionText(question);
                }

                @Override
                void onWrongAnswer(){
                    answerBButton.getBackground().setTint(Color.RED);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            answerBButton.getBackground().setTint(defulatButtonColor);
                        }
                    }, 1500);
                }

                @Override
                void onCorrectAnswer() {
                    answerBButton.getBackground().setTint(Color.GREEN);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            answerBButton.getBackground().setTint(defulatButtonColor);
                        }
                    }, 1500);
                }
            });
        });

        this.answerCButton.setOnClickListener(v -> {
            String answer = this.answerCButton.getText().toString();
            this.quizService.onAnswerSelect(answer, new Callback() {
                @Override
                void onQuestionReady(Question question) {
                    SetQuestionText(question);
                }

                @Override
                void onWrongAnswer(){
                    answerCButton.getBackground().setTint(Color.RED);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            answerCButton.getBackground().setTint(defulatButtonColor);
                        }
                    }, 1500);
                }

                @Override
                void onCorrectAnswer() {
                    answerCButton.getBackground().setTint(Color.GREEN);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            answerCButton.getBackground().setTint(defulatButtonColor);
                        }
                    }, 1500);
                }
            });
        });

        this.answerDButton.setOnClickListener(v -> {
            String answer = this.answerDButton.getText().toString();
            this.quizService.onAnswerSelect(answer, new Callback() {
                @Override
                void onQuestionReady(Question question) {
                    SetQuestionText(question);
                }

                @Override
                void onWrongAnswer(){
                    answerDButton.getBackground().setTint(Color.RED);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("CHUJ", "dadasdasdasdasd");
                            answerDButton.getBackground().setTint(defulatButtonColor);
                        }
                    }, 1500);
                }

                @Override
                void onCorrectAnswer() {
                    answerDButton.getBackground().setTint(Color.GREEN);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            answerDButton.getBackground().setTint(defulatButtonColor);
                        }
                    }, 1500);
                }
            });
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

            chronometer.start();
            quizService.getNextQuestion(new Callback() {
                @Override
                void onQuestionReady(Question question) {
                    SetQuestionText(question);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }
    };

    public void SetQuestionText(Question question){
        questionTextView.setText(question.text);
        answerAButton.setText(question.answers.get(0).value);
        answerBButton.setText(question.answers.get(1).value);
        answerCButton.setText(question.answers.get(2).value);
        answerDButton.setText(question.answers.get(3).value);
    }
}
