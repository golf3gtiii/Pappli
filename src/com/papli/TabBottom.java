package com.papli;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TabBottom extends TabActivity {
	public TabHost tabHost;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tab_bottom);

            // Get the tabHost
	    this.tabHost = getTabHost();

	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch the first Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, PAppliActivity.class);

	    // Initialize a TabSpec for the first tab and add it to the TabHost
	    spec = tabHost.newTabSpec("FirstGroup").setIndicator("Actualités Entendre",
	    		getResources().getDrawable(R.drawable.actualites)) // Replace null with R.drawable.your_icon to set tab icon
	    				.setContent(intent);
	    tabHost.addTab(spec);

            // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, ListDetailsActivity.class);

	    // Initialize a TabSpec for the second tab and add it to the TabHost
	    spec = tabHost.newTabSpec("SecondGroup").setIndicator("Les centres Entendre",
	    		getResources().getDrawable(R.drawable.search_pressed)) // Replace null with R.drawable.your_icon to set tab icon
	    				.setContent(intent);
	    tabHost.addTab(spec);
	    
	    
	    // Initialize a TabSpec for the second tab and add it to the TabHost
	    spec = tabHost.newTabSpec("SecondGroup").setIndicator("Fiches audition",
	    		getResources().getDrawable(R.drawable.search_pressed)) // Replace null with R.drawable.your_icon to set tab icon
	    				.setContent(intent);
	    tabHost.addTab(spec);
	    
	    // Initialize a TabSpec for the second tab and add it to the TabHost
	    spec = tabHost.newTabSpec("SecondGroup").setIndicator("Nouveautés produits",
	    		getResources().getDrawable(R.drawable.search_pressed)) // Replace null with R.drawable.your_icon to set tab icon
	    				.setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(0);
	}
}
