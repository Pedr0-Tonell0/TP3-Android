package com.example.login_registro;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.login_registro.ui.home.Parqueos;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DBNAME = "TPLab.db";

    public DBHelper(Context context) {
        super(context, "TPLab.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create table users (email text primary key, password text, username text)");
        myDB.execSQL("create table parqueos (patente text, tiempo text, user text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
        myDB.execSQL("drop table if exists users");
        myDB.execSQL("drop table if exists parqueos");

    }
    public boolean insertData (String email, String password, String username)
    {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("username",username);
        long result = myDB.insert("users",null,contentValues);
        if(result==-1)return false;
        else return true;
    }
    public boolean insertParqueo (String patente, String tiempo, String user)
    {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("patente",patente);
        contentValues.put("tiempo",tiempo);
        contentValues.put("user",user);
        long result = myDB.insert("parqueos",null,contentValues);
        if(result==-1)return false;
        else return true;
    }
    public boolean checkemail (String email)
    {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where email =?", new String []{email});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public boolean checkuser (String username)
    {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username =?", new String []{username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public String getemail (String username)
    {
        String email = null;
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select email from users where username =?", new String []{username});
        if (cursor.moveToNext()) email = cursor.getString(0);
        cursor.close();
        myDB.close();
        return email;
    }
    public ArrayList<Parqueos> getlistparqueos (String username)
    {
        ArrayList<Parqueos> list = new ArrayList<Parqueos>();
        Parqueos Parqueos;
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select patente,tiempo from parqueos where user =?", new String []{username});
        while (cursor.moveToNext())
        {
            Parqueos = new Parqueos();
            Parqueos.setPatente(cursor.getString(0));
            Parqueos.setTiempo(cursor.getString(1));
            list.add(Parqueos);
        }
        cursor.close();
        myDB.close();
        return list;
    }
    public boolean checkuserandpass (String username, String password)
    {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username =? and password = ?", new String []{username,password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
}
