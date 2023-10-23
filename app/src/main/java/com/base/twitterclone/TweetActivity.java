package com.base.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.slider.RangeSlider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class TweetActivity extends AppCompatActivity   {

    private EditText tweetEditText;
    private Button sendTweetButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        tweetEditText = findViewById(R.id.editTextText);
        sendTweetButton = findViewById(R.id.btnSendTweet);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        sendTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = auth.getCurrentUser();

                String tweetText = tweetEditText.getText().toString().trim();


                if (currentUser != null) {
                    String uid = currentUser.getUid();

                    if (!tweetText.isEmpty()) {
                        // Create a unique key for the new tweet
                        String tweetId = databaseReference.child("tweets").push().getKey();

                        // Create a HashMap to store the tweet data
                        HashMap<String, Object> tweetData = new HashMap<>();
                        tweetData.put("text", tweetText);

                        // Add the tweet to the user's node in the database
                        databaseReference.child("Twitter User").child(uid).child("tweets").child(tweetId).setValue(tweetData);

                        Toast.makeText(TweetActivity.this,"tweet sent",Toast.LENGTH_SHORT).show();
                        // Clear the EditText
                        tweetEditText.setText("");

                        // You now have the current user's UID, which can be used to access their data in the database.
                    } else {

                        Toast.makeText(TweetActivity.this,"user no Loggged in",Toast.LENGTH_SHORT).show();
                         }
                }
            }
        });

    }



}