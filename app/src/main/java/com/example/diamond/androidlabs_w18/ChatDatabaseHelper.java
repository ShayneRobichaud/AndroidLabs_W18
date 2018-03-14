package com.example.diamond.androidlabs_w18;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Diamond on 07/03/2018.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper{
    public static final String MESSAGES = "MESSAGES";
    public static final String ID = "ID";
    public static final String MESSAGE = "MESSAGE";
    public static String DATABASE_NAME;
    public static int VERSION_NUM = 3;

    @Override
    public void onCreate(SQLiteDatabase db){
        String ddl = "CREATE TABLE " + MESSAGES + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MESSAGE + " STRING " + ");";
        db.execSQL(ddl);
        Log.i("ChatDatabaseHelper", "Calling onCreate");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String dropCheck = "DROP TABLE IF EXISTS " + MESSAGES;
        db.execSQL(dropCheck);
        onCreate(db);

        //Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion=" + newVer);
    }

    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }
}
