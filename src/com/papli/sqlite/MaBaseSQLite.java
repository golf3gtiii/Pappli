package com.papli.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MaBaseSQLite extends SQLiteOpenHelper {

	private static final String TABLE_SYNCHROS = "synchros";
	private static final String TABLE_ACTUS = "actus";
	
	private static final String CREATE_BDD = "CREATE TABLE "+TABLE_SYNCHROS+" (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		" date TEXT NOT NULL, " +
		" url TEXT NOT NULL" 
	+");";
	
	private static final String CREATE_BDD2 = "CREATE TABLE "+TABLE_ACTUS+" (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		" titre TEXT NOT NULL, " +
		" message TEXT NOT NULL," +
		" image TEXT NOT NULL," +
		" image_min TEXT NOT NULL," +
		" date TEXT NOT NULL"
	+");";
	
	public MaBaseSQLite(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_BDD);
		db.execSQL(CREATE_BDD2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE " + TABLE_SYNCHROS + ";");
		db.execSQL("DROP TABLE " + TABLE_ACTUS + ";");
		onCreate(db);
	}
	
	public void test(SQLiteDatabase db) {
		db.execSQL("DROP TABLE " + TABLE_SYNCHROS + ";");
		db.execSQL("DROP TABLE " + TABLE_ACTUS + ";");
	}
	
}
