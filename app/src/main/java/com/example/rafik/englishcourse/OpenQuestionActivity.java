package com.example.rafik.englishcourse;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class OpenQuestionActivity extends AppCompatActivity {
    private Button submitButton;
    private EditText answerEditText;
    private Chronometer chronometer;
    private TextView questionTextView;
    private TextView progressTextView;
    private TextView accuracyTextView;
    private ProgressDialog dialog;

    QuizService quizService;
    boolean isBound = false;

    private int defaultButtonColor = Color.parseColor("#be8fe2");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_question);

        submitButton = (Button) findViewById(R.id.submit_answer_button);
        answerEditText = (EditText) findViewById(R.id.open_answer);
        chronometer = (Chronometer) findViewById(R.id.chronometer_open);
        questionTextView = (TextView) findViewById(R.id.open_text_question);
        progressTextView = (TextView) findViewById(R.id.open_questions_progress_text_view);
        accuracyTextView = (TextView) findViewById(R.id.correct_open_answers_text_view);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quizService.getCurrentQuestionIndex() + 1 > quizService.getQuestionCount()) {
                    chronometer.stop();

                    Intent intent = new Intent(OpenQuestionActivity.this, SummaryActivity.class);

                    intent.putExtra("accuracy", quizService.getCorrectAnswersPercentageToString());
                    intent.putExtra("time", chronometer.getText().toString());

                    quizService.onFinish();
                    stopService(new Intent(OpenQuestionActivity.this, QuizService.class));
                    startActivity(intent);
                }

                String answer = answerEditText.getText().toString();

                quizService.onAnswerSelect(answer, new Callback() {
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
                            answerEditText.getBackground().setTint(Color.RED);

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    answerEditText.getBackground().setTint(defaultButtonColor);
                                    answerEditText.setText("");
                                }
                            }, 1500);
                        } else {
                            dialog = ProgressDialog.show(OpenQuestionActivity.this, "",
                                    getString(R.string.loading_message), true);
                            answerEditText.setText("");
                        }

                        accuracyTextView.setText(quizService.getCorrectAnswersPercentageToString());
                        progressTextView.setText(quizService.getQuestionProgressToString());
                    }

                    @Override
                    void onCorrectAnswer() {
                        if (quizService.getMode() == Mode.TRAINING) {
                            answerEditText.getBackground().setTint(Color.GREEN);

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    answerEditText.getBackground().setTint(defaultButtonColor);
                                    answerEditText.setText("");
                                }
                            }, 1500);
                        } else {
                            dialog = ProgressDialog.show(OpenQuestionActivity.this, "",
                                    getString(R.string.loading_message), true);
                            answerEditText.setText("");
                        }

                        accuracyTextView.setText(quizService.getCorrectAnswersPercentageToString());
                        progressTextView.setText(quizService.getQuestionProgressToString());
                    }
                });
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

            chronometer.start();

            dialog = ProgressDialog.show(OpenQuestionActivity.this, "",
                    getString(R.string.loading_message), true);
            quizService.getNextQuestion(new Callback() {
                @Override
                void onQuestionReady(Question question) {
                    if (dialog != null) {
                        dialog.hide();
                    }

                    setQuestionText(question);
                }
            });

            accuracyTextView.setText(quizService.getCorrectAnswersPercentageToString());
            progressTextView.setText(quizService.getQuestionProgressToString());
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }
    };

    public void setQuestionText(Question question) {
        questionTextView.setText(question.text);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        quizService.onFinish();
        this.finish();
    }
}
