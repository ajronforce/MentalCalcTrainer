package com.aronszigetvari.mentalcalctrainer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView currentProblem;
    private EditText resultInput;

    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button buttonClear;
    Button changeLevel;

    private TextView counter;
    private final String COUNTER_TEXT = "Problems solved: ";
    private int solvedProblems;
    private TextView previousProblem;

    private boolean isCorrect;
    private int level;
    private Multiplication multiplication;

    //Fields saved - rotating the device
    private final String PREVIOUS_PROBLEM = "previousProblem";
    private final String SOLVED_PROBLEMS_COUNT = "solvedProblems";
    private final String CURRENT_PROBLEM = "multiplication";

    //Fields saved - permanent storage
    private final String SOLVED_PROBLEMS_COUNT_FILE = "solvedProblems";
    private final String CURRENT_PROBLEM_FILE = "currentProblem";
    private final String LEVEL_FILE = "level";
    private final String PREVIOUS_PROBLEM_TEXT_FILE = "previousProblem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareLayout();
        setDigitKeys();
        setFunctionKeys();
        loadPreviousSession();
        display();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(PREVIOUS_PROBLEM, previousProblem.getText().toString());
        outState.putInt(SOLVED_PROBLEMS_COUNT, solvedProblems);
        outState.putString(CURRENT_PROBLEM, multiplication.save());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        previousProblem.setText(savedInstanceState.getString(PREVIOUS_PROBLEM));
        solvedProblems = savedInstanceState.getInt(SOLVED_PROBLEMS_COUNT);
        counter.setText(COUNTER_TEXT.concat(Integer.toString(solvedProblems)));
        multiplication.load(savedInstanceState.getString(CURRENT_PROBLEM));
    }

    @Override
    protected void onPause() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(SOLVED_PROBLEMS_COUNT_FILE, solvedProblems);
        editor.putString(CURRENT_PROBLEM_FILE, multiplication.save());
        editor.putInt(LEVEL_FILE, level);
        editor.putString(PREVIOUS_PROBLEM_TEXT_FILE, previousProblem.getText().toString());
        editor.apply();
        super.onPause();
    }

    private void loadPreviousSession() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        level = sharedPref.getInt(LEVEL_FILE,99);
        solvedProblems = sharedPref.getInt(SOLVED_PROBLEMS_COUNT_FILE,0);

        String savedProblem = sharedPref.getString(CURRENT_PROBLEM_FILE, null);
        if(null != savedProblem) {
            multiplication = new Multiplication();
            multiplication = multiplication.load(savedProblem);
        } else {
            multiplication = new Multiplication(level);
        }

        String previousProblemText = sharedPref.getString(PREVIOUS_PROBLEM_TEXT_FILE, null  );
        if(null != previousProblemText) {
            previousProblem.setText(previousProblemText);
        }
    }

    private void prepareLayout() {
        currentProblem = (TextView) findViewById(R.id.currentProblem);
        resultInput = (EditText) findViewById(R.id.resultInput);
        counter = (TextView) findViewById(R.id.counter);
        previousProblem = (TextView) findViewById(R.id.previousProblem);

        currentProblem.setText("");
        resultInput.setText("");
        counter.setText("");
        previousProblem.setText("");
    }

    private void display() {
        currentProblem.setText(multiplication.problemDisplayed);
        resultInput.setText("");
        counter.setText(COUNTER_TEXT.concat(Integer.toString(solvedProblems)));
    }

    private void setDigitKeys() {
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);

        View.OnClickListener digitIsPressed = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;

                resultInput.append(b.getText().toString());
                String input = resultInput.getText().toString();

                if(input.length() > 16) {
                    resultInput.setText("");
                }
                if("0".equals(input)) {
                    resultInput.setText("");
                }

                isCorrect = multiplication.checkResult(input);
                if (isCorrect) {
                    solvedProblems++;
                    previousProblem.setText(multiplication.storedValue);
                    multiplication = new Multiplication(level);
                    display();
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
    }

    private void setFunctionKeys() {
        buttonClear = (Button) findViewById(R.id.buttonClear);
        View.OnClickListener buttonClearIsPressed = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultInput.setText("");
            }
        };
        buttonClear.setOnClickListener(buttonClearIsPressed);

        changeLevel = (Button) findViewById(R.id.buttonChangeLevel);
        View.OnClickListener changeLevelIsPressed = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(level < 100000) {
                    level = level * 10 + 9;
                } else {
                    level = 99;
                }
                multiplication = new Multiplication(level);
                display();
            }
        };
        changeLevel.setOnClickListener(changeLevelIsPressed);
    }
}