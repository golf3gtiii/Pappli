package com.papli.sqlite;

public class Synchro {
	private int id;
	private String date;
	private String url;
 
	public Synchro(){}
 
	public Synchro(String date, String url){
		this.date = date;
		this.url = url;
	}
 
	public int getId() {
		return id;
	}
 
	public void setId(int id) {
		this.id = id;
	}
 
	public String getDate() {
		return date;
	}
 
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getUrl() {
		return url;
	}
 
	public void setUrl(String url) {
		this.url = url;
	}
}
