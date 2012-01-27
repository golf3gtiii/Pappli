package com.composants;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.papli.R;

public class CListActivity extends ListView {
	
//	private Context context;
	
	
	
	ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
	
	public CListActivity(Context context) {
		super(context);		
//		this.context = context;
		initList();		
	}
	 
	public CListActivity(Context context, AttributeSet attrs) {
		super( context, attrs );
//		this.context = context;
		initList();		
	}
	 
	public CListActivity(Context context, AttributeSet attrs, int defStyle) {	 
		super( context, attrs, defStyle );
//		this.context = context;
		initList();
	}
	
	public void initList() {
		//création du layout affiché pour chaque ligne de la liste
		/*LinearLayout linear = new LinearLayout(getContext());
		TextView titre 		= new TextView(this.getContext());
		
		linear.setOrientation(LinearLayout.VERTICAL);
		titre.setText("Mon titre ici");
		titre.setTag("@+id/score_name_entry");
		titre.setId(1);
		linear.addView(titre);
		*/
		
		
		
		
		
		//SimpleAdapter mSchedule = new SimpleAdapter(this.getContext(), listItem, linear, new String[] {"titre"}, new int[] {"dfs"});
		
		if (isInEditMode()) {
			
			HashMap<String, String> map;
			map = new HashMap<String, String>();
			map.put("titre", "titre 1");
			map.put("description", "description 1");
			listItem.add(map);
			map = new HashMap<String, String>();
			map.put("titre", "titre 2");
			map.put("description", "description 2");
			listItem.add(map);
			map = new HashMap<String, String>();
			map.put("titre", "titre 3");
			map.put("description", "description 3");
			listItem.add(map);
			
			SimpleAdapter mSchedule = new SimpleAdapter(this.getContext(), listItem, R.layout.actualites_list_row, new String[] {"titre", "description"}, new int[] {R.id.titre, R.id.description});

			
//		//data display edit mode
		//ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this., android.R.layout.simple_list_item_1, dataExamples);
//		this.setAdapter(new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, dataExamples));
		this.setAdapter(mSchedule);
//		
//		setBackgroundColor(Color.BLUE);
//		setPadding(10, 20, 10, 0);
		}
		else {
			//données dynamiques
		}
	}
	
	public void setAdapterTest(ArrayList<HashMap<String, String>> listItem) {
		SimpleAdapter mSchedule = new SimpleAdapter(this.getContext(), listItem, R.layout.actualites_list_row, new String[] {"titre", "date", "description", "image_min"}, new int[] {R.id.titre, R.id.date, R.id.description, R.id.img});
		super.setAdapter(mSchedule);
		this.listItem = listItem;
	}
	
	
	public HashMap<String, String> getItem(int position) {
	    return this.listItem.get(position);
	}

	
	

}
