package com.example.rafik.englishcourse;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class ServerService extends Service {

    private final IBinder mBinder = new ServerBinder();
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public class ServerBinder extends Binder {
        ServerService getService() {
            return ServerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("/Words");
    }

    public void updateQuestion(String key, String value){
        myRef.child(key).setValue(value);
    }

    public void getWords(Callback callback){

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, String> data = (HashMap<String, String>) dataSnapshot.getValue();
                callback.callback(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DBerror", databaseError.getMessage());
            }
        });
    }

    class Pair{
        public String key;
        public String value;

        Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public void getQuestion(Callback callback) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long wordCount = dataSnapshot.getChildrenCount();
                ArrayList<Pair> words = new ArrayList<>();
                int[] indexes = ThreadLocalRandom.current().ints(0, (int)wordCount).distinct().limit(4).toArray();

                Iterable<DataSnapshot> data = dataSnapshot.getChildren();
                Iterator<DataSnapshot> it = data.iterator();

                int i = 0;
                while (it.hasNext()){
                    DataSnapshot child = it.next();

                    if (i == indexes[0]){
                        words.add(new Pair(child.getKey(), child.getValue(String.class)));
                    }

                    if (i == indexes[1]){
                        words.add(new Pair(child.getKey(), child.getValue(String.class)));
                    }

                    if (i == indexes[2]){
                        words.add(new Pair(child.getKey(), child.getValue(String.class)));
                    }

                    if (i == indexes[3]){
                        words.add(new Pair(child.getKey(), child.getValue(String.class)));
                    }

                    i++;
                }

                callback.onWordsReceived(words);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        myRef.child("mysz").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DBerror", databaseError.getMessage());
            }
        });
    }

    public void deleteWord(String word) {
        myRef.child(word).removeValue();
    }

    public void addWord(String word, String translation){
        myRef.child(word).setValue(translation);
    }

    public  void editTranslation(String word, String newTranslation){
        myRef.child(word).setValue(newTranslation);
    }

}
