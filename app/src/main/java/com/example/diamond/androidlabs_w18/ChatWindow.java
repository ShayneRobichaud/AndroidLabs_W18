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

    ChatDatabaseHelper help;
    ListView lv;
    EditText chatEditTxt;
    Button sendButton;
    ArrayList<String> al = new ArrayList<>();
    Cursor data;
    ChatAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        messageAdapter = new ChatAdapter(this);

        lv = findViewById(R.id.listView);
        chatEditTxt = findViewById(R.id.chatEditTxt);
        sendButton = findViewById(R.id.sendBtn);

        help = new ChatDatabaseHelper(this);
        final SQLiteDatabase db = help.getWritableDatabase();

        data = db.query(false, "MESSAGES", new String[] {ChatDatabaseHelper.MESSAGE, ChatDatabaseHelper.ID},
                null,null,null,null,null,null);
        final int index = data.getColumnIndex("MESSAGE");

        data.moveToFirst();
        while(!data.isAfterLast()) {
            String m = data.getString(index);
            al.add(m);
            data.moveToNext();
        }
        messageAdapter.notifyDataSetChanged();

        lv.setAdapter(messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String message = chatEditTxt.getText().toString();
                ContentValues cv = new ContentValues();
                cv.put("MESSAGE", message);
                db.insert(ChatDatabaseHelper.MESSAGES, null, cv);

                al.add(message);
                chatEditTxt.setText("");
                messageAdapter.notifyDataSetChanged();
            }
        });

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
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
            View result;
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
