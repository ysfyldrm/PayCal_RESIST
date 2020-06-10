package com.grapesoftware.paycal_resist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;


public class VerifyPhoneNo extends AppCompatActivity {
    String verificationCodeBySystem;
    Button verify_btn,lock_btn;
    EditText phoneNoEnteredByTheUser,verificationCodeEnteredByTheUser;
    ProgressBar progressBar;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    String _USERNAME,_NAME,_EMAIL,_PHONENO,_PASSWORD;
    int _COUNTER;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationCodeBySystem=s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if (code!=null){
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneNo.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_no);

        verify_btn =findViewById(R.id.verify_btn);
        lock_btn=findViewById(R.id.lock_btn);
        verificationCodeEnteredByTheUser =findViewById(R.id.verification_code_entered_by_user);
        phoneNoEnteredByTheUser=findViewById(R.id.phone_no_fromuser);
        progressBar =findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        preferences=getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
        editor=preferences.edit();
        _NAME=preferences.getString("name","");
        _USERNAME=preferences.getString("username","");
        _EMAIL=preferences.getString("email","");
        _PHONENO=preferences.getString("phoneNo","");
        _PASSWORD=preferences.getString("password","");
        _COUNTER=preferences.getInt("counter",0);

        verificationCodeEnteredByTheUser.setEnabled(false);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        lock_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCodeToUser(phoneNoEnteredByTheUser.getText().toString());
                phoneNoEnteredByTheUser.setEnabled(false);
                verificationCodeEnteredByTheUser.setEnabled(true);
                lock_btn.setVisibility(View.GONE);
            }
        });

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code= verificationCodeEnteredByTheUser.getText().toString();
                if (code.isEmpty()||code.length()<6){
                    verificationCodeEnteredByTheUser.setError("Wrong Code...");
                    verificationCodeEnteredByTheUser.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
                finish();
            }
        });



// Attach a listener to read the data at our posts reference
        reference.child(_USERNAME).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserHelperClass userHelperClass = dataSnapshot.getValue(UserHelperClass.class);
                _COUNTER=userHelperClass.getCounter();
                editor.putInt("counter",_COUNTER);
                editor.commit();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void sendVerificationCodeToUser(String phoneNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+90"+phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    private void verifyCode(String codeByUser) {

        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationCodeBySystem,codeByUser);

//        UserHelperClass helperClass = new UserHelperClass(_NAME, _USERNAME, _EMAIL, _PHONENO, _PASSWORD,0);
//        reference.child(_USERNAME).setValue(helperClass);



        signInTheUserByCredentials(credential);



    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyPhoneNo.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent= new Intent(getApplicationContext(), StartActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            editor.putString("name",_NAME);
                            editor.putString("username",_USERNAME);
                            editor.putString("email",_EMAIL);
                            editor.putString("phoneNo",_PHONENO);
                            editor.putString("password",_PASSWORD);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(VerifyPhoneNo.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
