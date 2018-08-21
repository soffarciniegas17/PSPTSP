package com.example.sofia.psptsp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    private static final String name ="registro.db";
    private static final String table_project= "CREATE TABLE DATOS(ID INTEGER PRIMARY KEY, NOMBRE TEXT, TIEMPO INTEGER)";
    private static final String table_Time= "CREATE TABLE TIMELOG(IDTIME INTEGER PRIMARY KEY, INICIO TEXT, FINAL TEXT," +
            "PHASE TEXT, DELTA INTEGER, COMENTARIOS TEXT)";
    private static final String table_Defect= "CREATE TABLE DEFECT(IDDEFECT INTEGER PRIMARY KEY, DATE TEXT, TYPE TEXT," +
            "INYECTED INTEGER, REMOVED INTEGER, FIX INTEGER, SOLUCION TEXT)";


    private static int version=1;
    public DataBase(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table_project);

        db.execSQL(table_Time);

        db.execSQL(table_Defect);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CREATE"+table_project);
        db.execSQL(table_project);


        db.execSQL("DROP TABLE IF EXISTS CREATE"+table_Time);
        db.execSQL(table_Time);

        db.execSQL("DROP TABLE IF EXISTS CREATE"+table_Defect);
        db.execSQL(table_Defect);


    }

    public void traer(String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues= new ContentValues();


        contentValues.put("NOMBRE", name);

        long datos = db.insert("DATOS", null, contentValues);
    }

    public Cursor cargar(){
        Cursor cursor= null;
        SQLiteDatabase db = getReadableDatabase();

        try {
            String find []= {"NOMBRE","ID" };

            cursor= db.query("DATOS", find, null, null, null, null, null);

        } catch (Exception e){

        }

        return cursor;
    }

    public void ingresarId(int id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put("ID", id);

        long datos = db.insert("DATOS", null, contentValues);
    }


    public void insertTime(String inicio, String Final, String phase, int delta, String comentarios){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put("INICIO", inicio);
        contentValues.put("FINAL", Final);
        contentValues.put("PHASE", phase);
        contentValues.put("DELTA", delta);
        contentValues.put("COMENTARIOS", comentarios);

        long datos = db.insert("TIMELOG", null, contentValues);
    }

    public void insertDefect (String date, String type, int inyected, int removed, int fix, String comentarios){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put("DATE", date);
        contentValues.put("TYPE", type);
        contentValues.put("INYECTED", inyected);
        contentValues.put("REMOVED", removed);
        contentValues.put("FIX", fix);
        contentValues.put("SOLUCION", comentarios);

        long datos = db.insert("DEFECT", null, contentValues);
    }



}
