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
            checkIfAllQuestionsHaveBeenAnswered();

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
                    updateViewOnWrongAnswer(answerAButton);
                }

                @Override
                void onCorrectAnswer() {
                    updateViewOnCorrectAnswer(answerAButton);
                }
            });
        });

        this.answerBButton.setOnClickListener(v -> {
            checkIfAllQuestionsHaveBeenAnswered();

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
                    updateViewOnWrongAnswer(answerBButton);
                }

                @Override
                void onCorrectAnswer() {
                    updateViewOnCorrectAnswer(answerBButton);

                }
            });
        });

        this.answerCButton.setOnClickListener(v -> {
            checkIfAllQuestionsHaveBeenAnswered();

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
                    updateViewOnWrongAnswer(answerCButton);
                }

                @Override
                void onCorrectAnswer() {
                    updateViewOnCorrectAnswer(answerCButton);
                }
            });
        });

        this.answerDButton.setOnClickListener(v -> {
            checkIfAllQuestionsHaveBeenAnswered();

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
                    updateViewOnWrongAnswer(answerDButton);
                }

                @Override
                void onCorrectAnswer() {
                    updateViewOnCorrectAnswer(answerDButton);
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
            dialog = ProgressDialog.show(QuestionActivity.this, "",
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

    public void updateViewOnWrongAnswer(Button button){
        if (quizService.getMode() == Mode.TRAINING) {
            button.getBackground().setTint(Color.RED);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    button.getBackground().setTint(defaultButtonColor);
                    questionsProgressTextView.setText(quizService.getQuestionProgressToString());
                }
            }, 1500);
        } else {
            questionsProgressTextView.setText(quizService.getQuestionProgressToString());
            dialog = ProgressDialog.show(QuestionActivity.this, "",
                    getString(R.string.loading_message), true);
        }
    }

    public void updateViewOnCorrectAnswer(Button button){
        if (quizService.getMode() == Mode.TRAINING) {
            button.getBackground().setTint(Color.GREEN);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    button.getBackground().setTint(defaultButtonColor);
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

    public void checkIfAllQuestionsHaveBeenAnswered(){
        if (quizService.getCurrentQuestionIndex() + 1 > quizService.getQuestionCount()) {
            this.chronometer.stop();

            Intent intent = new Intent(this, SummaryActivity.class);

            intent.putExtra("accuracy", quizService.getCorrectAnswersPercentageToString());
            intent.putExtra("time", this.chronometer.getText().toString());

            quizService.onFinish();
            stopService(new Intent(this, QuizService.class));
            startActivity(intent);
        }
    }
}
