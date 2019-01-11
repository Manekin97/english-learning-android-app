package com.example.rafik.englishcourse;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class QuizService extends Service {
    private final IBinder mBinder = new QuizServiceBinder();
    private Difficulty difficulty;
    private Mode mode;
    private int questionsCount;
    private ServerService db;
    private QuestionFactory factory;
    public HashMap<String, String> words = new HashMap<String, String>();
    private boolean isBound = false;
    private Question question;

    public class QuizServiceBinder extends Binder {
        QuizService getService() {
            return QuizService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public HashMap<String, String> getWords() {
        return words;
    }

    @Override
    public void onCreate() {
        Intent intent = new Intent(this, ServerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    public void loadWords(Callback callback) {
        if (db == null) {
            return;
        }

        db.getWords(new Callback() {
            @Override
            public void callback(HashMap<String, String> data) {
                if (words == null) {
                    words = new HashMap<String, String>();
                }

                data.forEach((key, value) -> {
                    words.put(key, value);
                });

                callback.onDataReceived();
            }
        });
    }

    public void onAnswerSelect(String answer, Callback callback) {
        question.onAnswerSelect(answer, this, callback);
    }

    public void setQuizMode(Mode mode) {
        if (mode == Mode.TEST) {
            this.factory = new TestQuestionFactory();
        } else {
            this.factory = new TrainingQuestionFactory();
        }
    }

    public void getNextQuestion(Callback callback){
        Question q = factory.getQuestion();

        db.getQuestion(new Callback() {
            @Override
            void onWordsReceived(ArrayList<ServerService.Pair> words) {
                int rndIndex = new Random().nextInt(words.size());

                q.text = words.get(rndIndex).key;
                q.correctAnswerIndex = rndIndex;
                q.answers = words;
                q.type = QuestionType.CLOSED_QUESTION;

                question = q;

                callback.onQuestionReady(q);
            }
        });
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            ServerService.ServerBinder binder = (ServerService.ServerBinder) service;
            db = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }
    };

    public void addNewWord(String word, String translation) {
        db.addWord(word, translation);
    }

    public void deleteWord(String word) {
        db.deleteWord(word);
    }

    public void editTranslation(String word, String newTranslation) {
        db.editTranslation(word, newTranslation);
    }
}
