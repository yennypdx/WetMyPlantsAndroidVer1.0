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

    private static final String STORAGE_TABLE = "storage";
    private static final String STORAGE_ID = "id";
    private static final String STORAGE_COLUMN_EMAIL = "email";
    private static final String STORAGE_COLUMN_TOKEN = "token";

    private static final String PLANT_TABLE = "plant";
    private static final String PLANT_ID = "id";
    private static final String PLANT_COLUMN_SENSOR_ID = "sensor_id";
    private static final String PLANT_COLUMN_NICKNAME = "nickname";
    private static final String PLANT_COLUMN_SPECIES_ID = "species_id";
    private static final String PLANT_COLUMN_CURR_WTR = "curr_water";
    private static final String PLANT_COLUMN_CURR_LIGHT = "curr_light";
    private static final String STORAGE_REF_COLUMN = "sto_id";

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

    public DbHelper(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STORAGE_TABLE);
        db.execSQL(CREATE_PLANT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STORAGE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PLANT_TABLE);
        onCreate(db);
    }

    /* Handle all UserCredential queries */
    public void insertCredential (UserCredentials userCredentials) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try{
            values.put(STORAGE_COLUMN_EMAIL, userCredentials.getEmail());
            values.put(STORAGE_COLUMN_TOKEN, userCredentials.getToken());

            db.insert(STORAGE_TABLE, null, values);
        } catch(Exception e){
            Log.e(LOG, e.getMessage());
        } finally {
            db.close();
        }
    }

    public UserCredentials getUserCredential(String inEmail){
        SQLiteDatabase db = this.getReadableDatabase();
        UserCredentials user = new UserCredentials();

        String selectQuery =
                "SELECT * FROM " + STORAGE_TABLE + " WHERE "
                        + STORAGE_COLUMN_EMAIL + " = \'" + inEmail + "\'";
        Log.e(LOG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        try{
            if(cursor != null && cursor.moveToFirst()){
                user.setId(cursor.getInt(cursor.getColumnIndex(STORAGE_ID)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(STORAGE_COLUMN_EMAIL)));
                user.setToken(cursor.getString(cursor.getColumnIndex(STORAGE_COLUMN_TOKEN)));
            }
        }catch (Exception e){
            Log.e(LOG, e.getMessage());
        }finally {
            cursor.close();
            db.close();
        }
        return user;
    }

    public void updateCredential(UserCredentials userCredentials){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try{
            values.put(STORAGE_COLUMN_EMAIL, userCredentials.getEmail());
            values.put(STORAGE_COLUMN_TOKEN, userCredentials.getToken());

            // updating row
            db.update(STORAGE_TABLE, values, STORAGE_COLUMN_EMAIL + " = ?",
                    new String[] {String.valueOf(userCredentials.getEmail())});
        }catch (Exception e){
            Log.e(LOG, e.getMessage());
        }finally {
            db.close();
        }
    }

    /* Handle all Plants queries */
    public void insertPlant(Plant inPlant, String inEmail)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try{
            values.put(PLANT_COLUMN_SENSOR_ID, inPlant.getId());
            values.put(PLANT_COLUMN_NICKNAME, inPlant.getNickname());
            values.put(PLANT_COLUMN_SPECIES_ID, inPlant.getSpeciesId());
            values.put(PLANT_COLUMN_CURR_WTR, inPlant.getCurrentWater());
            values.put(PLANT_COLUMN_CURR_LIGHT, inPlant.getCurrentLight());
            values.put(STORAGE_REF_COLUMN, inEmail);

            db.insert(PLANT_TABLE, null, values);
        }catch (Exception e){
            Log.e(LOG, e.getMessage());
        }finally {
            db.close();
        }
    }

    public void updatePlantList(Plant inPlant, String inEmail)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try{
            values.put(PLANT_COLUMN_SENSOR_ID, inPlant.getId());
            values.put(PLANT_COLUMN_NICKNAME, inPlant.getNickname());
            values.put(PLANT_COLUMN_SPECIES_ID, inPlant.getSpeciesId());
            values.put(PLANT_COLUMN_CURR_WTR, inPlant.getCurrentWater());
            values.put(PLANT_COLUMN_CURR_LIGHT, inPlant.getCurrentLight());
            values.put(STORAGE_REF_COLUMN, inPlant.getEmailRef());

            db.update(PLANT_TABLE, values, STORAGE_REF_COLUMN + " = ?",
                    new String[] {String.valueOf(inEmail)});
        }catch (Exception e){
            Log.e(LOG, e.getMessage());
        }finally {
            db.close();
        }
    }

    public void editPlantData(Plant inPlant, String inEmail)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try{
            values.put(PLANT_COLUMN_SENSOR_ID, inPlant.getId());
            values.put(PLANT_COLUMN_NICKNAME, inPlant.getNickname());
            values.put(PLANT_COLUMN_SPECIES_ID, inPlant.getSpeciesId());
            values.put(PLANT_COLUMN_CURR_WTR, inPlant.getCurrentWater());
            values.put(PLANT_COLUMN_CURR_LIGHT, inPlant.getCurrentLight());
            values.put(STORAGE_REF_COLUMN, inPlant.getEmailRef());

            db.update(PLANT_TABLE, values, STORAGE_REF_COLUMN + " = ?",
                    new String[] {String.valueOf(inEmail)});
        }catch (Exception e){
            Log.e(LOG, e.getMessage());
        }finally {
            db.close();
        }
    }

    public Plant getPlant(String inName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Plant plant = new Plant();

        String selectQuery =
                "SELECT * FROM " + PLANT_TABLE + " WHERE "
                        + PLANT_COLUMN_NICKNAME + " = \'" + inName + "\'";
        Log.e(LOG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        try{

            if(cursor != null && cursor.moveToFirst()){
                plant.setId(cursor.getString(cursor.getColumnIndex(PLANT_COLUMN_SENSOR_ID)));
                plant.setNickname(cursor.getString(cursor.getColumnIndex(PLANT_COLUMN_NICKNAME)));
                plant.setSpeciesId(cursor.getInt(cursor.getColumnIndex(PLANT_COLUMN_SPECIES_ID)));
                plant.setCurrentWater(cursor.getDouble(cursor.getColumnIndex(PLANT_COLUMN_CURR_WTR)));
                plant.setCurrentLight(cursor.getDouble(cursor.getColumnIndex(PLANT_COLUMN_CURR_LIGHT)));

            }
        } catch (Exception e){
            Log.e(LOG, e.getMessage());
        }finally {
            cursor.close();
            db.close();
        }
        return plant;
    }

    public List<Plant> getAllPlants(String inEmail)
    {
        List<Plant> plants = new ArrayList<>();
        String selectQuery =
                "SELECT * FROM " + PLANT_TABLE + " WHERE " + STORAGE_REF_COLUMN + " = \'"
                        + inEmail + "\'";
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        try{
            if(cursor.moveToFirst()){
                while(!cursor.isAfterLast()){
                    Plant plant = new Plant();
                    plant.setId(cursor.getString(cursor.getColumnIndex(PLANT_COLUMN_SENSOR_ID)));
                    plant.setNickname(cursor.getString(cursor.getColumnIndex(PLANT_COLUMN_NICKNAME)));
                    plant.setSpeciesId(cursor.getInt(cursor.getColumnIndex(PLANT_COLUMN_SPECIES_ID)));
                    plant.setCurrentWater(cursor.getDouble(cursor.getColumnIndex(PLANT_COLUMN_CURR_WTR)));
                    plant.setCurrentLight(cursor.getDouble(cursor.getColumnIndex(PLANT_COLUMN_CURR_LIGHT)));

                    plants.add(plant);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.e(LOG, e.getMessage());
        } finally {
            cursor.close();
            db.close();
        }

        return plants;
    }
    public void deletePlants(String inEmail)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PLANT_TABLE, STORAGE_REF_COLUMN + " = ?", new String[] {String.valueOf(inEmail)});
        db.close();
    }

    /* Searching for Email in Plant Table */
    public boolean isEmailExist(String inEmail)
    {
        boolean found = false;
        String selectQuery =
                "SELECT * FROM " + PLANT_TABLE + " WHERE " + STORAGE_REF_COLUMN + " = \'"
                        + inEmail + "\'";
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try{
            if(cursor.getCount() > 0){
                found = true;
            }
        }catch (Exception e){
            Log.e(LOG, e.getMessage());
        }finally {
            cursor.close();
            db.close();
        }

        return found;
    }

}