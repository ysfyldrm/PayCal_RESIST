package com.grapesoftware.paycal_resist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    private EditText editTextMobile;
    private Button buttonRegister;
    private FirebaseAuth mAuth;
    private String userName;
    private String userPassword;
    String text11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerUserName = (EditText) findViewById(R.id.registerUserName);
        registerPassword = (EditText) findViewById(R.id.registerPassword);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        //editTextMobile = findViewById(R.id.editTextMobile);

        //editTextMobile.setTransformationMethod(null);
        MaskedEditText editText = (MaskedEditText) findViewById(R.id.editTextMobile);

        editText.setMask("+90 (5##) ### ## ##");
        text11=editText.getRawText();

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

                Toast.makeText(getApplicationContext(),text11,Toast.LENGTH_SHORT).show();

//                userName = registerUserName.getText().toString();
//                userPassword = registerPassword.getText().toString();
//                if (userName.isEmpty() || userPassword.isEmpty()) {
//
//                    Toast.makeText(getApplicationContext(), "Please fill in the required fields. ", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    registerFunc();
//                }
//
//
//                String mobile = editTextMobile.getText().toString().trim();
//
//                if (mobile.isEmpty() || mobile.length() < 10) {
//                    editTextMobile.setError("Please enter a valid number");
//                    editTextMobile.requestFocus();
//                    return;
//                }
//
//                Intent intent = new Intent(RegisterActivity.this, VerifyPhoneActivity.class);
//                intent.putExtra("mobile", mobile);
//                intent.putExtra("userName", userName);
//                intent.putExtra("userPassword", userPassword);
//                startActivity(intent);

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
