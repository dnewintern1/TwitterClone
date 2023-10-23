package com.base.twitterclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listVIew;
    private ArrayList<UserDetails> arrayList;

    private TextView loadingText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listVIew = findViewById(R.id.listVIew);
        loadingText = findViewById(R.id.loadingText);

        arrayList =new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Twitter User");

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear(); // Clear the list to avoid duplicates

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserDetails user = snapshot.getValue(UserDetails.class); // Assuming User is a custom class representing user details
                    if (user != null && !snapshot.getKey().equals(userId)) {
                        arrayList.add(user);
                    }
                }

                // Create an ArrayAdapter to populate the ListView
                UserAdapter adapter = new UserAdapter(MainActivity.this, R.layout.user_list_item, arrayList);
                listVIew.setAdapter(adapter);
                loadingText.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors here
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.postimage) {
            startActivity(new Intent(MainActivity.this, Loginactivity.class));
        }
        if (item.getItemId() == R.id.sentTweet){
            startActivity(new Intent(MainActivity.this,TweetActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }
}