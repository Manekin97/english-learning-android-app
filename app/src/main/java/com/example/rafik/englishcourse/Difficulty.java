package com.example.rafik.englishcourse;

public enum Difficulty {
    BEGINNER(10),
    ELEMENTARY(20),
    INTERMEDIATE(30),
    EXPERT(30);

    public int QUESTIONS_COUNT;

    Difficulty(int count){
        QUESTIONS_COUNT = count;
    }
}
