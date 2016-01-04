package com.jonathancromie.coinflip;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class StatisticsActivity extends AppCompatActivity {

    public static final String SHAREDPREFFILE = "temp";

    private int playerWins;
    private int computerWins;
    private int numberOfRounds;
    private double playerPercentage;
    private double computerPercentage;
    private int total;

    private TextView txtPlayerWins;
    private TextView txtComputerWins;
    private TextView txtPlayerPercentage;
    private TextView txtComputerPercentage;
    private TextView txtNumberOfRounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences(SHAREDPREFFILE, MODE_PRIVATE);
        playerWins = sharedPreferences.getInt("playerWins", 0);
        computerWins = sharedPreferences.getInt("computerWins", 0);
        numberOfRounds = sharedPreferences.getInt("numberOfRounds", 0);

        try {
            total = playerWins + computerWins;
            playerPercentage = ((double) playerWins / total) * 100;
            computerPercentage = ((double) computerWins / total) * 100;
        }
        catch (ArithmeticException e) {
            e.printStackTrace();
        }

        txtPlayerWins = (TextView) findViewById(R.id.txtPlayerWins);
        txtComputerWins = (TextView) findViewById(R.id.txtComputerWins);
        txtPlayerPercentage = (TextView) findViewById(R.id.txtPlayerPercentage);
        txtComputerPercentage = (TextView) findViewById(R.id.txtComputerPercentage);
        txtNumberOfRounds = (TextView) findViewById(R.id.txtNumberOfRounds);

        txtPlayerWins.setText(String.valueOf(playerWins));
        txtComputerWins.setText(String.valueOf(computerWins));
        txtPlayerPercentage.setText(String.valueOf((int)playerPercentage));
        txtComputerPercentage.setText(String.valueOf((int)computerPercentage));
        txtNumberOfRounds.setText(String.valueOf(numberOfRounds));

    }

}
