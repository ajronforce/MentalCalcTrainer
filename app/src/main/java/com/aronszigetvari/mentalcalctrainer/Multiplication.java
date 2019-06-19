package com.aronszigetvari.mentalcalctrainer;

public class Multiplication extends Calculation{
    private class MultiplicationProblem extends Calculation.Problem{

        private int factor1;
        private int factor2;
        private long product;

        public MultiplicationProblem(int factor1, int factor2) {
            this.factor1= factor1;
            this.factor2 = factor2;
            this.product = (long) (factor1) * (long) (factor2);
        }
    }

    private MultiplicationProblem multiplicationProblem;

    public Multiplication(int... a) {
        switch (a.length) {
            case 0:
                multiplicationProblem = createProblem("maxFactor", 99);
                break;
            case 1:
                multiplicationProblem = createProblem("maxFactor", a[0]);
                break;
            default:
                multiplicationProblem = createProblem("twoFactors", a[0], a[1]);
                break;
        }
        problemDisplayed =problemAsString();
        resultCorrect = resultAsString();
        storedValue = problemDisplayed + resultCorrect;
    }

    private MultiplicationProblem createProblem(String type, int maxFactor) {
        if(!type.equalsIgnoreCase("maxFactor")) {
            return createProblem("twoFactors",-1,-1);
        } else {
            int factor1 = (int) (Math.random()*maxFactor) + 1;
            int factor2 = (int) (Math.random()*maxFactor) + 1;
            return createProblem("twoFactors", factor1, factor2);
        }
    }

    private MultiplicationProblem createProblem(String type, int factor1, int factor2) {
        if(!type.equalsIgnoreCase("twoFactors")) {
            return new MultiplicationProblem(-1,-1);
        } else {
            return new MultiplicationProblem(factor1,factor2);
        }
    }

    private String problemAsString() {
        return multiplicationProblem.factor1 + " X " + multiplicationProblem.factor2 + " = ";
    }

    private String resultAsString() {
        return Long.toString(multiplicationProblem.product);
    }

    @Override
    public boolean checkResult(String input) {
        if(null==input) {
            return false;
        } else {
            return (input.equals(resultCorrect));
        }
    }

    @Override
    public Multiplication load(String contents) {
        int stringPosition = contents.indexOf(" X ");
        if(stringPosition >= 0) {
            int factor1 = Integer.parseInt(contents.substring(0, stringPosition));
            contents = contents.substring(stringPosition + 3);
            stringPosition = contents.indexOf(" = ");
            if(stringPosition >= 0) {
                int factor2 = Integer.parseInt(contents.substring(0, stringPosition));
                return new Multiplication(factor1, factor2);
            }
        }

        return new Multiplication(-1, -1);
    }

    @Override
    public String save() {
        return storedValue;
    }
}
