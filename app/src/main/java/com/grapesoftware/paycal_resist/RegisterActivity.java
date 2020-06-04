package com.grapesoftware.paycal_resist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class RegisterActivity extends AppCompatActivity {

    ImageView backButton;
    private EditText registerUserName;
    private EditText registerPassword;
    private Button buttonRegister;
    private FirebaseAuth mAuth;
    private String userName;
    private String userPassword;
    MaskedEditText editTextMobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerUserName = (EditText) findViewById(R.id.registerUserName);
        registerPassword = (EditText) findViewById(R.id.registerPassword);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        editTextMobile = (MaskedEditText) findViewById(R.id.editTextMobile);

        editTextMobile.setMask("+90 (5##) ### ## ##");


        mAuth = FirebaseAuth.getInstance();

        backButton = findViewById(R.id.imageView5);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                /* finish();*/
            }
        });

        // register buton tiklaninca
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = registerUserName.getText().toString();
                userPassword = registerPassword.getText().toString();
                String mobile ="5"+editTextMobile.getRawText();

                if(TextUtils.isEmpty(userName)) {
                    registerUserName.setError("This field cannot be empty");
                    registerUserName.requestFocus();
                    return;
                }

                else if(TextUtils.isEmpty(userPassword)){
                    registerPassword.setError("This field cannot be empty");
                    registerPassword.requestFocus();
                    return;
                }

                else if (mobile.isEmpty() || mobile.length() < 10) {
                    editTextMobile.setError("Please enter a valid  phone number");
                    editTextMobile.requestFocus();
                    return;
                }

                else{
                    registerFunc();

                }

                Intent intent = new Intent(RegisterActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("mobile", mobile);
                intent.putExtra("userName", userName);
                intent.putExtra("userPassword", userPassword);
                startActivity(intent);

            }
        });
    }

    private void registerFunc() {

        mAuth.createUserWithEmailAndPassword(userName, userPassword)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(RegisterActivity.this, VerifyPhoneActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

}
