package com.papli;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.papli.sqlite.Actus;
import com.papli.sqlite.BDDActus;
import com.papli.sqlite.BDDSynchros;
import com.papli.sqlite.Synchro;
import com.papli.xmlparse.MyXMLHandler;
import com.papli.xmlparse.SitesList;

public class Loader extends Activity {

	protected boolean _active = true;
	protected int _splashTime = 2000; // time to display the splash screen in ms

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loader);

		checkUpdates();
		gotoSommaire();

	}

	public void gotoSommaire() {
		// thread for displaying the SplashScreen
		/*Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (_active && (waited < _splashTime)) {
						sleep(100);
						if (_active) {
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					finish();
					Intent intent = new Intent(Loader.this, Sommaire.class);
					startActivity(intent);
					stop();
				}
			}
		};
		splashTread.start();*/
		Intent intent = new Intent(Loader.this, Sommaire.class);
		startActivity(intent);
	}

	// on regarde si le fichier xml en ligne est bien le dernier
	public boolean checkUpdates() {

		// on regarde quel est la derniere update

		// on parse le xml
		try {
			// url du xml
			URL sourceUrl = new URL(
					"http://entendre.infotronique.fr/flux-articles.php");

			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			/** Create handler to handle XML Tags ( extends DefaultHandler ) */
			MyXMLHandler myXMLHandler = new MyXMLHandler();
			/** Get result from MyXMLHandler SitlesList Object */
			xr.setContentHandler(myXMLHandler);
			xr.parse(new InputSource(sourceUrl.openStream()));
			SitesList sitesList = MyXMLHandler.sitesList;

			// Création d'une instance de ma classe BDDsynchro
			BDDSynchros bddSynchro = new BDDSynchros(this);

			// si la date de synchro a changé on regénère la bdd
			// if
			// (bddSynchro.getSynchroWithDate(sitesList.getLastUpdate().get(0))
			// == null) {

			Log.v("recréation de la bdd", "test");

			// création d'une instance de synchro
			Synchro synchro = new Synchro(sitesList.getLastUpdate().get(0),
					sourceUrl.toString());

			// on ouvre l'accès à la bdd
			bddSynchro.open();

			// réinitialisation de la bdd
			bddSynchro.vide();

			bddSynchro.insertSynchro(synchro);
			bddSynchro.close();

			// Création d'une instance de ma classe BDDsynchro
			BDDActus bddActu = new BDDActus(this);

			// création d'une instance de synchro
			// Actus actu = new Actus("titre", "message", "image",
			// "image_min", "date");
			Actus actu;
			String tmp1;
			String tmp2;
			// on ouvre l'accès à la bdd
			bddActu.open();
			bddActu.vide();

			for (int i = 0; i < sitesList.getTitre().size(); i++) {
				tmp1 = sitesList.getImage().get(i) != sitesList.getMessage()
						.get(i) ? sitesList.getImage().get(i) : "";
				tmp2 = sitesList.getImageMin().get(i) != sitesList.getMessage()
						.get(i) ? sitesList.getImageMin().get(i) : "";

				actu = new Actus(sitesList.getTitre().get(i), sitesList
						.getMessage().get(i), tmp1, tmp2, sitesList.getDate()
						.get(i));

				bddActu.insertActu(actu);

				// HashMap<String, String> map;
				// map = new HashMap<String, String>();
				// map.put("titre", sitesList.getTitre().get(i));
				// map.put("description", premiers_mots(5,
				// sitesList.getDescription().get(i)));
				// map.put("descriptionlong",
				// sitesList.getDescription().get(i));
				// listItem.add(map);

			}

			bddActu.close();
			// }
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Problème Connexion Internet", Toast.LENGTH_LONG).show();
		}
		return false;
	}

}
