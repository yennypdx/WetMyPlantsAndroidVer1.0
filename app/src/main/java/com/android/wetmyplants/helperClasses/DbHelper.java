package com.android.wetmyplants.helperClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.wetmyplants.model.Plant;
import com.android.wetmyplants.model.PlantRow;
import com.android.wetmyplants.model.UserCredentials;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    /*Logcat tag*/
    private static final String LOG = "DbHelper_WetMyPlants";

    /*DataBase*/
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WetMyPlantsStorage";

    public static final String STORAGE_TABLE = "storage";
    public static final String STORAGE_ID = "id";
    public static final String STORAGE_COLUMN_EMAIL = "email";
    public static final String STORAGE_COLUMN_TOKEN = "token";

    public static final String PLANT_TABLE = "plant";
    public static final String PLANT_ID = "id";
    public static final String PLANT_COLUMN_SENSOR_ID = "sensor_id";
    public static final String PLANT_COLUMN_NICKNAME = "nickname";
    public static final String PLANT_COLUMN_SPECIES_ID = "species_id";
    public static final String PLANT_COLUMN_CURR_WTR = "curr_water";
    public static final String PLANT_COLUMN_CURR_LIGHT = "curr_light";
    public static final String STORAGE_REF_COLUMN = "sto_id";

    public static final String PLANTROW_TABLE = "plant_row";
    public static final String PLANTROW_ID = "id";
    public static final String PLANTROW_COLUMN_PLANT_ID = "plant_id";
    public static final String PLANTROW_COLUMN_NICKNAME = "nickname";
    public static final String STORAGE_REF_COLUMN_PROW = "sto_prow_id";

    private static final String CREATE_STORAGE_TABLE =
            "CREATE TABLE " + STORAGE_TABLE + "("
                    + STORAGE_ID + " INTEGER PRIMARY KEY, "
                    + STORAGE_COLUMN_EMAIL + " TEXT, "
                    + STORAGE_COLUMN_TOKEN + " TEXT " +")";

    private static final String CREATE_PLANT_TABLE =
            "CREATE TABLE " + PLANT_TABLE + "("
                    + PLANT_ID + " INTEGER PRIMARY KEY, "
                    + PLANT_COLUMN_SENSOR_ID + " TEXT, "
                    + PLANT_COLUMN_NICKNAME + " TEXT, "
                    + PLANT_COLUMN_SPECIES_ID + " INTEGER, "
                    + PLANT_COLUMN_CURR_WTR + " REAL, "
                    + PLANT_COLUMN_CURR_LIGHT + " REAL, "
                    + STORAGE_REF_COLUMN + " INTEGER, FOREIGN KEY (" + STORAGE_ID + ") REFERENCES "
                    + STORAGE_TABLE + "( " + STORAGE_ID + ")" + ")";

    private static final String CREATE_PLANTROW_TABLE =
            "CREATE TABLE " + PLANTROW_TABLE + "("
                    + PLANTROW_ID + " INTEGER PRIMARY KEY, "
                    + PLANTROW_COLUMN_PLANT_ID + " TEXT, "
                    + PLANTROW_COLUMN_NICKNAME + " TEXT, "
                    + STORAGE_REF_COLUMN_PROW + " INTEGER, FOREIGN KEY (" + STORAGE_ID + ") REFERENCES "
                    + STORAGE_TABLE + "( " + STORAGE_ID + ")" + ")";

    public DbHelper(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STORAGE_TABLE);
        db.execSQL(CREATE_PLANT_TABLE);
        db.execSQL(CREATE_PLANTROW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STORAGE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PLANT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PLANTROW_TABLE);
        onCreate(db);
    }

    public void insertCredential (UserCredentials userCredentials) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(STORAGE_COLUMN_EMAIL, userCredentials.getEmail());
        values.put(STORAGE_COLUMN_TOKEN, userCredentials.getToken());

        db.insert(STORAGE_TABLE, null, values);
        db.close();
    }

    public void insertPlant(Plant inPlant, String inEmail)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PLANT_COLUMN_SENSOR_ID, inPlant.getId());
        values.put(PLANT_COLUMN_NICKNAME, inPlant.getNickname());
        values.put(PLANT_COLUMN_SPECIES_ID, inPlant.getSpeciesId());
        values.put(PLANT_COLUMN_CURR_WTR, inPlant.getCurrentWater());
        values.put(PLANT_COLUMN_CURR_LIGHT, inPlant.getCurrentLight());
        values.put(STORAGE_REF_COLUMN, inEmail);

        db.insert(PLANT_TABLE, null, values);
        db.close();
    }

    public void insertPlantRow(PlantRow inPlantRow, String inEmail)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PLANTROW_COLUMN_PLANT_ID, inPlantRow.getId());
        values.put(PLANTROW_COLUMN_NICKNAME, inPlantRow.getNickname());
        values.put(STORAGE_REF_COLUMN_PROW, inEmail);

        db.insert(PLANTROW_TABLE, null, values);
        db.close();
    }

    public int updateCredential(UserCredentials userCredentials){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(STORAGE_COLUMN_EMAIL, userCredentials.getEmail());
        values.put(STORAGE_COLUMN_TOKEN, userCredentials.getToken());

        // updating row
        return db.update(STORAGE_TABLE, values, STORAGE_COLUMN_EMAIL + " = ?",
                new String[] {String.valueOf(userCredentials.getEmail())});

    }

    public int updatePlantData(Plant inPlant, String inEmail)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PLANT_COLUMN_SENSOR_ID, inPlant.getId());
        values.put(PLANT_COLUMN_NICKNAME, inPlant.getNickname());
        values.put(PLANT_COLUMN_SPECIES_ID, inPlant.getSpeciesId());
        values.put(PLANT_COLUMN_CURR_WTR, inPlant.getCurrentWater());
        values.put(PLANT_COLUMN_CURR_LIGHT, inPlant.getCurrentLight());

        // updating row
        return db.update(PLANT_TABLE, values, STORAGE_REF_COLUMN + " = ?",
                new String[] {String.valueOf(inEmail)});
    }

    public int updatePartsOfPlantData(Plant inPlant, String inEmail)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PLANT_COLUMN_CURR_WTR, inPlant.getCurrentWater());
        values.put(PLANT_COLUMN_CURR_LIGHT, inPlant.getCurrentLight());

        // updating row
        return db.update(PLANT_TABLE, values, STORAGE_REF_COLUMN + " = ?",
                new String[] {String.valueOf(inEmail)});
    }

    public UserCredentials getUserCredential(String inEmail){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery =
                "SELECT * FROM " + STORAGE_TABLE + " WHERE "
                        + STORAGE_COLUMN_EMAIL + " = \'" + inEmail + "\'";
        Log.e(LOG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor != null){ cursor.moveToFirst(); }

        UserCredentials user = new UserCredentials();
        user.setId(cursor.getInt(cursor.getColumnIndex(STORAGE_ID)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(STORAGE_COLUMN_EMAIL)));
        user.setToken(cursor.getString(cursor.getColumnIndex(STORAGE_COLUMN_TOKEN)));

        return user;
    }

    public Plant getPlant(String inId)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery =
                "SELECT * FROM " + PLANT_TABLE + " WHERE "
                        + PLANT_COLUMN_SENSOR_ID + " = \'" + inId + "\'";
        Log.e(LOG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor != null){ cursor.moveToFirst(); }

        Plant plant = new Plant();
        plant.setId(cursor.getString(cursor.getColumnIndex(PLANT_COLUMN_SENSOR_ID)));
        plant.setNickname(cursor.getString(cursor.getColumnIndex(PLANT_COLUMN_NICKNAME)));
        plant.setSpeciesId(cursor.getInt(cursor.getColumnIndex(PLANT_COLUMN_SPECIES_ID)));
        plant.setCurrentWater(cursor.getDouble(cursor.getColumnIndex(PLANT_COLUMN_CURR_WTR)));
        plant.setCurrentLight(cursor.getDouble(cursor.getColumnIndex(PLANT_COLUMN_CURR_LIGHT)));

        return plant;
    }

    public PlantRow getPlantRow(String inEmail)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery =
                "SELECT * FROM " + PLANTROW_TABLE + " WHERE "
                        + STORAGE_REF_COLUMN_PROW + " = \'" + inEmail + "\'";
        Log.e(LOG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor != null){ cursor.moveToFirst(); }

        PlantRow plant = new PlantRow();
        plant.setId(cursor.getString(cursor.getColumnIndex(PLANTROW_COLUMN_PLANT_ID)));
        plant.setNickname(cursor.getString(cursor.getColumnIndex(PLANTROW_COLUMN_NICKNAME)));

        return plant;
    }

    public List<Plant> getAllPlants(String inEmail)
    {
        List<Plant> plants = new ArrayList<Plant>();
        String selectQuery =
                "SELECT * FROM " + PLANT_TABLE + " WHERE " + STORAGE_REF_COLUMN + " = \'"
                        + inEmail + "\'";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                Plant plant = new Plant();
                plant.setId(cursor.getString(cursor.getColumnIndex(PLANT_COLUMN_SENSOR_ID)));
                plant.setNickname(cursor.getString(cursor.getColumnIndex(PLANT_COLUMN_NICKNAME)));
                plant.setSpeciesId(cursor.getInt(cursor.getColumnIndex(PLANT_COLUMN_SPECIES_ID)));
                plant.setCurrentWater(cursor.getDouble(cursor.getColumnIndex(PLANT_COLUMN_CURR_WTR)));
                plant.setCurrentLight(cursor.getDouble(cursor.getColumnIndex(PLANT_COLUMN_CURR_LIGHT)));

                plants.add(plant);
            } while (cursor.moveToNext());
        }
        return plants;
    }

    public boolean isEmailExist(String inEmail)
    {
        boolean found = false;
        Plant plant = new Plant();
        String selectQuery =
                "SELECT * FROM " + PLANT_TABLE + " WHERE " + STORAGE_REF_COLUMN + " = \'"
                        + inEmail + "\'";
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.getCount() > 0){
            found = true;
        }
        cursor.close();
        return found;
    }

}