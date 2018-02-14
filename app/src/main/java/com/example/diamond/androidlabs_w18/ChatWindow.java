package com.example.diamond.androidlabs_w18;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        messageAdapter = new ChatAdapter(this);
        lv = findViewById(R.id.listView);
        chatEditTxt = findViewById(R.id.chatEditTxt);
        sendButton = findViewById(R.id.sendBtn);
        al = new ArrayList<>();
        sendButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                al.add(chatEditTxt.getText().toString());
                messageAdapter.notifyDataSetChanged();
                chatEditTxt.setText("");
            }
        });
        lv.setAdapter(messageAdapter);

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
