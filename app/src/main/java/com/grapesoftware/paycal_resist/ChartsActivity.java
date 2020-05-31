package com.grapesoftware.paycal_resist;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ChartsActivity extends AppCompatActivity {
    LineChart mpLineChart;
    Float[] cashFloat = new Float[24];
    private int currentIndex = 0;
    int kırmızı = 255, yesil = 0, mavi = 0;
    LinearLayout content;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        int countkırmızı = 0, countyesil = 0, sarı = 4;
        int gecis1, gecis2;
        int renk1, renk2;
        int[] colorArray = new int[24];

        button = findViewById(R.id.buttonsave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout savingLayout = (LinearLayout) findViewById(R.id.linearLayout_chart);
                File file = saveBitMap(ChartsActivity.this, savingLayout);
                if (file != null) {
                    Log.i("TAG", "Drawing saved to the gallery!");
                } else {
                    Log.i("TAG", "Oops! Image could not be saved.");
                }
            }
        });

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

        int countfourkırmızı = colorArraykırmızı.length / countkırmızı;

        gecis1 = countkırmızı - sarı;
        renk1 = 255 / gecis1;
        gecis2 = countyesil - sarı;
        renk2 = 255 / gecis2;

        for (int i = 0; i < 12; i++) {
            yesil = yesil + renk1;
        }
        for (int i = 12; i < 24; i++) {
            kırmızı = kırmızı - renk2;
        }


        mpLineChart = (LineChart) findViewById(R.id.line_chart);
        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "         CashFlow Chart");
        lineDataSet1.setColors(colorArray, ChartsActivity.this);
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setLineWidth(3);
        lineDataSet1.setValueTextSize(10);
        lineDataSet1.setDrawVerticalHighlightIndicator(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        LineData data = new LineData(dataSets);
        Legend legend=mpLineChart.getLegend();
        legend.setEnabled(true);
        legend.setStackSpace(0);

        //mpLineChart.setDrawGridBackground(true);
        mpLineChart.setDrawBorders(true);
        mpLineChart.getAxisRight().setEnabled(false);
        mpLineChart.getAxisLeft().setLabelCount((lineDataSet1.getEntryCount()));
        mpLineChart.getXAxis().setLabelCount(24);
        mpLineChart.setData(data);
        mpLineChart.animateX(5000);
        mpLineChart.invalidate();
    }

    private ArrayList<Entry> dataValues1() {


        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        for (int i = 0; i < cashFloat.length; i++) {
            dataVals.add(new Entry(i, cashFloat[i]));
        }

        return dataVals;
    }


    private File saveBitMap(Context context, View drawView) {
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Logicchip");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if (!isDirectoryCreated)
                Log.i("TAG", "Can't create directory to save the image");
            return null;
        }
        String filename = pictureFileDir.getPath() + File.separator + System.currentTimeMillis() + ".jpg";
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
}
