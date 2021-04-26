package com.example.se328_hesham190202;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class displayUsers extends AppCompatActivity {
    ListView myListview;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> al = new ArrayList<>();
    DatabaseReference mRef;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    DatabaseHelper mydb;
    users user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_users);

        user = new users();
        myListview = (ListView) findViewById(R.id.listview);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("users");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userinfo, list);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){

                    user = ds.getValue(users.class);
                    list.add("________________________________________\n"+ "First name: "+user.getFname() +
                            "\nLast name: "+user.getLname()+ "\nE-Mail: "+user.getMail() + "\nUser ID: "+user.getNum()
                            +"\n________________________________________\n");



                }
                myListview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}