package com.papli;

import android.app.ActivityGroup;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ListDetailsActivity extends ActivityGroup {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_details);
		
		//element de la vue
		TextView description = (TextView)findViewById(R.id.description);
		ImageView imageView = (ImageView)findViewById(R.id.imageView);
		TextView titre = (TextView)findViewById(R.id.titre);
		
		
		//On récupère l'objet Bundle envoyŽ par l'autre Activity
        Bundle objetbunble  = this.getIntent().getExtras();
        if (objetbunble != null && objetbunble.containsKey("description")) {
                description.setText(this.getIntent().getStringExtra("description"));
                titre.setText(this.getIntent().getStringExtra("titre"));
                
                String Image = this.getIntent().getStringExtra("image");
                if (Image.length() > 0 && !Image.contentEquals("/data/data/com.papli/images/")) {
                	Drawable image;
                	image = Drawable.createFromPath(Image);
                	imageView.setImageDrawable(image);
                	Log.v("Image set", Image);
                	
                }
                else {
                	Log.v("image", "n'existe pas");
                	imageView.setVisibility(ImageView.INVISIBLE);
                	LinearLayout.LayoutParams layoutParams = new LayoutParams(0,0);
                	imageView.setLayoutParams(layoutParams);
                	
                	
                }
                
        }else {
        	//Erreur
        	description.setText("error");
        }
        
       
	}

	
	


	
}
