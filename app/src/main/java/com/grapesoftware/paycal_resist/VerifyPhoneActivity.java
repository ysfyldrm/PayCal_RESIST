package com.grapesoftware.paycal_resist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.text.BreakIterator;
import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {


    //Kullanıcıya gönderdigimiz aktivasyon kodu
    private String mVerificationId;

    private EditText editTextCode;

    private FirebaseAuth mAuth;

    private TextView mobileText;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);
        editTextCode = findViewById(R.id.editTextCode);
        mobileText = findViewById(R.id.mobileTextViev);

        editTextCode.setTransformationMethod(null);

        //Diger activity sayfasından aldıgımız mobile numarası
        Intent intent = getIntent();
        String mobile = intent.getStringExtra("mobile");
        mobileText.setText("+90" + mobile);
        sendVerificationCode(mobile);


        //Eger otomatik sms algılayıcı calısmazsa
        findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = editTextCode.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    editTextCode.setError("Enter valid code");
                    editTextCode.requestFocus();
                    return;
                }

                verifyVerificationCode(code);
            }
        });

    }


    //Ülke kodu +90 olarak girili, aktivasyon kodunu bu metod gönderiyor
    private void sendVerificationCode(String mobile) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+90" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }


    //geridönüsü kontrol ettigimiz yer
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Sms olarak gelen kodu cagırma
            String code = phoneAuthCredential.getSmsCode();

            if (code != null) {
                editTextCode.setText(code);
                verifyVerificationCode(code);

            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            //Kullanıcıya gönderdigimiz kodu s olarak tutuyoruz
            mVerificationId = s;
        }
    };


    private void verifyVerificationCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyPhoneActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //Onaylandı ve giris sayfasına gidiyoruz
                            Intent intent = new Intent(VerifyPhoneActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {

                            //Onaylanmadı ve hata mesajı gösteriliyor

                            Toast.makeText(VerifyPhoneActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}

