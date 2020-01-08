package com.grapesoftware.paycal_resist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgetPassActivity extends AppCompatActivity {

    private EditText userMail;
    private Button sendMail;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        userMail=findViewById(R.id.edtMailResetPass);
        sendMail=findViewById(R.id.btnResetPass);

        mAuth=FirebaseAuth.getInstance();

        backButton=findViewById(R.id.imageView2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                //finish();
            }
        });

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.sendPasswordResetEmail(userMail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ForgetPassActivity.this,"Şifre sıfırlama bilgileriniz mail adresinize gönderildi.",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(ForgetPassActivity.this,"Girilen mail adresi sistemimizde bulunmamaktadır.",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }
}