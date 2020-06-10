package com.grapesoftware.paycal_resist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {

    TextInputLayout fullName,email,phoneNo,password;
    TextView fullNameLabel,usernameLabel,counterLabel;
    String _USERNAME,_NAME,_EMAIL,_PHONENO,_PASSWORD;
    int _COUNTER;
    DatabaseReference reference;
    Button logout,back_btn;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        reference= FirebaseDatabase.getInstance().getReference("users");

        fullName=findViewById(R.id.full_name_profile);
        email=findViewById(R.id.email_profile);
        phoneNo=findViewById(R.id.phone_no_profile);
        password=findViewById(R.id.password_profile);
        fullNameLabel=findViewById(R.id.fullname_field);
        usernameLabel=findViewById(R.id.username_field);
        counterLabel=findViewById(R.id.counter_label);
        logout=findViewById(R.id.logout);
        back_btn=findViewById(R.id.backBtn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(UserProfile.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        showAllUserData();

    }

    private void showAllUserData() {


        preferences=getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
        editor=preferences.edit();
        _NAME=preferences.getString("name","");
        _USERNAME=preferences.getString("username","");
        _EMAIL=preferences.getString("email","");
        _PHONENO=preferences.getString("phoneNo","");
        _PASSWORD=preferences.getString("password","");
        _COUNTER=preferences.getInt("counter",0);



        fullNameLabel.setText(_NAME);
        usernameLabel.setText(_USERNAME);
        fullName.getEditText().setText(_NAME);
        email.getEditText().setText(_EMAIL);
        phoneNo.getEditText().setText(_PHONENO);
        password.getEditText().setText(_PASSWORD);
        counterLabel.setText(String.valueOf(_COUNTER));



    }

    public void update(View view){
        if(isNameChanged()||isPasswordChanged()||isMailChanged()|isPhoneChanged()){
            Toast.makeText(this,"Data has been updated",Toast.LENGTH_LONG).show();
            editor.putString("name",fullName.getEditText().getText().toString());
            editor.putString("email",email.getEditText().getText().toString());
            editor.putString("phoneNo",phoneNo.getEditText().getText().toString());
            editor.putString("password",password.getEditText().getText().toString());
            editor.commit();


        }
        else{
            Toast.makeText(this,"Data is same and can not be updated",Toast.LENGTH_LONG).show();
        }
    }
    private boolean isPhoneChanged() {
        if(!_PHONENO.equals(phoneNo.getEditText().getText().toString()))
        {
            reference.child(_USERNAME).child("phoneNo").setValue(phoneNo.getEditText().getText().toString());
            _PHONENO=phoneNo.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }
    private boolean isMailChanged() {
        if(!_EMAIL.equals(email.getEditText().getText().toString()))
        {
            reference.child(_USERNAME).child("email").setValue(email.getEditText().getText().toString());
            _EMAIL=email.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean isPasswordChanged(){
        if(!_PASSWORD.equals(password.getEditText().getText().toString()))
        {
            reference.child(_USERNAME).child("password").setValue(password.getEditText().getText().toString());
            _PASSWORD=password.getEditText().getText().toString();

            return true;
        }else{
            return false;
        }
    }
    private boolean isNameChanged(){
        if(!_NAME.equals(fullName.getEditText().getText().toString())){
            reference.child(_USERNAME).child("name").setValue(fullName.getEditText().getText().toString());
            _NAME=fullName.getEditText().getText().toString();
            fullNameLabel.setText(_NAME);
            return true;
        }else{
            return false;
        }

    }
}
