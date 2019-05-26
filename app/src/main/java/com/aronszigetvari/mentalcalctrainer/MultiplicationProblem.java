package com.aronszigetvari.mentalcalctrainer;

public class MultiplicationProblem {

    private int factorOne;
    private int factorTwo;
    private long product;

    public MultiplicationProblem(int maxFactor) {

        if(maxFactor>9999) {
            maxFactor = 9999;
        }

        int factorOne = (int) (Math.random()*maxFactor) + 1;
        int factorTwo = (int) (Math.random()*maxFactor) + 1;

        this.factorOne = factorOne;
        this.factorTwo = factorTwo;
        this.product = factorOne*factorTwo;
    }

    public int getFactorOne() {
        return factorOne;
    }

    public int getFactorTwo() {
        return factorTwo;
    }

    public long getProduct() {
        return product;
    }

    public String save() {
        return (Integer.toString(factorOne) + ";" + Integer.toString(factorTwo) + ";" + Long.toString(product));
    }

    public void load(String contents) {
        String[] fields = contents.split(";");
        factorOne = Integer.parseInt(fields[0]);
        factorTwo = Integer.parseInt(fields[1]);
        product = Long.parseLong(fields[2]);
    }
}
