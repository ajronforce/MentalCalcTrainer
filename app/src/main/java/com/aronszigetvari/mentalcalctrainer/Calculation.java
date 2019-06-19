package com.aronszigetvari.mentalcalctrainer;

public abstract class Calculation implements Savable {

    class Problem {
        Problem() {
        }
    }

    String problemDisplayed;
    String resultCorrect;
    String storedValue;

    public Calculation() {
    }

    public abstract String save();
    public abstract Calculation load(String storedValue);
    public abstract boolean checkResult(String input);
}
