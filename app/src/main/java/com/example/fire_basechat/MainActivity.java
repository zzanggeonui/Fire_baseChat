package com.example.fire_basechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    private EditText userChat;
    private TextView txtEmail;
    private Button userNext;
    private ListView chatList;
    ArrayAdapter<String> adapter;
    public String strEmail;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference DatabaseRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userChat = findViewById(R.id.userChat);
        txtEmail = findViewById(R.id.txtEmail);
        userNext = findViewById(R.id.userNext);
        chatList = findViewById(R.id.chatList);

        strEmail = getIntent().getStringExtra("email");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (strEmail == null) {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        String email = getIntent().getStringExtra("email");

        txtEmail.setText(email+"님 안녕하세요");




        userNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( userChat.getText().toString().equals(""))
                    return;

                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra("chatName", userChat.getText().toString());
                intent.putExtra("userName", txtEmail.getText().toString());
                startActivity(intent);
            }
        });

        showChatList();

    }

    private void showChatList() {
        // 리스트 어댑터 생성 및 세팅
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        chatList.setAdapter(adapter);

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        DatabaseRef.child("chat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("CHATBOT_APP", "dataSnapshot.getKey() : " + dataSnapshot.getKey());
                adapter.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
