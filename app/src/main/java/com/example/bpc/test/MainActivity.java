package com.example.bpc.test;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    int[] vertical1 = {R.id.textView10, R.id.textView16, R.id.textView17};
    int[] vertical2 = {R.id.textView5,  R.id.textView6,  R.id.textView7,  R.id.textView8};
    int[] vertical3 = {R.id.textView27, R.id.textView26, R.id.textView25, R.id.textView24, R.id.textView23, R.id.textView15};
    int[] vertical4 = {R.id.textView13, R.id.textView18, R.id.textView19, R.id.textView20};
    int[] vertical5 = {R.id.textView37, R.id.textView35, R.id.textView38, R.id.textView39, R.id.textView40, R.id.textView41};

    int[] horizontal1 = {R.id.textView,   R.id.textView2,  R.id.textView3,  R.id.textView4,  R.id.textView5};
    int[] horizontal2 = {R.id.textView9,  R.id.textView10, R.id.textView11, R.id.textView8,  R.id.textView12, R.id.textView13, R.id.textView14, R.id.textView15};
    int[] horizontal3 = {R.id.textView28, R.id.textView27, R.id.textView29, R.id.textView30, R.id.textView31};
    int[] horizontal4 = {R.id.textView32, R.id.textView33, R.id.textView24, R.id.textView34, R.id.textView35, R.id.textView36};
    int[] horizontal5 = {R.id.textView21, R.id.textView19, R.id.textView22};

    int[][] verticalArray   = {vertical1,   vertical2,   vertical3,   vertical4,   vertical5};
    int[][] horizontalArray = {horizontal1, horizontal2, horizontal3, horizontal4, horizontal5};

    TextView[] verticalTxt1 = new TextView[vertical1.length];
    TextView[] verticalTxt2 = new TextView[vertical2.length];
    TextView[] verticalTxt3 = new TextView[vertical3.length];
    TextView[] verticalTxt4 = new TextView[vertical4.length];
    TextView[] verticalTxt5 = new TextView[vertical5.length];

    TextView[] horizontalTxt1 = new TextView[horizontal1.length];
    TextView[] horizontalTxt2 = new TextView[horizontal2.length];
    TextView[] horizontalTxt3 = new TextView[horizontal3.length];
    TextView[] horizontalTxt4 = new TextView[horizontal4.length];
    TextView[] horizontalTxt5 = new TextView[horizontal5.length];

    TextView timer;
    TextView points;
    TextView txtHint;
    EditText txtAns;
    Button btnChange;
    CountDownTimer countDownTimer;
    AlertDialog startDialog, restartDialog1, restartDialog2;

    int minutes, seconds;
    int doneCount = 0, index = 0;
    long timeLeftInMilliseconds = 60000;
    boolean timerRunning, v1, v2, v3, v4, v5, h1, h2, h3, h4, h5;
    boolean restartGame = false, firstTime = true;
    String timeLeftText, timeUsedText, words, time;
    String[] verticalAnswers, horizontalAnswers, verticalQuestions, horizontalQuestions, answer1Array, answer2Array, answer3Array, answer4Array, answer5Array, answer6Array, answer7Array, answer8Array, answer9Array, answer10Array, answer11Array, answer12Array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer     = (TextView) findViewById(R.id.timer);
        points    = (TextView) findViewById(R.id.points);
        txtHint   = (TextView) findViewById(R.id.hint);
        txtAns    = (EditText) findViewById(R.id.txtAns);
        btnChange = (Button) findViewById(R.id.btnChange);
        verticalAnswers     = getResources().getStringArray(R.array.vertical_answers);
        horizontalAnswers   = getResources().getStringArray(R.array.horizontal_answers);
        verticalQuestions   = getResources().getStringArray(R.array.vertical_questions);
        horizontalQuestions = getResources().getStringArray(R.array.horizontal_questions);

        points.setText(String.valueOf(doneCount));

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        firstTime = sp.getBoolean("dialogShown", true);

        if (firstTime) {
            showAlertBox();

            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("dialogShown", false);
            e.apply();
        }

        txtAns.setEnabled(false);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(txtAns, InputMethodManager.SHOW_FORCED);

        btnChange.setText("Vertical");
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnChange.getText().toString().equalsIgnoreCase("Vertical")) {
                    btnChange.setText("Horizontal");

                    if (h1 && h2 && h3 && h4 && h5) {
                        btnChange.setAlpha(0.5f);
                        btnChange.setClickable(false);
                    }

                    txtAns.setEnabled(false);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(txtAns, InputMethodManager.SHOW_FORCED);

                    txtHint.setText("");
                    for (int i = 0; i < verticalArray.length; i++) {
                        //vertical1
                        for (int j = 0; j < vertical1.length; j++) {
                            verticalTxt1[j] = (TextView) findViewById(vertical1[j]);

                            if (v1) {
                                verticalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button));
                                verticalTxt1[j].setClickable(false);
                            } else {
                                verticalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                verticalTxt1[j].setClickable(true);
                                verticalTxt1[j].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        txtAns.setEnabled(true);
                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.showSoftInput(txtAns, InputMethodManager.SHOW_IMPLICIT);

                                        for (int j = 0; j < vertical1.length; j++) {
                                            txtHint.setText(verticalQuestions[index]);
                                            words = verticalAnswers[index];
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (!v2 || !v3 || !v4 || !v5) {
                                            for (int j = 0; j < vertical2.length; j++) {
                                                verticalTxt2[j] = (TextView) findViewById(vertical2[j]);
                                                verticalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < vertical3.length; j++) {
                                                verticalTxt3[j] = (TextView) findViewById(vertical3[j]);
                                                verticalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < vertical4.length; j++) {
                                                verticalTxt4[j] = (TextView) findViewById(vertical4[j]);
                                                verticalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < vertical5.length; j++) {
                                                verticalTxt5[j] = (TextView) findViewById(vertical5[j]);
                                                verticalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (v2) {
                                            for (int j = 0; j < vertical2.length; j++) {
                                                verticalTxt2[j] = (TextView) findViewById(vertical2[j]);
                                                verticalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (v3) {
                                            for (int j = 0; j < vertical3.length; j++) {
                                                verticalTxt3[j] = (TextView) findViewById(vertical3[j]);
                                                verticalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (v4) {
                                            for (int j = 0; j < vertical4.length; j++) {
                                                verticalTxt4[j] = (TextView) findViewById(vertical4[j]);
                                                verticalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (v5) {
                                            for (int j = 0; j < vertical5.length; j++) {
                                                verticalTxt5[j] = (TextView) findViewById(vertical5[j]);
                                                verticalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }
                                    }
                                });
                            }
                        }

                        //vertical2
                        for (int j = 0; j < vertical2.length; j++) {
                            verticalTxt2[j] = (TextView) findViewById(vertical2[j]);

                            if (v2) {
                                verticalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button));
                                verticalTxt2[j].setClickable(false);
                            } else {
                                verticalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                verticalTxt2[j].setClickable(true);
                                verticalTxt2[j].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        txtAns.setEnabled(true);
                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.showSoftInput(txtAns, InputMethodManager.SHOW_IMPLICIT);

                                        for (int j = 0; j < vertical2.length; j++) {
                                            txtHint.setText(verticalQuestions[index + 1]);
                                            words = verticalAnswers[index + 1];
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (!v1 || !v3 || !v4 || !v5) {
                                            for (int j = 0; j < vertical1.length; j++) {
                                                verticalTxt1[j] = (TextView) findViewById(vertical1[j]);
                                                verticalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < vertical3.length; j++) {
                                                verticalTxt3[j] = (TextView) findViewById(vertical3[j]);
                                                verticalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < vertical4.length; j++) {
                                                verticalTxt4[j] = (TextView) findViewById(vertical4[j]);
                                                verticalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < vertical5.length; j++) {
                                                verticalTxt5[j] = (TextView) findViewById(vertical5[j]);
                                                verticalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (v1) {
                                            for (int j = 0; j < vertical1.length; j++) {
                                                verticalTxt1[j] = (TextView) findViewById(vertical1[j]);
                                                verticalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (v3) {
                                            for (int j = 0; j < vertical3.length; j++) {
                                                verticalTxt3[j] = (TextView) findViewById(vertical3[j]);
                                                verticalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (v4) {
                                            for (int j = 0; j < vertical4.length; j++) {
                                                verticalTxt4[j] = (TextView) findViewById(vertical4[j]);
                                                verticalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (v5) {
                                            for (int j = 0; j < vertical5.length; j++) {
                                                verticalTxt5[j] = (TextView) findViewById(vertical5[j]);
                                                verticalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }
                                    }
                                });
                            }
                        }

                        //vertical3
                        for (int j = 0; j < vertical3.length; j++) {
                            verticalTxt3[j] = (TextView) findViewById(vertical3[j]);

                            if (v3) {
                                verticalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button));
                                verticalTxt3[j].setClickable(false);
                            } else {
                                verticalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                verticalTxt3[j].setClickable(true);
                                verticalTxt3[j].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        txtAns.setEnabled(true);
                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.showSoftInput(txtAns, InputMethodManager.SHOW_IMPLICIT);

                                        for (int j = 0; j < vertical3.length; j++) {
                                            txtHint.setText(verticalQuestions[index + 2]);
                                            words = verticalAnswers[index + 2];
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (!v1 || !v2 || !v4 || !v5) {
                                            for (int j = 0; j < vertical1.length; j++) {
                                                verticalTxt1[j] = (TextView) findViewById(vertical1[j]);
                                                verticalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < vertical2.length; j++) {
                                                verticalTxt2[j] = (TextView) findViewById(vertical2[j]);
                                                verticalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < vertical4.length; j++) {
                                                verticalTxt4[j] = (TextView) findViewById(vertical4[j]);
                                                verticalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < vertical5.length; j++) {
                                                verticalTxt5[j] = (TextView) findViewById(vertical5[j]);
                                                verticalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (v1) {
                                            for (int j = 0; j < vertical1.length; j++) {
                                                verticalTxt1[j] = (TextView) findViewById(vertical1[j]);
                                                verticalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (v2) {
                                            for (int j = 0; j < vertical2.length; j++) {
                                                verticalTxt2[j] = (TextView) findViewById(vertical2[j]);
                                                verticalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (v4) {
                                            for (int j = 0; j < vertical4.length; j++) {
                                                verticalTxt4[j] = (TextView) findViewById(vertical4[j]);
                                                verticalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (v5) {
                                            for (int j = 0; j < vertical5.length; j++) {
                                                verticalTxt5[j] = (TextView) findViewById(vertical5[j]);
                                                verticalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }
                                    }
                                });
                            }
                        }

                        //vertical4
                        for (int j = 0; j < vertical4.length; j++) {
                            verticalTxt4[j] = (TextView) findViewById(vertical4[j]);

                            if (v4) {
                                verticalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button));
                                verticalTxt4[j].setClickable(false);
                            } else {
                                verticalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                verticalTxt4[j].setClickable(true);
                                verticalTxt4[j].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        txtAns.setEnabled(true);
                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.showSoftInput(txtAns, InputMethodManager.SHOW_IMPLICIT);

                                        for (int j = 0; j < vertical4.length; j++) {
                                            txtHint.setText(verticalQuestions[index + 3]);
                                            words = verticalAnswers[index + 3];
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (!v1 || !v2 || !v3 || !v5) {
                                            for (int j = 0; j < vertical1.length; j++) {
                                                verticalTxt1[j] = (TextView) findViewById(vertical1[j]);
                                                verticalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < vertical2.length; j++) {
                                                verticalTxt2[j] = (TextView) findViewById(vertical2[j]);
                                                verticalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < vertical3.length; j++) {
                                                verticalTxt3[j] = (TextView) findViewById(vertical3[j]);
                                                verticalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < vertical5.length; j++) {
                                                verticalTxt5[j] = (TextView) findViewById(vertical5[j]);
                                                verticalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (v1) {
                                            for (int j = 0; j < vertical1.length; j++) {
                                                verticalTxt1[j] = (TextView) findViewById(vertical1[j]);
                                                verticalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (v2) {
                                            for (int j = 0; j < vertical2.length; j++) {
                                                verticalTxt2[j] = (TextView) findViewById(vertical2[j]);
                                                verticalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (v3) {
                                            for (int j = 0; j < vertical3.length; j++) {
                                                verticalTxt3[j] = (TextView) findViewById(vertical3[j]);
                                                verticalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (v5) {
                                            for (int j = 0; j < vertical5.length; j++) {
                                                verticalTxt5[j] = (TextView) findViewById(vertical5[j]);
                                                verticalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }
                                    }
                                });
                            }
                        }

                        //vertical5
                        for (int j = 0; j < vertical5.length; j++) {
                            verticalTxt5[j] = (TextView) findViewById(vertical5[j]);

                            if (v5) {
                                verticalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button));
                                verticalTxt5[j].setClickable(false);
                            } else {
                                verticalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                verticalTxt5[j].setClickable(true);
                                verticalTxt5[j].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        txtAns.setEnabled(true);
                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.showSoftInput(txtAns, InputMethodManager.SHOW_IMPLICIT);

                                        for (int j = 0; j < vertical5.length; j++) {
                                            txtHint.setText(verticalQuestions[index + 4]);
                                            words = verticalAnswers[index + 4];
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (!v1 || !v2 || !v3 || !v4) {
                                            for (int j = 0; j < vertical1.length; j++) {
                                                verticalTxt1[j] = (TextView) findViewById(vertical1[j]);
                                                verticalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < vertical2.length; j++) {
                                                verticalTxt2[j] = (TextView) findViewById(vertical2[j]);
                                                verticalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < vertical3.length; j++) {
                                                verticalTxt3[j] = (TextView) findViewById(vertical3[j]);
                                                verticalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < vertical4.length; j++) {
                                                verticalTxt4[j] = (TextView) findViewById(vertical4[j]);
                                                verticalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (v1) {
                                            for (int j = 0; j < vertical1.length; j++) {
                                                verticalTxt1[j] = (TextView) findViewById(vertical1[j]);
                                                verticalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (v2) {
                                            for (int j = 0; j < vertical2.length; j++) {
                                                verticalTxt2[j] = (TextView) findViewById(vertical2[j]);
                                                verticalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (v3) {
                                            for (int j = 0; j < vertical3.length; j++) {
                                                verticalTxt3[j] = (TextView) findViewById(vertical3[j]);
                                                verticalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (v4) {
                                            for (int j = 0; j < vertical4.length; j++) {
                                                verticalTxt4[j] = (TextView) findViewById(vertical4[j]);
                                                verticalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }

                    for (int i = 0; i < horizontalArray.length; i++) {
                        //horizontal1
                        for (int j = 0; j < horizontal1.length; j++) {
                            horizontalTxt1[j] = (TextView) findViewById(horizontal1[0]);
                            horizontalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button));
                            horizontalTxt1[j].setClickable(false);
                        }

                        for (int j = 0; j < horizontal1.length; j++) {
                            horizontalTxt1[j] = (TextView) findViewById(horizontal1[1]);
                            horizontalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button));
                            horizontalTxt1[j].setClickable(false);
                        }

                        for (int j = 0; j < horizontal1.length; j++) {
                            horizontalTxt1[j] = (TextView) findViewById(horizontal1[2]);
                            horizontalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button));
                            horizontalTxt1[j].setClickable(false);
                        }

                        for (int j = 0; j < horizontal1.length; j++) {
                            horizontalTxt1[j] = (TextView) findViewById(horizontal1[3]);
                            horizontalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button));
                            horizontalTxt1[j].setClickable(false);
                        }

                        //horizontal2
                        for (int j = 0; j < horizontal2.length; j++) {
                            horizontalTxt2[j] = (TextView) findViewById(horizontal2[0]);
                            horizontalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button));
                            horizontalTxt2[j].setClickable(false);
                        }

                        for (int j = 0; j < horizontal2.length; j++) {
                            horizontalTxt2[j] = (TextView) findViewById(horizontal2[2]);
                            horizontalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button));
                            horizontalTxt2[j].setClickable(false);
                        }

                        for (int j = 0; j < horizontal2.length; j++) {
                            horizontalTxt2[j] = (TextView) findViewById(horizontal2[4]);
                            horizontalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button));
                            horizontalTxt2[j].setClickable(false);
                        }

                        for (int j = 0; j < horizontal2.length; j++) {
                            horizontalTxt2[j] = (TextView) findViewById(horizontal2[6]);
                            horizontalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button));
                            horizontalTxt2[j].setClickable(false);
                        }

                        //horizontal3
                        for (int j = 0; j < horizontal3.length; j++) {
                            horizontalTxt3[j] = (TextView) findViewById(horizontal3[0]);
                            horizontalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button));
                            horizontalTxt3[j].setClickable(false);
                        }

                        for (int j = 0; j < horizontal3.length; j++) {
                            horizontalTxt3[j] = (TextView) findViewById(horizontal3[2]);
                            horizontalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button));
                            horizontalTxt3[j].setClickable(false);
                        }

                        for (int j = 0; j < horizontal3.length; j++) {
                            horizontalTxt3[j] = (TextView) findViewById(horizontal3[3]);
                            horizontalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button));
                            horizontalTxt3[j].setClickable(false);
                        }

                        for (int j = 0; j < horizontal3.length; j++) {
                            horizontalTxt3[j] = (TextView) findViewById(horizontal3[4]);
                            horizontalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button));
                            horizontalTxt3[j].setClickable(false);
                        }

                        //horizontal4
                        for (int j = 0; j < horizontal4.length; j++) {
                            horizontalTxt4[j] = (TextView) findViewById(horizontal4[0]);
                            horizontalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button));
                            horizontalTxt4[j].setClickable(false);
                        }

                        for (int j = 0; j < horizontal4.length; j++) {
                            horizontalTxt4[j] = (TextView) findViewById(horizontal4[1]);
                            horizontalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button));
                            horizontalTxt4[j].setClickable(false);
                        }

                        for (int j = 0; j < horizontal4.length; j++) {
                            horizontalTxt4[j] = (TextView) findViewById(horizontal4[3]);
                            horizontalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button));
                            horizontalTxt4[j].setClickable(false);
                        }

                        for (int j = 0; j < horizontal4.length; j++) {
                            horizontalTxt4[j] = (TextView) findViewById(horizontal4[5]);
                            horizontalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button));
                            horizontalTxt4[j].setClickable(false);
                        }

                        //horizontal5
                        for (int j = 0; j < horizontal5.length; j++) {
                            horizontalTxt5[j] = (TextView) findViewById(horizontal5[0]);
                            horizontalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button));
                            horizontalTxt5[j].setClickable(false);
                        }

                        for (int j = 0; j < horizontal5.length; j++) {
                            horizontalTxt5[j] = (TextView) findViewById(horizontal5[2]);
                            horizontalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button));
                            horizontalTxt5[j].setClickable(false);
                        }
                    }
                } else if (btnChange.getText().toString().equalsIgnoreCase("Horizontal")) {
                    btnChange.setText("Vertical");

                    if (v1 && v2 && v3 && v4 && v5) {
                        btnChange.setAlpha(0.5f);
                        btnChange.setClickable(false);
                    }

                    txtAns.setEnabled(false);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(txtAns, InputMethodManager.SHOW_FORCED);

                    txtHint.setText("");
                    for (int i = 0; i < horizontalArray.length; i++) {
                        //horizontal1
                        for (int j = 0; j < horizontal1.length; j++) {
                            horizontalTxt1[j] = (TextView) findViewById(horizontal1[j]);

                            if (h1) {
                                horizontalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button));
                                horizontalTxt1[j].setClickable(false);
                            } else {
                                horizontalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                horizontalTxt1[j].setClickable(true);
                                horizontalTxt1[j].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        txtAns.setEnabled(true);
                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.showSoftInput(txtAns, InputMethodManager.SHOW_IMPLICIT);

                                        for (int j = 0; j < horizontal1.length; j++) {
                                            txtHint.setText(horizontalQuestions[index]);
                                            words = horizontalAnswers[index];
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (!h2 || !h3 || !h4 || !h5) {
                                            for (int j = 0; j < horizontal2.length; j++) {
                                                horizontalTxt2[j] = (TextView) findViewById(horizontal2[j]);
                                                horizontalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < horizontal3.length; j++) {
                                                horizontalTxt3[j] = (TextView) findViewById(horizontal3[j]);
                                                horizontalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < horizontal4.length; j++) {
                                                horizontalTxt4[j] = (TextView) findViewById(horizontal4[j]);
                                                horizontalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < horizontal5.length; j++) {
                                                horizontalTxt5[j] = (TextView) findViewById(horizontal5[j]);
                                                horizontalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (h2) {
                                            for (int j = 0; j < horizontal2.length; j++) {
                                                horizontalTxt2[j] = (TextView) findViewById(horizontal2[j]);
                                                horizontalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (h3) {
                                            for (int j = 0; j < horizontal3.length; j++) {
                                                horizontalTxt3[j] = (TextView) findViewById(horizontal3[j]);
                                                horizontalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (h4) {
                                            for (int j = 0; j < horizontal4.length; j++) {
                                                horizontalTxt4[j] = (TextView) findViewById(horizontal4[j]);
                                                horizontalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (h5) {
                                            for (int j = 0; j < horizontal5.length; j++) {
                                                horizontalTxt5[j] = (TextView) findViewById(horizontal5[j]);
                                                horizontalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }
                                    }
                                });
                            }
                        }

                        //horizontal2
                        for (int j = 0; j < horizontal2.length; j++) {
                            horizontalTxt2[j] = (TextView) findViewById(horizontal2[j]);

                            if (h2) {
                                horizontalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button));
                                horizontalTxt2[j].setClickable(false);
                            } else {
                                horizontalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                horizontalTxt2[j].setClickable(true);
                                horizontalTxt2[j].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        txtAns.setEnabled(true);
                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.showSoftInput(txtAns, InputMethodManager.SHOW_IMPLICIT);

                                        for (int j = 0; j < horizontal2.length; j++) {
                                            txtHint.setText(horizontalQuestions[index + 1]);
                                            words = horizontalAnswers[index + 1];
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (!h1 || !h3 || !h4 || !h5) {
                                            for (int j = 0; j < horizontal1.length; j++) {
                                                horizontalTxt1[j] = (TextView) findViewById(horizontal1[j]);
                                                horizontalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < horizontal3.length; j++) {
                                                horizontalTxt3[j] = (TextView) findViewById(horizontal3[j]);
                                                horizontalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < horizontal4.length; j++) {
                                                horizontalTxt4[j] = (TextView) findViewById(horizontal4[j]);
                                                horizontalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < horizontal5.length; j++) {
                                                horizontalTxt5[j] = (TextView) findViewById(horizontal5[j]);
                                                horizontalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (h1) {
                                            for (int j = 0; j < horizontal1.length; j++) {
                                                horizontalTxt1[j] = (TextView) findViewById(horizontal1[j]);
                                                horizontalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (h3) {
                                            for (int j = 0; j < horizontal3.length; j++) {
                                                horizontalTxt3[j] = (TextView) findViewById(horizontal3[j]);
                                                horizontalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (h4) {
                                            for (int j = 0; j < horizontal4.length; j++) {
                                                horizontalTxt4[j] = (TextView) findViewById(horizontal4[j]);
                                                horizontalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (h5) {
                                            for (int j = 0; j < horizontal5.length; j++) {
                                                horizontalTxt5[j] = (TextView) findViewById(horizontal5[j]);
                                                horizontalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }
                                    }
                                });
                            }
                        }

                        //horizontal3
                        for (int j = 0; j < horizontal3.length; j++) {
                            horizontalTxt3[j] = (TextView) findViewById(horizontal3[j]);

                            if (h3) {
                                horizontalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button));
                                horizontalTxt3[j].setClickable(false);
                            } else {
                                horizontalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                horizontalTxt3[j].setClickable(true);
                                horizontalTxt3[j].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        txtAns.setEnabled(true);
                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.showSoftInput(txtAns, InputMethodManager.SHOW_IMPLICIT);

                                        for (int j = 0; j < horizontal3.length; j++) {
                                            txtHint.setText(horizontalQuestions[index + 2]);
                                            words = horizontalAnswers[index + 2];
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (!h1 || !h2 || !h4 || !h5) {
                                            for (int j = 0; j < horizontal1.length; j++) {
                                                horizontalTxt1[j] = (TextView) findViewById(horizontal1[j]);
                                                horizontalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < horizontal2.length; j++) {
                                                horizontalTxt2[j] = (TextView) findViewById(horizontal2[j]);
                                                horizontalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < horizontal4.length; j++) {
                                                horizontalTxt4[j] = (TextView) findViewById(horizontal4[j]);
                                                horizontalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < horizontal5.length; j++) {
                                                horizontalTxt5[j] = (TextView) findViewById(horizontal5[j]);
                                                horizontalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (h1) {
                                            for (int j = 0; j < horizontal1.length; j++) {
                                                horizontalTxt1[j] = (TextView) findViewById(horizontal1[j]);
                                                horizontalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (h2) {
                                            for (int j = 0; j < horizontal2.length; j++) {
                                                horizontalTxt2[j] = (TextView) findViewById(horizontal2[j]);
                                                horizontalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (h4) {
                                            for (int j = 0; j < horizontal4.length; j++) {
                                                horizontalTxt4[j] = (TextView) findViewById(horizontal4[j]);
                                                horizontalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (h5) {
                                            for (int j = 0; j < horizontal5.length; j++) {
                                                horizontalTxt5[j] = (TextView) findViewById(horizontal5[j]);
                                                horizontalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }
                                    }
                                });
                            }
                        }

                        //horizontal4
                        for (int j = 0; j < horizontal4.length; j++) {
                            horizontalTxt4[j] = (TextView) findViewById(horizontal4[j]);

                            if (h4) {
                                horizontalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button));
                                horizontalTxt4[j].setClickable(false);
                            } else {
                                horizontalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                horizontalTxt4[j].setClickable(true);
                                horizontalTxt4[j].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        txtAns.setEnabled(true);
                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.showSoftInput(txtAns, InputMethodManager.SHOW_IMPLICIT);

                                        for (int j = 0; j < horizontal4.length; j++) {
                                            txtHint.setText(horizontalQuestions[index + 3]);
                                            words = horizontalAnswers[index + 3];
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (!h1 || !h2 || !h3 || !h5) {
                                            for (int j = 0; j < horizontal1.length; j++) {
                                                horizontalTxt1[j] = (TextView) findViewById(horizontal1[j]);
                                                horizontalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < horizontal2.length; j++) {
                                                horizontalTxt2[j] = (TextView) findViewById(horizontal2[j]);
                                                horizontalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < horizontal3.length; j++) {
                                                horizontalTxt3[j] = (TextView) findViewById(horizontal3[j]);
                                                horizontalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < horizontal5.length; j++) {
                                                horizontalTxt5[j] = (TextView) findViewById(horizontal5[j]);
                                                horizontalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (h1) {
                                            for (int j = 0; j < horizontal1.length; j++) {
                                                horizontalTxt1[j] = (TextView) findViewById(horizontal1[j]);
                                                horizontalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (h2) {
                                            for (int j = 0; j < horizontal2.length; j++) {
                                                horizontalTxt2[j] = (TextView) findViewById(horizontal2[j]);
                                                horizontalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (h3) {
                                            for (int j = 0; j < horizontal3.length; j++) {
                                                horizontalTxt3[j] = (TextView) findViewById(horizontal3[j]);
                                                horizontalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (h5) {
                                            for (int j = 0; j < horizontal5.length; j++) {
                                                horizontalTxt5[j] = (TextView) findViewById(horizontal5[j]);
                                                horizontalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }
                                    }
                                });
                            }
                        }

                        //horizontal5
                        for (int j = 0; j < horizontal5.length; j++) {
                            horizontalTxt5[j] = (TextView) findViewById(horizontal5[j]);

                            if (h5) {
                                horizontalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button));
                                horizontalTxt5[j].setClickable(false);
                            } else {
                                horizontalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                horizontalTxt5[j].setClickable(true);
                                horizontalTxt5[j].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        txtAns.setEnabled(true);
                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.showSoftInput(txtAns, InputMethodManager.SHOW_IMPLICIT);

                                        for (int j = 0; j < horizontal5.length; j++) {
                                            txtHint.setText(horizontalQuestions[index + 4]);
                                            words = horizontalAnswers[index + 4];
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (!h1 || !h2 || !h3 || !h4) {
                                            for (int j = 0; j < horizontal1.length; j++) {
                                                horizontalTxt1[j] = (TextView) findViewById(horizontal1[j]);
                                                horizontalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < horizontal2.length; j++) {
                                                horizontalTxt2[j] = (TextView) findViewById(horizontal2[j]);
                                                horizontalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < horizontal3.length; j++) {
                                                horizontalTxt3[j] = (TextView) findViewById(horizontal3[j]);
                                                horizontalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }

                                            for (int j = 0; j < horizontal4.length; j++) {
                                                horizontalTxt4[j] = (TextView) findViewById(horizontal4[j]);
                                                horizontalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button2));
                                            }
                                        }

                                        //----------------------------------------------------------------------------------------------------
                                        if (h1) {
                                            for (int j = 0; j < horizontal1.length; j++) {
                                                horizontalTxt1[j] = (TextView) findViewById(horizontal1[j]);
                                                horizontalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (h2) {
                                            for (int j = 0; j < horizontal2.length; j++) {
                                                horizontalTxt2[j] = (TextView) findViewById(horizontal2[j]);
                                                horizontalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (h3) {
                                            for (int j = 0; j < horizontal3.length; j++) {
                                                horizontalTxt3[j] = (TextView) findViewById(horizontal3[j]);
                                                horizontalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }

                                        if (h4) {
                                            for (int j = 0; j < horizontal4.length; j++) {
                                                horizontalTxt4[j] = (TextView) findViewById(horizontal4[j]);
                                                horizontalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button));
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }

                    for (int i = 0; i < verticalArray.length; i++) {
                        //vertical1
                        for (int j = 0; j < vertical1.length; j++) {
                            verticalTxt1[j] = (TextView) findViewById(vertical1[1]);
                            verticalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button));
                            verticalTxt1[j].setClickable(false);
                        }

                        for (int j = 0; j < vertical1.length; j++) {
                            verticalTxt1[j] = (TextView) findViewById(vertical1[2]);
                            verticalTxt1[j].setBackground(getResources().getDrawable(R.drawable.button));
                            verticalTxt1[j].setClickable(false);
                        }

                        //vertical2
                        for (int j = 0; j < vertical2.length; j++) {
                            verticalTxt2[j] = (TextView) findViewById(vertical2[1]);
                            verticalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button));
                            verticalTxt2[j].setClickable(false);
                        }

                        for (int j = 0; j < vertical2.length; j++) {
                            verticalTxt2[j] = (TextView) findViewById(vertical2[2]);
                            verticalTxt2[j].setBackground(getResources().getDrawable(R.drawable.button));
                            verticalTxt2[j].setClickable(false);
                        }

                        //vertical3
                        for (int j = 0; j < vertical3.length; j++) {
                            verticalTxt3[j] = (TextView) findViewById(vertical3[1]);
                            verticalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button));
                            verticalTxt3[j].setClickable(false);
                        }

                        for (int j = 0; j < vertical3.length; j++) {
                            verticalTxt3[j] = (TextView) findViewById(vertical3[2]);
                            verticalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button));
                            verticalTxt3[j].setClickable(false);
                        }

                        for (int j = 0; j < vertical3.length; j++) {
                            verticalTxt3[j] = (TextView) findViewById(vertical3[4]);
                            verticalTxt3[j].setBackground(getResources().getDrawable(R.drawable.button));
                            verticalTxt3[j].setClickable(false);
                        }

                        //vertical4
                        for (int j = 0; j < vertical4.length; j++) {
                            verticalTxt4[j] = (TextView) findViewById(vertical4[1]);
                            verticalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button));
                            verticalTxt4[j].setClickable(false);
                        }

                        for (int j = 0; j < vertical4.length; j++) {
                            verticalTxt4[j] = (TextView) findViewById(vertical4[3]);
                            verticalTxt4[j].setBackground(getResources().getDrawable(R.drawable.button));
                            verticalTxt4[j].setClickable(false);
                        }

                        //vertical5
                        for (int j = 0; j < vertical5.length; j++) {
                            verticalTxt5[j] = (TextView) findViewById(vertical5[0]);
                            verticalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button));
                            verticalTxt5[j].setClickable(false);
                        }

                        for (int j = 0; j < vertical5.length; j++) {
                            verticalTxt5[j] = (TextView) findViewById(vertical5[2]);
                            verticalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button));
                            verticalTxt5[j].setClickable(false);
                        }

                        for (int j = 0; j < vertical5.length; j++) {
                            verticalTxt5[j] = (TextView) findViewById(vertical5[3]);
                            verticalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button));
                            verticalTxt5[j].setClickable(false);
                        }

                        for (int j = 0; j < vertical5.length; j++) {
                            verticalTxt5[j] = (TextView) findViewById(vertical5[4]);
                            verticalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button));
                            verticalTxt5[j].setClickable(false);
                        }

                        for (int j = 0; j < vertical5.length; j++) {
                            verticalTxt5[j] = (TextView) findViewById(vertical5[5]);
                            verticalTxt5[j].setBackground(getResources().getDrawable(R.drawable.button));
                            verticalTxt5[j].setClickable(false);
                        }
                    }
                }
            }
        });

        txtAns.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String answer = txtAns.getText().toString().trim();

                if (words.length() != answer.length()) {
                    return;
                }

                if (answer.equalsIgnoreCase(words)) {
                    Toast.makeText(getApplicationContext(), "Congratulations", Toast.LENGTH_SHORT).show();
                    txtAns.setText("");
                    txtAns.setEnabled(false);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(txtAns, InputMethodManager.SHOW_FORCED);

                    if (words.equalsIgnoreCase(verticalAnswers[index])) {
                        v1 = true;

                        for (int i = 0; i < vertical1.length; i++) {
                            answer1Array = words.split("(?!^)");
                            verticalTxt1[i] = (TextView) findViewById(vertical1[i]);
                            verticalTxt1[i].setText(answer1Array[i]);
                            verticalTxt1[i].setAllCaps(true);
                            verticalTxt1[i].setClickable(false);
                            verticalTxt1[i].setBackground(getResources().getDrawable(R.drawable.button));
                        }
                    }

                    if (words.equalsIgnoreCase(verticalAnswers[index + 1])) {
                        v2 = true;

                        for (int i = 0; i < vertical2.length; i++) {
                            answer2Array = words.split("(?!^)");
                            verticalTxt2[i] = (TextView) findViewById(vertical2[i]);
                            verticalTxt2[i].setText(answer2Array[i]);
                            verticalTxt2[i].setAllCaps(true);
                            verticalTxt2[i].setClickable(false);
                            verticalTxt2[i].setBackground(getResources().getDrawable(R.drawable.button));
                        }
                    }

                    if (words.equalsIgnoreCase(verticalAnswers[index + 2])) {
                        v3 = true;

                        for (int i = 0; i < vertical3.length; i++) {
                            answer3Array = words.split("(?!^)");
                            verticalTxt3[i] = (TextView) findViewById(vertical3[i]);
                            verticalTxt3[i].setText(answer3Array[i]);
                            verticalTxt3[i].setAllCaps(true);
                            verticalTxt3[i].setClickable(false);
                            verticalTxt3[i].setBackground(getResources().getDrawable(R.drawable.button));
                        }
                    }

                    if (words.equalsIgnoreCase(verticalAnswers[index + 3])) {
                        v4 = true;

                        for (int i = 0; i < vertical4.length; i++) {
                            answer4Array = words.split("(?!^)");
                            verticalTxt4[i] = (TextView) findViewById(vertical4[i]);
                            verticalTxt4[i].setText(answer4Array[i]);
                            verticalTxt4[i].setAllCaps(true);
                            verticalTxt4[i].setClickable(false);
                            verticalTxt4[i].setBackground(getResources().getDrawable(R.drawable.button));
                        }
                    }

                    if (words.equalsIgnoreCase(verticalAnswers[index + 4])) {
                        v5 = true;

                        for (int i = 0; i < vertical5.length; i++) {
                            answer5Array = words.split("(?!^)");
                            verticalTxt5[i] = (TextView) findViewById(vertical5[i]);
                            verticalTxt5[i].setText(answer5Array[i]);
                            verticalTxt5[i].setAllCaps(true);
                            verticalTxt5[i].setClickable(false);
                            verticalTxt5[i].setBackground(getResources().getDrawable(R.drawable.button));
                        }
                    }

                    if (words.equalsIgnoreCase(horizontalAnswers[index])) {
                        h1 = true;

                        for (int i = 0; i < horizontal1.length; i++) {
                            answer6Array = words.split("(?!^)");
                            horizontalTxt1[i] = (TextView) findViewById(horizontal1[i]);
                            horizontalTxt1[i].setText(answer6Array[i]);
                            horizontalTxt1[i].setAllCaps(true);
                            horizontalTxt1[i].setClickable(false);
                            horizontalTxt1[i].setBackground(getResources().getDrawable(R.drawable.button));
                        }
                    }

                    if (words.equalsIgnoreCase(horizontalAnswers[index + 1])) {
                        h2 = true;

                        for (int i = 0; i < horizontal2.length; i++) {
                            answer7Array = words.split("(?!^)");
                            horizontalTxt2[i] = (TextView) findViewById(horizontal2[i]);
                            horizontalTxt2[i].setText(answer7Array[i]);
                            horizontalTxt2[i].setAllCaps(true);
                            horizontalTxt2[i].setClickable(false);
                            horizontalTxt2[i].setBackground(getResources().getDrawable(R.drawable.button));
                        }
                    }

                    if (words.equalsIgnoreCase(horizontalAnswers[index + 2])) {
                        h3 = true;

                        for (int i = 0; i < horizontal3.length; i++) {
                            answer8Array = words.split("(?!^)");
                            horizontalTxt3[i] = (TextView) findViewById(horizontal3[i]);
                            horizontalTxt3[i].setText(answer8Array[i]);
                            horizontalTxt3[i].setAllCaps(true);
                            horizontalTxt3[i].setClickable(false);
                            horizontalTxt3[i].setBackground(getResources().getDrawable(R.drawable.button));
                        }
                    }

                    if (words.equalsIgnoreCase(horizontalAnswers[index + 3])) {
                        h4 = true;

                        for (int i = 0; i < horizontal4.length; i++) {
                            answer9Array = words.split("(?!^)");
                            horizontalTxt4[i] = (TextView) findViewById(horizontal4[i]);
                            horizontalTxt4[i].setText(answer9Array[i]);
                            horizontalTxt4[i].setAllCaps(true);
                            horizontalTxt4[i].setClickable(false);
                            horizontalTxt4[i].setBackground(getResources().getDrawable(R.drawable.button));
                        }
                    }

                    if (words.equalsIgnoreCase(horizontalAnswers[index + 4])) {
                        h5 = true;

                        for (int i = 0; i < horizontal5.length; i++) {
                            answer10Array = words.split("(?!^)");
                            horizontalTxt5[i] = (TextView) findViewById(horizontal5[i]);
                            horizontalTxt5[i].setText(answer10Array[i]);
                            horizontalTxt5[i].setAllCaps(true);
                            horizontalTxt5[i].setClickable(false);
                            horizontalTxt5[i].setBackground(getResources().getDrawable(R.drawable.button));
                        }
                    }

                    doneCount++;
                    points.setText(String.valueOf(doneCount));

                    txtHint.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Try again!", Toast.LENGTH_SHORT).show();
                    txtAns.setText("");
                }
            }
        });
    }

    public void startStop() {
        if (timerRunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                minutes = (int) (millisUntilFinished / 1000) / 60;
                seconds = (int) (millisUntilFinished/ 1000) % 60;

                timeLeftText = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                timer.setText(timeLeftText);

                if (minutes == 0 && seconds < 10) {
                    timer.setTextColor(Color.RED);
                }

                if (seconds > 0 && doneCount == 10) {
                    stopTimer();
                    showAlertBox2();
                }

                if (minutes > 0) {
                    timeUsedText = "" + minutes + " minutes " + seconds + " seconds";
                }

                if (minutes == 0) {
                    timeUsedText = "" + seconds + " seconds";
                }
            }

            @Override
            public void onFinish() {
                showAlertBox1();
            }
        }.start();

        timerRunning = true;
    }

    public void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    public void showAlertBox() {
        AlertDialog.Builder box = new AlertDialog.Builder(this);
        startDialog = box.create();
        startDialog.setTitle("Do you want to play?");
        startDialog.setMessage("Click 'Start' to play!");
        startDialog.setButton(Dialog.BUTTON_POSITIVE, "Start", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                startStop();
            }
        });
        startDialog.setCancelable(false);
        startDialog.show();
    }

    public void showAlertBox1() {
        AlertDialog.Builder box = new AlertDialog.Builder(this);
        restartDialog1 = box.create();
        restartDialog1.setTitle("Good Game!");
        restartDialog1.setMessage("You answered " + doneCount + " question. " + '\n' + "Play again?");
        restartDialog1.setButton(Dialog.BUTTON_POSITIVE, "Restart", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                restartGame = true;

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor e = sp.edit();
                e.clear();
                e.apply();

                finish();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                showAlertBox();
            }
        });
        restartDialog1.setCancelable(false);
        restartDialog1.show();
    }

    public void showAlertBox2() {
        AlertDialog.Builder box = new AlertDialog.Builder(this);
        restartDialog2 = box.create();
        restartDialog2.setTitle("Congratulations");
        restartDialog2.setMessage("You answered " + doneCount + " questions with " + timeUsedText + " left on the clock." + '\n' + "Play again?");
        restartDialog2.setButton(Dialog.BUTTON_POSITIVE, "Restart", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                restartGame = true;

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor e = sp.edit();
                e.clear();
                e.apply();

                finish();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                showAlertBox();

            }
        });
        restartDialog2.setCancelable(false);
        restartDialog2.show();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (!restartGame){
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor e = sp.edit();

            String answer1 = Arrays.toString(answer1Array).replace("[", "").replace("]", "").replace(",", "").replace(" ", "");
            String answer2 = Arrays.toString(answer2Array).replace("[", "").replace("]", "").replace(",", "").replace(" ", "");
            String answer3 = Arrays.toString(answer3Array).replace("[", "").replace("]", "").replace(",", "").replace(" ", "");
            String answer4 = Arrays.toString(answer4Array).replace("[", "").replace("]", "").replace(",", "").replace(" ", "");
            String answer5 = Arrays.toString(answer5Array).replace("[", "").replace("]", "").replace(",", "").replace(" ", "");
            String answer6 = Arrays.toString(answer6Array).replace("[", "").replace("]", "").replace(",", "").replace(" ", "");
            String answer7 = Arrays.toString(answer7Array).replace("[", "").replace("]", "").replace(",", "").replace(" ", "");
            String answer8 = Arrays.toString(answer8Array).replace("[", "").replace("]", "").replace(",", "").replace(" ", "");
            String answer9 = Arrays.toString(answer9Array).replace("[", "").replace("]", "").replace(",", "").replace(" ", "");
            String answer10 = Arrays.toString(answer10Array).replace("[", "").replace("]", "").replace(",", "").replace(" ", "");

            e.putString("answer1", answer1);
            e.putString("answer2", answer2);
            e.putString("answer3", answer3);
            e.putString("answer4", answer4);
            e.putString("answer5", answer5);
            e.putString("answer6", answer6);
            e.putString("answer7", answer7);
            e.putString("answer8", answer8);
            e.putString("answer9", answer9);
            e.putString("answer10", answer10);
            e.putLong("time", timeLeftInMilliseconds);
            e.putInt("point", doneCount);
            e.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        if (sp.contains("answer1")) {
            String text1 = sp.getString("answer1", "");
            if (text1 != null && !text1.equalsIgnoreCase("null") && !text1.equals("")) {
                answer1Array = text1.split("(?!^)");
                for (int i = 0; i < text1.length(); i++) {
                    v1 = true;
                    verticalTxt1[i] = (TextView) findViewById(vertical1[i]);
                    verticalTxt1[i].setText(answer1Array[i]);
                    verticalTxt1[i].setAllCaps(true);
                    verticalTxt1[i].setClickable(false);
                    verticalTxt1[i].setBackground(getResources().getDrawable(R.drawable.button));
                }
            }
        }

        if (sp.contains("answer2")) {
            String text2 = sp.getString("answer2", "");
            if (text2 != null && !text2.equalsIgnoreCase("null") && !text2.equals("")) {
                answer2Array = text2.split("(?!^)");
                for (int i = 0; i < text2.length(); i++) {
                    v2 = true;
                    verticalTxt2[i] = (TextView) findViewById(vertical2[i]);
                    verticalTxt2[i].setText(answer2Array[i]);
                    verticalTxt2[i].setAllCaps(true);
                    verticalTxt2[i].setClickable(false);
                    verticalTxt2[i].setBackground(getResources().getDrawable(R.drawable.button));
                }
            }
        }

        if (sp.contains("answer3")) {
            String text3 = sp.getString("answer3", "");
            if (text3 != null && !text3.equalsIgnoreCase("null") && !text3.equals("")) {
                answer3Array = text3.split("(?!^)");
                for (int i = 0; i < text3.length(); i++) {
                    v3 = true;
                    verticalTxt3[i] = (TextView) findViewById(vertical3[i]);
                    verticalTxt3[i].setText(answer3Array[i]);
                    verticalTxt3[i].setAllCaps(true);
                    verticalTxt3[i].setClickable(false);
                    verticalTxt3[i].setBackground(getResources().getDrawable(R.drawable.button));
                }
            }
        }

        if (sp.contains("answer4")) {
            String text4 = sp.getString("answer4", "");
            if (text4 != null && !text4.equalsIgnoreCase("null") && !text4.equals("")) {
                answer4Array = text4.split("(?!^)");
                for (int i = 0; i < text4.length(); i++) {
                    v4 = true;
                    verticalTxt4[i] = (TextView) findViewById(vertical4[i]);
                    verticalTxt4[i].setText(answer4Array[i]);
                    verticalTxt4[i].setAllCaps(true);
                    verticalTxt4[i].setClickable(false);
                    verticalTxt4[i].setBackground(getResources().getDrawable(R.drawable.button));
                }
            }
        }

        if (sp.contains("answer5")) {
            String text5 = sp.getString("answer5", "");
            if (text5 != null && !text5.equalsIgnoreCase("null") && !text5.equals("")) {
                answer5Array = text5.split("(?!^)");
                for (int i = 0; i < text5.length(); i++) {
                    v5 = true;
                    verticalTxt5[i] = (TextView) findViewById(vertical5[i]);
                    verticalTxt5[i].setText(answer5Array[i]);
                    verticalTxt5[i].setAllCaps(true);
                    verticalTxt5[i].setClickable(false);
                    verticalTxt5[i].setBackground(getResources().getDrawable(R.drawable.button));
                }
            }
        }

        if (sp.contains("answer6")) {
            String text6 = sp.getString("answer6", "");
            if (text6 != null && !text6.equalsIgnoreCase("null") && !text6.equals("")) {
                answer6Array = text6.split("(?!^)");
                for (int i = 0; i < text6.length(); i++) {
                    h1 = true;
                    horizontalTxt1[i] = (TextView) findViewById(horizontal1[i]);
                    horizontalTxt1[i].setText(answer6Array[i]);
                    horizontalTxt1[i].setAllCaps(true);
                    horizontalTxt1[i].setClickable(false);
                    horizontalTxt1[i].setBackground(getResources().getDrawable(R.drawable.button));
                }
            }
        }

        if (sp.contains("answer7")) {
            String text7 = sp.getString("answer7", "");
            if (text7 != null && !text7.equalsIgnoreCase("null") && !text7.equals("")) {
                answer7Array = text7.split("(?!^)");
                for (int i = 0; i < text7.length(); i++) {
                    h2 = true;
                    horizontalTxt2[i] = (TextView) findViewById(horizontal2[i]);
                    horizontalTxt2[i].setText(answer7Array[i]);
                    horizontalTxt2[i].setAllCaps(true);
                    horizontalTxt2[i].setClickable(false);
                    horizontalTxt2[i].setBackground(getResources().getDrawable(R.drawable.button));
                }
            }
        }

        if (sp.contains("answer8")) {
            String text8 = sp.getString("answer8", "");
            if (text8 != null && !text8.equalsIgnoreCase("null") && !text8.equals("")) {
                answer8Array = text8.split("(?!^)");
                for (int i = 0; i < text8.length(); i++) {
                    h3 = true;
                    horizontalTxt3[i] = (TextView) findViewById(horizontal3[i]);
                    horizontalTxt3[i].setText(answer8Array[i]);
                    horizontalTxt3[i].setAllCaps(true);
                    horizontalTxt3[i].setClickable(false);
                    horizontalTxt3[i].setBackground(getResources().getDrawable(R.drawable.button));
                }
            }
        }

        if (sp.contains("answer9")) {
            String text9 = sp.getString("answer9", "");
            if (text9 != null && !text9.equalsIgnoreCase("null") && !text9.equals("")) {
                answer9Array = text9.split("(?!^)");
                for (int i = 0; i < text9.length(); i++) {
                    h4 = true;
                    horizontalTxt4[i] = (TextView) findViewById(horizontal4[i]);
                    horizontalTxt4[i].setText(answer9Array[i]);
                    horizontalTxt4[i].setAllCaps(true);
                    horizontalTxt4[i].setClickable(false);
                    horizontalTxt4[i].setBackground(getResources().getDrawable(R.drawable.button));
                }
            }
        }

        if (sp.contains("answer10")) {
            String text10 = sp.getString("answer10", "");
            if (text10 != null && !text10.equalsIgnoreCase("null") && !text10.equals("")) {
                answer10Array = text10.split("(?!^)");
                for (int i = 0; i < text10.length(); i++) {
                    h5 = true;
                    horizontalTxt5[i] = (TextView) findViewById(horizontal5[i]);
                    horizontalTxt5[i].setText(answer10Array[i]);
                    horizontalTxt5[i].setAllCaps(true);
                    horizontalTxt5[i].setClickable(false);
                    horizontalTxt5[i].setBackground(getResources().getDrawable(R.drawable.button));
                }
            }
        }

        if (sp.getLong("time", 0) != 0) {
            timeLeftInMilliseconds = sp.getLong("time", 0);
            minutes = (int) (timeLeftInMilliseconds / 1000) / 1000;
            seconds = (int) (timeLeftInMilliseconds / 1000) % 1000;
            timeLeftText = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            timer.setText(timeLeftText);

            if (minutes == 0 && seconds < 10) {
                timer.setTextColor(Color.RED);
            }
        }

        doneCount = sp.getInt("point", 0);
        points.setText(String.valueOf(doneCount));

        if (!firstTime) {
            AlertDialog.Builder box = new AlertDialog.Builder(this);
            startDialog = box.create();
            startDialog.setTitle("Paused");
            startDialog.setMessage("Click 'Resume' to continue where you left off!");
            startDialog.setButton(Dialog.BUTTON_POSITIVE, "Resume", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){
                    startStop();
                }
            });
            startDialog.setCancelable(false);
            startDialog.show();

            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("dialogShown", false);
            e.apply();
        }

        if (v1 && v2 && v3 && v4 && v5) {
            btnChange.setText("Horizontal");
        } else if (h1 && h2 && h3 && h4 && h5) {
            btnChange.setText("Vertical");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
