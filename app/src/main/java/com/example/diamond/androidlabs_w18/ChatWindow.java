package com.example.diamond.androidlabs_w18;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    ListView lv;
    EditText chatEditTxt;
    Button sendButton;
    ArrayList<String> al;
    ChatAdapter messageAdapter;
    ChatDatabaseHelper help;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        help = new ChatDatabaseHelper(this);
        db = help.getWritableDatabase();
        String q = "SELECT " + ChatDatabaseHelper.MESSAGE + " FROM " + ChatDatabaseHelper.MESSAGES;
        Cursor c = db.rawQuery(q,null);

        messageAdapter = new ChatAdapter(this);
        lv = findViewById(R.id.listView);
        chatEditTxt = findViewById(R.id.chatEditTxt);
        sendButton = findViewById(R.id.sendBtn);
        al = new ArrayList<>();


        sendButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                al.add(chatEditTxt.getText().toString());
                ContentValues cv = new ContentValues();
                cv.put(ChatDatabaseHelper.MESSAGE, chatEditTxt.getText().toString());
                db.insert(ChatDatabaseHelper.MESSAGES, null, cv);
                messageAdapter.notifyDataSetChanged();
                chatEditTxt.setText("");
            }
        });
        lv.setAdapter(messageAdapter);

        while(c.moveToNext()){
            Log.i("ChatWindow", "SQL MESSAGE:" + c.getString( c.getColumnIndex( ChatDatabaseHelper.MESSAGE) ) );

            String message = c.getString(c.getColumnIndex(ChatDatabaseHelper.MESSAGE));
            al.add(message);
        }
        Log.i("ChatWindow", "Cursor's  column count =" + c.getColumnCount() );
        for(int i = 0; i < c.getColumnCount(); i++){
            Log.i("ChatWindow",c.getColumnName(i));
        }

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.close();
    }
    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context cntx){
            super(cntx, 0);
        }
        public int getCount(){
            return al.size();
        }
        public String getItem(int position){
            return al.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if(position % 2 == 0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing,null);
            }
            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }
        public long getId(int position){
            return position;
        }


    }
}
