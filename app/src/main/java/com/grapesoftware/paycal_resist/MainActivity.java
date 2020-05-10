package com.grapesoftware.paycal_resist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button startButton;
    private TextView mTextViewResult;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            Toast.makeText(getApplicationContext(), "Giriş Yapınız.", Toast.LENGTH_SHORT).show();
        }

        mTextViewResult =findViewById(R.id.text_view_result);
        Button startButton=findViewById(R.id.btn_basla);

        mQueue= Volley.newRequestQueue(this);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
            }
        });

//        findViewById(R.id.btn_basla).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//            }
//        });


        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                jsonParse();
//                FirebaseAuth.getInstance().signOut();
//
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
            }
        });

    }

    private void jsonParse() {

        String url="https://power.larc.nasa.gov/cgi-bin/v1/DataAccess.py?&request=execute&tempAverage=CLIMATOLOGY&identifier=SinglePoint&parameters=ALLSKY_SFC_LW_DWN,WS50M,WS10M&userCommunity=AG&lon=38.500&lat=27.065&outputList=JSON&siteElev=50&user=DOCUMENTATION";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String deneme;
                try {


                    JSONArray jsonArraydeneme=response.getJSONArray("features");

                    for (int i=0;i<jsonArraydeneme.length();i++){
                        JSONArray jsonArraydeneme2=response.getJSONArray("properties");
                        for (int j=0;j<jsonArraydeneme2.length();j++){
                            JSONObject parameter =jsonArraydeneme.getJSONObject(j);
                            deneme=parameter.getString("parameter");
                            mTextViewResult.append(deneme);
                            Toast.makeText(MainActivity.this,deneme,Toast.LENGTH_LONG).show();
Log.e("HT",deneme);
Log.e("JS",parameter.toString());
                        }
;


//                    for (int i=0;i<jsonArraydeneme.length();i++){
//                        JSONObject features =jsonArraydeneme.getJSONObject(i);
//                        String deneme=features.getString("properties");
//                        mTextViewResult.append(deneme);
                        Toast.makeText(MainActivity.this,mTextViewResult.getText(), Toast.LENGTH_LONG).show();
//                    JSONArray jsonArray1=response.getJSONArray("ALLSKY_SFC_LW_DWN");
//
//                    for (int i=0; i<jsonArray1.length();i++){
//                        JSONObject ALLSKY_SFC_LW_DWN =jsonArray1.getJSONObject(i);
//                        int a1=ALLSKY_SFC_LW_DWN.getInt("1");
//                        int a10=ALLSKY_SFC_LW_DWN.getInt("10");
//                        int a11=ALLSKY_SFC_LW_DWN.getInt("11");
//                        int a12=ALLSKY_SFC_LW_DWN.getInt("12");
//                        int a13=ALLSKY_SFC_LW_DWN.getInt("13");
//                        int a2=ALLSKY_SFC_LW_DWN.getInt("2");
//                        int a3=ALLSKY_SFC_LW_DWN.getInt("3");
//                        int a4=ALLSKY_SFC_LW_DWN.getInt("4");
//                        int a5=ALLSKY_SFC_LW_DWN.getInt("5");
//                        int a6=ALLSKY_SFC_LW_DWN.getInt("6");
//                        int a7=ALLSKY_SFC_LW_DWN.getInt("7");
//                        int a8=ALLSKY_SFC_LW_DWN.getInt("8");
//                        int a9=ALLSKY_SFC_LW_DWN.getInt("9");
//
//mTextViewResult.append(String.valueOf(a1)+","+String.valueOf(a2)+","+String.valueOf(a3)+","+String.valueOf(a4)+","+String.valueOf(a5)+","+String.valueOf(a6)+","+String.valueOf(a7)+","+String.valueOf(a8)+","+String.valueOf(a9)+","+String.valueOf(a10)+","+String.valueOf(a11)+","+String.valueOf(a12)+","+String.valueOf(a13));
//                        Log.w("deneme",mTextViewResult.getText());

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray jsonArray2=response.getJSONArray("WS10M");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray jsonArray3=response.getJSONArray("WS50M");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             error.printStackTrace();
            }
        });
mQueue.add(request);
    }


}