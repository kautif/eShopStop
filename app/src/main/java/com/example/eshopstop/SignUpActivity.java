package com.example.eshopstop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText signupName, signupEmail, signupPw;
    private String name, email, password;
    Button signupBtn;
    FirebaseAuth mAuth;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signupName = findViewById(R.id.signup_name_editText);
        signupEmail = findViewById(R.id.signup_email_editText);
        signupPw = findViewById(R.id.signup_password_editText);
        signupBtn = findViewById(R.id.signup_button);
        mAuth = FirebaseAuth.getInstance();
        mToolbar = findViewById(R.id.signup_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = signupName.getText().toString();
                email = signupEmail.getText().toString();
                password = signupPw.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                            Toast.makeText(SignUpActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(SignUpActivity.this, "Account creation failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void toLogin(View view) {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
    }
}
