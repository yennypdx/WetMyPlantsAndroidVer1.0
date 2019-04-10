package com.wmp.android.wetmyplants.activities;

import android.animation.Animator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.wmp.android.wetmyplants.R;

public class PlantDetailActivity extends AppCompatActivity {

    FloatingActionButton fab, fabedit, fabdel;
    LinearLayout fablayoutEdit, fablayoutDel;
    View fabBGLayout;
    boolean isFABOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantdetail);

        fablayoutEdit = findViewById(R.id.fabLayoutEdit);
        fabedit = findViewById(R.id.fabEdit);
        fablayoutDel = findViewById(R.id.fabLayoutDelete);
        fabdel = findViewById(R.id.fabDelete);
        fab = findViewById(R.id.fab);
        fabBGLayout = findViewById(R.id.fabBGLayout);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFABOpen){
                    showFabMenu();
                } else {
                    closeFabMenu();
                }
            }
        });

        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFabMenu();
            }
        });
    }

    private void showFabMenu(){
        isFABOpen = true;
        fablayoutEdit.setVisibility(View.VISIBLE);
        fablayoutDel.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                if(fab.getRotation() != 180){
                    fab.setRotation(180);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        fablayoutEdit.animate().translationY(-getResources().getDimension(R.dimen.stand_55));
        fablayoutDel.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
    }

    private void closeFabMenu(){
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotationBy(-180);
        fablayoutEdit.animate().translationY(0);
        fablayoutDel.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                if(!isFABOpen){
                    fablayoutEdit.setVisibility(View.GONE);
                    fablayoutDel.setVisibility(View.GONE);
                }

                if(fab.getRotation() != -180){
                    fab.setRotation(-180);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
    }

    @Override
    public void onBackPressed(){
        if(isFABOpen){
            closeFabMenu();
        } else {
            super.onBackPressed();
        }
    }
}
