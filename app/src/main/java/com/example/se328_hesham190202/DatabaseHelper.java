package com.example.se328_hesham190202;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Mitch on 2016-05-13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "us.db";
    public static final String TABLE_NAME = "users_values";
    public static final String COL1 = "num";
    public static final String COL2 = "fname";
    public static final String COL3 = "lname";
    public static final String COL4 = "mail";
    public static final String COL5 = "password";

    //public static final String COL4 = "Unused";

    /* Constructor */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /* Code runs automatically when the dB is created */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (num INTEGER PRIMARY KEY, " +
                " fname TEXT, lname TEXT, mail TEXT, password TEXT)";

        db.execSQL(createTable);
    }

    /* Every time the dB is updated (or upgraded) */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /* Basic function to add data. REMEMBER: The fields
       here, must be in accordance with those in
       the onCreate method above.
    */
    public boolean addData(int num,String fname, String lname, String mail, String password) {
        long result;

        Cursor cur=getSpecifiedResultByID(num);

        if(cur.getCount()==1)
            return false;
        else {


            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL1, num);
            contentValues.put(COL2, fname);
            contentValues.put(COL3, lname);
            contentValues.put(COL4, mail);
            contentValues.put(COL5, password);

            result = db.insert(TABLE_NAME, null, contentValues);
        }


        //if data are inserted incorrectly, it will return -1
        if(result == -1 ) {
            return false;
        } else {
            return true;
        }
    }

    /* Returns only one result */
    public Cursor structuredQuery(int ID) {
        SQLiteDatabase db = this.getReadableDatabase(); // No need to write
        Cursor cursor = db.query(
                TABLE_NAME, new String[]{COL1,
                        COL2, COL3, COL4, COL5}, COL1 + "=?",
                new String[]{String.valueOf(ID)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    public Cursor getSpecificResult(String fname){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME +" Where fname=? ",new String[]{fname});
        return data;
    }

    public Cursor getSpecifiedResultByID(int ID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME +" Where num=?  ",new String[]{String.valueOf(ID)});
        return data;

    }

    // Return everything inside a specific table
    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;

    }

    public int dltRow(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        int delete=0;

        long result= DatabaseUtils.queryNumEntries(db,TABLE_NAME,"num=?",new String[]{id});

        if(result>=1)
            delete=db.delete(TABLE_NAME,"num=?",new String[]{id});

        return delete;

    }

    public void update(int id, String mail){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL4,mail);

        db.update(TABLE_NAME,contentValues,"num=?",new String[]{String.valueOf(id)});

    }


}

