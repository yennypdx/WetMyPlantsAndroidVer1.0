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
import com.android.wetmyplants.model.Hub;

import java.util.List;
// temporary changed from List<Hub> to List<String> for testing purposes
public class HubRowAdapter extends ArrayAdapter<String> {
    private List<String> hubList;
    private Context mContext;

    public HubRowAdapter(@NonNull Context context, List<String> array){
        super(context, 0, array);
        this.mContext = context;
        this.hubList = array;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.row_hub, parent, false);
        }

        //Hub hub = hubList.get(position);
        String hub = hubList.get(position);

        TextView hubName = listItem.findViewById(R.id.HubIdTextView);
        //String name = hub.getId();
        //hubName.setText(name);
        hubName.setText(hub);

        return listItem;
    }
}
