package com.example.fire_basechat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.fire_basechat.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {



    private String CHAT_NAME;


    Button btnAdd;
    Button btnLogout;

    ListView chatView;
    EditText editChat;

    public String strEmail;

    ArrayAdapter<String> adapter;

    private FirebaseAuth FirebaseAuth;
    // 데이터베이스연결(실시간 데이터베이스)

   FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference DatabaseRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        database = FirebaseDatabase.getInstance();

        strEmail = getIntent().getStringExtra("email");
        btnLogout = findViewById(R.id.btnLogout);

        editChat = findViewById(R.id.editChat);
        btnAdd = findViewById(R.id.btnAdd);
        chatView = findViewById(R.id.chatView);


        Intent intent = getIntent();
        CHAT_NAME = intent.getStringExtra("chatName");




        openChat(CHAT_NAME);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editChat.getText().toString().equals(""))
                    return;

                User user = new User(strEmail, editChat.getText().toString());
                DatabaseRef.child("chat").child(CHAT_NAME).push().setValue(user); // 데이터 푸쉬
                editChat.setText(""); //입력창 초기화
            }
        });



    }

    private void openChat(String chatName){
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        chatView.setAdapter(adapter);

        DatabaseRef.child("chat").child(chatName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot , @Nullable String previousChildName) {
                addMessage(dataSnapshot, adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 변수초기화후 개체 반환 클래스
                FirebaseAuth = FirebaseAuth.getInstance();
                //로그아웃
                FirebaseAuth.signOut();

                Intent intent = new Intent(ChatActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


   private void addMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter) {
        User user = dataSnapshot.getValue(User.class);
        adapter.add(user.getEmailId() + " : " + user.getMessage());
    }



}
