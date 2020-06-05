package com.grapesoftware.paycal_resist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import github.ishaan.buttonprogressbar.ButtonProgressBar;


public class ChartsActivity extends AppCompatActivity {
    String typeforuser, storagetype, restype, payback, winddaykwh, windmonthkwh, windyearkwh, windyearlycost, windcapitalcost, newavgmonthbill, storagecapacity, storagecapitalcost, storagepercentage, turbinecount, turbinetype, address, country, area, pvprofityear, pvgenavgday, pvgenyear, pvnum, pvpower, pvcapitalcost, pvyearlycost, turbineprice, yearlyprofit, systemprofit, yearlystoragecost;
    LineChart mpLineChart;
    Float[] cashFloat = new Float[24];
    private int currentIndex = 0;
    int kırmızı = 255, yesil = 0, mavi = 0;
    final Context context = this;
    LinearLayout content;
    TextView title1, title2, title3, title4, title5, title6, title7, title8, title9, title10, title11, title12, title13, output1, output2, output3, output4, output5, output6, output7, output8, output9, output10, output11, output12, output13;
    Button button, backbutton, profile;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Document document;
    String filename, filenamepdf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        int countkırmızı = 0, countyesil = 0, sarı = 4;
        int gecis1, gecis2;
        int renk1, renk2;
        int[] colorArray = new int[24];

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
                Intent intent = new Intent(ChartsActivity.this, ProfileActivity.class);
                startActivity(intent);
                //finish();
            }
        });


        final ButtonProgressBar bar = (ButtonProgressBar) findViewById(R.id.bpb_main);
        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.startLoader();
                bar.setProgressColor(Color.parseColor("#157de6"));
                RelativeLayout savingLayout = (RelativeLayout) findViewById(R.id.save_layout);
                File file = saveBitMap(ChartsActivity.this, savingLayout);

                if (file != null) {
                    Log.i("TAG", "Drawing saved to the gallery!");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bar.stopLoader();
                        }
                    }, 1800);

                } else {
                    Log.i("TAG", "Oops! Image could not be saved.");
                }

                // Will run the conversion in another thread to avoid the UI to be frozen
                Thread t = new Thread() {
                    public void run() {
                        // Input file
                        String inputPath = filename;

                        // Output file
                        String outputPath = Environment.getExternalStorageDirectory() + File.separator + filename + "PaycalPDF.pdf";

                        File pdfFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "PayCal_RESIST");
                        filenamepdf = pdfFileDir.getPath() + File.separator + "Results" + System.currentTimeMillis() + ".pdf";

                        // Run conversion
                        final boolean result = ChartsActivity.this.convertToPdf(inputPath, filenamepdf);

                        // Notify the UI
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if (!result)
                                    Toast.makeText(ChartsActivity.this, "An error occured while converting the JPG to PDF.", Toast.LENGTH_SHORT).show();

//                                if (result) Toast.makeText(ChartsActivity.this, "The JPG was successfully converted to PDF.", Toast.LENGTH_SHORT).show();
//                                else Toast.makeText(ChartsActivity.this, "An error occured while converting the JPG to PDF.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                };
                t.start();
            }
        });

        title1 = findViewById(R.id.result_1);
        title2 = findViewById(R.id.result_2);
        title3 = findViewById(R.id.result_3);
        title4 = findViewById(R.id.result_4);
        title5 = findViewById(R.id.result_5);
        title6 = findViewById(R.id.result_6);
        title7 = findViewById(R.id.result_7);
        title8 = findViewById(R.id.result_8);
        title9 = findViewById(R.id.result_9);
        title10 = findViewById(R.id.result_10);
        title11 = findViewById(R.id.result_11);
        title12 = findViewById(R.id.result_12);
        title13 = findViewById(R.id.result_13);
        output1 = findViewById(R.id.result_1_1);
        output2 = findViewById(R.id.result_2_2);
        output3 = findViewById(R.id.result_3_3);
        output4 = findViewById(R.id.result_4_4);
        output5 = findViewById(R.id.result_5_5);
        output6 = findViewById(R.id.result_6_6);
        output7 = findViewById(R.id.result_7_7);
        output8 = findViewById(R.id.result_8_8);
        output9 = findViewById(R.id.result_9_9);
        output10 = findViewById(R.id.result_10_10);
        output11 = findViewById(R.id.result_11_11);
        output12 = findViewById(R.id.result_12_12);
        output13 = findViewById(R.id.result_13_13);

        preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
        editor = preferences.edit();

        typeforuser = preferences.getString("Type", "Consumer");
        storagetype = preferences.getString("Storage Type", null);
        restype = preferences.getString("RES Type", "PV SOLAR");
        payback = preferences.getString("Payback", null);
        winddaykwh = preferences.getString("Wind Day Kwh", null);
        windmonthkwh = preferences.getString("Wind Month Kwh", null);
        windyearkwh = preferences.getString("Wind Year Kwh", null);
        windyearlycost = preferences.getString("Wind Yearly Cost", null);
        newavgmonthbill = preferences.getString("New Avg Month Bill", null);
        windcapitalcost = preferences.getString("Wind Capital Cost", null);
        storagecapacity = preferences.getString("Storage Capacity", null);
        storagecapitalcost = preferences.getString("Storage Capital Cost", null);
        storagepercentage = preferences.getString("Storage Percentage", null);
        turbinecount = preferences.getString("Turbine Count", "1.00");
        turbinetype = preferences.getString("Turbine Type", "1500.0");
        address = preferences.getString("Adress", null);
        country = preferences.getString("Country", null);
        pvprofityear = preferences.getString("PV Profit Year", null);
        pvgenavgday = preferences.getString("PV Gen Average Day", null);
        pvgenyear = preferences.getString("PV Gen Year", null);
        pvnum = preferences.getString("Panel Num", null);
        pvpower = preferences.getString("PV Power", null);
        area = preferences.getString("Area", null);
        pvcapitalcost = preferences.getString("PV Capital Cost", null);
        pvyearlycost = preferences.getString("PV Yearly Cost", null);
        turbineprice = preferences.getString("Price", null);
        yearlyprofit = preferences.getString("Yearly Profit", null);
        systemprofit = preferences.getString("System Profit", null);
        yearlystoragecost = preferences.getString("Yearly Storage Cost", null);


//        button = findViewById(R.id.buttonsave);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                RelativeLayout savingLayout = (RelativeLayout) findViewById(R.id.save_layout);
//                File file = saveBitMap(ChartsActivity.this, savingLayout);
//
//                if (file != null) {
//                    Log.i("TAG", "Drawing saved to the gallery!");
//
//                } else {
//                    Log.i("TAG", "Oops! Image could not be saved.");
//                }
//
//                // Will run the conversion in another thread to avoid the UI to be frozen
//                Thread t = new Thread() {
//                    public void run()
//                    {
//                        // Input file
//                        String inputPath = filename;
//
//                        // Output file
//                        String outputPath = Environment.getExternalStorageDirectory() + File.separator + "out.pdf";
//
//                        // Run conversion
//                        final boolean result = ChartsActivity.this.convertToPdf(inputPath, outputPath);
//
//                        // Notify the UI
//                        runOnUiThread(new Runnable() {
//                            public void run()
//                            {
//                                if (result) Toast.makeText(ChartsActivity.this, "The JPG was successfully converted to PDF.", Toast.LENGTH_SHORT).show();
//                                else Toast.makeText(ChartsActivity.this, "An error occured while converting the JPG to PDF.", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                };
//                t.start();
//            }
//        });

        SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);

        int colorArraykırmızı[] = {R.color.kırmızı1, R.color.kırmızı2, R.color.kırmızı3, R.color.kırmızı4, R.color.kırmızı5, R.color.kırmızı6, R.color.kırmızı7, R.color.kırmızı8, R.color.kırmızı9, R.color.kırmızı10, R.color.kırmızı11, R.color.kırmızı12, R.color.kırmızı13, R.color.kırmızı14, R.color.kırmızı15, R.color.kırmızı16, R.color.kırmızı17, R.color.kırmızı18, R.color.kırmızı19, R.color.kırmızı20, R.color.kırmızı21, R.color.kırmızı22, R.color.kırmızı23, R.color.kırmızı24};
        int colorArrayyesil[] = {R.color.yesil1, R.color.yesil2, R.color.yesil3, R.color.yesil4, R.color.yesil5, R.color.yesil6, R.color.yesil7, R.color.yesil8, R.color.yesil9, R.color.yesil10, R.color.yesil11, R.color.yesil12, R.color.yesil13, R.color.yesil14, R.color.yesil15, R.color.yesil16, R.color.yesil17, R.color.yesil18, R.color.yesil19, R.color.yesil20, R.color.yesil21, R.color.yesil22, R.color.yesil23, R.color.yesil24};


        int ikiser = 0, ücer = 0, dörder = 0;
        for (int i = 0; i < cashFloat.length; i++) {
            cashFloat[i] = preferences.getFloat("Cashflow" + i, 0.0f);
            if (cashFloat[i] < 0) {
                countkırmızı++;
            } else
                countyesil++;
            //Toast.makeText(getApplicationContext(),"CashFlow "+i+": "+cashFloat[i],Toast.LENGTH_SHORT).show();
        }
        if (countkırmızı > 12) {
            for (int i = 0; i < countkırmızı; i++) {
                colorArray[i] = colorArraykırmızı[i];
            }
        } else if (countkırmızı > 8 && countkırmızı < 13) {
            for (int i = 0; i < countkırmızı; i++) {
                colorArray[i] = colorArrayyesil[ikiser];
                ikiser = ikiser + 2;
            }
        } else if (countkırmızı > 6 && countkırmızı < 9) {
            for (int i = 0; i < countkırmızı; i++) {
                colorArray[ücer] = colorArraykırmızı[ücer];
                ücer = ücer + 3;
            }
        } else if (countkırmızı < 7) {
            for (int i = 0; i < countkırmızı; i++) {
                colorArray[i] = colorArraykırmızı[dörder];
                dörder = dörder + 4;
            }
        }
        ikiser = 0;
        ücer = 0;
        dörder = 0;
        if (24 - countkırmızı > 12) {
            for (int i = countkırmızı; i < 24; i++) {
                colorArray[i] = colorArrayyesil[i];
            }
        } else if (24 - countkırmızı > 8 && 24 - countkırmızı < 13) {
            for (int i = countkırmızı; i < 24; i++) {
                colorArray[i] = colorArrayyesil[ikiser];
                ikiser = ikiser + 2;
            }
        } else if (24 - countkırmızı > 6 && 24 - countkırmızı < 9) {
            for (int i = countkırmızı; i < 24; i++) {
                colorArray[i] = colorArrayyesil[ücer];
                ücer = ücer + 3;
            }
        } else if (24 - countkırmızı < 7) {
            for (int i = countkırmızı; i < 24; i++) {
                colorArray[i] = colorArrayyesil[dörder];
                dörder = dörder + 4;
            }
        }


        mpLineChart = (LineChart) findViewById(R.id.line_chart);
        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "         CashFlow Chart");
        lineDataSet1.setColors(colorArray, ChartsActivity.this);
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setLineWidth(3f);
        lineDataSet1.setValueTextSize(10f);
        lineDataSet1.setDrawVerticalHighlightIndicator(true);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        LineData data = new LineData(dataSets);
        Legend legend = mpLineChart.getLegend();
        legend.setEnabled(true);
        legend.setStackSpace(0);

        //String[] values=new String[]{"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
        //mpLineChart.setDrawGridBackground(true);
        XAxis xAxis = mpLineChart.getXAxis();
        mpLineChart.setDrawBorders(true);
        mpLineChart.getAxisRight().setEnabled(false);
        mpLineChart.getAxisLeft().setLabelCount((lineDataSet1.getEntryCount()));
        mpLineChart.getXAxis().setLabelCount(24);
        mpLineChart.setData(data);
        mpLineChart.animateX(5000);

        mpLineChart.invalidate();
        showResult();
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

    private ArrayList<Entry> dataValues1() {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        for (int i = 0; i < cashFloat.length; i++) {
            dataVals.add(new Entry(i, cashFloat[i]));
        }

        return dataVals;
    }

    public class MyXAxisValueFormatter implements IAxisValueFormatter {
        private String[] mValues;

        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];
        }
    }


    private File saveBitMap(Context context, View drawView) {
        // Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "PayCal_RESIST");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if (!isDirectoryCreated)
                Log.i("TAG", "Can't create directory to save the image");
            return null;
        }
        filename = pictureFileDir.getPath() + File.separator + System.currentTimeMillis() + ".jpg";
        File pictureFile = new File(filename);
        Bitmap bitmap = getBitmapFromView(drawView);
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }
        scanGallery(context, pictureFile.getAbsolutePath());
        return pictureFile;
    }


    //create bitmap from view and returns it
    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    private void showResult() {

        if (typeforuser.equals("Consumer")) {
            if (restype.equals("WIND") && storagetype.equals("NoStorage")) {
                title1.setText("Location : ");
                title2.setText("User Type : ");
                title3.setText("RES Type : ");
                title4.setText("Storage Type : ");
                title5.setText("Turbine Type : ");
                title6.setText("Turbine Number : ");
                title7.setText("Monthly Wind Kwh : ");
                title8.setText("Wind Capital Cost : ");
                title9.setText("PAYBACK : ");
                title10.setVisibility(View.GONE);

                output1.setText(address);
                output2.setText(typeforuser);
                output3.setText(restype);
                output4.setText("No Storage");
                SpannableString ss = new SpannableString(turbinetype + "   Price : " + turbineprice + "$");
                ForegroundColorSpan fcsTitleBlue = new ForegroundColorSpan(getResources().getColor(R.color.backgroundBlue));
                ss.setSpan(fcsTitleBlue, 7, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                output5.setText(ss);
                output6.setText(turbinecount);
                output7.setText(windmonthkwh);
                output8.setText(windcapitalcost);
                output9.setText(payback);
                output10.setVisibility(View.GONE);

            } else if (restype.equals("WIND") && !storagetype.equals("NoStorage")) {
                title1.setText("Location : ");
                title2.setText("User Type : ");
                title3.setText("RES Type : ");
                title4.setText("Storage Type : ");
                title5.setText("Storage Capital Cost : ");
                title6.setText("Turbine Type : ");
                title7.setText("Turbine Number : ");
                title8.setText("Monthly Wind Kwh : ");
                title9.setText("Wind Capital Cost : ");
                title10.setText("PAYBACK : ");
                output1.setText(address);
                output2.setText(typeforuser);
                output3.setText(restype);
                if (storagetype.equals("Li-ion")) {
                    SpannableString ss = new SpannableString(storagetype + "   Capacity : " + storagepercentage + "%");
                    ForegroundColorSpan fcsTitleBlue = new ForegroundColorSpan(getResources().getColor(R.color.backgroundBlue));
                    ss.setSpan(fcsTitleBlue, 7, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    output4.setText(ss);
                }
                if (storagetype.equals("Thermal")) {
                    SpannableString ss = new SpannableString(storagetype + "   Capacity : " + storagepercentage + "%");
                    ForegroundColorSpan fcsTitleBlue = new ForegroundColorSpan(getResources().getColor(R.color.backgroundBlue));
                    ss.setSpan(fcsTitleBlue, 8, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    output4.setText(ss);
                }
                if (storagetype.equals("Lead Acid")) {
                    SpannableString ss = new SpannableString(storagetype + "   Capacity : " + storagepercentage + "%");
                    ForegroundColorSpan fcsTitleBlue = new ForegroundColorSpan(getResources().getColor(R.color.backgroundBlue));
                    ss.setSpan(fcsTitleBlue, 10, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    output4.setText(ss);
                }
                output5.setText(storagecapitalcost);
                SpannableString ss = new SpannableString(turbinetype + "   Price : " + turbineprice + "$");
                ForegroundColorSpan fcsTitleBlue = new ForegroundColorSpan(getResources().getColor(R.color.backgroundBlue));
                ss.setSpan(fcsTitleBlue, 7, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                output6.setText(ss);
                output7.setText(turbinecount);
                output8.setText(windmonthkwh);
                output9.setText(windcapitalcost);
                output10.setText(payback);


            } else if (restype.equals("SOLAR") && storagetype.equals("NoStorage")) {
                title1.setText("Location : ");
                title2.setText("User Type : ");
                title3.setText("RES Type : ");
                title4.setText("Storage Type : ");
                title5.setText("Yearly Solar Profit : ");
                title6.setText("Daily Average Generation : ");
                title7.setText("Yearly Generation : ");
                title8.setText("Panel number : ");
                title9.setText("Area : ");
                title10.setText("Solar Capital Cost : ");
                title11.setText("Yearly Solar Cost : ");
                title12.setText("New Monthly Average Bill : ");
                title13.setText("PAYBACK : ");
                output1.setText(address);
                output2.setText(typeforuser);
                output3.setText(restype);
                output4.setText("No Storage");
                output5.setText(pvprofityear);
                output6.setText(pvgenavgday);
                output7.setText(pvgenyear);
                Double dpvnum = ParseDouble(pvnum);
                int ipvnum = (int) Math.round(dpvnum);
                pvnum = Integer.toString(ipvnum);
                Double dpvpower = ParseDouble(pvpower);
                int ipvpower = (int) Math.round(dpvpower);
                pvpower = Integer.toString(ipvpower);
                Toast.makeText(getApplicationContext(), pvpower, Toast.LENGTH_LONG).show();
                SpannableString ss = new SpannableString(pvnum + "   Solar Power : " + pvpower + " Kw");
                ForegroundColorSpan fcsTitleBlue = new ForegroundColorSpan(getResources().getColor(R.color.backgroundBlue));
                ss.setSpan(fcsTitleBlue, pvnum.length() + 1, pvnum.length() + 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                output8.setText(ss);
                output9.setText(area + " m\u00B2");
                output10.setText(pvcapitalcost);
                output11.setText(pvyearlycost);
                output12.setText(newavgmonthbill);
                output13.setText(payback);

            } else { //SOLAR AND STORAGE TYPE
                title1.setText("Location : ");
                title2.setText("User Type : ");
                title3.setText("RES Type : ");
                title4.setText("Storage Type : ");
                title5.setText("Yearly Profit : ");
                title6.setText("Daily Average Generation : ");
                title7.setText("Yearly Generation : ");
                title8.setText("Panel number : ");
                title9.setText("Area : ");
                title10.setText("Storage Capital Cost : ");
                title11.setText("Solar Capital Cost : ");
                title12.setText("Yearly Solar Cost");
                title13.setText("PAYBACK : ");
                output1.setText(address);
                output2.setText(typeforuser);
                output3.setText(restype);
                ForegroundColorSpan fcsTitleBlue = new ForegroundColorSpan(getResources().getColor(R.color.backgroundBlue));
                SpannableString ss = new SpannableString(storagetype + "   Capacity : " + storagepercentage + "%");
                ss.setSpan(fcsTitleBlue, storagetype.length() + 1, storagetype.length() + 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                output4.setText(ss);
                output5.setText(yearlyprofit);
                output6.setText(pvgenavgday);
                output7.setText(pvgenyear);
                Double dpvnum = ParseDouble(pvnum);
                int ipvnum = (int) Math.round(dpvnum);
                pvnum = Integer.toString(ipvnum);
                Double dpvpower = ParseDouble(pvpower);
                int ipvpower = (int) Math.round(dpvpower);
                pvpower = Integer.toString(ipvpower);
                Toast.makeText(getApplicationContext(), pvpower, Toast.LENGTH_LONG).show();
                SpannableString cc = new SpannableString(pvnum + "   Solar Power : " + pvpower + " Kw");
                ss.setSpan(fcsTitleBlue, pvnum.length() + 1, pvnum.length() + 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                output8.setText(cc);
                SpannableString aa = new SpannableString(area + " m\u00B2" + "   Storage Capacity : %" + storagepercentage);
                aa.setSpan(fcsTitleBlue, area.length() + 4, area.length() + 4 + 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                output9.setText(aa);
                output10.setText(storagecapitalcost);
                output11.setText(pvcapitalcost);
                output12.setText(pvyearlycost);
                output13.setText(payback);
            }
        } else if (typeforuser.equals("Supplier")) {
            if (restype.equals("WIND") && storagetype.equals("NoStorage")) {
                title1.setText("Location : ");
                title2.setText("User Type : ");
                title3.setText("RES Type : ");
                title4.setText("Storage Type : ");
                title5.setText("Turbine Type : ");
                title6.setText("Turbine Number : ");
                title7.setText("Yearly Wind Kwh : ");
                title8.setText("Yearly Wind Cost : ");
                title9.setText("Wind Capital Cost : ");
                title10.setText("Payback : ");

                output1.setText(address);
                output2.setText(typeforuser);
                output3.setText(restype);
                output4.setText("No Storage");
                SpannableString ss = new SpannableString("1500 Kw   Price : 1154.76 $");
                ForegroundColorSpan fcsTitleBlue = new ForegroundColorSpan(getResources().getColor(R.color.backgroundBlue));
                ss.setSpan(fcsTitleBlue, 10, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                output5.setText(ss);
                output6.setText(turbinecount);
                output7.setText(windyearkwh);
                output8.setText(windyearlycost);
                output9.setText(windcapitalcost);
                output10.setText(payback);
            } else if (restype.equals("WIND") && !storagetype.equals("NoStorage")) {
                title1.setText("Location : ");
                title2.setText("User Type : ");
                title3.setText("RES Type : ");
                title4.setText("Storage Type : ");
                title5.setText("Storage Capital Cost : ");
                title6.setText("Turbine Type : ");
                title7.setText("Turbine Number : ");
                title8.setText("Yearly Wind Kwh : ");
                title9.setText("Wind Capital Cost : ");
                title10.setText("PAYBACK : ");
                title11.setVisibility(View.GONE);
                title12.setVisibility(View.GONE);
                title13.setVisibility(View.GONE);
                output1.setText(address);
                output2.setText(typeforuser);
                output3.setText(restype);
                ForegroundColorSpan fcsTitleBlue = new ForegroundColorSpan(getResources().getColor(R.color.backgroundBlue));
                if (storagetype.equals("Li-ion")) {
                    SpannableString ss = new SpannableString(storagetype + "   Capacity : " + storagepercentage + "%");
                    ss.setSpan(fcsTitleBlue, 7, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    output4.setText(ss);
                } else if (storagetype.equals("Thermal")) {
                    SpannableString ss = new SpannableString(storagetype + "   Capacity : " + storagepercentage + "%");
                    ss.setSpan(fcsTitleBlue, 8, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    output4.setText(ss);
                } else if (storagetype.equals("Lead Acid")) {
                    SpannableString ss = new SpannableString(storagetype + "   Capacity : " + storagepercentage + "%");
                    ss.setSpan(fcsTitleBlue, 10, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    output4.setText(ss);
                }
                output5.setText(storagecapitalcost);
                SpannableString ss = new SpannableString("1500 Kw   Price : 1154.76 $");
                ss.setSpan(fcsTitleBlue, 10, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                output6.setText(ss);
                output7.setText(turbinecount);
                output8.setText(windyearkwh);
                output9.setText(windcapitalcost);
                output10.setText(payback);
                output11.setVisibility(View.GONE);
                output12.setVisibility(View.GONE);
                output13.setVisibility(View.GONE);

            } else if (restype.equals("SOLAR") && storagetype.equals("NoStorage")) {
                title1.setText("Location : ");
                title2.setText("User Type : ");
                title3.setText("RES Type : ");
                title4.setText("Storage Type : ");
                title5.setText("Yearly Solar Profit : ");
                title6.setText("Daily Average Generation : ");
                title7.setText("Yearly Generation : ");
                title8.setText("Panel number : ");
                title9.setText("Area : ");
                title10.setText("Solar Capital Cost : ");
                title11.setText("Yearly Solar Cost : ");
                title12.setText("PAYBACK : ");
                title13.setVisibility(View.GONE);
                output1.setText(address);
                output2.setText(typeforuser);
                output3.setText(restype);
                output4.setText("No Storage");
                output5.setText(pvprofityear);
                output6.setText(pvgenavgday);
                output7.setText(pvgenyear);
                Double dpvnum = ParseDouble(pvnum);
                int ipvnum = (int) Math.round(dpvnum);
                pvnum = Integer.toString(ipvnum);
                Double dpvpower = ParseDouble(pvpower);
                int ipvpower = (int) Math.round(dpvpower);
                pvpower = Integer.toString(ipvpower);
                Toast.makeText(getApplicationContext(), pvpower, Toast.LENGTH_LONG).show();
                SpannableString ss = new SpannableString(pvnum + "   Solar Power : " + pvpower + " Kw");
                ForegroundColorSpan fcsTitleBlue = new ForegroundColorSpan(getResources().getColor(R.color.backgroundBlue));
                ss.setSpan(fcsTitleBlue, pvnum.length() + 1, pvnum.length() + 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                output8.setText(ss);
                output9.setText(area + " m\u00B2");
                output10.setText(pvcapitalcost);
                output11.setText(pvyearlycost);
                output12.setText(payback);
                output13.setVisibility(View.GONE);

            } else { //SOLAR AND STORAGE TYPE
                title1.setText("Location : ");
                title2.setText("User Type : ");
                title3.setText("RES Type : ");
                title4.setText("Storage Type : ");
                title5.setText("Yearly Profit : ");
                title6.setText("Daily Average Generation : ");
                title7.setText("Yearly Generation : ");
                title8.setText("Panel number : ");
                title9.setText("Area : ");
                title10.setText("Storage Capital Cost : ");
                title11.setText("Solar Capital Cost : ");
                title12.setText("Yearly Solar Cost");
                title13.setText("PAYBACK : ");
                output1.setText(address);
                output2.setText(typeforuser);
                output3.setText(restype);
                output4.setText(storagetype);
                output5.setText(yearlyprofit);
                output6.setText(pvgenavgday);
                output7.setText(pvgenyear);
                Double dpvnum = ParseDouble(pvnum);
                int ipvnum = (int) Math.round(dpvnum);
                pvnum = Integer.toString(ipvnum);
                Double dpvpower = ParseDouble(pvpower);
                int ipvpower = (int) Math.round(dpvpower);
                pvpower = Integer.toString(ipvpower);
                Toast.makeText(getApplicationContext(), pvpower, Toast.LENGTH_LONG).show();
                SpannableString ss = new SpannableString(pvnum + "   Solar Power : " + pvpower + " Kw");
                ForegroundColorSpan fcsTitleBlue = new ForegroundColorSpan(getResources().getColor(R.color.backgroundBlue));
                ss.setSpan(fcsTitleBlue, pvnum.length() + 1, pvnum.length() + 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                output8.setText(ss);
                SpannableString aa = new SpannableString(area + " m\u00B2" + "   Storage Capacity : %" + storagepercentage);
                aa.setSpan(fcsTitleBlue, area.length() + 4, area.length() + 4 + 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                output9.setText(aa);
                output10.setText(storagecapitalcost);
                output11.setText(pvcapitalcost);
                output12.setText(pvyearlycost);
                output13.setText(payback);

            }
        } else {
            title1.setText("Location : ");
            title2.setText("User Type : ");
            title3.setText("RES Type : ");
            title4.setText("Storage Type : ");
            title5.setText("Storage Capacity : ");
            title6.setText("System Profit : ");
            title7.setText("Yearly Storage Cost : ");
            title8.setText("Storage Capital Cost : ");
            title9.setText("Payback : ");
            title10.setVisibility(View.GONE);
            title11.setVisibility(View.GONE);
            title12.setVisibility(View.GONE);
            title13.setVisibility(View.GONE);
            output1.setText(address);
            output2.setText(typeforuser);
            output3.setText(restype);
            output4.setText(storagetype);
            output5.setText(storagecapacity);
            output6.setText(systemprofit);
            output7.setText(yearlystoragecost);
            output8.setText(storagecapitalcost);
            output9.setText(payback);
            output10.setVisibility(View.GONE);
            output11.setVisibility(View.GONE);
            output12.setVisibility(View.GONE);
            output13.setVisibility(View.GONE);

        }
    }

    // used for scanning gallery
    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue scanning gallery.");
        }
    }

    public static boolean convertToPdf(String jpgFilePath, String outputPdfPath) {
        try {
            // Check if Jpg file exists or not
            File inputFile = new File(jpgFilePath);
            if (!inputFile.exists())
                throw new Exception("File '" + jpgFilePath + "' doesn't exist.");

            // Create output file if needed
            File outputFile = new File(outputPdfPath);
            if (!outputFile.exists()) outputFile.createNewFile();

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputFile));
            document.open();
            Image image = Image.getInstance(jpgFilePath);
            int indentation = 0;

            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - indentation) / image.getWidth()) * 100;

            image.scalePercent(scaler);
            document.add(image);
            document.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
