package com.helpers;

public class Fonctions {

	public Fonctions() {
		
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
