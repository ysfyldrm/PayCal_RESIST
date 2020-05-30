package com.grapesoftware.paycal_resist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class ChartsActivity extends AppCompatActivity {
    LineChart mpLineChart;
    Float []cashFloat=new Float[24];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
        int colorArray[]={R.color.color1,R.color.color2,R.color.color3,R.color.color4,R.color.color5,R.color.color6};

        for (int i=0;i<cashFloat.length;i++){
            cashFloat[i]=preferences.getFloat("Cashflow"+i,0.0f);
            Toast.makeText(getApplicationContext(),"CashFlow "+i+": "+cashFloat[i],Toast.LENGTH_SHORT).show();
        }

        mpLineChart=(LineChart) findViewById(R.id.line_chart);
        LineDataSet lineDataSet1=new LineDataSet(dataValues1(),"CashFlow Diagram");
        lineDataSet1.setColors(colorArray,ChartsActivity.this);
        lineDataSet1.setDrawCircles(false);

        ArrayList<ILineDataSet> dataSets=new ArrayList<>();
        dataSets.add(lineDataSet1);
        LineData data=new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.animateX(5000);
        mpLineChart.invalidate();
    }

    private ArrayList<Entry> dataValues1(){


        ArrayList<Entry> dataVals=new ArrayList<Entry>();
        for(int i=0;i<cashFloat.length;i++){
            dataVals.add(new Entry(i,cashFloat[i]));
        }

        return dataVals;
    }
}
