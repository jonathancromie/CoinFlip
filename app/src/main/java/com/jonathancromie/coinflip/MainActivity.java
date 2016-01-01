package com.jonathancromie.coinflip;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Coin coin;

    private TextView txtDisplay;
    private TextView txtNumberOfFlips;
    private TextView txtAmount;
    private Button btnFlip;
    private Button btnReset;

    private int numberOfFlips = 0;
    private int amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        coin = new Coin();

        txtDisplay = (TextView) findViewById(R.id.txtDisplay);
        txtNumberOfFlips = (TextView) findViewById(R.id.txtNumberOfFlips);
        txtAmount = (TextView) findViewById(R.id.txtAmount);
        btnFlip = (Button) findViewById(R.id.btnFlip);
        btnFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coin.flipCoin();
                numberOfFlips++;
                txtDisplay.setText(coin.toString());

                if (!coin.isHeads()) {
                    if (numberOfFlips == 1) {
                        amount = 1;
                    }
                    else {
                        amount = amount * 2;
                    }

                }
                else {
                    btnFlip.setEnabled(false);
                }

                txtNumberOfFlips.setText(String.valueOf(numberOfFlips));
                txtAmount.setText(String.valueOf(amount));
            }
        });

        btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    private void reset() {
        amount = 0;
        numberOfFlips = 0;
        txtDisplay.setText("");
        txtAmount.setText(String.valueOf(amount));
        txtNumberOfFlips.setText(String.valueOf(numberOfFlips));
        btnFlip.setEnabled(true);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
