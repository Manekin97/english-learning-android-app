package com.example.rafik.englishcourse;

import android.app.ProgressDialog;
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
    private TextView correctAnswersTextView;
    private TextView questionsProgressTextView;
    private Button answerAButton;
    private Button answerBButton;
    private Button answerCButton;
    private Button answerDButton;
    private Chronometer chronometer;
    private ProgressDialog dialog;

    private int defaultButtonColor = Color.parseColor("#be8fe2");

    QuizService quizService;
    boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        this.questionTextView = (TextView) findViewById(R.id.text_question);
        this.correctAnswersTextView = (TextView) findViewById(R.id.correct_answers_text_view);
        this.questionsProgressTextView = (TextView) findViewById(R.id.questions_progress_text_view);
        this.answerAButton = (Button) findViewById(R.id.first_answer);
        this.answerBButton = (Button) findViewById(R.id.second_answer);
        this.answerCButton = (Button) findViewById(R.id.third_answer);
        this.answerDButton = (Button) findViewById(R.id.fourth_answer);
        this.chronometer = (Chronometer) findViewById(R.id.chronometer);

        this.answerAButton.setOnClickListener(v -> {
            if (quizService.getCurrentQuestionIndex() + 1 > quizService.getQuestionCount()) {
                this.chronometer.stop();

                Intent intent = new Intent(this, SummaryActivity.class);

                Log.d("accuracy", quizService.getCorrectAnswersPercentageToString());
                intent.putExtra("accuracy", quizService.getCorrectAnswersPercentageToString());
                intent.putExtra("time", this.chronometer.getText().toString());

                quizService.onFinish();
                stopService(new Intent(this, QuizService.class));
                startActivity(intent);
            }

            String answer = this.answerAButton.getText().toString();

            this.quizService.onAnswerSelect(answer, new Callback() {
                @Override
                void onQuestionReady(Question question) {
                    if (dialog != null){
                        dialog.hide();
                    }
                    setQuestionText(question);
                }

                @Override
                void onWrongAnswer() {
                    if (quizService.getMode() == Mode.TRAINING) {
                        answerAButton.getBackground().setTint(Color.RED);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                answerAButton.getBackground().setTint(defaultButtonColor);
                                questionsProgressTextView.setText(quizService.getQuestionProgressToString());
                            }
                        }, 1500);
                    } else {
                        questionsProgressTextView.setText(quizService.getQuestionProgressToString());
                        dialog = ProgressDialog.show(QuestionActivity.this, "",
                                getString(R.string.loading_message), true);
                    }
                }

                @Override
                void onCorrectAnswer() {
                    if (quizService.getMode() == Mode.TRAINING) {
                        answerAButton.getBackground().setTint(Color.GREEN);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                answerAButton.getBackground().setTint(defaultButtonColor);
                                correctAnswersTextView.setText(quizService.getCorrectAnswersPercentageToString());
                                questionsProgressTextView.setText(quizService.getQuestionProgressToString());
                            }
                        }, 1500);
                    }
                    else {
                        correctAnswersTextView.setText(quizService.getCorrectAnswersPercentageToString());
                        questionsProgressTextView.setText(quizService.getQuestionProgressToString());
                        dialog = ProgressDialog.show(QuestionActivity.this, "",
                                getString(R.string.loading_message), true);
                    }
                }
            });
        });

        this.answerBButton.setOnClickListener(v -> {
            if (quizService.getCurrentQuestionIndex() + 1 > quizService.getQuestionCount()) {
                this.chronometer.stop();

                Intent intent = new Intent(this, SummaryActivity.class);

                Log.d("accuracy", quizService.getCorrectAnswersPercentageToString());
                intent.putExtra("accuracy", quizService.getCorrectAnswersPercentageToString());
                intent.putExtra("time", this.chronometer.getText().toString());

                quizService.onFinish();
                stopService(new Intent(this, QuizService.class));
                startActivity(intent);
            }

            String answer = this.answerBButton.getText().toString();
            this.quizService.onAnswerSelect(answer, new Callback() {
                @Override
                void onQuestionReady(Question question) {
                    if (dialog != null){
                        dialog.hide();
                    }

                    setQuestionText(question);
                }

                @Override
                void onWrongAnswer() {
                    if (quizService.getMode() == Mode.TRAINING) {
                        answerBButton.getBackground().setTint(Color.RED);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                answerBButton.getBackground().setTint(defaultButtonColor);
                                questionsProgressTextView.setText(quizService.getQuestionProgressToString());
                            }
                        }, 1500);
                    }
                    else {
                        questionsProgressTextView.setText(quizService.getQuestionProgressToString());
                        dialog = ProgressDialog.show(QuestionActivity.this, "",
                                getString(R.string.loading_message), true);
                    }
                }

                @Override
                void onCorrectAnswer() {
                    if (quizService.getMode() == Mode.TRAINING) {
                        answerBButton.getBackground().setTint(Color.GREEN);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                answerBButton.getBackground().setTint(defaultButtonColor);
                                correctAnswersTextView.setText(quizService.getCorrectAnswersPercentageToString());
                                questionsProgressTextView.setText(quizService.getQuestionProgressToString());
                            }
                        }, 1500);
                    }
                    else {
                        correctAnswersTextView.setText(quizService.getCorrectAnswersPercentageToString());
                        questionsProgressTextView.setText(quizService.getQuestionProgressToString());
                        dialog = ProgressDialog.show(QuestionActivity.this, "",
                                getString(R.string.loading_message), true);
                    }
                }
            });
        });

        this.answerCButton.setOnClickListener(v -> {
            if (quizService.getCurrentQuestionIndex() + 1 > quizService.getQuestionCount()) {
                this.chronometer.stop();

                Intent intent = new Intent(this, SummaryActivity.class);

                Log.d("accuracy", quizService.getCorrectAnswersPercentageToString());
                intent.putExtra("accuracy", quizService.getCorrectAnswersPercentageToString());
                intent.putExtra("time", this.chronometer.getText().toString());

                quizService.onFinish();
                stopService(new Intent(this, QuizService.class));
                startActivity(intent);
            }

            String answer = this.answerCButton.getText().toString();
            this.quizService.onAnswerSelect(answer, new Callback() {
                @Override
                void onQuestionReady(Question question) {
                    if (dialog != null){
                        dialog.hide();
                    }

                    setQuestionText(question);
                }

                @Override
                void onWrongAnswer() {
                    if (quizService.getMode() == Mode.TRAINING) {
                        answerCButton.getBackground().setTint(Color.RED);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                answerCButton.getBackground().setTint(defaultButtonColor);
                                questionsProgressTextView.setText(quizService.getQuestionProgressToString());
                            }
                        }, 1500);
                    }
                    else {
                        questionsProgressTextView.setText(quizService.getQuestionProgressToString());
                        dialog = ProgressDialog.show(QuestionActivity.this, "",
                                "Loading...", true);
                    }
                }

                @Override
                void onCorrectAnswer() {
                    if (quizService.getMode() == Mode.TRAINING) {
                        answerCButton.getBackground().setTint(Color.GREEN);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                answerCButton.getBackground().setTint(defaultButtonColor);
                                correctAnswersTextView.setText(quizService.getCorrectAnswersPercentageToString());
                                questionsProgressTextView.setText(quizService.getQuestionProgressToString());
                            }
                        }, 1500);
                    }
                    else {
                        correctAnswersTextView.setText(quizService.getCorrectAnswersPercentageToString());
                        questionsProgressTextView.setText(quizService.getQuestionProgressToString());
                        dialog = ProgressDialog.show(QuestionActivity.this, "",
                                "Loading...", true);
                    }
                }
            });
        });

        this.answerDButton.setOnClickListener(v -> {
            if (quizService.getCurrentQuestionIndex() + 1 > quizService.getQuestionCount()) {
                this.chronometer.stop();

                Intent intent = new Intent(this, SummaryActivity.class);

                Log.d("accuracy", quizService.getCorrectAnswersPercentageToString());
                intent.putExtra("accuracy", quizService.getCorrectAnswersPercentageToString());
                intent.putExtra("time", this.chronometer.getText().toString());

                quizService.onFinish();
                stopService(new Intent(this, QuizService.class));
                startActivity(intent);
            }

            String answer = this.answerDButton.getText().toString();
            this.quizService.onAnswerSelect(answer, new Callback() {
                @Override
                void onQuestionReady(Question question) {
                    if (dialog != null){
                        dialog.hide();
                    }

                    setQuestionText(question);
                }

                @Override
                void onWrongAnswer() {
                    if (quizService.getMode() == Mode.TRAINING) {
                        answerDButton.getBackground().setTint(Color.RED);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                answerDButton.getBackground().setTint(defaultButtonColor);
                                questionsProgressTextView.setText(quizService.getQuestionProgressToString());
                            }
                        }, 1500);
                    }
                    else {
                        questionsProgressTextView.setText(quizService.getQuestionProgressToString());
                        dialog = ProgressDialog.show(QuestionActivity.this, "",
                                "Loading...", true);
                    }
                }

                @Override
                void onCorrectAnswer() {
                    if (quizService.getMode() == Mode.TRAINING) {
                        answerDButton.getBackground().setTint(Color.GREEN);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                answerDButton.getBackground().setTint(defaultButtonColor);
                                correctAnswersTextView.setText(quizService.getCorrectAnswersPercentageToString());
                                questionsProgressTextView.setText(quizService.getQuestionProgressToString());
                            }
                        }, 1500);
                    }
                    else {
                        correctAnswersTextView.setText(quizService.getCorrectAnswersPercentageToString());
                        questionsProgressTextView.setText(quizService.getQuestionProgressToString());
                        dialog = ProgressDialog.show(QuestionActivity.this, "",
                                "Loading...", true);
                    }
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
                    setQuestionText(question);
                }
            });

            correctAnswersTextView.setText(quizService.getCorrectAnswersPercentageToString());
            questionsProgressTextView.setText(quizService.getQuestionProgressToString());
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        quizService.onFinish();
        this.finish();
    }

    public void setQuestionText(Question question) {
        questionTextView.setText(question.text);
        answerAButton.setText(question.answers.get(0).value);
        answerBButton.setText(question.answers.get(1).value);
        answerCButton.setText(question.answers.get(2).value);
        answerDButton.setText(question.answers.get(3).value);
    }
}
