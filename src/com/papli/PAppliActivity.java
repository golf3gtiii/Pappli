package com.papli;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.composants.CListActivity;
import com.papli.sqlite.BDDActus;
import com.papli.xmlparse.SitesList;

//public class PAppliActivity extends ListActivity {
public class PAppliActivity extends ActivityGroup {
	/** Called when the activity is first created. */

	/** Create Object For SiteList Class */
	SitesList sitesList = null;

	public ArrayList<String> titres = new ArrayList<String>();
	public ArrayList<String> descriptions = new ArrayList<String>();
	public ArrayList<String> images = new ArrayList<String>();
	private ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);

		try {
			
			//on récupère les actus
			BDDActus bddactus = new BDDActus(this);
			bddactus.open();	
			listItem = bddactus.getActus();
			
			bddactus.close();
			

		} catch (Exception e) {
			// System.out.println("XML Pasing Excpetion = " + e);
			Toast.makeText(getApplicationContext(),
					"Problème Connexion Internet", Toast.LENGTH_LONG).show();
		}

		// CLIC SUR L'ITEM
		// ListView lv = getListView();
		final CListActivity lv = (CListActivity) findViewById(R.id.ListViewFlo);

		lv.setTextFilterEnabled(true);
		lv.setAdapterTest(listItem);

		// clic sur l'item
		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {

				// on rÈcupËre la HashMap contenant les infos de notre item
				// (titre, description, img)
				HashMap<String, String> map = (HashMap<String, String>) lv.getItem(position);

				// On crÈÈ un objet Bundle, c'est ce qui va nous permetre
				// d'envoyer des donnÈes ‡ l'autre Activity
				Bundle objetbunble = new Bundle();
				// Cela fonctionne plus ou moins comme une HashMap, on entre une
				// clef et sa valeur en face
				objetbunble.putString("titre", (String) map.get("titre"));
				objetbunble.putString("description",(String) map.get("descriptionlong"));
				objetbunble.putString("image",(String) map.get("image"));

				// appel de l'activity
				Intent intent = new Intent(PAppliActivity.this,
						ListDetailsActivity.class);

				// On affecte ‡ l'Intent le Bundle que l'on a crÈÈ
				intent.putExtras(objetbunble);

				/*startActivity(intent);*/
				
				View view = getLocalActivityManager().startActivity("ListDetailsActivity", intent
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        .getDecorView();

				// Replace the view of this ActivityGroup
                // Changes this Groups View to the new View.
				setContentView(view);

			}
		});

	}

	
	
	/* HELPERS */
	public String premiers_mots(int nombre, String texte) {
		String NouveauTexte = "";
		String[] tabTexte = texte.split(" ");

		if (tabTexte.length > 5) {

			int i;
			for (i = 0; i < nombre; i++) {
				NouveauTexte += " " + tabTexte[i];
			}

			if (tabTexte[i + 1] != null)
				NouveauTexte += " ...";
		} else
			NouveauTexte = texte;

		return NouveauTexte;
	}

}
