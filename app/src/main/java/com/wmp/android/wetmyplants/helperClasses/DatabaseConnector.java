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

    public void insertUserToken(String intoken, String inemail)
    {
        ContentValues newToken = new ContentValues();
        newToken.put("Token", intoken);
        newToken.put("Email", inemail);

        open();
        database.insert("UserToken", null, newToken);
        close();
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
            String createUserTokenTableQuery = "CREATE TABLE UserToken" +
                    "(Id INTEGER primary key autoincrement, Token TEXT, Email TEXT);";

            db.execSQL(createUserTokenTableQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        { }
    }
}
