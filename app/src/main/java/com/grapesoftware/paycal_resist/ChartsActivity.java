package com.grapesoftware.paycal_resist;

import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class ChartsActivity extends AppCompatActivity {
    LineChart mpLineChart;
    Float[] cashFloat = new Float[24];
    private int currentIndex = 0;
    int kırmızı = 255, yesil = 0, mavi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        int countkırmızı = 0, countyesil = 0, sarı = 4;
        int gecis1, gecis2;
        int renk1, renk2;
        int[] colorArray = new int[24];




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
        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "CashFlow Diagram");
        lineDataSet1.setColors(colorArray, ChartsActivity.this);
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setLineWidth(2);
        lineDataSet1.setValueTextSize(12);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        LineData data = new LineData(dataSets);
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

    public void addColorToArray(int red, int green, int blue) {
    }
}
