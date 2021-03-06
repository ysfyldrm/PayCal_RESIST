package com.grapesoftware.paycal_resist;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class TariffSelectionActivity extends AppCompatActivity {

    private Button epdkbtn, useownbtn, backbutton, profile;
    final Context context = this;
    Bundle gelentype;
    String typeforuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tariff_selection);

        epdkbtn = findViewById(R.id.epdk_button);
        useownbtn = findViewById(R.id.useOwn_button);
        backbutton = findViewById(R.id.backBtn);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TariffSelectionActivity.this, UserProfile.class);
                startActivity(intent);
                //finish();
            }
        });

        gelentype = getIntent().getExtras();
        typeforuser = gelentype.getString("Type");


        epdkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (typeforuser.equals("Consumer")) {
                    showMyCustomEPDKAlertDialog();
                } else {
                    showMyCustomEPDKAlertDialogPro();
                }
            }
        });


        useownbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (typeforuser.equals("Consumer")) {
                    showMyCustomOwnAlertDialog();
                } else {
                    showMyCustomOwnAlertDialogPro();
                }

            }
        });


    }

    //Bunu klassa çekelim her seferinde ayrı ayrı layoutlar için yazmak zorunda kalmayalım..
    public void showMyCustomEPDKAlertDialog() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.consumer_type);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button reslowvolt = dialog.findViewById(R.id.button_res_low_voltage);
        Button resmedvolt = dialog.findViewById(R.id.button_res_med_voltage);
        Button comlowvolt = dialog.findViewById(R.id.button_com_low_voltage);
        Button commedvolt = dialog.findViewById(R.id.button_com_med_voltage);
        Button indlowvolt = dialog.findViewById(R.id.button_ind_low_voltage);
        Button indmedvolt = dialog.findViewById(R.id.button_ind_med_voltage);


        reslowvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                {

                    final Dialog dialog1 = new Dialog(context);
                    dialog1.setContentView(R.layout.after_consumer_tariff);
                    dialog1.show();

                    Button confirm = dialog1.findViewById(R.id.wanted_data_confirm_button);
                    final EditText AvgConsMonth = dialog1.findViewById(R.id.cons_avgmonth_edt);
                    final EditText MorConsMonth = dialog1.findViewById(R.id.morning_cons_month_edt);
                    final EditText AvgMonthBill = dialog1.findViewById(R.id.avg_month_bill_edt);


                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (TextUtils.isEmpty(AvgConsMonth.getText().toString())) {
                                AvgConsMonth.setError("This field cannot be empty");
                                AvgConsMonth.requestFocus();
                                return;
                            } else if (TextUtils.isEmpty(MorConsMonth.getText().toString())) {
                                MorConsMonth.setError("This field cannot be empty");
                                MorConsMonth.requestFocus();
                                return;
                            } else if (TextUtils.isEmpty(AvgMonthBill.getText().toString())) {
                                AvgMonthBill.setError("This field cannot be empty");
                                AvgMonthBill.requestFocus();
                                return;
                            } else {

                                dialog1.dismiss();
                                Intent intent = new Intent(TariffSelectionActivity.this, ResTypeActivity.class);
                                Intent calculationIntent = new Intent(TariffSelectionActivity.this, CalculationActivity.class);

                                String morning = "0,08";
                                String peak = "0,12";
                                //String offpeak="0,37";
                                String tax = "0,24";
                                String avgconsmonth = AvgConsMonth.getText().toString();
                                String morconsmonth = MorConsMonth.getText().toString();
                                String avgmonthbill = AvgMonthBill.getText().toString();
                                SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);

                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Morning Tariff", morning);
                                editor.putString("Peak Tariff", peak);
                                //editor.putString("Off Peak Tariff",offpeak);
                                editor.putString("Tax", tax);
                                editor.putString("Cons Avg Month", avgconsmonth);
                                editor.putString("Morning Cons Month", morconsmonth);
                                editor.putString("Avg Month Bill", avgmonthbill);

                                editor.commit();

                                startActivity(intent);
                            }
                        }
                    });


                    Window window = dialog1.getWindow();
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                }

            }
        });

        resmedvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                {

                    final Dialog dialog1 = new Dialog(context);
                    dialog1.setContentView(R.layout.after_consumer_tariff);
                    dialog1.show();

                    Button confirm = dialog1.findViewById(R.id.wanted_data_confirm_button);
                    final EditText AvgConsMonth = dialog1.findViewById(R.id.cons_avgmonth_edt);
                    final EditText MorConsMonth = dialog1.findViewById(R.id.morning_cons_month_edt);
                    final EditText AvgMonthBill = dialog1.findViewById(R.id.avg_month_bill_edt);


                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (TextUtils.isEmpty(AvgConsMonth.getText().toString())) {
                                AvgConsMonth.setError("This field cannot be empty");
                                AvgConsMonth.requestFocus();
                                return;
                            } else if (TextUtils.isEmpty(MorConsMonth.getText().toString())) {
                                MorConsMonth.setError("This field cannot be empty");
                                MorConsMonth.requestFocus();
                                return;
                            } else if (TextUtils.isEmpty(AvgMonthBill.getText().toString())) {
                                AvgMonthBill.setError("This field cannot be empty");
                                AvgMonthBill.requestFocus();
                                return;
                            } else {
                                dialog1.dismiss();
                                Intent intent = new Intent(TariffSelectionActivity.this, ResTypeActivity.class);

                                String morning = "0,08";
                                String peak = "0,12";
                                //String offpeak="0,34";
                                String tax = "0,24";
                                String avgconsmonth = AvgConsMonth.getText().toString();
                                String morconsmonth = MorConsMonth.getText().toString();
                                String avgmonthbill = AvgMonthBill.getText().toString();

                                SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);

                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Morning Tariff", morning);
                                editor.putString("Peak Tariff", peak);
                                //editor.putString("Off Peak Tariff",offpeak);
                                editor.putString("Tax", tax);
                                editor.putString("Cons Avg Month", avgconsmonth);
                                editor.putString("Morning Cons Month", morconsmonth);
                                editor.putString("Avg Month Bill", avgmonthbill);

                                editor.commit();

                                startActivity(intent);
                            }
                        }
                    });


                    Window window = dialog1.getWindow();
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                }


            }
        });

        comlowvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {

                    final Dialog dialog1 = new Dialog(context);
                    dialog1.setContentView(R.layout.after_consumer_tariff);
                    dialog1.show();

                    Button confirm = dialog1.findViewById(R.id.wanted_data_confirm_button);
                    final EditText AvgConsMonth = dialog1.findViewById(R.id.cons_avgmonth_edt);
                    final EditText MorConsMonth = dialog1.findViewById(R.id.morning_cons_month_edt);
                    final EditText AvgMonthBill = dialog1.findViewById(R.id.avg_month_bill_edt);


                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (TextUtils.isEmpty(AvgConsMonth.getText().toString())) {
                                AvgConsMonth.setError("This field cannot be empty");
                                AvgConsMonth.requestFocus();
                                return;
                            } else if (TextUtils.isEmpty(MorConsMonth.getText().toString())) {
                                MorConsMonth.setError("This field cannot be empty");
                                MorConsMonth.requestFocus();
                                return;
                            } else if (TextUtils.isEmpty(AvgMonthBill.getText().toString())) {
                                AvgMonthBill.setError("This field cannot be empty");
                                AvgMonthBill.requestFocus();
                                return;
                            } else {
                                dialog1.dismiss();
                                Intent intent = new Intent(TariffSelectionActivity.this, ResTypeActivity.class);
                                Intent calculationIntent = new Intent(TariffSelectionActivity.this, CalculationActivity.class);

                                String morning = "0,11";
                                String peak = "0,16";
                                //String offpeak="0,49";
                                String tax = "0,24";
                                String avgconsmonth = AvgConsMonth.getText().toString();
                                String morconsmonth = MorConsMonth.getText().toString();
                                String avgmonthbill = AvgMonthBill.getText().toString();

                                SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Morning Tariff", morning);
                                editor.putString("Peak Tariff", peak);
                                //editor.putString("Off Peak Tariff",offpeak);
                                editor.putString("Tax", tax);
                                editor.putString("Cons Avg Month", avgconsmonth);
                                editor.putString("Morning Cons Month", morconsmonth);
                                editor.putString("Avg Month Bill", avgmonthbill);

                                editor.commit();

                                startActivity(intent);
                            }
                        }
                    });


                    Window window = dialog1.getWindow();
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                }
            }
        });

        commedvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {

                    final Dialog dialog1 = new Dialog(context);
                    dialog1.setContentView(R.layout.after_consumer_tariff);
                    dialog1.show();

                    Button confirm = dialog1.findViewById(R.id.wanted_data_confirm_button);
                    final EditText AvgConsMonth = dialog1.findViewById(R.id.cons_avgmonth_edt);
                    final EditText MorConsMonth = dialog1.findViewById(R.id.morning_cons_month_edt);
                    final EditText AvgMonthBill = dialog1.findViewById(R.id.avg_month_bill_edt);


                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (TextUtils.isEmpty(AvgConsMonth.getText().toString())) {
                                AvgConsMonth.setError("This field cannot be empty");
                                AvgConsMonth.requestFocus();
                                return;
                            } else if (TextUtils.isEmpty(MorConsMonth.getText().toString())) {
                                MorConsMonth.setError("This field cannot be empty");
                                MorConsMonth.requestFocus();
                                return;
                            } else if (TextUtils.isEmpty(AvgMonthBill.getText().toString())) {
                                AvgMonthBill.setError("This field cannot be empty");
                                AvgMonthBill.requestFocus();
                                return;
                            } else {
                                dialog1.dismiss();
                                Intent intent = new Intent(TariffSelectionActivity.this, ResTypeActivity.class);

                                String morning = "0,11";
                                String peak = "0,16";
                                //String offpeak="0,45";
                                String tax = "0,24";
                                String avgconsmonth = AvgConsMonth.getText().toString();
                                String morconsmonth = MorConsMonth.getText().toString();
                                String avgmonthbill = AvgMonthBill.getText().toString();
                                SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);

                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Morning Tariff", morning);
                                editor.putString("Peak Tariff", peak);
                                //editor.putString("Off Peak Tariff",offpeak);
                                editor.putString("Tax", tax);
                                editor.putString("Cons Avg Month", avgconsmonth);
                                editor.putString("Morning Cons Month", morconsmonth);
                                editor.putString("Avg Month Bill", avgmonthbill);

                                editor.commit();

                                startActivity(intent);
                            }
                        }
                    });


                    Window window = dialog1.getWindow();
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                }
            }
        });

        indlowvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {

                    final Dialog dialog1 = new Dialog(context);
                    dialog1.setContentView(R.layout.after_consumer_tariff);
                    dialog1.show();

                    Button confirm = dialog1.findViewById(R.id.wanted_data_confirm_button);
                    final EditText AvgConsMonth = dialog1.findViewById(R.id.cons_avgmonth_edt);
                    final EditText MorConsMonth = dialog1.findViewById(R.id.morning_cons_month_edt);
                    final EditText AvgMonthBill = dialog1.findViewById(R.id.avg_month_bill_edt);


                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (TextUtils.isEmpty(AvgConsMonth.getText().toString())) {
                                AvgConsMonth.setError("This field cannot be empty");
                                AvgConsMonth.requestFocus();
                                return;
                            } else if (TextUtils.isEmpty(MorConsMonth.getText().toString())) {
                                MorConsMonth.setError("This field cannot be empty");
                                MorConsMonth.requestFocus();
                                return;
                            } else if (TextUtils.isEmpty(AvgMonthBill.getText().toString())) {
                                AvgMonthBill.setError("This field cannot be empty");
                                AvgMonthBill.requestFocus();
                                return;
                            } else {
                                dialog1.dismiss();
                                Intent intent = new Intent(TariffSelectionActivity.this, ResTypeActivity.class);

                                String morning = "0,1";
                                String peak = "0,14";
                                //String offpeak="0,40";
                                String tax = "0,24";
                                String avgconsmonth = AvgConsMonth.getText().toString();
                                String morconsmonth = MorConsMonth.getText().toString();
                                String avgmonthbill = AvgMonthBill.getText().toString();
                                SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);

                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Morning Tariff", morning);
                                editor.putString("Peak Tariff", peak);
                                //editor.putString("Off Peak Tariff",offpeak);
                                editor.putString("Tax", tax);
                                editor.putString("Cons Avg Month", avgconsmonth);
                                editor.putString("Morning Cons Month", morconsmonth);
                                editor.putString("Avg Month Bill", avgmonthbill);

                                editor.commit();

                                startActivity(intent);
                            }
                        }
                    });


                    Window window = dialog1.getWindow();
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                }

            }
        });

        indmedvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {

                    final Dialog dialog1 = new Dialog(context);
                    dialog1.setContentView(R.layout.after_consumer_tariff);
                    dialog1.show();

                    Button confirm = dialog1.findViewById(R.id.wanted_data_confirm_button);
                    final EditText AvgConsMonth = dialog1.findViewById(R.id.cons_avgmonth_edt);
                    final EditText MorConsMonth = dialog1.findViewById(R.id.morning_cons_month_edt);
                    final EditText AvgMonthBill = dialog1.findViewById(R.id.avg_month_bill_edt);


                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (TextUtils.isEmpty(AvgConsMonth.getText().toString())) {
                                AvgConsMonth.setError("This field cannot be empty");
                                AvgConsMonth.requestFocus();
                                return;
                            } else if (TextUtils.isEmpty(MorConsMonth.getText().toString())) {
                                MorConsMonth.setError("This field cannot be empty");
                                MorConsMonth.requestFocus();
                                return;
                            } else if (TextUtils.isEmpty(AvgMonthBill.getText().toString())) {
                                AvgMonthBill.setError("This field cannot be empty");
                                AvgMonthBill.requestFocus();
                                return;
                            } else {
                                dialog1.dismiss();
                                Intent intent = new Intent(TariffSelectionActivity.this, ResTypeActivity.class);

                                String morning = "0,09";
                                String peak = "0,13";
                                //String offpeak="0,34";
                                String tax = "0,24";
                                String avgconsmonth = AvgConsMonth.getText().toString();
                                String morconsmonth = MorConsMonth.getText().toString();
                                String avgmonthbill = AvgMonthBill.getText().toString();
                                SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);

                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Morning Tariff", morning);
                                editor.putString("Peak Tariff", peak);
                                //editor.putString("Off Peak Tariff",offpeak);
                                editor.putString("Tax", tax);
                                editor.putString("Cons Avg Month", avgconsmonth);
                                editor.putString("Morning Cons Month", morconsmonth);
                                editor.putString("Avg Month Bill", avgmonthbill);

                                editor.commit();

                                startActivity(intent);
                            }
                        }
                    });


                    Window window = dialog1.getWindow();
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                }

            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    public void showMyCustomOwnAlertDialog() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.use_own_consumer);

        // custom dialog elemanlarını tanımla - text, image ve button

        final EditText morningTariff = dialog.findViewById(R.id.morningTariff_edt);
        final EditText peakTariff = dialog.findViewById(R.id.peakTariff_edt);
        final EditText taxx = dialog.findViewById(R.id.tax_edt);
        final EditText consavgMonth = dialog.findViewById(R.id.cons_avgmonth_edt);
        final EditText consyear = dialog.findViewById(R.id.cons_year_edt);
        final EditText morningconsmonth = dialog.findViewById(R.id.morning_cons_month_edt);
        final EditText avgmonthbill = dialog.findViewById(R.id.avg_month_bill_edt);
        final Button ownconfirmbtn = dialog.findViewById(R.id.own_confirm_button);

        // custom dialog elemanlarına değer ataması yap - text, image

        // tamam butonunun tıklanma olayları
        ownconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(morningTariff.getText().toString())) {
                    morningTariff.setError("This field cannot be empty");
                    morningTariff.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(peakTariff.getText().toString())) {
                    peakTariff.setError("This field cannot be empty");
                    peakTariff.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(taxx.getText().toString())) {
                    taxx.setError("This field cannot be empty");
                    taxx.requestFocus();
                    return;
                }
                String dtaxxvalue;
                dtaxxvalue = taxx.getText().toString().replace(",", ".");

                Double tax = ParseDouble(dtaxxvalue);

                if (tax > 100) {
                    taxx.setError("Tax value is wrong");
                    taxx.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(consavgMonth.getText().toString())) {
                    consavgMonth.setError("This field cannot be empty");
                    consavgMonth.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(consyear.getText().toString())) {
                    consyear.setError("This field cannot be empty");
                    consyear.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(morningconsmonth.getText().toString())) {
                    morningconsmonth.setError("This field cannot be empty");
                    morningconsmonth.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(avgmonthbill.getText().toString())) {
                    avgmonthbill.setError("This field cannot be empty");
                    avgmonthbill.requestFocus();
                    return;
                } else {
                    SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);

                    SharedPreferences.Editor editor = preferences.edit();

                    Double taxpercent = tax / 100;

                    editor.putString("Morning Tariff", morningTariff.getText().toString());
                    editor.putString("Peak Tariff", peakTariff.getText().toString());
                    editor.putString("Tax", taxpercent.toString());
                    editor.putString("Cons Avg Month", consavgMonth.getText().toString());
                    editor.putString("Cons Year", consyear.getText().toString());
                    editor.putString("Morning Cons Month", morningconsmonth.getText().toString());
                    editor.putString("Avg Month Bill", avgmonthbill.getText().toString());
                    editor.commit();


                    Intent intent = new Intent(TariffSelectionActivity.this, ResTypeActivity.class);
                    startActivity(intent);
                    //dialog.dismiss();


                }

            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    public void clickedMorphButton() {

    }

    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch (Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        } else return 0;
    }

    public void showMyCustomEPDKAlertDialogPro() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.prosumer_type);


        // custom dialog elemanlarını tanımla - text, image ve button
        Button reslowvolt = dialog.findViewById(R.id.button_res_low_voltage);
        Button resmedvolt = dialog.findViewById(R.id.button_res_med_voltage);
        Button comlowvolt = dialog.findViewById(R.id.button_com_low_voltage);
        Button commedvolt = dialog.findViewById(R.id.button_com_med_voltage);
        Button indlowvolt = dialog.findViewById(R.id.button_ind_low_voltage);
        Button indmedvolt = dialog.findViewById(R.id.button_ind_med_voltage);

        // custom dialog elemanlarına değer ataması yap - text, image


        reslowvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TariffSelectionActivity.this, ResTypeActivity.class);

                String morning = "0,08";
                String peak = "0,12";
                //String offpeak="0,37";
                String tax = "0,24";


                SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Morning Tariff", morning);
                editor.putString("Peak Tariff", peak);
                //editor.putString("Off Peak Tariff",offpeak);
                editor.putString("Tax", tax);
                editor.commit();
                startActivity(intent);


//                {
//
//                    final Dialog dialog1=new Dialog(context);
//                    dialog1.setContentView(R.layout.after_prosumer_tariff);
//                    dialog1.show();
//
//                    Button confirm=dialog1.findViewById(R.id.wanted_data_confirm_button);
//                    final EditText AvgConsMonth=dialog1.findViewById(R.id.cons_avgmonth_edt);
//                    final EditText MorConsMonth=dialog1.findViewById(R.id.morning_cons_month_edt);
//                    final EditText AvgMonthBill=dialog1.findViewById(R.id.avg_month_bill_edt);
//                    final EditText ResGenDaily=dialog1.findViewById(R.id.res_gen_daily_edt);
//                    final EditText ResGenMonthly=dialog1.findViewById(R.id.res_gen_mothly_edt);
//                    final EditText StoragePerc=dialog1.findViewById(R.id.storage_percent_edt);
//
//
//
//
//
//                    confirm.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Toast.makeText(getApplicationContext(),AvgConsMonth.getText()+"\n"+ResGenMonthly.getText()+"\n"+MorConsMonth.getText()+"\n"+StoragePerc.getText()+"\n"+AvgMonthBill.getText()+"\n"+ResGenDaily.getText(),Toast.LENGTH_LONG).show();
//                            dialog1.dismiss();
//                            Intent intent=new Intent(TariffSelectionActivity.this,ResTypeActivity.class);
//
//                            String morning="0,57";
//                            String peak="0,85";
//                            String offpeak="0,37";
//                            String tax="0,24";
//                            String avgconsmonth=AvgConsMonth.getText().toString();
//                            String morconsmonth=MorConsMonth.getText().toString();
//                            String avgmonthbill=AvgMonthBill.getText().toString();
//                            String resgendaily= ResGenDaily.getText().toString();
//                            String resgenmonthly=ResGenMonthly.getText().toString();
//                            String storageperc=StoragePerc.getText().toString();
//
//                            SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
//
//                            SharedPreferences.Editor editor = preferences.edit();
//                            editor.putString("Morning Tariff",morning);
//                            editor.putString("Peak Tariff",peak);
//                            editor.putString("Off Peak Tariff",offpeak);
//                            editor.putString("Tax",tax);
//                            editor.putString("Cons Avg Month",avgconsmonth);
//                            editor.putString("Morning Cons Month",morconsmonth);
//                            editor.putString("Avg Month Bill",avgmonthbill);
//                            editor.putString("RES Gen Daily",resgendaily);
//                            editor.putString("RES Gen Monthly",resgenmonthly);
//                            editor.putString("Storage Percentage",storageperc);
//
//                            editor.commit();
//
//                            startActivity(intent);
//                        }
//                    });
//
//
//
//
//
//                    Window window = dialog1.getWindow();
//                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//                }
            }
        });

        resmedvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TariffSelectionActivity.this, ResTypeActivity.class);

                String morning = "0,08";
                String peak = "0,12";
                //String offpeak="0,34";
                String tax = "0,24";
                SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Morning Tariff", morning);
                editor.putString("Peak Tariff", peak);
                //editor.putString("Off Peak Tariff",offpeak);
                editor.putString("Tax", tax);
                editor.commit();
                startActivity(intent);

//                {
//
//                    final Dialog dialog1=new Dialog(context);
//                    dialog1.setContentView(R.layout.after_prosumer_tariff);
//                    dialog1.show();
//
//                    Button confirm=dialog1.findViewById(R.id.wanted_data_confirm_button);
//                    final EditText AvgConsMonth=dialog1.findViewById(R.id.cons_avgmonth_edt);
//                    final EditText MorConsMonth=dialog1.findViewById(R.id.morning_cons_month_edt);
//                    final EditText AvgMonthBill=dialog1.findViewById(R.id.avg_month_bill_edt);
//                    final EditText ResGenDaily=dialog1.findViewById(R.id.res_gen_daily_edt);
//                    final EditText ResGenMonthly=dialog1.findViewById(R.id.res_gen_mothly_edt);
//                    final EditText StoragePerc=dialog1.findViewById(R.id.storage_percent_edt);
//
//
//                    confirm.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dialog1.dismiss();
//                            Intent intent=new Intent(TariffSelectionActivity.this,ResTypeActivity.class);
//                            Intent calculationIntent=new Intent(TariffSelectionActivity.this,CalculationActivity.class);
//
//                            String morning="0,54";
//                            String peak="0,82";
//                            String offpeak="0,34";
//                            String tax="0,24";
//                            String avgconsmonth=AvgConsMonth.getText().toString();
//                            String morconsmonth=MorConsMonth.getText().toString();
//                            String avgmonthbill=AvgMonthBill.getText().toString();
//                            String resgendaily= ResGenDaily.getText().toString();
//                            String resgenmonthly=ResGenMonthly.getText().toString();
//                            String storageperc=StoragePerc.getText().toString();
//
//                            SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
//
//                            SharedPreferences.Editor editor = preferences.edit();
//
//                            editor.putString("Morning Tariff",morning);
//                            editor.putString("Peak Tariff",peak);
//                            editor.putString("Off Peak Tariff",offpeak);
//                            editor.putString("Tax",tax);
//                            editor.putString("Cons Avg Month",avgconsmonth);
//                            editor.putString("Morning Cons Month",morconsmonth);
//                            editor.putString("Avg Month Bill",avgmonthbill);
//                            editor.putString("RES Gen Daily",resgendaily);
//                            editor.putString("RES Gen Monthly",resgenmonthly);
//                            editor.putString("Storage Percentage",storageperc);
//
//                            editor.commit();
//                            startActivity(intent);
//                        }
//                    });
//
//
//
//
//
//                    Window window = dialog1.getWindow();
//                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//                }
            }
        });

        comlowvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TariffSelectionActivity.this, ResTypeActivity.class);

                String morning = "0,11";
                String peak = "0,16";
                //String offpeak="0,49";
                String tax = "0,24";
                SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Morning Tariff", morning);
                editor.putString("Peak Tariff", peak);
                //editor.putString("Off Peak Tariff",offpeak);
                editor.putString("Tax", tax);
                editor.commit();
                startActivity(intent);

//                {
//
//                    final Dialog dialog1=new Dialog(context);
//                    dialog1.setContentView(R.layout.after_prosumer_tariff);
//                    dialog1.show();
//
//                    Button confirm=dialog1.findViewById(R.id.wanted_data_confirm_button);
//                    final EditText AvgConsMonth=dialog1.findViewById(R.id.cons_avgmonth_edt);
//                    final EditText MorConsMonth=dialog1.findViewById(R.id.morning_cons_month_edt);
//                    final EditText AvgMonthBill=dialog1.findViewById(R.id.avg_month_bill_edt);
//                    final EditText ResGenDaily=dialog1.findViewById(R.id.res_gen_daily_edt);
//                    final EditText ResGenMonthly=dialog1.findViewById(R.id.res_gen_mothly_edt);
//                    final EditText StoragePerc=dialog1.findViewById(R.id.storage_percent_edt);
//
//
//                    confirm.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dialog1.dismiss();
//                            Intent intent=new Intent(TariffSelectionActivity.this,ResTypeActivity.class);
//                            Intent calculationIntent=new Intent(TariffSelectionActivity.this,CalculationActivity.class);
//
//                            String morning="0,76";
//                            String peak="1,11";
//                            String offpeak="0,49";
//                            String tax="0,2366";
//                            String avgconsmonth=AvgConsMonth.getText().toString();
//                            String morconsmonth=MorConsMonth.getText().toString();
//                            String avgmonthbill=AvgMonthBill.getText().toString();
//                            String resgendaily= ResGenDaily.getText().toString();
//                            String resgenmonthly=ResGenMonthly.getText().toString();
//                            String storageperc=StoragePerc.getText().toString();
//
//                            SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
//
//                            SharedPreferences.Editor editor = preferences.edit();
//                            editor.putString("Morning Tariff",morning);
//                            editor.putString("Peak Tariff",peak);
//                            editor.putString("Off Peak Tariff",offpeak);
//                            editor.putString("Tax",tax);
//                            editor.putString("Cons Avg Month",avgconsmonth);
//                            editor.putString("Morning Cons Month",morconsmonth);
//                            editor.putString("Avg Month Bill",avgmonthbill);
//                            editor.putString("RES Gen Daily",resgendaily);
//                            editor.putString("RES Gen Monthly",resgenmonthly);
//                            editor.putString("Storage Percentage",storageperc);
//
//                            editor.commit();
//
//                            startActivity(intent);
//                        }
//                    });
//
//
//
//
//
//                    Window window = dialog1.getWindow();
//                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//                }
            }
        });

        commedvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TariffSelectionActivity.this, ResTypeActivity.class);

                String morning = "0,11";
                String peak = "0,16";
                //String offpeak="0,45";
                String tax = "0,24";
                SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Morning Tariff", morning);
                editor.putString("Peak Tariff", peak);
                //editor.putString("Off Peak Tariff",offpeak);
                editor.putString("Tax", tax);
                editor.commit();
                startActivity(intent);


//                {
//
//                    final Dialog dialog1=new Dialog(context);
//                    dialog1.setContentView(R.layout.after_prosumer_tariff);
//                    dialog1.show();
//
//                    Button confirm=dialog1.findViewById(R.id.wanted_data_confirm_button);
//                    final EditText AvgConsMonth=dialog1.findViewById(R.id.cons_avgmonth_edt);
//                    final EditText MorConsMonth=dialog1.findViewById(R.id.morning_cons_month_edt);
//                    final EditText AvgMonthBill=dialog1.findViewById(R.id.avg_month_bill_edt);
//                    final EditText ResGenDaily=dialog1.findViewById(R.id.res_gen_daily_edt);
//                    final EditText ResGenMonthly=dialog1.findViewById(R.id.res_gen_mothly_edt);
//                    final EditText StoragePerc=dialog1.findViewById(R.id.storage_percent_edt);
//
//
//                    confirm.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dialog1.dismiss();
//                            Intent intent=new Intent(TariffSelectionActivity.this,ResTypeActivity.class);
//                            Intent calculationIntent=new Intent(TariffSelectionActivity.this,CalculationActivity.class);
//
//                            String morning="0,72";
//                            String peak="1,07";
//                            String offpeak="0,45";
//                            String tax="0,2366";
//                            String avgconsmonth=AvgConsMonth.getText().toString();
//                            String morconsmonth=MorConsMonth.getText().toString();
//                            String avgmonthbill=AvgMonthBill.getText().toString();
//                            String resgendaily= ResGenDaily.getText().toString();
//                            String resgenmonthly=ResGenMonthly.getText().toString();
//                            String storageperc=StoragePerc.getText().toString();
//
//                            SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
//
//                            SharedPreferences.Editor editor = preferences.edit();
//                            editor.putString("Morning Tariff",morning);
//                            editor.putString("Peak Tariff",peak);
//                            editor.putString("Off Peak Tariff",offpeak);
//                            editor.putString("Tax",tax);
//                            editor.putString("Cons Avg Month",avgconsmonth);
//                            editor.putString("Morning Cons Month",morconsmonth);
//                            editor.putString("Avg Month Bill",avgmonthbill);
//                            editor.putString("RES Gen Daily",resgendaily);
//                            editor.putString("RES Gen Monthly",resgenmonthly);
//                            editor.putString("Storage Percentage",storageperc);
//
//                            editor.commit();
//
//                            startActivity(intent);
//                        }
//                    });
//
//
//
//
//
//                    Window window = dialog1.getWindow();
//                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//                }
            }
        });

        indlowvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TariffSelectionActivity.this, ResTypeActivity.class);

                String morning = "0,1";
                String peak = "0,14";
                //String offpeak="0,40";
                String tax = "0,24";
                SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Morning Tariff", morning);
                editor.putString("Peak Tariff", peak);
                //editor.putString("Off Peak Tariff",offpeak);
                editor.putString("Tax", tax);
                editor.commit();
                startActivity(intent);

//                {
//
//                    final Dialog dialog1=new Dialog(context);
//                    dialog1.setContentView(R.layout.after_prosumer_tariff);
//                    dialog1.show();
//
//                    Button confirm=dialog1.findViewById(R.id.wanted_data_confirm_button);
//                    final EditText AvgConsMonth=dialog1.findViewById(R.id.cons_avgmonth_edt);
//                    final EditText MorConsMonth=dialog1.findViewById(R.id.morning_cons_month_edt);
//                    final EditText AvgMonthBill=dialog1.findViewById(R.id.avg_month_bill_edt);
//                    final EditText ResGenDaily=dialog1.findViewById(R.id.res_gen_daily_edt);
//                    final EditText ResGenMonthly=dialog1.findViewById(R.id.res_gen_mothly_edt);
//                    final EditText StoragePerc=dialog1.findViewById(R.id.storage_percent_edt);
//
//
//                    confirm.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dialog1.dismiss();
//                            Intent intent=new Intent(TariffSelectionActivity.this,ResTypeActivity.class);
//                            Intent calculationIntent=new Intent(TariffSelectionActivity.this,CalculationActivity.class);
//
//                            String morning="0,66";
//                            String peak="0,99";
//                            String offpeak="0,40";
//                            String tax="0,2366";
//                            String avgconsmonth=AvgConsMonth.getText().toString();
//                            String morconsmonth=MorConsMonth.getText().toString();
//                            String avgmonthbill=AvgMonthBill.getText().toString();
//                            String resgendaily= ResGenDaily.getText().toString();
//                            String resgenmonthly=ResGenMonthly.getText().toString();
//                            String storageperc=StoragePerc.getText().toString();
//
//                            SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
//
//                            SharedPreferences.Editor editor = preferences.edit();
//                            editor.putString("Morning Tariff",morning);
//                            editor.putString("Peak Tariff",peak);
//                            editor.putString("Off Peak Tariff",offpeak);
//                            editor.putString("Tax",tax);
//                            editor.putString("Cons Avg Month",avgconsmonth);
//                            editor.putString("Morning Cons Month",morconsmonth);
//                            editor.putString("Avg Month Bill",avgmonthbill);
//                            editor.putString("RES Gen Daily",resgendaily);
//                            editor.putString("RES Gen Monthly",resgenmonthly);
//                            editor.putString("Storage Percentage",storageperc);
//
//                            editor.commit();
//
//                            startActivity(intent);
//                        }
//                    });
//
//
//
//
//
//                    Window window = dialog1.getWindow();
//                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//                }

            }
        });

        indmedvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TariffSelectionActivity.this, ResTypeActivity.class);

                String morning = "0,09";
                String peak = "0,13";
                //String offpeak="0,34";
                String tax = "0,24";
                SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Morning Tariff", morning);
                editor.putString("Peak Tariff", peak);
                //editor.putString("Off Peak Tariff",offpeak);
                editor.putString("Tax", tax);
                editor.commit();
                startActivity(intent);

//                {
//
//                    final Dialog dialog1=new Dialog(context);
//                    dialog1.setContentView(R.layout.after_prosumer_tariff);
//                    dialog1.show();
//
//                    Button confirm=dialog1.findViewById(R.id.wanted_data_confirm_button);
//                    final EditText AvgConsMonth=dialog1.findViewById(R.id.cons_avgmonth_edt);
//                    final EditText MorConsMonth=dialog1.findViewById(R.id.morning_cons_month_edt);
//                    final EditText AvgMonthBill=dialog1.findViewById(R.id.avg_month_bill_edt);
//                    final EditText ResGenDaily=dialog1.findViewById(R.id.res_gen_daily_edt);
//                    final EditText ResGenMonthly=dialog1.findViewById(R.id.res_gen_mothly_edt);
//                    final EditText StoragePerc=dialog1.findViewById(R.id.storage_percent_edt);
//
//                    confirm.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dialog1.dismiss();
//                            Intent intent=new Intent(TariffSelectionActivity.this,ResTypeActivity.class);
//                            Intent calculationIntent=new Intent(TariffSelectionActivity.this,CalculationActivity.class);
//
//                            String morning="0,60";
//                            String peak="0,92";
//                            String offpeak="0,34";
//                            String tax="0,2366";
//                            String avgconsmonth=AvgConsMonth.getText().toString();
//                            String morconsmonth=MorConsMonth.getText().toString();
//                            String avgmonthbill=AvgMonthBill.getText().toString();
//                            String resgendaily= ResGenDaily.getText().toString();
//                            String resgenmonthly=ResGenMonthly.getText().toString();
//                            String storageperc=StoragePerc.getText().toString();
//
//                            SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
//
//                            SharedPreferences.Editor editor = preferences.edit();
//                            editor.putString("Morning Tariff",morning);
//                            editor.putString("Peak Tariff",peak);
//                            editor.putString("Off Peak Tariff",offpeak);
//                            editor.putString("Tax",tax);
//                            editor.putString("Cons Avg Month",avgconsmonth);
//                            editor.putString("Morning Cons Month",morconsmonth);
//                            editor.putString("Avg Month Bill",avgmonthbill);
//                            editor.putString("RES Gen Daily",resgendaily);
//                            editor.putString("RES Gen Monthly",resgenmonthly);
//                            editor.putString("Storage Percentage",storageperc);
//
//                            editor.commit();
//
//                            startActivity(intent);
//                        }
//                    });
//                    Window window = dialog1.getWindow();
//                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//                }

            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }


    public void showMyCustomOwnAlertDialogPro() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.use_own_prosumer);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button ownconfirmbtn = dialog.findViewById(R.id.own_confirm_button);
        final EditText morning = dialog.findViewById(R.id.morningTariff_edt);
        final EditText peak = dialog.findViewById(R.id.peakTariff_edt);
        final EditText taxx = dialog.findViewById(R.id.tax_edt);


        // custom dialog elemanlarına değer ataması yap - text, image


        // tamam butonunun tıklanma olayları
        ownconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(morning.getText().toString())) {
                    morning.setError("This field cannot be empty");
                    morning.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(peak.getText().toString())) {
                    peak.setError("This field cannot be empty");
                    peak.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(taxx.getText().toString())) {
                    taxx.setError("This field cannot be empty");
                    taxx.requestFocus();
                    return;
                }

                String dtaxxvalue;
                dtaxxvalue = taxx.getText().toString().replace(",", ".");
                Double tax = ParseDouble(dtaxxvalue);

                if (tax > 100) {
                    taxx.setError("Tax value is wrong");
                    taxx.requestFocus();
                    return;
                } else {
                    dialog.dismiss();
                    Intent intent = new Intent(TariffSelectionActivity.this, ResTypeActivity.class);
                    SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);

                    Double taxpercent = tax / 100;
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Morning Tariff", morning.getText().toString());
                    editor.putString("Peak Tariff", peak.getText().toString());
                    editor.putString("Tax", taxpercent.toString());

                    editor.commit();
                    startActivity(intent);
                }
            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }
}
