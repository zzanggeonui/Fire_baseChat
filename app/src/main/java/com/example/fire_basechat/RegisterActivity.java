package com.example.fire_basechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fire_basechat.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    // 회원가입 인증관련(파이어베이스 인증)
    private FirebaseAuth mFirebaseAuth;
    // 데이터베이스연결(실시간 데이터베이스)
    private DatabaseReference mDatabaseRef;

    private EditText editEmail;
    private  EditText editPassword;
    private Button btnRegister;
    private TextView txtLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("chat");

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회갑 정보 받기
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                //파이어베이스 회원가입 진행
                mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                   if (task.isSuccessful()){
                       //가입 성공시 user를 불러올수 있으며 firebaseuser 객체안에 현재 가입 성공한 유저 넣어줌
                       FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                       User user = new User();
                       user.setEmailId(firebaseUser.getEmail());
                       user.setPassword(password);
                       user.setIdToken(firebaseUser.getUid());

                       // "chat" 이라는 하위개념에 child 안에 내용 넣고 setvalue(데이터베이스에 삽입해주기 가입성공한 User 정보)
                       mDatabaseRef.child("User").child(firebaseUser.getUid()).setValue(user);
                       Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();

                   }else {
                       Toast.makeText(RegisterActivity.this, "회갑 실패", Toast.LENGTH_SHORT).show();
                   }
                    }
                });

                txtLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });


            }
        });



    }
}