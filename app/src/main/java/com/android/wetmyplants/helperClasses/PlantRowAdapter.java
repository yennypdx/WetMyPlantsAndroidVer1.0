package com.android.wetmyplants.helperClasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.wetmyplants.R;
import com.android.wetmyplants.model.Plant;
import com.android.wetmyplants.model.PlantRow;

import java.util.ArrayList;
import java.util.List;

public class PlantRowAdapter extends ArrayAdapter<Plant> {

    private List<Plant> plantList;
    private Context mContext;

    public PlantRowAdapter(@NonNull Context context, List<Plant> array) {
        super(context, 0, array);
        this.mContext = context;
        this.plantList = array;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.row_plant, parent, false);
        }

        Plant plant = plantList.get(position);

        //TODO: upgrade changing the plant images in Ver2.0
        TextView plantName = listItem.findViewById(R.id.plantNameTextView);
        String name = plant.getNickname();
        plantName.setText(name);
        TextView plantId = listItem.findViewById(R.id.plantIdTextView);
        String pid = plant.getId();
        plantId.setText(pid);

        return listItem;
    }
}
