package com.example.rafik.englishcourse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {
    private TextView scoreTextView;
    private TextView timeTextView;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        String accuracy = getIntent().getStringExtra("accuracy");
        String time = getIntent().getStringExtra("time");

        scoreTextView = (TextView) findViewById(R.id.score_summary);
        timeTextView = (TextView) findViewById(R.id.time_summary);
        homeButton = (Button) findViewById(R.id.main_menu_button);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SummaryActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });

        scoreTextView.setText(accuracy);
        timeTextView.setText(time);
    }
}
