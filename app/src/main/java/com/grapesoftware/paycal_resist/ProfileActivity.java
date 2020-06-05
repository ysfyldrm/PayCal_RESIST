package com.grapesoftware.paycal_resist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.content.DialogInterface;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {
    Button backButton;
    private TextView userNameTxt,detailstxt;
    private Button changeEmailBttn;
    private Button changePasswordBttn;
    private Button signOutBttn;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener authListener;
    private String str;
    Long creationDate,lastLoginDate;

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); // get current user

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) { // when auth state change
                    finish();
                    Intent intent= new Intent(ProfileActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        };

        userNameTxt = (TextView)findViewById(R.id.userNameTxt);
        changeEmailBttn = (Button) findViewById(R.id.changeEmailBttn2);
        changePasswordBttn = (Button)findViewById(R.id.changePasswordBttn);
        signOutBttn = (Button)findViewById(R.id.signOutBttn);
        detailstxt=findViewById(R.id.detailstxt);

        backButton=findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                onBackPressed();

            }
        });


        creationDate=auth.getCurrentUser().getMetadata().getCreationTimestamp();
        lastLoginDate=auth.getCurrentUser().getMetadata().getLastSignInTimestamp();
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(creationDate);
        String dateforcreation = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
        cal.setTimeInMillis(lastLoginDate);
        String dateforlastlogin=DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();

        userNameTxt.setText(auth.getCurrentUser().getEmail());
        detailstxt.setText("Account Creation Date: "+dateforcreation+"\nAccount Last Login Date: "+dateforlastlogin);

        signOutBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutFunc(); // sign out
            }
        });

        changeEmailBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = "Please type new mail";
                changeEmailOrPasswordFunc(str,true);
            }
        });

        changePasswordBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = "Please set your new password";
                changeEmailOrPasswordFunc(str,false);
            }
        });

    }

    private void signOutFunc() {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    private void changeEmailOrPasswordFunc(String title, final boolean option) {

        AlertDialog.Builder builder = new AlertDialog.Builder(
                ProfileActivity.this);
        final EditText edit = new EditText(ProfileActivity.this);
        builder.setPositiveButton(getString(R.string.change_txt), null);
        builder.setNegativeButton(getString(R.string.close_txt), null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        edit.setLayoutParams(lp);
        if(!option){  // password type
            edit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        builder.setTitle(title);
        builder.setView(edit);

        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        if(edit.getText().toString().isEmpty()){

                            edit.setError("This field cannot be empty");

                        }else{

                            if(option){ // email change

                                changeEmail();

                            }else{  // password change

                                changePassword();

                            }
                        }

                    }
                });
            }

            private void changePassword() {

                firebaseUser.updatePassword(edit.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ProfileActivity.this, "Password has been changed", Toast.LENGTH_LONG).show();
                                    signOutFunc();
                                } else {
                                    edit.setText("");
                                    Toast.makeText(ProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }

            private void changeEmail() {

                firebaseUser.updateEmail(edit.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ProfileActivity.this, "E-Mail has been changed", Toast.LENGTH_LONG).show();
                                    signOutFunc();

                                } else {
                                    edit.setText("");
                                    Toast.makeText(ProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        mAlertDialog.show();

    }
}
