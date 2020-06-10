package com.grapesoftware.paycal_resist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgetPassActivity extends AppCompatActivity {

    TextInputLayout userMail;
    private Button resetButton;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        userMail=findViewById(R.id.email_reset);
        resetButton=findViewById(R.id.reset_btn);

        mAuth=FirebaseAuth.getInstance();

        View view = this.getCurrentFocus();



        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    mAuth.sendPasswordResetEmail(userMail.getEditText().getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ForgetPassActivity.this,"New password link sent to your mail address.",Toast.LENGTH_LONG).show();
                                    }

                                    else {
                                        Toast.makeText(ForgetPassActivity.this,"Your mail address does not match any account.",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });



            }
        });

    }
}