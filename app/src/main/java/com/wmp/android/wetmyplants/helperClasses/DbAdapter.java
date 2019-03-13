package com.wmp.android.wetmyplants.helperClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAdapter {

    DbHelper dbhelper;

    public DbAdapter(Context context){

        dbhelper = new DbHelper(context);
    }

    public long insertUserData(String inFName, String inLName, String inPhone,
                               String inEmail, String inPass){

        SQLiteDatabase dtb = dbhelper.getWritableDatabase();
        ContentValues cValues = new ContentValues();

        cValues.put(DbHelper.FNAME, inFName);
        cValues.put(DbHelper.LNAME, inLName);
        cValues.put(DbHelper.PHN, inPhone);
        cValues.put(DbHelper.EMAIL, inEmail);
        cValues.put(DbHelper.PASSWORD, inPass);

        long id = dtb.insert(DbHelper.USER_TABLE, null, cValues);
        return id;
    }

    public String getUserData(){

        SQLiteDatabase dtb = dbhelper.getWritableDatabase();
        String[] tableColumns =
                {
                        DbHelper.UID,
                        DbHelper.FNAME,
                        DbHelper.LNAME,
                        DbHelper.PHN,
                        DbHelper.EMAIL,
                        DbHelper.PASSWORD
                };
        Cursor cursor = dtb.query(DbHelper.USER_TABLE, tableColumns, null, null,
                                    null, null, null );
                StringBuffer buffer = new StringBuffer();

        while(cursor.moveToNext()){

            int cid = cursor.getInt(cursor.getColumnIndex(DbHelper.UID));
            String firstname = cursor.getString(cursor.getColumnIndex(DbHelper.FNAME));
            String lastname = cursor.getString(cursor.getColumnIndex(DbHelper.LNAME));
            String phone = cursor.getString(cursor.getColumnIndex(DbHelper.PHN));
            String email = cursor.getString(cursor.getColumnIndex(DbHelper.EMAIL));
            String password = cursor.getString(cursor.getColumnIndex(DbHelper.PASSWORD));

            buffer.append(cid+ " " +firstname+ " " +lastname+ " " +phone+ " " +email+ " " +password+ "\n");
        }

        return buffer.toString();
    }

    public void deleteUser(String inEmail){

        SQLiteDatabase dtb = dbhelper.getWritableDatabase();
        String[] inArgs = {inEmail};

        dtb.delete(DbHelper.USER_TABLE, DbHelper.EMAIL+" ?", inArgs);
    }

    public void UpdateUserInfoFirstname(){

        SQLiteDatabase dtb = dbhelper.getWritableDatabase();
        ContentValues cValues = new ContentValues();


    }

    public void UpdateUserInfoLastname(){

        SQLiteDatabase dtb = dbhelper.getWritableDatabase();
        ContentValues cValues = new ContentValues();


    }

    public void UpdateUserInfoPhone(){

        SQLiteDatabase dtb = dbhelper.getWritableDatabase();
        ContentValues cValues = new ContentValues();


    }

    public void UpdateUserInfoEmail(){

        SQLiteDatabase dtb = dbhelper.getWritableDatabase();
        ContentValues cValues = new ContentValues();


    }

    /*-------------------------------DATABASE---------------------------------*/

    static class DbHelper extends SQLiteOpenHelper {

        /*DataBase*/
        private static final String DATABASE_NAME = "wmpLiteDb";      // Database Name
        private static final int DATABASE_Version = 1;                  // Database Version

        /*user table*/
        private static final String USER_TABLE = "userTable";           // Table Name
        private static final String UID="_uid";                         // Column 1 (Primary Key)
        private static final String FNAME = "FirstName";                // Column 2
        private static final String LNAME = "LastName";                 // Column 3
        private static final String PHN = "Phone";                      // Column 4
        private static final String EMAIL = "Email";                    // Column 5
        private static final String PASSWORD= "Password";               // Column 6

        /*plant table*/
        private static final String PLANT_TABLE = "plantTable";         // Table Name
        private static final String PID="_pid";                         // Column 1 (Primary Key)
        private static final String UID_P="_uidp";                      // Column 2 (Foreign Key)

        /*plant-type table*/
        private static final String TYPE_TABLE = "planttypeTable";      // Table Name
        private static final String PTID="_ptid";                       // Column 1 (Primary Key)
        private static final String TNAME = "TypeName";                 // Column 2
        private static final String LATNAME = "LatinName";              // Column 3
        private static final String WVMAX = "WaterVarMax";              // Column 4
        private static final String WVMIN = "WaterVarMin";              // Column 5
        private static final String LGVMAX = "LightVarMax";             // Column 6
        private static final String LGVMIN = "LightVarMin";             // Column 7

        /*sensor table*/
        private static final String SENSOR_TABLE = "plantTable";        // Table Name
        private static final String SID="_sid";                         // Column 1 (Primary Key)
        private static final String NNAME = "NickName";                 // Column 2
        private static final String WTRVAR = "WaterVariable";           // Column 3
        private static final String LGTVAR = "LightVariable";           // Column 4
        private static final String PID_S="_pids";                      // Column 5 (Foreign Key)
        private static final String PTID_S="_ptids";                    // Column 6 (Foreign Key)

        /*token table*/
        private static final String TOKEN_TABLE = "tokenTable";         // Table Name
        private static final String TID="_tid";                         // Column 1 (Primary Key)
        private static final String TOKEN = "Token";                    // Column 2
        private static final String UID_T = "_uidt";                    // Column 3 (Foreign Key)

        /*creating tables - ORM*/
        private static final String CREATE_USER_TABLE =
                "CREATE TABLE "+USER_TABLE+
                        " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ""+FNAME+" VARCHAR(255)," +
                        ""+LNAME+" VARCHAR(225)," +
                        ""+PHN+" VARCHAR(225)," +
                        ""+EMAIL+" VARCHAR(225)," +
                        ""+PASSWORD+" VARCHAR(225));";

        private static final String CREATE_PLANT_TABLE =
                " CREATE TABLE "+PLANT_TABLE+
                        "("+PID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+UID_P+" INTEGER, FOREIGN KEY("+UID_P+") REFERENCES "+USER_TABLE+"("+UID+"));";

        private static final String CREATE_TYPE_TABLE =
                "CREATE TABLE "+TYPE_TABLE+
                        " ("+PTID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ""+TNAME+" VARCHAR(255)," +
                        ""+LATNAME+" VARCHAR(225)," +
                        ""+WVMAX+" INTEGER," +
                        ""+WVMIN+" INTEGER," +
                        ""+LGVMAX+" INTEGER," +
                        ""+LGVMIN+" INTEGER);";

        private static final String CREATE_SENSOR_TABLE =
                "CREATE TABLE "+SENSOR_TABLE+
                        " ("+SID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ""+NNAME+" VARCHAR(225)," +
                        ""+WTRVAR+" INTEGER," +
                        ""+LGTVAR+" INTEGER," +
                        ""+PID_S+" INTEGER," +
                        ""+PTID_S+" INTEGER," +
                        "FOREIGN KEY("+PID_S+") REFERENCES "+PLANT_TABLE+"("+PID+")," +
                        "FOREIGN KEY("+PTID_S+") REFERENCES "+TYPE_TABLE+"("+PTID+"))";

        private static final String CREATE_TOKEN_TABLE =
                " CREATE TABLE "+TOKEN_TABLE+
                        "("+TID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ""+TOKEN+" VARCHAR(225)," +
                        ""+UID_T+" INTEGER, FOREIGN KEY("+UID_T+") REFERENCES "+USER_TABLE+"("+UID+"));";

        /*dropping table(s) if they already exist */
        private static final String DROP_USER_TABLE ="DROP TABLE IF EXISTS "+USER_TABLE;
        private static final String DROP_PLANT_TABLE ="DROP TABLE IF EXISTS "+PLANT_TABLE;
        private static final String DROP_TYPE_TABLE = "DROP TABLE IF EXISTS "+TYPE_TABLE;
        private static final String DROP_SENSOR_TABLE = "DROP TABLE IF EXISTS "+SENSOR_TABLE;
        private static final String DROP_TOKEN_TABLE ="DROP TABLE IF EXISTS "+TOKEN_TABLE;

        private Context context;

        public DbHelper(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;

        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_USER_TABLE);
                db.execSQL(CREATE_PLANT_TABLE);
                db.execSQL(CREATE_TYPE_TABLE);
                db.execSQL(CREATE_SENSOR_TABLE);
                db.execSQL(CREATE_TOKEN_TABLE);
            }
            catch (Exception e) {
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_USER_TABLE);
                db.execSQL(DROP_PLANT_TABLE);
                db.execSQL(DROP_TYPE_TABLE);
                db.execSQL(DROP_SENSOR_TABLE);
                db.execSQL(DROP_TOKEN_TABLE);

                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }
        }
    }
}
