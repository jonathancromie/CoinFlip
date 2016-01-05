package com.jonathancromie.coinflip;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    public static final String SHAREDPREFFILE = "temp";

    private int playerWins;
    private int computerWins;
    private int total;
    private int numberOfRounds;
//    private double playerPercentage;
//    private double computerPercentage;
    private int numberOfHeads;
    private int numberOfTails;

    private TextView txtPlayerWins;
    private TextView txtComputerWins;
    private TextView txtNumberOfRounds;
    private TextView txtNumberOfHeads;
    private TextView txtNumberOfTails;

    private PieChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(R.string.title_activity_statistics);

        SharedPreferences sharedPreferences = getSharedPreferences(SHAREDPREFFILE, MODE_PRIVATE);
        playerWins = sharedPreferences.getInt("playerWins", 0);
        computerWins = sharedPreferences.getInt("computerWins", 0);
        numberOfRounds = sharedPreferences.getInt("numberOfRounds", 0);
        numberOfHeads = sharedPreferences.getInt("numberOfHeads", 0);
        numberOfTails = sharedPreferences.getInt("numberOfTails", 0);

//        try {
//            total = playerWins + computerWins;
//            playerPercentage = ((double) playerWins / total) * 100;
//            computerPercentage = ((double) computerWins / total) * 100;
//        }
//        catch (ArithmeticException e) {
//            e.printStackTrace();
//        }

        txtPlayerWins = (TextView) findViewById(R.id.txtPlayerWins);
        txtComputerWins = (TextView) findViewById(R.id.txtComputerWins);
        txtNumberOfRounds = (TextView) findViewById(R.id.txtNumberOfRounds);
        txtNumberOfHeads = (TextView) findViewById(R.id.txtNumberOfHeads);
        txtNumberOfTails = (TextView) findViewById(R.id.txtNumberOfTails);

        txtPlayerWins.setText(String.valueOf(playerWins));
        txtComputerWins.setText(String.valueOf(computerWins));
        txtNumberOfRounds.setText(String.valueOf(numberOfRounds));
        txtNumberOfHeads.setText(String.valueOf(numberOfHeads));
        txtNumberOfTails.setText(String.valueOf(numberOfTails));

        mChart = (PieChart) findViewById(R.id.chart1);
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

//        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

//        mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
//        mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(30f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//        mChart.setOnChartValueSelectedListener(this);

        setData(2, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

    }

    private void setData(int count, float range) {

        float mult = range;

        float[] scores = new float[] {playerWins, computerWins};
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < count; i++) {
            yVals1.add(new Entry(scores[i] , i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

        String[] mParties = new String[] {"Player", "Computer"};

        for (int i = 0; i < count; i++)
            xVals.add(mParties[i % mParties.length]);

        PieDataSet dataSet = new PieDataSet(yVals1, "Results");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);

//        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(new int[]{R.color.colorPrimary, R.color.colorAccent}, this);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }
}
