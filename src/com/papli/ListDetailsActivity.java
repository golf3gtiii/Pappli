package com.papli;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ListDetailsActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_details);
		
		//element de la vue
		TextView description = (TextView)findViewById(R.id.description);
		ImageView imageView = (ImageView)findViewById(R.id.imageView);
		TextView titre = (TextView)findViewById(R.id.titre);
		
		//variables
		Bitmap bmImg;
		
		//On récupère l'objet Bundle envoyŽ par l'autre Activity
        Bundle objetbunble  = this.getIntent().getExtras();
        //"http://www.la-guile.com/cake/images/interface/top.jpg";
        if (objetbunble != null && objetbunble.containsKey("description")) {
                description.setText(this.getIntent().getStringExtra("description"));
                titre.setText(this.getIntent().getStringExtra("titre"));
                
                if (this.getIntent().getStringExtra("image") != null) {	
                	Log.v("image", "http://entendre.infotronique.fr/_upload/"+this.getIntent().getStringExtra("image"));
                	 
	                try {
	  
	                	Drawable image;
	                	FileOutputStream fileOutputStream = null;
	                	String nameFile = this.getIntent().getStringExtra("image").toString();

	                	if (Drawable.createFromPath(this.getApplicationInfo().dataDir+"/" +nameFile) != null) {
	                		Log.v("local", nameFile);
	                		image = Drawable.createFromPath(this.getApplicationInfo().dataDir+"/" + nameFile);
		                	imageView.setImageDrawable(image);
	                	
	                	}	else {
						URL myFileUrl= new URL("http://entendre.infotronique.fr/_upload/"+this.getIntent().getStringExtra("image"));
						HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
		                conn.setDoInput(true);
		                conn.connect();
		                //int length = conn.getContentLength();
		                //int[] bitmapData =new int[length];
		                //byte[] bitmapData2 =new byte[length];
		                InputStream is = conn.getInputStream();
		
		                bmImg = BitmapFactory.decodeStream(is);
		                //imageView.setImageBitmap(bmImg);
		                
	                	
	                	
		                
			                try {
			                	Log.v("telechargement", nameFile);
			                	
			                	
			                	int quality = 100;
			                	BitmapFactory.Options options=new BitmapFactory.Options();
			                	options.inSampleSize = 5;
			                	
			                	fileOutputStream = new FileOutputStream(this.getApplicationInfo().dataDir+"/" + nameFile);
	
			                	BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
	
			                	bmImg.compress(CompressFormat.PNG, quality, bos);
	
			                	bos.flush();
	
			                	bos.close();
			                	
			                	image = Drawable.createFromPath(this.getApplicationInfo().dataDir+"/" + nameFile);
			                	
			                	imageView.setImageDrawable(image);
	
			                }
			                catch (Exception e) {
			                	Log.v("erreur save", "1");
			                }
	                	}
		                
	                } catch (MalformedURLException e) {
	                	
	                	e.printStackTrace();
	                }
	                catch (IOException e) {
	                	
	                	e.printStackTrace();
	                
	                }
                }
                else {
                	Log.v("image", "n'existe pas");
                	imageView.willNotDraw();
                	
                }
                

                
                
                
                //imageView.setI(new URL("http://entendre.infotronique.fr/flux-articles.php"));
        }else {
        	//Erreur
        	description.setText("error");
        }
        
       
	}
	
	


	
}
