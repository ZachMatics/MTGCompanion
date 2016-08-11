package com.zacharymitchell.mtgcompanion;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.



        Button plusTop = (Button) findViewById(R.id.plusTop);
        Button minusTop = (Button) findViewById(R.id.minusTop);
        Button plusBottom = (Button) findViewById(R.id.plusBottom);
        Button minusBottom = (Button) findViewById(R.id.minusBottom);
        ImageButton manaTop = (ImageButton) findViewById(R.id.manaTop);
        ImageButton manaBottom = (ImageButton) findViewById(R.id.manaBottom);


        final TextView topNumber = (TextView) findViewById(R.id.topNumber);
        final TextView bottomNumber = (TextView) findViewById(R.id.bottomNumber);

        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0F);


        assert manaTop != null;
        manaTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(buttonClick);
                ViewGroup v = (ViewGroup) findViewById(R.id.test);
                Button plus = (Button) findViewById(R.id.plusTop);
                Button minus = (Button) findViewById(R.id.minusTop);
                ViewGroup menuLayout = (ViewGroup) findViewById(R.id.menuLayout);
                ImageButton closeMenuButton = (ImageButton) findViewById(R.id.closeMenuButton);
                ViewGroup menuPane = (ViewGroup) findViewById(R.id.test);

                menuLayout.setVisibility(View.VISIBLE);
                plus.setVisibility(View.GONE);
                minus.setVisibility(View.GONE);
                v.setVisibility(View.VISIBLE);
                menuLayout.bringToFront();

                menuPane.bringToFront();
                view.bringToFront();


                assert closeMenuButton != null;
                closeMenuButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        view.startAnimation(buttonClick);
                        ViewGroup v = (ViewGroup) findViewById(R.id.test);
                        Button plus = (Button) findViewById(R.id.plusTop);
                        Button minus = (Button) findViewById(R.id.minusTop);
                        ViewGroup menuLayout = (ViewGroup) findViewById(R.id.menuLayout);


                        menuLayout.setVisibility(View.GONE);
                        plus.setVisibility(View.VISIBLE);
                        minus.setVisibility(View.VISIBLE);
                        v.setVisibility(View.GONE);

                    }
                });


//                LayoutInflater L = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View v = L.inflate(R.layout.test,null);
//                RelativeLayout F = (RelativeLayout) findViewById(R.id.topRelative);
//                F.addView(v,200,200);

            }
        });
                assert manaBottom != null;
        manaBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(buttonClick);
////                LayoutInflater L = getLayoutInflater();
////                View v = L.inflate(R.layout.test, null);
////                RelativeLayout F = (RelativeLayout) findViewById(R.id.topRelative);
////
////                F.addView(v);
//                ViewGroup v = (ViewGroup) findViewById(R.id.test);
//                v.setVisibility(View.VISIBLE);

            }
        });
//        assert manaTop != null;
//        manaTop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.startAnimation(buttonClick);
//                Intent intent = new Intent(getApplicationContext(), Mana.class);
//                startActivity(intent);
//            }
//        });

//        assert manaBottom != null;
//        manaBottom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.startAnimation(buttonClick);
//                Intent intent = new Intent(getApplicationContext(), Mana.class);
//                startActivity(intent);
//            }
//        });



        assert plusTop != null;
        plusTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(buttonClick);
                int Num = 0;
                Num = Integer.parseInt(topNumber.getText().toString());
                Num++;
                String textNum = Integer.toString(Num);
                topNumber.setText(textNum);
            }
        });

        assert minusTop != null;
        minusTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(buttonClick);
                int Num = 0;
                Num = Integer.parseInt(topNumber.getText().toString());
                Num--;
                String textNum = Integer.toString(Num);
                topNumber.setText(textNum);
            }
        });

        assert plusBottom != null;
        plusBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(buttonClick);
                int Num = 0;
                Num = Integer.parseInt(bottomNumber.getText().toString());
                Num++;
                String textNum = Integer.toString(Num);
                bottomNumber.setText(textNum);
            }
        });

        assert minusBottom != null;
        minusBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(buttonClick);
                int Num = 0;
                Num = Integer.parseInt(bottomNumber.getText().toString());
                Num--;
                String textNum = Integer.toString(Num);
                bottomNumber.setText(textNum);
            }
        });


    }
    public void showPopUp(View view) {
        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0F);
        view.startAnimation(buttonClick);
        PopupMenu manaMenu = new PopupMenu(this, view);
        MenuInflater menuInflater = manaMenu.getMenuInflater();
        menuInflater.inflate(R.menu.mana_menu,manaMenu.getMenu());
        try {
            Class<?> classPopupMenu = Class.forName(manaMenu
                    .getClass().getName());
            Field mPopup = classPopupMenu.getDeclaredField("mPopup");
            mPopup.setAccessible(true);
            Object menuPopupHelper = mPopup.get(manaMenu);
            Class<?> classPopupHelper = Class.forName(menuPopupHelper
                    .getClass().getName());
            Method setForceIcons = classPopupHelper.getMethod(
                    "setForceShowIcon", boolean.class);
            setForceIcons.invoke(menuPopupHelper, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        manaMenu.show();

    }




}
