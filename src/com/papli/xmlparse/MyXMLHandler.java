package com.papli.xmlparse;



import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyXMLHandler extends DefaultHandler {

	Boolean currentElement = false;
	String currentValue = null;
	public static SitesList sitesList = null;

	
	public static SitesList getSitesList() {
		return sitesList;
	}

	public static void setSitesList(SitesList sitesList) {
		MyXMLHandler.sitesList = sitesList;
	}

	/** Called when tag starts ( ex:- <name>AndroidPeople</name> 
	 * -- <name> )*/
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = true;

		if (localName.equals("Root"))
		{
			/** Start */ 
			sitesList = new SitesList();
		} 
		/*else if (localName.equals("website")) {
			
			String attr = attributes.getValue("category");
			sitesList.setCategory(attr);
		}*/

	}

	/** Called when tag closing ( ex:- <name>AndroidPeople</name> 
	 * -- </name> )*/
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		currentElement = false;

		/** set value */ 
		if (localName.equalsIgnoreCase("titre"))
			sitesList.setTitre(currentValue);
		else if (localName.equalsIgnoreCase("message"))
			sitesList.setMessage(currentValue);
		else if (localName.equalsIgnoreCase("image"))
			sitesList.setImage(currentValue);
		else if (localName.equalsIgnoreCase("lastUpdate"))
			sitesList.setLastUpdate(currentValue);
		else if (localName.equalsIgnoreCase("image_min"))
			sitesList.setImageMin(currentValue);
		else if (localName.equalsIgnoreCase("date"))
			sitesList.setDate(currentValue);

	}

	/** Called to get tag characters ( ex:- <name>AndroidPeople</name> 
	 * -- to get AndroidPeople Character ) */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (currentElement) {
			currentValue = new String(ch, start, length);
			currentElement = false;
		}

	}

}

