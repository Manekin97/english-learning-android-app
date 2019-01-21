package com.example.rafik.englishcourse;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Callback {
    void onWordsReceived(HashMap<String, String> data) {}
    void onDataReceived(){}
    void onWordsReceived(ArrayList<ServerService.Pair> words){}
    void onQuestionReady(Question question){}
    void onWrongAnswer(){}
    void onCorrectAnswer(){}
}
