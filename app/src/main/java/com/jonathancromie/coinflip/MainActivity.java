package com.jonathancromie.coinflip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    public static final String SHAREDPREFFILE = "temp";

    private Coin coin;
    private FlipCoin handler;

    private TextView txtResult;
    private TextView txtChoice;

    private TextView txtPlayer;
    private TextView txtComputer;
    private Button btnHeads;
    private Button btnTails;

    ImageView imgCoin;

    private int numberOfRounds = 0;
    private int numberOfHeads = 0;
    private int numberOfTails = 0;
    private int playerAmount = 0;
    private int computerAmount = 10;

    private int playerWin = 0;
    private int computerWin = 0;

    private boolean isClicked = false;
    private boolean isHeads = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coin = new Coin();

        imgCoin = (ImageView) findViewById(R.id.imgCoin);
        final Animation coinFlip = AnimationUtils.loadAnimation(this, R.anim.animation_coin_flip);
        handler = new FlipCoin(imgCoin, coinFlip);

        txtResult = (TextView) findViewById(R.id.txtResult);
        txtChoice = (TextView) findViewById(R.id.txtChoice);

        txtResult.setText("Flip the Coin");

        txtPlayer = (TextView) findViewById(R.id.txtPlayer);
        txtComputer = (TextView) findViewById(R.id.txtComputer);

        txtPlayer.setText(String.valueOf(playerAmount));
        txtComputer.setText(String.valueOf(computerAmount));

        btnHeads = (Button) findViewById(R.id.btnHeads);
        btnTails = (Button) findViewById(R.id.btnTails);

        btnHeads.setEnabled(false);
        btnTails.setEnabled(false);
    }

    private void startCoinChoiceAnimation() {
        final ImageView imgHeadsOrTails = (ImageView) findViewById(R.id.imgCoin);
        final Animation coinChoice = AnimationUtils.loadAnimation(this, R.anim.animation_scale_up);

        coinChoice.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                imgHeadsOrTails.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        imgHeadsOrTails.setVisibility(View.VISIBLE);
        imgHeadsOrTails.startAnimation(coinChoice);
        imgHeadsOrTails.setEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences(SHAREDPREFFILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("playerWins", playerWin);
        editor.putInt("computerWins", computerWin);
        editor.putInt("numberOfRounds", numberOfRounds);
        editor.putInt("numberOfHeads", numberOfHeads);
        editor.putInt("numberOfTails", numberOfTails);
        editor.commit();
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
        if (id == R.id.action_statistics) {
            Intent i = new Intent(MainActivity.this, StatisticsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        isClicked = true;

        btnHeads.setEnabled(false);
        btnTails.setEnabled(false);

        if (v.getId() == R.id.btnHeads) {
            isHeads = true;
            txtChoice.setText("You choose Heads");
        }
        else {
            isHeads = false;
            txtChoice.setText("You choose Tails");
        }
    }

    class FlipCoin extends Handler {
        private short mCoinImageValue = 0;
        private ImageView mCoin;

        public FlipCoin(ImageView coin, Animation animate) {
            final Animation animation = animate;
            mCoin = coin;

            coin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mCoin.setOnClickListener(null);
                    mCoin.startAnimation(animation);
                    mCoin.setEnabled(false);
                    btnHeads.setEnabled(true);
                    btnTails.setEnabled(true);

                    handler.sendMessageDelayed(Message.obtain(handler, 0, 20), 50);
                }
            });
        }

        public void handleMessage(Message msg) {
            if (mCoinImageValue == 0) {
                mCoinImageValue = 1;
            }
            else {
                mCoinImageValue = 0;
            }
            mCoin.setImageLevel(mCoinImageValue);

            Integer flips = (Integer) msg.obj;
            if (flips > 0) {
                MainActivity.this.handler.sendMessageDelayed(Message.obtain(MainActivity.this.handler, 0, --flips), 50);
            }
            else {
                int coinFlip;
                coin.flipCoin();

                numberOfRounds++;
                txtResult.setText(coin.toString());

                if (isClicked) {
                    // if coin is heads and player picked heads
                    if (coin.isHeads() && isHeads == true) {
                        playerWin += 1;
                        playerAmount += 10;
                        computerAmount -= 10;
                    }
                    // if coin is heads and player picked tails
                    else if (coin.isHeads() && isHeads == false) {
                        computerWin += 1;
                        playerAmount -= 10;
                        computerAmount += 10;
                    }
                    //if coin is tails and player picked heads
                    else if (!coin.isHeads() && isHeads == true) {
                        computerWin += 1;
                        playerAmount -= 10;
                        computerAmount += 10;
                    }
                    //if coin is tails and player picked tails
                    else {
                        playerWin += 1;
                        playerAmount += 10;
                        computerAmount -= 10;
                    }
                }

                if (coin.isHeads()) {
                    coinFlip = 0;
                    numberOfHeads += 1;
                }
                else {
                    coinFlip = 1;
                    numberOfTails += 1;
                }

                txtPlayer.setText(String.valueOf(playerAmount));
                txtComputer.setText(String.valueOf(computerAmount));

                btnHeads.setEnabled(false);
                btnTails.setEnabled(false);

                System.out.println("Coin Value: " + String.valueOf(coinFlip));

                mCoin.setImageLevel(coinFlip);

                startCoinChoiceAnimation();


            }



        }
    }
}
