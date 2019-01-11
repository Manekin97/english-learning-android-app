package com.example.rafik.englishcourse;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Callback {
    void callback(HashMap<String, String> data) {}
    void onDataReceived(){}
    void onWordDelete(){}
    void onWordsReceived(ArrayList<ServerService.Pair> words){}
    void onQuestionReady(Question question){}
    void onWrongAnswer(){}
    void onCorrectAnswer(){}
}
