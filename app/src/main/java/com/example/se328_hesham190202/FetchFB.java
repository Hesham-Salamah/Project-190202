package com.example.se328_hesham190202;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FetchFB extends AppCompatActivity {

    Button back, view, add;
    FirebaseDatabase database;
    DatabaseReference ref;
    users u;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_f_b);

        u = new users();
        back = (Button) findViewById(R.id.act2);
        view = (Button) findViewById(R.id.viewusersfb);
        add = (Button) findViewById(R.id.addtosql);
        myDB = new DatabaseHelper(this);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("users");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FetchFB.this, MainActivity2.class));
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FetchFB.this, displayUsers.class));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{

                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds: snapshot.getChildren()){

                                try{

                                    u = ds.getValue(users.class);
                                    if(!myDB.addData(Integer.parseInt(u.getNum()), u.getFname(), u.getLname(), u.getMail(),
                                            u.getPassword())) {
                                        Toast.makeText(FetchFB.this, "Users fetched from Firebase to local Database", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(FetchFB.this, "Users already exists in local Database", Toast.LENGTH_LONG).show();

                                    }}

                                catch(Exception e){
                                    Toast.makeText(FetchFB.this, "No Users", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                catch(Exception e){
                    Toast.makeText(FetchFB.this, "No Users", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}