package com.wmp.android.wetmyplants.helperClasses;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wmp.android.wetmyplants.R;
import com.wmp.android.wetmyplants.model.PlantTestModel;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<PlantTestModel> implements View.OnClickListener{

    private ArrayList<PlantTestModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtPlantId;
        ImageView imgPlant;
    }

    public CustomAdapter(ArrayList<PlantTestModel> data, Context context) {
        super(context, R.layout.row_plant, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);

        //TODO: go to Plant detail
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PlantTestModel plantModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_plant, parent, false);
            viewHolder.txtPlantId = (TextView) convertView.findViewById(R.id.plant_desc);
            viewHolder.imgPlant = (ImageView) convertView.findViewById(R.id.plant_icon);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtPlantId.setText(plantModel.getSpeciesId());
        viewHolder.txtPlantId.setOnClickListener(this);
        viewHolder.txtPlantId.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
