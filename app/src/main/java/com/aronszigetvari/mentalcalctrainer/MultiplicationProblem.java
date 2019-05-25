package com.aronszigetvari.mentalcalctrainer;

public class MultiplicationProblem {

    private int factorOne;
    private int factorTwo;
    private int product;

    public MultiplicationProblem(int maxFactor) {

        if(maxFactor>9999) {
            maxFactor = 9999;
        }

        int szorzo1 = (int) (Math.random()*maxFactor) + 1;
        int szorzo2 = (int) (Math.random()*maxFactor) + 1;

        this.factorOne = szorzo1;
        this.factorTwo = szorzo2;
        this.product = szorzo1*szorzo2;
    }

    public int getFactorOne() {
        return factorOne;
    }

    public int getFactorTwo() {
        return factorTwo;
    }

    public int getProduct() {
        return product;
    }
}
