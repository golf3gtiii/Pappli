package com.papli.sqlite;

public class Actus {
	private int id;
	private String titre;
	private String message;
	private String image;
	private String image_min;
	private String date;
	
	public Actus() {
		
	}
	
	public Actus(String titre, String message, String image, String image_min, String date) {
		this.titre = titre;
		this.message = message;
		this.image = image;
		this.image_min = image_min;
		this.date = date;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitre() {
		return titre;
	}
	
	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getImageMin() {
		return image_min;
	}
	
	public void setImageMin(String image_min) {
		this.image = image_min;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date= date;
	}
	
	public String toString(){
		return "ID : "+id+"\n Titre: "+titre+"\n Message: "+message+"\nImage : "+image+"\nImageMin : "+image_min+"\nDate : "+date;
	}
}
