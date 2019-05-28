package com.android.wetmyplants.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.wetmyplants.R;
import com.android.wetmyplants.helperClasses.DbHelper;
import com.android.wetmyplants.helperClasses.HubRowAdapter;
import com.android.wetmyplants.model.UserCredentials;
import com.android.wetmyplants.restAdapter.Communicator;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

public class HubActivity extends AppCompatActivity {

    private Communicator communicator;
    private DbHelper database;
    String storedToken;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        communicator = new Communicator();
        database = new DbHelper(getApplicationContext());

        Intent getEmail = getIntent();
        userEmail = getEmail.getStringExtra("userEmail");
        final UserCredentials user = database.getUserCredential(userEmail);
        storedToken = user.getToken();

        List<String> hubTestList = new ArrayList<>();
        hubTestList.add("Hub 1");
        hubTestList.add("Hub 2");

        SwipeMenuListView hubListView = findViewById(R.id.hubListView);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                //create "Activate" item
                SwipeMenuItem activateItem = new SwipeMenuItem(getApplicationContext());
                activateItem.setBackground(new ColorDrawable(Color.rgb(0,153,0)));
                activateItem.setWidth(400);
                activateItem.setTitle("Activate");
                activateItem.setTitleSize(18);
                activateItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(activateItem);
            }
        };

        hubListView.setMenuCreator(creator);
        HubRowAdapter hubAdapter = new HubRowAdapter(getApplicationContext(), hubTestList);
        hubListView.setAdapter(hubAdapter);
        hubListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                //TODO: activate the code via retrofit here

                return false;
            }
        });

        FloatingActionButton homeHubFab = findViewById(R.id.hub_homeFab);
        homeHubFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HubActivity.this, DashboardActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });
    }
}
