package com.wmp.android.wetmyplants.activities;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wmp.android.wetmyplants.R;

public class PlantDetailActivity extends AppCompatActivity {

    FloatingActionButton fab, fabedit, fabdel;
    LinearLayout fablayoutEdit, fablayoutDel;
    TextView menuFabLabel;
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
        menuFabLabel = findViewById(R.id.fabmenu);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFABOpen){
                    menuFabLabel.setVisibility(View.GONE);
                    showFabMenu();
                } else {
                    menuFabLabel.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            menuFabLabel.setVisibility(View.VISIBLE);
                        }
                    }, 500);
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

        fabedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toPlantEdit = new Intent(
                        PlantDetailActivity.this, PlantEditActivity.class);
                startActivity(toPlantEdit);
            }
        });

        fabdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: create confirmation pop-up to user for deletion!
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //TODO: proceed to delete data via Retrofit!

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                Intent goToPlantList = new Intent(
                                        PlantDetailActivity.this, PlantsActivity.class);
                                startActivity(goToPlantList);
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(PlantDetailActivity.this);
                builder.setMessage("Are you sure to delete?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
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
