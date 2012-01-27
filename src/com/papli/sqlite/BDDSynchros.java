package com.papli.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BDDSynchros {
	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "entendre.db";
	
	private static final String TABLE_SYNCHROS = "synchros";
	private static final String COL_ID = "id";
	private static final int NUM_COL_ID = 0;
	private static final String COL_DATE = "date";
	private static final int NUM_COL_DATE = 1;
	private static final String COL_URL = "url";
	private static final int NUM_COL_URL = 2;
	
	private SQLiteDatabase bdd;
	 
	private MaBaseSQLite maBaseSQLite;
	
	public BDDSynchros(Context context){
		//On cr�er la BDD et sa table
		maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
	}
	
	public void open(){
		//on ouvre la BDD en �criture
		bdd = maBaseSQLite.getWritableDatabase();
	}
	
	public void close(){
		//on ferme l'acc�s � la BDD
		bdd.close();
	}
	
	public void vide() {
		//suppression des donn�es de la table
		bdd.execSQL("DELETE FROM "+TABLE_SYNCHROS);
		//r�initialisation de l'auto increment
		bdd.execSQL("DELETE from sqlite_sequence where name='"+TABLE_SYNCHROS+"'");
	}
	
	public SQLiteDatabase getBDD(){
		return bdd;
	}
	
	public long insertSynchro(Synchro synchro){
		//Cr�ation d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associ� � une cl� (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(COL_DATE, synchro.getDate());
		values.put(COL_URL, synchro.getUrl());
		//on ins�re l'objet dans la BDD via le ContentValues
		return bdd.insert(TABLE_SYNCHROS, null, values);
	}
 
	public int updateSynchro(int id, Synchro synchro){
		//La mise � jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
		//il faut simple pr�ciser quelle livre on doit mettre � jour gr�ce � l'ID
		ContentValues values = new ContentValues();
		values.put(COL_DATE, synchro.getDate());
		values.put(COL_URL, synchro.getUrl());
		return bdd.update(TABLE_SYNCHROS, values, COL_ID + " = " +id, null);
	}
	
	public int removeSynchroWithID(int id){
		//Suppression d'un livre de la BDD gr�ce � l'ID
		return bdd.delete(TABLE_SYNCHROS, COL_ID + " = " +id, null);
	}
 
	public Cursor getLastSynchro() {
		Cursor c = bdd.query(TABLE_SYNCHROS, null, null, null, null, null, null);
//		
//		c.moveToFirst();
		return c;
//		return c.getString(c.getColumnIndex(COL_DATE));
	}
	
	public Synchro getSynchroWithDate(String date){
		//R�cup�re dans un Cursor les valeur correspondant � un livre contenu dans la BDD (ici on s�lectionne le livre gr�ce � son titre)
		Cursor c = bdd.query(TABLE_SYNCHROS, new String[] {COL_ID, COL_DATE, COL_URL}, COL_DATE + " LIKE \"" + date +"\"", null, null, null, null);
		return cursorToSynchro(c);
	}
	
	public Synchro getSynchroWithID(int id){
		//R�cup�re dans un Cursor les valeur correspondant � un livre contenu dans la BDD (ici on s�lectionne le livre gr�ce � son titre)
		Cursor c = bdd.query(TABLE_SYNCHROS, new String[] {COL_ID, COL_DATE, COL_URL}, COL_ID + " LIKE \"" + id +"\"", null, null, null, null);
		return cursorToSynchro(c);
	}
	
	//Cette m�thode permet de convertir un cursor en un livre
	private Synchro cursorToSynchro(Cursor c){
		//si aucun �l�ment n'a �t� retourn� dans la requ�te, on renvoie null
		if (c.getCount() == 0)
			return null;
 
		//Sinon on se place sur le premier �l�ment
		c.moveToFirst();
		//On cr�� un livre
		Synchro synchro = new Synchro();
		//on lui affecte toutes les infos gr�ce aux infos contenues dans le Cursor
		synchro.setId(c.getInt(NUM_COL_ID));
		synchro.setDate(c.getString(NUM_COL_DATE));
		synchro.setUrl(c.getString(NUM_COL_URL));
		//On ferme le cursor
		c.close();
 
		//On retourne le livre
		return synchro;
	}
	
}
