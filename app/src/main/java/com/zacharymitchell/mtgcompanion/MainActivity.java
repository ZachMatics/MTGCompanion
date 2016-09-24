package com.zacharymitchell.mtgcompanion;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import android.widget.SimpleCursorAdapter;


public class MainActivity extends AppCompatActivity {

    private ViewGroup FrameLayout, menuRelativeLayoutTop, menuRelativeLayoutBottom, menuTop,
            menuBottom, menuCenter, menuNumbers, pictureMenu, diceMenu, historyMenu, blackScreen;
    private Button plusTop, minusTop, plusBottom, minusBottom, fireTopButton, earthTopButton, waterTopButton,
            plantTopButton, nightTopButton, poisonTopButton, fireBottomButton, earthBottomButton, waterBottomButton,
            plantBottomButton, nightBottomButton, poisonBottomButton, backHistory;
    private ImageButton manaTop, manaBottom, clearTop, clearBottom, centerButton, clearCenter,
            reset, numbersSet, backButton;
    private TextView numberTop, numberBottom, topPlayerScore, topPoisonCount, bottomPlayerScore,
            bottomPoisonCount, topScore, topCount, bottomScore, bottomCount, poisonTopText, poisonBottomText;
    private SeekBar poisonBarTop, poisonBarBottom;
    ImageButton fireF, plantF, earthF, waterF, nightF, poisonButtonF, fire, plant, earth, water,
            night, poisonButton, history, picture, dice, diceCenterDot, diceCenterDotF, plusPictureTop, plusPictureBottom;
    ImageView topDice, bottomImageView, topImageView;

    private ListView scoreHistory;
    SimpleCursorAdapter simpleCursorAdapter, simpleCursorAdapterLast;

    DatabaseHelper myDb;

    //Default Set-up: Top/Bottom mana menu open/closed tracker, set current life count to 20.
    boolean menuTopOpen, menuBottomOpen, menuCenterOpen, menuHistoryOpen, menuPictureOpen,
            menuNumbersOpen, menuInformationOpen, poisonFClicked, poisonClicked;

    String currentTextNumber = "20";
    int poisonTopUpdate = 0;
    int poisonBottomUpdate = 0;

    int windowNumber = 0;

    String lastTopScore = "0";
    String lastTopPoison = "0";
    String lastBottomScore = "0";
    String lastBottomPoison = "0";

    int i, j;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();    // Hide the status bar. May need API fix.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        plusTop = (Button) findViewById(R.id.plusTop);
        minusTop = (Button) findViewById(R.id.minusTop);
        plusBottom = (Button) findViewById(R.id.plusBottom);
        minusBottom = (Button) findViewById(R.id.minusBottom);
        reset = (ImageButton) findViewById(R.id.reset);
        numbersSet = (ImageButton) findViewById(R.id.numbers);
        history = (ImageButton) findViewById(R.id.history);
        picture = (ImageButton) findViewById(R.id.picturetest);
        dice = (ImageButton) findViewById(R.id.dice);
        backHistory = (Button) findViewById(R.id._backHistory);

        plusPictureTop = (ImageButton) findViewById(R.id.plusPictureTop);
        plusPictureBottom = (ImageButton) findViewById(R.id.plusPictureBottom);

        fireTopButton = (Button) findViewById(R.id._fire_gradient_flipped);
        earthTopButton = (Button) findViewById(R.id._earth_gradient_flipped);
        plantTopButton = (Button) findViewById(R.id._plant_gradient_flipped);
        waterTopButton = (Button) findViewById(R.id._water_gradient_flipped);
        nightTopButton = (Button) findViewById(R.id._night_gradient_flipped);
        poisonTopButton = (Button) findViewById(R.id.poison_flipped);

        fireBottomButton = (Button) findViewById(R.id._fire_gradient);
        earthBottomButton = (Button) findViewById(R.id._earth_gradient);
        plantBottomButton = (Button) findViewById(R.id._plant_gradient);
        waterBottomButton = (Button) findViewById(R.id._water_gradient);
        nightBottomButton = (Button) findViewById(R.id._night_gradient);
        poisonBottomButton = (Button) findViewById(R.id.poison);


        manaTop = (ImageButton) findViewById(R.id.manaTop);
        manaBottom = (ImageButton) findViewById(R.id.manaBottom);
        centerButton = (ImageButton) findViewById(R.id.centerButton);
        clearTop = (ImageButton) findViewById(R.id.clearButtonTop);
        clearBottom = (ImageButton) findViewById(R.id.clearButtonBottom);
        clearCenter = (ImageButton) findViewById(R.id.clearCenter);
        backButton = (ImageButton) findViewById(R.id.back);
        diceCenterDotF = (ImageButton) findViewById(R.id.diceCenterDotF);
        diceCenterDot = (ImageButton) findViewById(R.id.diceCenterDot);

        topDice = (ImageView) findViewById(R.id.topDice);
        bottomImageView = (ImageView) findViewById(R.id.bottomImageView);
        topImageView = (ImageView) findViewById(R.id.topImageView);

        numberBottom = (TextView) findViewById(R.id.numberBottom);
        numberTop = (TextView) findViewById(R.id.numberTop);
        topPlayerScore = (TextView) findViewById(R.id.topPlayerScore);
        topPoisonCount = (TextView) findViewById(R.id.topPoisonCount);
        bottomPlayerScore = (TextView) findViewById(R.id.bottomPlayerScore);
        bottomPoisonCount = (TextView) findViewById(R.id.bottomPoisonCount);
        topScore = (TextView) findViewById(R.id.topScore);
        topCount = (TextView) findViewById(R.id.topCount);
        bottomScore = (TextView) findViewById(R.id.bottomScore);
        bottomCount = (TextView) findViewById(R.id.bottomCount);
        poisonTopText = (TextView) findViewById(R.id.poisonTopText);
        poisonBottomText = (TextView) findViewById(R.id.poisonBottomText);

        menuTop = (ViewGroup) findViewById(R.id.menuRelativeLayoutTop);
        menuBottom = (ViewGroup) findViewById(R.id.menuRelativeLayoutBottom);
        menuCenter = (ViewGroup) findViewById(R.id.menuCenter);
        FrameLayout = (ViewGroup) findViewById(R.id.FrameLayout);
        menuNumbers = (ViewGroup) findViewById(R.id.menuNumbers);
        historyMenu = (ViewGroup) findViewById(R.id.historyMenu);

        fireF = (ImageButton) findViewById(R.id.fire_gradient_flipped);
        plantF = (ImageButton) findViewById(R.id.plant_gradient_flipped);
        earthF = (ImageButton) findViewById(R.id.earth_gradient_flipped);
        waterF = (ImageButton) findViewById(R.id.water_gradient_flipped);
        nightF = (ImageButton) findViewById(R.id.night_gradient_flipped);
        poisonButtonF = (ImageButton) findViewById(R.id.poison_gradient_flipped);

        fire = (ImageButton) findViewById(R.id.fire_gradient);
        plant = (ImageButton) findViewById(R.id.plant_gradient);
        earth = (ImageButton) findViewById(R.id.earth_gradient);
        water = (ImageButton) findViewById(R.id.water_gradient);
        night = (ImageButton) findViewById(R.id.night_gradient);
        poisonButton = (ImageButton) findViewById(R.id.poison_gradient);

        poisonBarTop = (SeekBar) findViewById(R.id.poisonBarTop);
        poisonBarBottom = (SeekBar) findViewById(R.id.poisonBarBottom);


        pictureMenu = (ViewGroup) findViewById(R.id.pictureMenu);
        diceMenu = (ViewGroup) findViewById(R.id.diceMenu);

        blackScreen = (ViewGroup) findViewById(R.id.blackScreen);


        menuRelativeLayoutTop = (ViewGroup) findViewById(R.id.topHalfRelativeLayout);
        menuRelativeLayoutBottom = (ViewGroup) findViewById(R.id.bottomHalfRelativeLayout);
        scoreHistory = (ListView) findViewById(R.id.score_history);

        myDb = new DatabaseHelper(this);

        Typeface magic = Typeface.createFromAsset(getAssets(), "Fonts/MAGIC.TTF");
        Typeface numberFont = Typeface.createFromAsset(getAssets(), "Fonts/NO.TTF");
        numberBottom.setTypeface(numberFont);
        numberTop.setTypeface(numberFont);
//        poisonTopText.setTypeface(numberFont);
//        poisonBottomText.setTypeface(numberFont);
        topPlayerScore.setTypeface(magic);
        topPoisonCount.setTypeface(magic);
        bottomPlayerScore.setTypeface(magic);
        bottomPoisonCount.setTypeface(magic);

        /* Although the visibility of these menus is initially set to 'INVISIBLE', the menuAnimation
        method is used here to set the initial position/scale of the menus to collapsed. Upon
        selection, these menus will then call their respective menuAnimation method but in reverse */

        initialMenuOrientation();

        //Set initial state of top/bottom theme menus to closed (false).
        menuTopOpen = false;
        menuBottomOpen = false;
        menuCenterOpen = false;
        menuHistoryOpen = false;
        menuPictureOpen = false;
        menuNumbersOpen = false;
        menuInformationOpen = false;

        poisonFClicked = false;
        poisonClicked = false;



        final Handler handler = new Handler(); //Wait for Animation to Complete
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                centerButton.performClick();

                final Handler handler2 = new Handler(); //Wait for Animation to Complete
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clearCenter.performClick();

                    }
                }, 1500);

            }
        }, 1500);

        final Handler handler2 = new Handler(); //Wait for Animation to Complete
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {


                AlphaAnimation animation = new AlphaAnimation(1f, 0f);
                animation.setDuration(5000);
                blackScreen.startAnimation(animation);

                final Handler handler2 = new Handler(); //Wait for Animation to Complete
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        blackScreen.setAlpha(0f);

                    }
                }, 5000);

            }
        }, 3000);



        AddData();
        topMenuClicked();
        bottomMenuClicked();
        centerMenuClicked();
        pictureButtonClicked();
        diceButtonClicked();
        historyButtonClicked();
        seekbarUpdate();


    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();    // Hide the status bar. May need API fix.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }


    public void historyButtonClicked() {

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                historyMenu.setVisibility(View.VISIBLE);
                menuAnimation(historyMenu, 0f, 0f, 1f, 200);
                menuHistoryOpen = true;
                populateListView();
                backHistory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        menuAnimation(historyMenu, 0f, 0f, 0.0005f, 200);
                        menuHistoryOpen = false;
                        myDb.deleteAll();
                        reset.performClick();
                        historyMenu.performClick();
                        simpleCursorAdapter.notifyDataSetChanged();
                        populateListView();



                    }
                });
//                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, 1);

//                menuCenter.animate().alpha(0f).setDuration(200);
//                menuTop.animate().alpha(0f).setDuration(200);
//                menuBottom.animate().alpha(0f).setDuration(200);


            }
        });

    }


    public void initialMenuOrientation() {
        menuCenter.setVisibility(View.INVISIBLE);
        menuTop.setVisibility(View.INVISIBLE);
        menuBottom.setVisibility(View.INVISIBLE);
        pictureMenu.setVisibility(View.INVISIBLE);
        menuNumbers.setVisibility(View.INVISIBLE);
        diceMenu.setVisibility(View.INVISIBLE);
        historyMenu.setVisibility(View.INVISIBLE);

        menuAnimation(menuCenter, 0f, 0f, 0.0005f, 0);
        menuAnimation(menuTop, -450f, 400f, 0.0005f, 0);
        menuAnimation(menuBottom, 450f, -400f, 0.0005f, 0);
        menuAnimation(pictureMenu, 0f, 0f, 0.0005f, 0);
        menuAnimation(diceMenu, 0f, 0f, 0.0005f, 0);
        menuAnimation(historyMenu, 0f, 0f, 0.0005f, 0);
        menuAnimation(menuNumbers, 0f, 0f, 0.0005f, 0);
        menuAnimation(menuCenter, 0f, 0f, 0.0005f, 0);


//        final Handler handler = new Handler(); //Wait for Animation to Complete
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                menuAnimation(menuTop, 450f, -400f, 1f, 200);
//
//                final Handler handler = new Handler(); //Wait for Animation to Complete
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        clearTop.setVisibility(View.VISIBLE);
//
//                    }
//                }, 200);
//
//                clearTop.bringToFront();
//
//
//
//                menuAnimation(menuBottom, -450f, 400f, 1f, 200);
//
//                final Handler handler2 = new Handler(); //Wait for Animation to Complete
//                handler2.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        clearBottom.setVisibility(View.VISIBLE); //Transparent ImageButton (behind menu)
//                        //that closes menu.
//                    }
//                }, 200);
//
//
//                clearBottom.bringToFront();
//
//
//
//
//
//
//                menuAnimation(menuCenter, 0f, 0f, 1f, 200);
//
//                final Handler handler3 = new Handler(); //Wait for Animation to Complete
//                handler3.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        clearCenter.setVisibility(View.VISIBLE); //Transparent ImageButton (behind menu)
//                        //that closes menu.
//                    }
//                }, 200);
//
//                final Handler handler4 = new Handler(); //Wait for Animation to Complete
//                handler4.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        menuAnimation(menuCenter, 0f, 0f, 0.0005f, 200);
//                        menuAnimation(pictureMenu, 0f, 0f, 0.0005f, 200);
//                        menuAnimation(diceMenu, 0f, 0f, 0.0005f, 200);
//                        menuAnimation(historyMenu, 0f, 0f, 0.0005f, 200);
//
//                        clearCenter.setVisibility(View.GONE);
//
//                        menuAnimation(menuNumbers, 0f, 0f, 0.0005f, 200);
//
//
//                        clearTop.setEnabled(true);
//                        clearBottom.setEnabled(true);
//
//                        manaTop.setEnabled(true);
//                        manaBottom.setEnabled(true);
//
//
//                        plusTop.setEnabled(true);
//                        minusTop.setEnabled(true);
//
//                        final Handler handler = new Handler(); //Wait for Animation to Complete
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                manaTop.setEnabled(true);
//
//                            }
//                        }, 200);
//
//                        final Handler handler2 = new Handler(); //Wait for Animation to Complete
//                        handler2.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                manaTop.setVisibility(View.VISIBLE);
//                                manaTop.animate().alpha(1f);
//                            }
//                        }, 125);
//
//
//                        menuAnimation(menuTop, -450f, 400f, 0.0005f, 200);
//
//                        clearTop.setVisibility(View.GONE);
//                        menuTopOpen = false;
//
//
//                        plusBottom.setEnabled(true);
//                        minusBottom.setEnabled(true);
//
//                        menuAnimation(menuBottom, 450f, -400f, 0.0005f, 200);
//
//                        final Handler handler5 = new Handler(); //Wait for Animation to Complete
//                        handler5.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                manaBottom.setEnabled(true);
//                            }
//                        }, 200);
//
//                        final Handler handler6 = new Handler(); //Wait for Animation to Complete
//                        handler6.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
////                                centerButton.setVisibility(View.VISIBLE);
//                                manaBottom.setVisibility(View.VISIBLE);
//                                manaBottom.animate().alpha(1f);
//                            }
//                        }, 125);
//
//                        clearBottom.setVisibility(View.GONE);
//                        menuBottomOpen = false;
//
//
//
//                        final Handler handler7 = new Handler(); //Wait for Animation to Complete
//                        handler7.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                centerButton.setEnabled(true);
//                                centerButton.setVisibility(View.VISIBLE);
//                                centerButton.animate().alpha(1f);
//                            }
//                        }, 125);
//                    }
//                }, 300);
//            }
//        }, 1000);

    }

    public void topMenuClicked() {
        assert manaTop != null;
        manaTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                manaTop.setEnabled(false);

                manaTop.setVisibility(View.GONE); //Menu Button disappears
                manaTop.animate().alpha(0f);
                menuTop.setVisibility(View.VISIBLE);

                menuAnimation(menuTop, 450f, -400f, 1f, 200);

                final Handler handler = new Handler(); //Wait for Animation to Complete
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clearTop.setVisibility(View.VISIBLE);

                    }
                }, 200);


                plusTop.setEnabled(false);
                minusTop.setEnabled(false);
                clearTop.bringToFront();
                menuTopOpen = true;


                clearTop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        plusTop.setEnabled(true);
                        minusTop.setEnabled(true);

                        final Handler handler = new Handler(); //Wait for Animation to Complete
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                manaTop.setEnabled(true);

                            }
                        }, 200);

                        final Handler handler2 = new Handler(); //Wait for Animation to Complete
                        handler2.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                manaTop.setVisibility(View.VISIBLE);
                                manaTop.animate().alpha(1f);
                            }
                        }, 125);


                        menuAnimation(menuTop, -450f, 400f, 0.0005f, 200);

                        clearTop.setVisibility(View.GONE);
                        menuTopOpen = false;
                    }
                });
            }
        });
    }

    public void bottomMenuClicked() {

        assert manaBottom != null;
        manaBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                manaBottom.setEnabled(false);
                manaBottom.setVisibility(View.GONE); //Menu Button disappears.
                manaBottom.animate().alpha(0f);

                menuBottom.setVisibility(View.VISIBLE);


                menuAnimation(menuBottom, -450f, 400f, 1f, 200);

                final Handler handler = new Handler(); //Wait for Animation to Complete
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clearBottom.setVisibility(View.VISIBLE); //Transparent ImageButton (behind menu)
                        //that closes menu.
                    }
                }, 200);


                plusBottom.setEnabled(false);
                minusBottom.setEnabled(false);
                clearBottom.bringToFront();

                menuBottomOpen = true;

                clearBottom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        plusBottom.setEnabled(true);
                        minusBottom.setEnabled(true);

                        menuAnimation(menuBottom, 450f, -400f, 0.0005f, 200);

                        final Handler handler = new Handler(); //Wait for Animation to Complete
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                manaBottom.setEnabled(true);
                            }
                        }, 200);

                        final Handler handler2 = new Handler(); //Wait for Animation to Complete
                        handler2.postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                centerButton.setVisibility(View.VISIBLE);
                                manaBottom.setVisibility(View.VISIBLE);
                                manaBottom.animate().alpha(1f);
                            }
                        }, 125);

                        clearBottom.setVisibility(View.GONE);
                        menuBottomOpen = false;
                    }
                });
            }
        });
    }

    public void centerMenuClicked() {
        centerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuCenterOpen = true;

                menuCenter.setVisibility(View.VISIBLE);
                if (!menuTopOpen) manaTop.performClick();
                if (!menuBottomOpen) manaBottom.performClick();

                clearTop.setEnabled(false);
                clearBottom.setEnabled(false);

                menuAnimation(menuCenter, 0f, 0f, 1f, 200);

                final Handler handler = new Handler(); //Wait for Animation to Complete
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clearCenter.setVisibility(View.VISIBLE); //Transparent ImageButton (behind menu)
                        //that closes menu.
                    }
                }, 200);


                manaTop.setEnabled(false);
                manaBottom.setEnabled(false);

                centerButton.setVisibility(View.GONE);
                centerButton.animate().alpha(0f);
                centerButton.setEnabled(false);
                clearCenter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        menuCenter.animate().alpha(1f);
//                        menuTop.animate().alpha(1f);
//                        menuBottom.animate().alpha(1f);

                        menuAnimation(menuCenter, 0f, 0f, 0.0005f, 200);
                        menuAnimation(pictureMenu, 0f, 0f, 0.0005f, 200);
                        menuAnimation(diceMenu, 0f, 0f, 0.0005f, 200);
                        menuAnimation(historyMenu, 0f, 0f, 0.0005f, 200);

                        clearCenter.setVisibility(View.GONE);

                        menuAnimation(menuNumbers, 0f, 0f, 0.0005f, 200);


                        clearTop.setEnabled(true);
                        clearBottom.setEnabled(true);

                        manaTop.setEnabled(true);
                        manaBottom.setEnabled(true);

                        clearTop.performClick();
                        clearBottom.performClick();

                        menuCenterOpen = false;

                        final Handler handler2 = new Handler(); //Wait for Animation to Complete
                        handler2.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                centerButton.setEnabled(true);
                                centerButton.setVisibility(View.VISIBLE);
                                centerButton.animate().alpha(1f);
                            }
                        }, 125);

                    }

                });

                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reset.setAlpha(0.5f);

                        numberTop.setText(currentTextNumber);
                        numberBottom.setText(currentTextNumber);
                        poisonTopUpdate = 0;
                        poisonBottomUpdate = 0;
                        poisonBarTop.setProgress(0);
                        poisonBarBottom.setProgress(0);

                        poisonButtonF.setAlpha(1f);
                        poisonBarTop.setVisibility(View.GONE);
                        poisonTopText.setVisibility(View.GONE);
                        poisonFClicked = false;

                        poisonButton.setAlpha(1f);
                        poisonBarBottom.setVisibility(View.GONE);
                        poisonBottomText.setVisibility(View.GONE);
                        poisonClicked = false;

                        Animation vibrate = AnimationUtils.loadAnimation(MainActivity.this, R.anim.vibrate);
                        numberTop.startAnimation(vibrate);
                        numberBottom.startAnimation(vibrate);

//                        System.out.println(topScore.getCurrentTextColor());
//                        topScore.setTextColor(Color.parseColor("#962A00"));
//                        topCount.setTextColor(Color.parseColor("#962A00"));
//                        bottomScore.setTextColor(Color.parseColor("#962A00"));
//                        bottomCount.setTextColor(Color.parseColor("#962A00"));
//                        System.out.println(topScore.getCurrentTextColor());



                        AddData();

                        final Handler handler = new Handler(); //Wait for Animation to Complete
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                reset.setAlpha(1f);
                            }
                        }, 200);


                    }
                });

                numbersSet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        menuNumbers.setVisibility(View.VISIBLE);
                        menuAnimation(menuNumbers, 0f, 0f, 1f, 200);
                        menuAnimation(menuCenter, 0f, 0f, 0.0005f, 200);
                        menuNumbersOpen = true;


                    }
                });

                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        menuNumbers.setVisibility(View.GONE);
                        menuAnimation(menuCenter, 0f, 0f, 1f, 200);
                        menuAnimation(menuNumbers, 0f, 0f, 0.0005f, 200);
                        menuNumbersOpen = false;

                    }
                });
            }
        });
    }

    public void menuAnimation(ViewGroup menu, float i, float j, float k, int duration) {

        menu.animate().translationXBy(i).setDuration(duration);
        menu.animate().translationYBy(j).setDuration(duration);
        menu.animate().scaleX(k).setDuration(duration);
        menu.animate().scaleY(k).setDuration(duration);
    }

    public void poisonButtonClicked(View view) {

        String currentId = view.getResources().getResourceEntryName(view.getId());
        currentId = currentId.startsWith("_") ? currentId.substring(1) : currentId;

        if (currentId.substring(currentId.length() - 1).equals("d") && !poisonFClicked) {

            poisonButtonF.setAlpha(0.5f);
            poisonBarTop.setVisibility(View.VISIBLE);
            poisonTopText.setVisibility(View.VISIBLE);
            poisonFClicked = true;
        } else if (currentId.substring(currentId.length() - 1).equals("d") && poisonFClicked) {

            poisonButtonF.setAlpha(1f);
            poisonBarTop.setVisibility(View.GONE);
            poisonTopText.setVisibility(View.GONE);
            poisonFClicked = false;
        } else if (!poisonClicked) {
            poisonButton.setAlpha(0.5f);
            poisonBarBottom.setVisibility(View.VISIBLE);
            poisonBottomText.setVisibility(View.VISIBLE);
            poisonClicked = true;
        } else {
            poisonButton.setAlpha(1f);
            poisonBarBottom.setVisibility(View.GONE);
            poisonBottomText.setVisibility(View.GONE);
            poisonClicked = false;
        }

        poisonBarTop.bringToFront();
        poisonBarBottom.bringToFront();


    }

    public void numberSet(View view) {

        currentTextNumber = view.getResources().getResourceEntryName(view.getId()).substring(1, 3);
        numberTop.setText(currentTextNumber);
        numberBottom.setText(currentTextNumber);

        poisonTopUpdate = 0;
        poisonBottomUpdate = 0;
        poisonBarTop.setProgress(0);
        poisonBarBottom.setProgress(0);

        Animation vibrate = AnimationUtils.loadAnimation(MainActivity.this, R.anim.vibrate);
        numberTop.startAnimation(vibrate);
        numberBottom.startAnimation(vibrate);

        AddData();
    }

    public void numberChange(View view) {
        String currentId = view.getResources().getResourceEntryName(view.getId());

        if (currentId.substring(currentId.length() - 1).equals("p")) {
            int Num = Integer.parseInt(numberTop.getText().toString());
            if (currentId.charAt(0) == 'm') numberTop.setText(Integer.toString(--Num));
            else numberTop.setText(Integer.toString(++Num));

        } else {
            int Num = Integer.parseInt(numberBottom.getText().toString());
            if (currentId.charAt(0) == 'm') numberBottom.setText(Integer.toString(--Num));
            else numberBottom.setText(Integer.toString(++Num));
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AddData();
            }
        }, 3000);


    }

    public void themeButtonClicked(View view) {

        String currentId = view.getResources().getResourceEntryName(view.getId());
        currentId = currentId.startsWith("_") ? currentId.substring(1) : currentId;

        if (currentId.substring(currentId.length() - 1).equals("d")) {
            currentId = currentId.split("_")[0];
            currentId += "F";

            fireF.setAlpha(1f);
            plantF.setAlpha(1f);
            earthF.setAlpha(1f);
            waterF.setAlpha(1f);
            nightF.setAlpha(1f);


            switch (currentId) {
                case "fireF":
                    menuRelativeLayoutTop.setBackgroundResource(R.drawable.fire_gradient_flipped);
                    fireF.setAlpha(0.5f);
                    break;
                case "plantF":
                    menuRelativeLayoutTop.setBackgroundResource(R.drawable.plant_gradient_flipped);
                    plantF.setAlpha(0.5f);
                    break;
                case "earthF":
                    menuRelativeLayoutTop.setBackgroundResource(R.drawable.earth_gradient_flipped);
                    earthF.setAlpha(0.5f);
                    break;
                case "waterF":
                    menuRelativeLayoutTop.setBackgroundResource(R.drawable.water_gradient_flipped);
                    waterF.setAlpha(0.5f);
                    break;
                case "nightF":
                    menuRelativeLayoutTop.setBackgroundResource(R.drawable.night_gradient_flipped);
                    nightF.setAlpha(0.5f);
                    break;
            }


        } else {
            currentId = currentId.split("_")[0];

            fire.setAlpha(1f);
            plant.setAlpha(1f);
            earth.setAlpha(1f);
            water.setAlpha(1f);
            night.setAlpha(1f);

            switch (currentId) {

                case "fire":
                    menuRelativeLayoutBottom.setBackgroundResource(R.drawable.fire_gradient);
                    fire.setAlpha(0.5f);
                    bottomImageView.setImageBitmap(null);
                    break;
                case "plant":
                    menuRelativeLayoutBottom.setBackgroundResource(R.drawable.plant_gradient);
                    plant.setAlpha(0.5f);
                    bottomImageView.setImageBitmap(null);
                    break;
                case "earth":
                    menuRelativeLayoutBottom.setBackgroundResource(R.drawable.earth_gradient);
                    earth.setAlpha(0.5f);
                    bottomImageView.setImageBitmap(null);
                    break;
                case "water":
                    menuRelativeLayoutBottom.setBackgroundResource(R.drawable.water_gradient);
                    water.setAlpha(0.5f);
                    bottomImageView.setImageBitmap(null);
                    break;
                case "night":
                    menuRelativeLayoutBottom.setBackgroundResource(R.drawable.night_gradient);
                    night.setAlpha(0.5f);
                    bottomImageView.setImageBitmap(null);
                    break;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap image = extras.getParcelable("data");
            Uri selectedImage = data.getData();

            bottomImageView.setImageBitmap(image);

        }
    }

    public void pictureButtonClicked() {

        //Button Listener for
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pictureMenu.setVisibility(View.VISIBLE);

                menuAnimation(pictureMenu, 0f, 0f, 1f, 200);
                menuPictureOpen = true;
                plusPictureTop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        i.putExtra("crop", "true");
                        i.putExtra("aspectX", 1);
                        i.putExtra("aspectY", 0.833);
                        i.putExtra("outputX", 240);
                        i.putExtra("outputY", 200);
                        i.putExtra("return-data", true);

                        startActivityForResult(i, 1);

                    }

                });
                plusPictureBottom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        i.putExtra("crop", "true");
                        i.putExtra("aspectX", 1);
                        i.putExtra("aspectY", 0.833);
                        i.putExtra("outputX", 240);
                        i.putExtra("outputY", 200);
                        i.putExtra("return-data", true);

                        startActivityForResult(i, 1);


                    }

                });


//                menuCenter.animate().alpha(0f).setDuration(200);
//                menuTop.animate().alpha(0f).setDuration(200);
//                menuBottom.animate().alpha(0f).setDuration(200);


            }
        });


    }

    public void diceButtonClicked() {
        dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset.setEnabled(false);
                numbersSet.setEnabled(false);
                history.setEnabled(false);
                picture.setEnabled(false);
                dice.setEnabled(false);
                poisonButton.setEnabled(false);
                clearCenter.setEnabled(false);
                dice.setAlpha(0.5f);

                fireF.setEnabled(false);
                waterF.setEnabled(false);
                earthF.setEnabled(false);
                plantF.setEnabled(false);
                nightF.setEnabled(false);
                fire.setEnabled(false);
                water.setEnabled(false);
                earth.setEnabled(false);
                plant.setEnabled(false);
                night.setEnabled(false);

                fireTopButton.setEnabled(false);
                waterTopButton.setEnabled(false);
                earthTopButton.setEnabled(false);
                plantTopButton.setEnabled(false);
                nightTopButton.setEnabled(false);
                poisonTopButton.setEnabled(false);

                fireBottomButton.setEnabled(false);
                waterBottomButton.setEnabled(false);
                earthBottomButton.setEnabled(false);
                plantBottomButton.setEnabled(false);
                nightBottomButton.setEnabled(false);
                poisonBottomButton.setEnabled(false);

                final float fireTopAlpha = fireF.getAlpha();
                final float waterTopAlpha = waterF.getAlpha();
                final float earthTopAlpha = earthF.getAlpha();
                final float nightTopAlpha = nightF.getAlpha();
                final float plantTopAlpha = plantF.getAlpha();
                final float poisonTopAlpha = poisonButtonF.getAlpha();

                final float fireBottomAlpha = fire.getAlpha();
                final float waterBottomAlpha = water.getAlpha();
                final float earthBottomAlpha = earth.getAlpha();
                final float nightBottomAlpha = night.getAlpha();
                final float plantBottomAlpha = plant.getAlpha();
                final float poisonBottomAlpha = poisonButton.getAlpha();


                menuTop.animate().rotation(3060).setDuration(1500);
                menuBottom.animate().rotation(2880).setDuration(1500);

                Random r = new Random();
                i = r.nextInt(6) + 1;
                j = r.nextInt(6) + 1;
                while (i == j) {
                    i = r.nextInt(6) + 1;
                    j = r.nextInt(6) + 1;
                }
//                System.out.println(i + " and " + j);

                final Handler handler = new Handler(); //Wait for Animation to Complete
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        fireF.setAlpha(1f);
                        waterF.setAlpha(1f);
                        earthF.setAlpha(1f);
                        plantF.setAlpha(1f);
                        nightF.setAlpha(1f);
                        poisonButtonF.setAlpha(1f);

                        fire.setAlpha(1f);
                        water.setAlpha(1f);
                        earth.setAlpha(1f);
                        plant.setAlpha(1f);
                        night.setAlpha(1f);
                        poisonButton.setAlpha(1f);


                        fireF.setImageResource(R.drawable.dice_dot);
                        earthF.setImageResource(R.drawable.dice_dot);
                        waterF.setImageResource(R.drawable.dice_dot);
                        nightF.setImageResource(R.drawable.dice_dot);
                        plantF.setImageResource(R.drawable.dice_dot);
                        poisonButtonF.setImageResource(R.drawable.dice_dot);
                        diceCenterDotF.setImageResource(R.drawable.dice_dot);

                        fireF.setVisibility(View.VISIBLE);
                        earthF.setVisibility(View.VISIBLE);
                        waterF.setVisibility(View.VISIBLE);
                        nightF.setVisibility(View.VISIBLE);
                        plantF.setVisibility(View.VISIBLE);
                        poisonButtonF.setVisibility(View.VISIBLE);
                        diceCenterDotF.setVisibility(View.VISIBLE);

                        fire.setImageResource(R.drawable.dice_dot);
                        earth.setImageResource(R.drawable.dice_dot);
                        water.setImageResource(R.drawable.dice_dot);
                        night.setImageResource(R.drawable.dice_dot);
                        plant.setImageResource(R.drawable.dice_dot);
                        poisonButton.setImageResource(R.drawable.dice_dot);
                        diceCenterDot.setImageResource(R.drawable.dice_dot);

                        fire.setVisibility(View.VISIBLE);
                        earth.setVisibility(View.VISIBLE);
                        water.setVisibility(View.VISIBLE);
                        night.setVisibility(View.VISIBLE);
                        plant.setVisibility(View.VISIBLE);
                        poisonButton.setVisibility(View.VISIBLE);
                        diceCenterDot.setVisibility(View.VISIBLE);

                        diceCenterDotF.bringToFront();
                        diceCenterDot.bringToFront();

//                        System.out.println(i + " and " + j);
//                        diceCenterDotF.setVisibility(View.GONE);
//                        diceCenterDot.setVisibility(View.GONE);
//
//                        if(i == 1 || i == 3 || i == 5) {
//                            diceCenterDotF.setVisibility(View.VISIBLE);
//                            diceCenterDotF.bringToFront();
//                            System.out.println("Reached");
//                        }
//                        if(j == 1 || j == 3 || j == 5) {
//                            diceCenterDot.setVisibility(View.VISIBLE);
//                        }
//                        if (i == 1) {
//                            diceCenterDotF.setImageResource(R.drawable.dice_dot);
//                            diceCenterDotF.setVisibility(View.VISIBLE);
//                            diceCenterDotF.bringToFront();
//                            System.out.println("Reached2");
//
//                        }


                        switch (i) {
                            case 1:
                                fireF.setVisibility(View.INVISIBLE);
                                earthF.setVisibility(View.INVISIBLE);
                                waterF.setVisibility(View.INVISIBLE);
                                nightF.setVisibility(View.INVISIBLE);
                                plantF.setVisibility(View.INVISIBLE);
                                poisonButtonF.setVisibility(View.INVISIBLE);
                                diceCenterDotF.setVisibility(View.VISIBLE);

                                break;

                            case 2:
                                fireF.setImageResource(R.drawable.dice_dot);
                                earthF.setVisibility(View.INVISIBLE);
                                waterF.setVisibility(View.INVISIBLE);
                                nightF.setVisibility(View.INVISIBLE);
                                plantF.setVisibility(View.INVISIBLE);
                                poisonButtonF.setImageResource(R.drawable.dice_dot);
                                diceCenterDotF.setVisibility(View.INVISIBLE);
                                break;

                            case 3:
                                fireF.setImageResource(R.drawable.dice_dot);
                                earthF.setVisibility(View.INVISIBLE);
                                waterF.setVisibility(View.INVISIBLE);
                                nightF.setVisibility(View.INVISIBLE);
                                plantF.setVisibility(View.INVISIBLE);
                                poisonButtonF.setImageResource(R.drawable.dice_dot);
                                diceCenterDotF.setVisibility(View.VISIBLE);
                                break;

                            case 4:
                                fireF.setImageResource(R.drawable.dice_dot);
                                earthF.setImageResource(R.drawable.dice_dot);
                                waterF.setImageResource(R.drawable.dice_dot);
                                nightF.setVisibility(View.INVISIBLE);
                                plantF.setVisibility(View.INVISIBLE);
                                poisonButtonF.setImageResource(R.drawable.dice_dot);
                                diceCenterDotF.setVisibility(View.INVISIBLE);
                                break;

                            case 5:
                                fireF.setImageResource(R.drawable.dice_dot);
                                earthF.setImageResource(R.drawable.dice_dot);
                                waterF.setImageResource(R.drawable.dice_dot);
                                nightF.setVisibility(View.INVISIBLE);
                                plantF.setVisibility(View.INVISIBLE);
                                poisonButtonF.setImageResource(R.drawable.dice_dot);
                                diceCenterDotF.setVisibility(View.VISIBLE);
                                break;

                            case 6:
                                fireF.setImageResource(R.drawable.dice_dot);
                                earthF.setImageResource(R.drawable.dice_dot);
                                waterF.setImageResource(R.drawable.dice_dot);
                                nightF.setImageResource(R.drawable.dice_dot);
                                plantF.setImageResource(R.drawable.dice_dot);
                                poisonButtonF.setImageResource(R.drawable.dice_dot);
                                diceCenterDotF.setVisibility(View.INVISIBLE);
                                break;

                        }

                        switch (j) {
                            case 1:
                                fire.setVisibility(View.INVISIBLE);
                                earth.setVisibility(View.INVISIBLE);
                                water.setVisibility(View.INVISIBLE);
                                night.setVisibility(View.INVISIBLE);
                                plant.setVisibility(View.INVISIBLE);
                                poisonButton.setVisibility(View.INVISIBLE);
                                diceCenterDot.setVisibility(View.VISIBLE);
                                break;

                            case 2:
                                fire.setImageResource(R.drawable.dice_dot);
                                earth.setVisibility(View.INVISIBLE);
                                water.setVisibility(View.INVISIBLE);
                                night.setVisibility(View.INVISIBLE);
                                plant.setVisibility(View.INVISIBLE);
                                poisonButton.setImageResource(R.drawable.dice_dot);
                                diceCenterDot.setVisibility(View.INVISIBLE);
                                break;

                            case 3:
                                fire.setImageResource(R.drawable.dice_dot);
                                earth.setVisibility(View.INVISIBLE);
                                water.setVisibility(View.INVISIBLE);
                                night.setVisibility(View.INVISIBLE);
                                plant.setVisibility(View.INVISIBLE);
                                poisonButton.setImageResource(R.drawable.dice_dot);
                                diceCenterDot.setVisibility(View.VISIBLE);
                                break;

                            case 4:
                                fire.setImageResource(R.drawable.dice_dot);
                                earth.setImageResource(R.drawable.dice_dot);
                                water.setImageResource(R.drawable.dice_dot);
                                night.setVisibility(View.INVISIBLE);
                                plant.setVisibility(View.INVISIBLE);
                                poisonButton.setImageResource(R.drawable.dice_dot);
                                diceCenterDot.setVisibility(View.INVISIBLE);
                                break;

                            case 5:
                                fire.setImageResource(R.drawable.dice_dot);
                                earth.setImageResource(R.drawable.dice_dot);
                                water.setImageResource(R.drawable.dice_dot);
                                night.setVisibility(View.INVISIBLE);
                                plant.setVisibility(View.INVISIBLE);
                                poisonButton.setImageResource(R.drawable.dice_dot);
                                diceCenterDot.setVisibility(View.VISIBLE);
                                break;

                            case 6:
                                fire.setImageResource(R.drawable.dice_dot);
                                earth.setImageResource(R.drawable.dice_dot);
                                water.setImageResource(R.drawable.dice_dot);
                                night.setImageResource(R.drawable.dice_dot);
                                plant.setImageResource(R.drawable.dice_dot);
                                poisonButton.setImageResource(R.drawable.dice_dot);
                                diceCenterDot.setVisibility(View.INVISIBLE);
                                break;

                        }

                    }
                }, 750);


                final Handler handler2 = new Handler(); //Wait for Animation to Complete
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        menuTop.animate().rotation(180).setDuration(500);
                        menuBottom.animate().rotation(0).setDuration(500);

                        fireF.setAlpha(fireTopAlpha);
                        waterF.setAlpha(waterTopAlpha);
                        earthF.setAlpha(earthTopAlpha);
                        plantF.setAlpha(plantTopAlpha);
                        nightF.setAlpha(nightTopAlpha);
                        poisonButtonF.setAlpha(poisonTopAlpha);

                        fire.setAlpha(fireBottomAlpha);
                        water.setAlpha(waterBottomAlpha);
                        earth.setAlpha(earthBottomAlpha);
                        plant.setAlpha(plantBottomAlpha);
                        night.setAlpha(nightBottomAlpha);
                        poisonButton.setAlpha(poisonBottomAlpha);

                        fireF.setImageResource(R.drawable.fire);
                        earthF.setImageResource(R.drawable.earth);
                        waterF.setImageResource(R.drawable.water);
                        nightF.setImageResource(R.drawable.night);
                        plantF.setImageResource(R.drawable.plant);
                        poisonButtonF.setImageResource(R.drawable.poison);
                        diceCenterDotF.setVisibility(View.INVISIBLE);

                        fireF.setVisibility(View.VISIBLE);
                        earthF.setVisibility(View.VISIBLE);
                        waterF.setVisibility(View.VISIBLE);
                        nightF.setVisibility(View.VISIBLE);
                        plantF.setVisibility(View.VISIBLE);
                        poisonButtonF.setVisibility(View.VISIBLE);

                        fire.setImageResource(R.drawable.fire);
                        earth.setImageResource(R.drawable.earth);
                        water.setImageResource(R.drawable.water);
                        night.setImageResource(R.drawable.night);
                        plant.setImageResource(R.drawable.plant);
                        poisonButton.setImageResource(R.drawable.poison);
                        diceCenterDot.setVisibility(View.INVISIBLE);

                        fire.setVisibility(View.VISIBLE);
                        earth.setVisibility(View.VISIBLE);
                        water.setVisibility(View.VISIBLE);
                        night.setVisibility(View.VISIBLE);
                        plant.setVisibility(View.VISIBLE);
                        poisonButton.setVisibility(View.VISIBLE);


                    }
                }, 4000);

                final Handler handler3 = new Handler(); //Wait for Animation to Complete
                handler3.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        reset.setEnabled(true);
                        numbersSet.setEnabled(true);
                        history.setEnabled(true);
                        picture.setEnabled(true);
                        dice.setEnabled(true);
                        poisonButton.setEnabled(true);
                        clearCenter.setEnabled(true);

                        dice.setAlpha(1f);

                        fireF.setEnabled(true);
                        waterF.setEnabled(true);
                        earthF.setEnabled(true);
                        plantF.setEnabled(true);
                        nightF.setEnabled(true);
                        fire.setEnabled(true);
                        water.setEnabled(true);
                        earth.setEnabled(true);
                        plant.setEnabled(true);
                        night.setEnabled(true);

                        fireTopButton.setEnabled(true);
                        waterTopButton.setEnabled(true);
                        earthTopButton.setEnabled(true);
                        plantTopButton.setEnabled(true);
                        nightTopButton.setEnabled(true);
                        poisonTopButton.setEnabled(true);

                        fireBottomButton.setEnabled(true);
                        waterBottomButton.setEnabled(true);
                        earthBottomButton.setEnabled(true);
                        plantBottomButton.setEnabled(true);
                        nightBottomButton.setEnabled(true);
                        poisonBottomButton.setEnabled(true);

                    }
                }, 4500);


//                diceMenu.setVisibility(View.VISIBLE);
//                topDice.setVisibility(View.VISIBLE);
//                menuAnimation(diceMenu, 0f, 0f, 1f, 200);

//                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, 1);

//                menuCenter.animate().alpha(0f).setDuration(200);
//                menuTop.animate().alpha(0f).setDuration(200);
//                menuBottom.animate().alpha(0f).setDuration(200);


            }
        });
    }

    public void AddData() {


        Cursor scores = myDb.lastRow();

        ArrayList<String> columnArray1 = new ArrayList<String>();
        ArrayList<String> columnArray2 = new ArrayList<String>();
        ArrayList<String> columnArray3 = new ArrayList<String>();
        ArrayList<String> columnArray4 = new ArrayList<String>();

        for (scores.moveToFirst(); !scores.isAfterLast(); scores.moveToNext()) {
            columnArray1.add(scores.getString(1));
            columnArray2.add(scores.getString(2));
            columnArray3.add(scores.getString(3));
            columnArray4.add(scores.getString(4));
        }
        String[] colStrArr1 = columnArray1.toArray(new String[columnArray1.size()]);
        String[] colStrArr2 = columnArray2.toArray(new String[columnArray2.size()]);
        String[] colStrArr3 = columnArray3.toArray(new String[columnArray3.size()]);
        String[] colStrArr4 = columnArray4.toArray(new String[columnArray4.size()]);


        if (colStrArr1 != null && colStrArr1.length > 0) {

            if (colStrArr1[0].equals(numberTop.getText().toString()) && colStrArr2[0].equals(Integer.toString(poisonTopUpdate)) &&
                    colStrArr3[0].equals(numberBottom.getText().toString()) && colStrArr4[0].equals(Integer.toString(poisonBottomUpdate))) {
                return;
            } else {
                myDb.insertData(numberTop.getText().toString(), Integer.toString(poisonTopUpdate), numberBottom.getText().toString(), Integer.toString(poisonBottomUpdate));
            }

        } else
            myDb.insertData(numberTop.getText().toString(), Integer.toString(poisonTopUpdate), numberBottom.getText().toString(), Integer.toString(poisonBottomUpdate));

    }

    public void populateListView() {


//        Typeface numberFont = Typeface.createFromAsset(getAssets(), "Fonts/NO.TTF");
        Cursor scores = myDb.getAllData();

        String[] columns = new String[]{
                myDb.COL_2,
                myDb.COL_3,
                myDb.COL_4,
                myDb.COL_5
        };

        int[] boundTo = new int[]{
                R.id.topScore,
                R.id.topCount,
                R.id.bottomScore,
                R.id.bottomCount
        };

        simpleCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.history_list,
                scores,
                columns,
                boundTo,
                0);
        scoreHistory.setAdapter(simpleCursorAdapter);

//        topScore.setTypeface(numberFont);
//        topCount.setTypeface(numberFont);
//        bottomScore.setTypeface(numberFont);
//        bottomCount.setTypeface(numberFont);


    }

    public void seekbarUpdate() {

        poisonBarTop.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                i = Math.round(i / 10) * 10;
                seekBar.setProgress(i);
                poisonTopText.setText("Poison: " + i / 10);
                poisonTopUpdate = i / 10;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                final Handler handler = new Handler(); //Wait for Animation to Complete
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AddData();
                    }
                }, 3000);

            }
        });

        poisonBarBottom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                i = Math.round(i / 10) * 10;
                seekBar.setProgress(i);
                poisonBottomText.setText("Poison: " + i / 10);
                poisonBottomUpdate = i / 10;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                final Handler handler = new Handler(); //Wait for Animation to Complete
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AddData();
                    }
                }, 3000);

            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        if (menuNumbersOpen) {
            backButton.performClick();
        } else if (menuHistoryOpen) {
            backHistory.performClick();
        } else if (menuTopOpen || menuBottomOpen || menuCenterOpen) {
            clearCenter.performClick();
        }

    }




}

