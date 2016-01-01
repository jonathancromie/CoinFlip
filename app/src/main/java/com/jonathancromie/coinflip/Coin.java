package com.jonathancromie.coinflip;

import java.util.Random;

/**
 * Created by Jonathan on 01-Jan-16.
 */
public class Coin {
    private static final int HEADS = 0;
    private static final int TAILS = 1;

    private int face;

    private Random random;

    public Coin() {
        random = new Random();
    }

    public void flipCoin() {
        face = random.nextInt(2);
    }

    public boolean isHeads() {
        return (face == HEADS);
    }

    public String toString() {
        String faceName;

        if (isHeads()) {
            faceName = "Heads";
        }
        else {
            faceName = "Tails";
        }

        return faceName;
    }
}
