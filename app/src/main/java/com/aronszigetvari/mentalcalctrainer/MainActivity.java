package com.aronszigetvari.mentalcalctrainer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private final String INDICATOR_TEXT = "Problems solved: ";
    private boolean isCorrect;
    private int solvedProblems;
    private int maxFactor;
    private MultiplicationProblem multiplicationProblem;

    //Fields need to be saved when rotating the device
    private final String PREVIOUS_PROBLEM = "previousProblem";
    private final String SOLVED_PROBLEMS_COUNT = "solvedProblems";
    private final String CURRENT_PROBLEM = "multiplicationProblem";

    //Information to store on the internal storage
    private final String SOLVED_PROBLEMS_COUNT_FILE = "solvedProblems";
    private final String LAST_PROBLEM_FILE = "lastProblem";
    private final String LEVEL_FILE = "maxFactor";

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
        counter.setText("");
        previousProblem.setText("");
        //Create first multiplication problem
        maxFactor = 99;
        multiplicationProblem = new MultiplicationProblem(maxFactor);

        //Check if SharedPreferences, i.e. saved state exists, and load that, overwrite the initialized multiplication problem
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        solvedProblems = sharedPref.getInt(SOLVED_PROBLEMS_COUNT_FILE,0);
        String savedProblem = sharedPref.getString(LAST_PROBLEM_FILE, null);
        if(null != savedProblem) {
            multiplicationProblem.load(savedProblem);
        }
        maxFactor = sharedPref.getInt(LEVEL_FILE,99);

        //Display the first or the loaded multiplication problem
        factorOne.setText(Integer.toString(multiplicationProblem.getFactorOne()));
        factorTwo.setText(Integer.toString(multiplicationProblem.getFactorTwo()));
        counter.setText(INDICATOR_TEXT.concat(Integer.toString(solvedProblems)));

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
                    counter.setText(INDICATOR_TEXT.concat(Integer.toString(solvedProblems)));
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

//------------------------------------------
    //Dealing with rotation of the device

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(PREVIOUS_PROBLEM, previousProblem.getText().toString());
        outState.putInt(SOLVED_PROBLEMS_COUNT, solvedProblems);
        outState.putString(CURRENT_PROBLEM, multiplicationProblem.save());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        previousProblem.setText(savedInstanceState.getString(PREVIOUS_PROBLEM));
        solvedProblems = savedInstanceState.getInt(SOLVED_PROBLEMS_COUNT);
        counter.setText(INDICATOR_TEXT.concat(Integer.toString(solvedProblems)));
        multiplicationProblem.load(savedInstanceState.getString(CURRENT_PROBLEM));
    }

    @Override
    protected void onResume() {

        super.onResume();

    }

    @Override
    protected void onPause() {

        //write current state to a SharedPreferences file
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(SOLVED_PROBLEMS_COUNT_FILE,solvedProblems);
        editor.putString(LAST_PROBLEM_FILE,multiplicationProblem.save());
        editor.putInt(LEVEL_FILE,maxFactor);
        editor.apply();

        super.onPause();
    }
}