package com.papli.xmlparse;

import java.util.ArrayList;

/** Contains getter and setter method for varialbles  */
public class SitesList {

	/** Variables */
	/*private ArrayList<String> name = new ArrayList<String>();
	private ArrayList<String> website = new ArrayList<String>();
	private ArrayList<String> category = new ArrayList<String>();*/
	private ArrayList<String> titre = new ArrayList<String>();
	private ArrayList<String> message = new ArrayList<String>();
	private ArrayList<String> image = new ArrayList<String>();
	private ArrayList<String> image_min = new ArrayList<String>();
	private ArrayList<String> date = new ArrayList<String>();
	private ArrayList<String> lastUpdate = new ArrayList<String>();
	
	/** In Setter method default it will return arraylist 
	 *  change that to add  */
	
	
	public ArrayList<String> getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre.add(titre);
	}
	
	public ArrayList<String> getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message.add(message);
	}
	
	public ArrayList<String> getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image.add(image);
	}
	
	public ArrayList<String> getImageMin() {
		return image;
	}

	public void setImageMin(String image_min) {
		this.image_min.add(image_min);
	}
	
	public ArrayList<String> getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date.add(date);
	}
	
	public ArrayList<String> getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate.add(lastUpdate);
	}

}

