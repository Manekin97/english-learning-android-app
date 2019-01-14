package com.example.rafik.englishcourse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {
    private TextView scoreTextView;
    private TextView timeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        String accuracy = getIntent().getStringExtra("accuracy");
        String time = getIntent().getStringExtra("time");

        scoreTextView = (TextView) findViewById(R.id.score_summary);
        timeTextView = (TextView) findViewById(R.id.time_summary);

        scoreTextView.setText(accuracy);
        timeTextView.setText(time);
    }
}
