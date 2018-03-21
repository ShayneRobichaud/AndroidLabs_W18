package com.example.diamond.androidlabs_w18;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Diamond on 21/03/2018.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper{
    private static String DATABASE_NAME = "Messages.db";
    private static int VERSION_NUM = 1;
    final static public String MESSAGES = "MESSAGES";
    final static public String ID = "ID";
    final static public String MESSAGE = "MESSAGE";


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");

        String ddl = "Create table " + MESSAGES + "( " + ID + " integer primary key autoincrement, " + MESSAGE + " text)";
        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int o, int n) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + o + " newVersion=" + n);

        String delete = "drop table if exists " + MESSAGES;
        db.execSQL(delete);
        onCreate(db);
    }

    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }
}
