package com.example.fire_basechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fire_basechat.adapter.ChatAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {




    Button btnAdd;
    Button btnLogout;

    EditText editChat;

    String strEmail;

    private FirebaseAuth mFirebaseAuth;
    // 데이터베이스연결(실시간 데이터베이스)
   DatabaseReference mDatabaseRef;
   FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        database = FirebaseDatabase.getInstance();

        strEmail = getIntent().getStringExtra("email");
        btnLogout = findViewById(R.id.btnLogout);

        editChat = findViewById(R.id.editChat);
        btnAdd = findViewById(R.id.btnAdd);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {

            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
            return;
        }


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 변수초기화후 개체 반환 클래스
                mFirebaseAuth = FirebaseAuth.getInstance();
                //로그아웃
                mFirebaseAuth.signOut();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });





    }
}