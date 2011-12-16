package com.papli;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Sommaire extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sommaire);
		
		ImageView imageActu = (ImageView) findViewById(R.id.imageActu);
		
		imageActu.setClickable(true);
		imageActu.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(Sommaire.this, TabBottom.class);
				startActivity(intent);
			}
		});
		
	}
	
	//empecher le retour sur le loader(splashscreen)
	/*public void onBackPressed() {
	   PAppliActivity.group.back();
        return;
    }*/
}
