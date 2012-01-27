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
		//On créer la BDD et sa table
		maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
	}
	
	public void open(){
		//on ouvre la BDD en écriture
		bdd = maBaseSQLite.getWritableDatabase();
	}
	
	public void close(){
		//on ferme l'accès à la BDD
		bdd.close();
	}
	
	public void vide() {
		//suppression des donnŽes de la table
		bdd.execSQL("DELETE FROM "+TABLE_SYNCHROS);
		//rŽinitialisation de l'auto increment
		bdd.execSQL("DELETE from sqlite_sequence where name='"+TABLE_SYNCHROS+"'");
	}
	
	public SQLiteDatabase getBDD(){
		return bdd;
	}
	
	public long insertSynchro(Synchro synchro){
		//Création d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(COL_DATE, synchro.getDate());
		values.put(COL_URL, synchro.getUrl());
		//on insère l'objet dans la BDD via le ContentValues
		return bdd.insert(TABLE_SYNCHROS, null, values);
	}
 
	public int updateSynchro(int id, Synchro synchro){
		//La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
		//il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
		ContentValues values = new ContentValues();
		values.put(COL_DATE, synchro.getDate());
		values.put(COL_URL, synchro.getUrl());
		return bdd.update(TABLE_SYNCHROS, values, COL_ID + " = " +id, null);
	}
	
	public int removeSynchroWithID(int id){
		//Suppression d'un livre de la BDD grâce à l'ID
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
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
		Cursor c = bdd.query(TABLE_SYNCHROS, new String[] {COL_ID, COL_DATE, COL_URL}, COL_DATE + " LIKE \"" + date +"\"", null, null, null, null);
		return cursorToSynchro(c);
	}
	
	public Synchro getSynchroWithID(int id){
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
		Cursor c = bdd.query(TABLE_SYNCHROS, new String[] {COL_ID, COL_DATE, COL_URL}, COL_ID + " LIKE \"" + id +"\"", null, null, null, null);
		return cursorToSynchro(c);
	}
	
	//Cette méthode permet de convertir un cursor en un livre
	private Synchro cursorToSynchro(Cursor c){
		//si aucun élément n'a été retourné dans la requête, on renvoie null
		if (c.getCount() == 0)
			return null;
 
		//Sinon on se place sur le premier élément
		c.moveToFirst();
		//On créé un livre
		Synchro synchro = new Synchro();
		//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
		synchro.setId(c.getInt(NUM_COL_ID));
		synchro.setDate(c.getString(NUM_COL_DATE));
		synchro.setUrl(c.getString(NUM_COL_URL));
		//On ferme le cursor
		c.close();
 
		//On retourne le livre
		return synchro;
	}
	
}
