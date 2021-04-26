package com.example.se328_hesham190202;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;


public class MainActivity2 extends AppCompatActivity {

    MainActivity4 weather=new MainActivity4(); //step 1
    DatabaseHelper myDB;


    EditText id, fname, lname, mail, pass, id2, mail2;
    Button bttnAdd, bttnUpdate, bttnDelete, bttnView, bttnSubmitUpdatedSalray, bttnViewDataOnList;

    LinearLayout updatingLayout;

    int x=1;// to make my UPDATE layout visble/unvisible

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ImageView img = (ImageView) findViewById(R.id.imgact2);
        img.setImageResource(weather.getImgResource());
        bttnAdd=(Button)findViewById(R.id.bttnAdd);
        bttnUpdate=(Button)findViewById(R.id.bttnUpdate);
        bttnDelete=(Button)findViewById(R.id.bttnDelete);
        bttnView=(Button)findViewById(R.id.bttnView);
        bttnSubmitUpdatedSalray=(Button)findViewById(R.id.bttnSubmitUpdatedQuantity);
        bttnViewDataOnList=(Button)findViewById(R.id.bttnViewDataOnList);
        Button back = (Button) findViewById(R.id.menu);
        Button fb = (Button) findViewById(R.id.firebase);


        myDB = new DatabaseHelper(this);

        id = (EditText) findViewById(R.id.iduserinput);
        fname = (EditText) findViewById(R.id.fnameInput);
        lname = (EditText) findViewById(R.id.lastnameinput);
        mail = (EditText) findViewById(R.id.mailinput);
        pass = (EditText) findViewById(R.id.passworduserinput);
        id2 = (EditText) findViewById(R.id.employeeID2);
        mail2 = (EditText) findViewById(R.id.mailupdate2);


        updatingLayout=(LinearLayout)findViewById(R.id.layout);// the UPDATE layout

        bttnViewDataOnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this,list.class));
            }
        });



        bttnAdd.setOnClickListener(new View.OnClickListener() {
            //adds a record to the table
            @Override
            public void onClick(View v) {

                try{
                    if(myDB.addData(Integer.parseInt(id.getText().toString()),fname.getText().toString(), lname.getText().toString(),
                            mail.getText().toString(), pass.getText().toString())==false)
                        Toast.makeText(MainActivity2.this,"User: "+ Integer.parseInt(id.getText().toString())
                                +" already exists!",Toast.LENGTH_LONG).show();
                    else {
                        Toast.makeText(MainActivity2.this, "User " + Integer.parseInt(id.getText().toString())
                                + " was successfully added", Toast.LENGTH_LONG).show();
                        id.getText().clear(); fname.getText().clear(); lname.getText().clear();
                        mail.getText().clear(); pass.getText().clear();
                    }}

                catch(Exception e){
                    Toast.makeText(MainActivity2.this, "Make sure you enter all inputs ", Toast.LENGTH_LONG).show();
                }
            }
        });

        bttnUpdate.setOnClickListener(new View.OnClickListener() {
            //updates the SALARY of a record specified by the id
            // press the update button to make the layout visible, press it again to make it invisble
            @Override
            public void onClick(View v) {
                x++;

                if(x%2==0){
                    updatingLayout.setVisibility(View.VISIBLE);

                    bttnSubmitUpdatedSalray.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDB.update(Integer.parseInt(id2.getText().toString()), mail2.getText().toString());
                            Toast.makeText(MainActivity2.this, "User: "+id2.getText().toString()+
                                    " updated his/her email", Toast.LENGTH_SHORT).show();
                            id2.getText().clear();
                            mail2.getText().clear();

                        }
                    });

                }
                else
                    updatingLayout.setVisibility(View.INVISIBLE);

            }
        });


        bttnDelete.setOnClickListener(new View.OnClickListener() {
            //deletes a row specified  by the employee_NAME
            //then display a toast with the count of rows deleted
            //if no rows are found, display a toast saying that nothing was deleted
            @Override
            public void onClick(View v) {

                int result=myDB.dltRow(id.getText().toString());

                if(result>=1) {
                    Toast.makeText(MainActivity2.this,"User: " +id.getText().toString() + " susscessfully deleted", Toast.LENGTH_LONG).show();
                    id.getText().clear(); fname.getText().clear(); lname.getText().clear();
                    mail.getText().clear(); pass.getText().clear();
                }
                else
                    Toast.makeText(MainActivity2.this,"No users were deleted \nPlease enter ID correctly",Toast.LENGTH_LONG).show();

            }
        });


        bttnView.setOnClickListener(new View.OnClickListener() {
            // if nothing is entered in the user NAME it will view all enteries in the table
            // if a string was written in the user NAME it will show the record
            //an error will appear if no results were found

            Cursor cur;
            StringBuffer buffer=new StringBuffer();

            @Override
            public void onClick(View v) {
                if (fname.getText().toString().equals("")) {
                    cur = myDB.getListContents();
                } else if (!(fname.getText().toString().equals(""))) {
                    cur = myDB.getSpecificResult(fname.getText().toString());
                }

                if (cur.getCount()==0)
                    Toast.makeText(MainActivity2.this, "No users found !", Toast.LENGTH_LONG).show();
                else {

                    while (cur.moveToNext()) {
                        for (int i = 0; i < cur.getColumnCount(); i++) {
                            buffer.append(cur.getColumnName(i) + ": " + cur.getString(i) + "\n");
                        }
                        buffer.append("\n");

                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                    builder.setCancelable(true);
                    builder.setTitle("Users: ");
                    builder.setMessage(buffer.toString());
                    builder.show();

                    buffer.delete(0, buffer.capacity());
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, MainActivity.class));
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, FetchFB.class));
            }
        });
    }

    protected Cursor getDataList(){

        return myDB.getListContents();

    }
}