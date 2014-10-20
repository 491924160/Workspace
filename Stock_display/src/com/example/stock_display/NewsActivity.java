package com.example.stock_display;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class NewsActivity extends Activity  {
	private int count;
	private String[] links;
	
	private void AddItem(String msg) throws Exception  {
		
		ComInfo info = new ComInfo();
		ListView list = (ListView) NewsActivity.this.findViewById(R.id.list);
		JSONArray item_list = new JSONArray(msg);
		count = item_list.length();
		String[] titles = new String[count];
		links = new String[count];
		
		for(int i = 0; i < count; i++){
			JSONObject unit = item_list.getJSONObject(i);
			titles[i] = unit.getString(info.TitleTag);
			links[i] = unit.getString(info.LinkTag);
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewsActivity.this, android.R.layout.simple_list_item_1, titles );
		list.setAdapter(adapter);		
	}
	
	private void show(final int pos){
		Builder test = new AlertDialog.Builder(NewsActivity.this);
		test.setTitle("View");
		test.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();				
			}
		});
		test.setNegativeButton("View", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub			
				String link = links[pos];
				Intent caonima = new Intent();
				caonima.setAction("android.intent.action.VIEW");
				caonima.setData(Uri.parse(link));
				caonima.setClassName("com.android.browser","com.android.browser.BrowserActivity");   
				startActivity(caonima);	
			}
		});
		
		AlertDialog dialog = test.create();
		dialog.show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		Intent intent = getIntent();
		try {
			AddItem(intent.getStringExtra("com.example.stock_display.MainActivity"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Toast toast = Toast.makeText(this, "Showing " + count + "headlines", 1000);
		toast.show();
		
		ListView list = (ListView) NewsActivity.this.findViewById(R.id.list);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				show(position);
			}
		});
		
	}

}
