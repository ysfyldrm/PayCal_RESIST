package com.grapesoftware.paycal_resist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private ImageView backButton;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private EditText edtuserPassword,edtuserName;
    private String userName,userPassword;
    private TextView txtRegister;
    private TextView txtForgetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtRegister = findViewById(R.id.txtViewReq);
        edtuserName= findViewById(R.id.edtLogin);
        edtuserPassword= findViewById(R.id.edtPass);
        txtForgetPass= findViewById(R.id.txtForgetP);

        txtForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgetPassActivity.class);
                startActivity(intent);
            }
        });


        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        if(firebaseUser != null){ // check user session

            Intent i=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
        }
        loginButton=findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName=edtuserName.getText().toString();
                userPassword=edtuserPassword.getText().toString();
                if(userName.isEmpty() || userPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Lütfen gerekli alanları doldurunuz!", Toast.LENGTH_SHORT).show();
                }
                else{
                    loginFunc();
                }
            }

            private void loginFunc() {

                mAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(LoginActivity.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(i);
                                    finish();

                                }
                                else{
                                    // hata
                                    Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
            }
        });
        backButton=findViewById(R.id.imageView);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),WelcomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                finish();
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    //Change UI according to user data.
    private void  updateUI(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"Başarıyla Giriş Yaptınız!",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,MainActivity.class));
        }else {
            Toast.makeText(this,"Giriş Yapmanız Gerekmekte.", Toast.LENGTH_LONG).show();
        }
    }

}