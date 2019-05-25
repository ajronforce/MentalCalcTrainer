package com.aronszigetvari.mentalcalctrainer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EditText factorOne;
    private EditText factorTwo;
    private EditText product;
    private TextView counter;
    private TextView previousProblem;
    private boolean isCorrect;
    private int solvedProblems;
    private int maxFactor;
    private MultiplicationProblem multiplicationProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        factorOne = (EditText) findViewById(R.id.factorOne);
        factorTwo = (EditText) findViewById(R.id.factorTwo);
        product = (EditText) findViewById(R.id.result);
        counter = (TextView) findViewById(R.id.indicator);
        previousProblem = (TextView) findViewById(R.id.previous);

        //Initialization
        factorOne.setText("");
        factorTwo.setText("");
        product.setText("");
        final String INDICATOR_TEXT = "Problems solved: ";
        counter.setText("");
        previousProblem.setText("");
        maxFactor = 99;

        //Create the first multiplication problem
        multiplicationProblem = new MultiplicationProblem(maxFactor);
        factorOne.setText(Integer.toString(multiplicationProblem.getFactorOne()));
        factorTwo.setText(Integer.toString(multiplicationProblem.getFactorTwo()));
        solvedProblems = 0;
        counter.setText(INDICATOR_TEXT + solvedProblems);

        //Set 'digit' buttons -> if they are pressed, the app checks whether the correct result is typed in
        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);

        View.OnClickListener digitIsPressed = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                try {
                    product.append(b.getText().toString());
                    isCorrect = (Integer.valueOf(product.getText().toString()) == multiplicationProblem.getProduct());
                } catch (NumberFormatException e) {
                    product.setText("");
                }
                if (isCorrect) {
                    solvedProblems++;
                    previousProblem.setText("("+multiplicationProblem.getFactorOne()+"x"+multiplicationProblem.getFactorTwo()+"="+multiplicationProblem.getProduct()+")");
                    multiplicationProblem = new MultiplicationProblem(maxFactor);
                    factorOne.setText(Integer.toString(multiplicationProblem.getFactorOne()));
                    factorTwo.setText(Integer.toString(multiplicationProblem.getFactorTwo()));
                    product.setText("");
                    counter.setText(INDICATOR_TEXT + solvedProblems);
                }
            }
        };

        button0.setOnClickListener(digitIsPressed);
        button1.setOnClickListener(digitIsPressed);
        button2.setOnClickListener(digitIsPressed);
        button3.setOnClickListener(digitIsPressed);
        button4.setOnClickListener(digitIsPressed);
        button5.setOnClickListener(digitIsPressed);
        button6.setOnClickListener(digitIsPressed);
        button7.setOnClickListener(digitIsPressed);
        button8.setOnClickListener(digitIsPressed);
        button9.setOnClickListener(digitIsPressed);

//-----------------------------------------
        //Set 'clear' button

        Button buttonClear = (Button) findViewById(R.id.buttonClear);

        View.OnClickListener buttonClearIsPressed = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.setText("");
            }
        };

        buttonClear.setOnClickListener(buttonClearIsPressed);

//-----------------------------------------
        //Set 'change level' button: 2-digit -> 3-digit -> 4-digit cyclical change

        Button changeLevel = (Button) findViewById(R.id.buttonChangeLevel);

        View.OnClickListener changeLevelIsPressed = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(maxFactor == 99) {
                    maxFactor = 999;
                } else if(maxFactor == 999) {
                    maxFactor = 9999;
                } else {
                    maxFactor = 99;
                }
                multiplicationProblem = new MultiplicationProblem(maxFactor);
                factorOne.setText(Integer.toString(multiplicationProblem.getFactorOne()));
                factorTwo.setText(Integer.toString(multiplicationProblem.getFactorTwo()));
                product.setText("");
            }
        };

        changeLevel.setOnClickListener(changeLevelIsPressed);
    }
}