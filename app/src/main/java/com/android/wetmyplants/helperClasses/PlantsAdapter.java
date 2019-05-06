package com.android.wetmyplants.helperClasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wetmyplants.R;
import com.android.wetmyplants.model.Plant;
import com.android.wetmyplants.model.PlantTestModel;

import java.util.ArrayList;

public class PlantsAdapter extends ArrayAdapter<Plant> {

    private ArrayList<Plant> plantList = new ArrayList<>();
    private Context mContext;


    public PlantsAdapter(@NonNull Context context, ArrayList<Plant> list) {
        super(context, 0, list);
        this.mContext = context;
        this.plantList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.row_plant, parent, false);
        }

        Plant currentPlant = plantList.get(position);

        //TODO: upgrade changing the plant images in Ver2.0
        TextView plantName = (TextView) listItem.findViewById(R.id.plantIdTextView);
        plantName.setText(currentPlant.getNickname());
        TextView plantId = (TextView) listItem.findViewById(R.id.plantIdTextView);
        plantId.setText(currentPlant.getSensorSerial());

        return listItem;
    }
}
