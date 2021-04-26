package com.example.se328_hesham190202;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class update extends AppCompatActivity {
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    EditText id, field, newVal;
    Button up, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        id = (EditText) findViewById(R.id.userIDup);
        field = (EditText) findViewById(R.id.fieldUp);
        newVal = (EditText) findViewById(R.id.valUp);
        up = (Button) findViewById(R.id.bttn44);
        back = (Button) findViewById(R.id.bttn55);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String i1 = id.getText().toString();
                String i2 = field.getText().toString();
                String i3 = newVal.getText().toString();

                change(i1, i2, i3);

            }

            private void change(String i1, String i2, String i3) {
                rootNode = FirebaseDatabase.getInstance(); //call the root node and include all of them
                reference = rootNode.getReference("users");

                try {
                    reference.child(i1).child(i2).setValue(i3);
                    Toast.makeText(update.this, "User "+i2+" updated", Toast.LENGTH_LONG).show();

                }

                catch(Exception e){
                    Toast.makeText(update.this, e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(update.this, MainActivity3.class));
            }
        });

    }
}