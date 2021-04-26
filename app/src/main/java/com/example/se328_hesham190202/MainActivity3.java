package com.example.se328_hesham190202;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity3 extends AppCompatActivity {

    TextView success;
    EditText name1, name2, email, phone, pass;
    Button go, delete, update, viewbttn, menu;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    MainActivity4 weather=new MainActivity4(); //step 1

    String fname, lname, mail, num, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        name1 = (EditText) findViewById(R.id.fullname);
        name2 = (EditText) findViewById(R.id.user);
        email = (EditText) findViewById(R.id.editTextTextPersonName);
        phone = (EditText) findViewById(R.id.editTextNumber);
        pass = (EditText) findViewById(R.id.pass);
        go = (Button) findViewById(R.id.go);
        delete = (Button) findViewById(R.id.delete);
        update = (Button) findViewById(R.id.update);
        viewbttn = (Button) findViewById(R.id.button9);
        menu = (Button) findViewById(R.id.mainmenu);
        ImageView img = (ImageView) findViewById(R.id.imgact3);
        img.setImageResource(weather.getImgResource());

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity3.this, MainActivity.class ));
            }
        });


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance(); //call the root node and include all of them
                reference = rootNode.getReference();


                fname = name1.getText().toString();
                lname = name2.getText().toString();
                mail = email.getText().toString();
                num= phone.getText().toString();
                password = pass.getText().toString();

                if(TextUtils.isEmpty(fname)||TextUtils.isEmpty(lname)||TextUtils.isEmpty(mail)||TextUtils.isEmpty(num)||TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity3.this, "Please enter all values", Toast.LENGTH_LONG).show();

                }

                else {
                    writeNewUser(fname, lname, mail, num, password);
                    name1.getText().clear();
                    name2.getText().clear();
                    email.getText().clear();
                    phone.getText().clear();
                    pass.getText().clear();
                    Toast.makeText(MainActivity3.this, "User Added!", Toast.LENGTH_LONG).show();

                }



            }



            private void writeNewUser(String fname, String lname, String mail, String num, String password) {
                User user = new User(fname, lname, mail, num, password);
                reference.child("users").child(num).setValue(user);
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                num = phone.getText().toString();
                if(TextUtils.isEmpty(phone.getText().toString())){

                    Toast.makeText(MainActivity3.this, "Please Enter ID number", Toast.LENGTH_LONG).show();
                }

                else
                    deleteUser(num);

            }


            private void deleteUser(String num) {
                DatabaseReference dltuser = FirebaseDatabase.getInstance().getReference("users").child(num);

                dltuser.removeValue();
                Toast.makeText(MainActivity3.this, "User Deleted", Toast.LENGTH_LONG).show();


            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity3.this, update.class));
            }
        });

        viewbttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity3.this, displayUsers.class));
            }
        });


    }


    @IgnoreExtraProperties
    public class User{
        public String fname;
        public String lname;
        public String mail;
        public String num;
        public String password;

        public User(){
            // constructor
        }
        public User(String fname, String lname, String mail, String num, String password){
            this.fname = fname;
            this.lname = lname;
            this.mail = mail;
            this.num = num;
            this.password = password;
        }
    }
}