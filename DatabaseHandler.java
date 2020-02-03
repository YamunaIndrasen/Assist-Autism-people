package com.example.autism;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DatabaseHandler extends SQLiteOpenHelper 
{

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "User";
	
	private static final String DETAILS="Userdetails";
	private static final String DETAILSQA="Userdetails";
	//private Bitmap usrphoto,urephoto;
	private static String usrphoto="usrphoto",urephoto="usrrephoto";
	private static String Usrname="Username";
	private static String UsrPhone="UserPhone";
	private static String Usradd="UsrAddress";
	private static String UsrDocno="UsrDoctNo";
	private static Bitmap b1;
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// 3rd argument to be passed is CursorFactory instance
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) 
	{ 
		String DetailsQA="CREATE TABLE UserDetailsQA(Username TEXT,UserPhone number,UsrAddress TEXT ,UsrDoctNo number,usrphoto BLOB,usrrephoto BLOB )";
		String Details="CREATE TABLE UserDetails(Username TEXT,UserPhone number,UsrAddress TEXT ,UsrDoctNo number,usrphoto BLOB,usrrephoto BLOB )";
		System.out.println("TABLE CREATED /.....SUCCESSFULLY...");
		db.execSQL(Details);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	void addregister(A2gs reg)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		
		values.put(Usrname, reg.getUsrname());
		values.put(UsrPhone, reg.getUsrPhone());
		values.put(Usradd, reg.getUsradd());

		values.put(UsrDocno, reg.getUsrDocno());
		values.put(usrphoto, Utility.getBytes(reg.getUsrphoto()));
		values.put(urephoto, Utility.getBytes(reg.getUrephoto()));
		// Inserting Row
		db.insert(DETAILS, null, values);
		// 2nd argument is String containing nullColumnHack
		db.close(); // Closing database connection
	}
	
	void select(A2gs reg)
	{
SQLiteDatabase db=this.getReadableDatabase();
		
		Cursor c=db.rawQuery("SELECT DISTINCT UserPhone,UserPhoto from UserDetails where Username='tamil'", null);
	
		if(c.moveToFirst())
		{
			
			String usrna=c.getString(c.getColumnIndex("UserPhone"));
			byte[] usrph=c.getBlob(c.getColumnIndex("UserPhoto"));
			b1=BitmapFactory.decodeByteArray(usrph, 0, usrph.length);
			System.out.println("Success******"+usrna);
			System.out.println("Success******"+b1);
		}
		
		db.close();
		
	}
}
