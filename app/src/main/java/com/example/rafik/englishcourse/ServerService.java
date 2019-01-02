package com.example.rafik.englishcourse;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServerService extends Service {
//    @androidx.annotation.Nullable
    private final IBinder mBinder = new ServerBinder();
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public class ServerBinder extends Binder {
        ServerService getService() {
            // Return this instance of ServerService so clients can call public methods
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
        myRef = database.getReference("/Words/9EfEoK6HyJG0GC8ChWUW");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
//                Post post = dataSnapshot.getValue(Post.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "onCancelled", databaseError.toException());
            }
        };

        myRef.addValueEventListener(postListener);
    }

    public void updateQuestion(){
        // test firebase
        myRef.child("czekolada").setValue("laptop");
    }

    public void GetQuestion(View view) {

        final TextView label = (TextView) view;

        myRef.child("czekolada").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String question = dataSnapshot.getValue(String.class);
                label.setText("question");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
