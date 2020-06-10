package com.grapesoftware.paycal_resist;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button callSignUp,login_btn,forgetpass_btn;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout username,password;
    int _COUNTER;
    String _USERNAME;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    public void onBackPressed() {
        recreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        callSignUp = findViewById(R.id.signup_screen);
        image=findViewById(R.id.logo_image);
        logoText=findViewById(R.id.logo_name);
        sloganText=findViewById(R.id.sLogan_name);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login_btn=findViewById(R.id.Login_btn);
        forgetpass_btn=findViewById(R.id.forgetpass_btn);
        preferences=getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
        editor=preferences.edit();

        forgetpass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),VerifyPhoneNo.class);
                startActivity(intent);
            }
        });

        _USERNAME=preferences.getString("username","");

        if (_USERNAME.length()>0){
            username.getEditText().setText(_USERNAME);
        }


        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Login.this,SignUp.class);
                Pair[] pairs=new Pair[7];
                pairs[0]=new Pair<View,String>(image,"logo_image");
                pairs[1]=new Pair<View,String>(logoText,"logo_text");
                pairs[2]=new Pair<View,String>(sloganText,"logo_desc");
                pairs[3]=new Pair<View,String>(username,"username_tran");
                pairs[4]=new Pair<View,String>(password,"password_tran");
                pairs[5]=new Pair<View,String>(login_btn,"button_tran");
                pairs[6]=new Pair<View,String>(callSignUp,"login_signup_tran");


                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                    startActivity(intent,options.toBundle());
                }
            }
        });


    }

    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString();
        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
    public void loginUser(View view) {
        //Validate Login Info
        if (!validateUsername() | !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }
    private void isUser() {
        final String userEnteredUsername = username.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    username.setError(null);
                    username.setErrorEnabled(false);
                    password.setError(null);
                    password.setErrorEnabled(false);

                    String passwordFromDB=dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    if(passwordFromDB.equals(userEnteredPassword)){

                        String nameFromDB=dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB=dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneNoFromDB=dataSnapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);
                        String emailFromDB=dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);
                        _COUNTER=dataSnapshot.child(userEnteredUsername).child("counter").getValue(int.class);

                        Intent intent=new Intent(getApplicationContext(),StartActivity.class);

                        editor.putString("name",nameFromDB);
                        editor.putString("username",usernameFromDB);
                        editor.putString("phoneNo",phoneNoFromDB);
                        editor.putString("email",emailFromDB);
                        editor.putString("password",passwordFromDB);
                        editor.putInt("counter",_COUNTER);
                        editor.commit();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }
                else{
                    username.setError("No such user exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
