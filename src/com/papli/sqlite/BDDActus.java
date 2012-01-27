package com.papli.sqlite;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.helpers.Fonctions;
import com.papli.R;

public class BDDActus {
	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "entendre.db";
	
	private static final String TABLE_ACTUS = "actus";
	private static final String COL_ID = "id";
	private static final int NUM_COL_ID = 0;
	private static final String COL_TITRE = "titre";
	private static final int NUM_COL_TITRE = 1;
	private static final String COL_MESSAGE = "message";
	private static final int NUM_COL_MESSAGE = 2;
	private static final String COL_IMAGE = "image";
	private static final int NUM_COL_IMAGE = 3;
	private static final String COL_IMAGEMIN = "image_min";
	private static final int NUM_COL_IMAGEMIN = 4;
	private static final String COL_DATE = "date";
	private static final int NUM_COL_DATE = 5;
	
	private SQLiteDatabase bdd;
	 
	private MaBaseSQLite maBaseSQLite;
	
	public BDDActus(Context context){
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
		bdd.execSQL("DELETE FROM "+TABLE_ACTUS);
		//rŽinitialisation de l'auto increment
		bdd.execSQL("DELETE from sqlite_sequence where name='"+TABLE_ACTUS+"'");
	}
	
	public SQLiteDatabase getBDD(){
		return bdd;
	}

	public long insertActu(Actus actu){
		//Création d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		
		values.put(COL_DATE, actu.getDate());
		values.put(COL_TITRE, actu.getTitre());
		values.put(COL_MESSAGE, actu.getMessage());
		values.put(COL_IMAGE, actu.getImage());
		values.put(COL_IMAGEMIN, actu.getImageMin());
		
		//on insère l'objet dans la BDD via le ContentValues
		return bdd.insert(TABLE_ACTUS, null, values);
	}
 
	public int updateActu(int id, Actus actu){
		//La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
		//il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
		ContentValues values = new ContentValues();
		values.put(COL_DATE, actu.getDate());
		values.put(COL_TITRE, actu.getTitre());
		values.put(COL_MESSAGE, actu.getMessage());
		values.put(COL_IMAGE, actu.getImage());
		values.put(COL_IMAGEMIN, actu.getImageMin());
		return bdd.update(TABLE_ACTUS, values, COL_ID + " = " +id, null);
	}
	
	public int removeActuWithID(int id){
		//Suppression d'un livre de la BDD grâce à l'ID
		return bdd.delete(TABLE_ACTUS, COL_ID + " = " +id, null);
	}
 
	public Cursor getActusImage() {
		Cursor c = bdd.query(TABLE_ACTUS, new String[] {COL_IMAGE, COL_IMAGEMIN}, null, null, null, null, null);
		return c;
	}
	
	public ArrayList<HashMap<String, String>> getActus() {
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		Cursor actus = bdd.query(TABLE_ACTUS, new String[] {COL_ID, COL_DATE, COL_TITRE, COL_MESSAGE, COL_IMAGE, COL_IMAGEMIN}, null, null, null, null, null);
		
		Fonctions fonction = new Fonctions();
		
		actus.moveToFirst();
		while (actus.isAfterLast() == false) {
			HashMap<String, String> map;
			map = new HashMap<String, String>();
			map.put("titre", actus.getString(actus.getColumnIndex("titre")));

			Timestamp timeStamp = new Timestamp((long) (actus.getLong(actus.getColumnIndex("date"))*1000)  );
			Date      date      = new Date(timeStamp.getTime());
//			SimpleDateFormat formatter = new SimpleDateFormat("dd/MMMM/YYYY ˆ HH:mm:ss");
			String formatter = (new SimpleDateFormat("dd MMMM yyyy", Locale.FRANCE)).format(date);
			map.put("date", ""+formatter);
//			map.put("date", actus.getString(actus.getColumnIndex("date")));
			
			map.put("description", fonction.premiers_mots(5,actus.getString(actus.getColumnIndex("message"))));
			map.put("descriptionlong", actus.getString(actus.getColumnIndex("message")));
			map.put("image", "/data/data/com.papli/images/"+actus.getString(actus.getColumnIndex("image")));
			
			String Image = actus.getString(actus.getColumnIndex("image"));
			if (Image.contentEquals("")) {
				map.put("image_min", String.valueOf(R.drawable.centerpin));
			}
			else {
				map.put("image_min", "/data/data/com.papli/images/"+Image);
			}
			listItem.add(map);
			
			actus.moveToNext();
		}
		actus.close();
		
		return listItem;
	}
	
	public Actus getActuWithID(int id){
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
		Cursor c = bdd.query(TABLE_ACTUS, new String[] {COL_ID, COL_DATE, COL_TITRE, COL_MESSAGE, COL_IMAGE, COL_IMAGEMIN}, COL_ID + " LIKE \"" + id +"\"", null, null, null, null);
		return cursorToActu(c);
	}
	
	
	
	//Cette méthode permet de convertir un cursor en un livre
	public Actus cursorToActu(Cursor c){
		//si aucun élément n'a été retourné dans la requête, on renvoie null
		if (c.getCount() == 0)
			return null;
 
		//Sinon on se place sur le premier élément
		c.moveToFirst();
		//On créé un livre
		Actus actu = new Actus();
		//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
		actu.setId(c.getInt(NUM_COL_ID));
		actu.setDate(c.getString(NUM_COL_DATE));
		actu.setTitre(c.getString(NUM_COL_TITRE));
		actu.setMessage(c.getString(NUM_COL_MESSAGE));
		actu.setImage(c.getString(NUM_COL_IMAGE));
		actu.setImageMin(c.getString(NUM_COL_IMAGEMIN));
		//On ferme le cursor
		c.close();
 
		//On retourne le livre
		return actu;
	}
	
	//Cette méthode permet de convertir un cursor en un livre
	public Actus cursorToActu2(Cursor c){
		//si aucun élément n'a été retourné dans la requête, on renvoie null
		if (c.getCount() == 0)
			return null;
 
		//Sinon on se place sur le premier élément
		//c.moveToFirst();
		//On créé un livre
		Actus actu = new Actus();
		//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
		actu.setId(c.getInt(NUM_COL_ID));
		actu.setDate(c.getString(NUM_COL_DATE));
		actu.setTitre(c.getString(NUM_COL_TITRE));
		actu.setMessage(c.getString(NUM_COL_MESSAGE));
		actu.setImage(c.getString(NUM_COL_IMAGE));
		actu.setImageMin(c.getString(NUM_COL_IMAGEMIN));
		//On ferme le cursor
		c.close();
 
		//On retourne le livre
		return actu;
	}
}
