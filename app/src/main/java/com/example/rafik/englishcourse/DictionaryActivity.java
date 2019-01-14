package com.example.rafik.englishcourse;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.HashMap;

public class DictionaryActivity extends AppCompatActivity implements NewWordDialog.NewWordDialogListener, EditWordDialog.EditWordDialogListener {
    private Button addWordButton;

    QuizService quizService;
    boolean mBound = false;
    private RecyclerView mRecyclerView;
    private DictionaryViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context activityContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        addWordButton = (Button) findViewById(R.id.addButton);

        addWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DictionaryViewAdapter(this, new HashMap<String, String>());
        mRecyclerView.setAdapter(mAdapter);
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
        mBound = false;
    }


    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            QuizService.QuizServiceBinder binder = (QuizService.QuizServiceBinder) service;
            quizService = binder.getService();
            mBound = true;

            quizService.loadWords(new Callback() {
                @Override
                public void onDataReceived() {
                    mAdapter.setData(quizService.getWords());
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    private void openDialog(){
        NewWordDialog dialog = new NewWordDialog();
        dialog.show(getSupportFragmentManager(), "newWordDialog");
    }

    @Override
    public void onAddButtonClick(String word, String translation) {
        quizService.addNewWord(word, translation);
        mAdapter.addWord(word, translation);
    }

    public void onDeleteButtonClick(String word){
        quizService.deleteWord(word);
    }

    @Override
    public void onSaveButtonClick(String word, String translation){
        quizService.editTranslation(word, translation);
        mAdapter.replaceTranslation(word, translation);
    }
}
