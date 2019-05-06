package com.android.wetmyplants.helperClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.wetmyplants.model.UserCredentials;

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

    private static final String CREATE_STORAGE_TABLE =
            "CREATE TABLE " + STORAGE_TABLE + "("
                    + STORAGE_ID + " INTEGER PRIMARY KEY, "
                    + STORAGE_COLUMN_EMAIL + " TEXT, "
                    + STORAGE_COLUMN_TOKEN + " TEXT " +")";

    public DbHelper(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STORAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STORAGE_TABLE);
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

    public int updateCredential(UserCredentials userCredentials){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(STORAGE_COLUMN_EMAIL, userCredentials.getEmail());
        values.put(STORAGE_COLUMN_TOKEN, userCredentials.getToken());

        // updating row
        return db.update(STORAGE_TABLE, values, STORAGE_COLUMN_EMAIL + " = ?",
                new String[] {String.valueOf(userCredentials.getEmail())});

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

}