package com.wmp.android.wetmyplants.helperClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseConnector {

    /*DataBase*/
    private static final String DATABASE_NAME = "WMP_DB";
    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseOpenHelper;

    public DatabaseConnector(Context context)
    {
        databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
    }

    public void open() throws SQLException
    {
        database = databaseOpenHelper.getWritableDatabase();
    }

    public void close()
    {
        if(database != null){
            database.close();
        }
    }

    /**----------------------- DATA INSERTION ---------------------------------*/

    public void insertUser(String infirstname, String inlastname, String inphone,
                            String inemail, String inpassword)
    {
        ContentValues newUser = new ContentValues();
        newUser.put("FirstName", infirstname);
        newUser.put("LastName", inlastname);
        newUser.put("PhoneNumber", inphone);
        newUser.put("Email", inemail);
        newUser.put("Password", inpassword);

        open();
        database.insert("User", null, newUser);
        close();
    }

    public void insertSpecies(String incommonname, String inlatinname, Double inwatermax,
                              Double inwatermin, Double inlightmax, Double inlightmin)
    {
        ContentValues newSpecies = new ContentValues();
        newSpecies.put("CommonName", incommonname);
        newSpecies.put("LatinName", inlatinname);
        newSpecies.put("WaterMax", inwatermax);
        newSpecies.put("WaterMin", inwatermin);
        newSpecies.put("LightMax", inlightmax);
        newSpecies.put("LightMin", inlightmin);

        open();
        database.insert("Species", null, newSpecies);
        close();
    }

    public void insertPlant(int inspeciesid, String innickname, Double incurrwater,
                            Double incurrlight)
    {
        ContentValues newPlant = new ContentValues();
        newPlant.put("SpeciesId", inspeciesid);
        newPlant.put("Nickname", innickname);
        newPlant.put("CurrentWater", incurrwater);
        newPlant.put("CurrentLight", incurrlight);

        open();
        database.insert("Plant", null, newPlant);
        close();
    }

    public void insertSensor(String inalias, Double inwatervar, Double inlightvar,
                             int inplantid, int inspeciesid)
    {
        ContentValues newSensor = new ContentValues();
        newSensor.put("Alias", inalias);
        newSensor.put("WaterVariable", inwatervar);
        newSensor.put("LightVariable", inlightvar);
        newSensor.put("PlantId", inplantid);
        newSensor.put("SpeciesId", inspeciesid);

        open();
        database.insert("Sensor", null, newSensor);
        close();
    }

    /**--------------------------- DATA UPDATE ---------------------------------*/

    public void updateUser(int inuserid, String infirstname, String inlastname, String inphone,
                           String inemail, String inpassword)
    {
        ContentValues editUser = new ContentValues();
        editUser.put("UserId", inuserid);
        editUser.put("FirstName", infirstname);
        editUser.put("LastName", inlastname);
        editUser.put("PhoneNumber", inphone);
        editUser.put("Email", inemail);
        editUser.put("Password", inpassword);

        open();
        database.update("User", editUser, "Email" + inemail, null);
        close();
    }

    /**----------------------- LOAD USER ACCOUNT ---------------------------------*/

    public Cursor loadOneUser(String email)
    {
        return database.query("User", null, "Email =" + email,
                null, null,null, null);
    }

    /**----------------------- DATABASE HELPER ---------------------------------*/

    private class DatabaseOpenHelper extends SQLiteOpenHelper
    {
        public DatabaseOpenHelper(Context context, String table_name,
                                  CursorFactory factory, int version)
        {
            super(context, table_name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            String createUserTableQuery = "CREATE TABLE User" +
                    "(UserId INTEGER primary key autoincrement," +
                    "FirstName TEXT, LastName TEXT, PhoneNumber TEXT," +
                    "Email TEXT, Password TEXT);";

            String createSpeciesTableQuery = "CREATE TABLE Species" +
                    "(SpeciesId INTEGER primary key autoincrement," +
                    "CommonName TEXT, LatinName TEXT, WaterMax REAL, WaterMin REAL," +
                    "LightMax REAL, LightMin REAL);";

            String createPlantTableQuery = "CREATE TABLE Plant" +
                    "(PlantId INTEGER primary key autoincrement," +
                    "Nickname TEXT, CurrentWater REAL, CurrentLight REAL," +
                    "SpeciesId INTEGER, FOREIGN KEY (SpeciesId) references Species (SpeciesId));";

            String createSensorTableQuery = "CREATE TABLE Sensor" +
                    "(SensorId INTEGER primary key autoincrement," +
                    "WaterVariable REAL, LightVariable REAL," +
                    "PlantId INTEGER, foreign key (PlantId) references Plant (PlantId))";

            db.execSQL(createUserTableQuery);
            db.execSQL(createSpeciesTableQuery);
            db.execSQL(createPlantTableQuery);
            db.execSQL(createSensorTableQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        { }
    }
}
