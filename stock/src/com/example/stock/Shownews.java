package com.example.stock;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.net.*;

public class Shownews extends Activity{
	static String[] titles;
	static String[] links;	
	static Builder builder;
	public  void onCreate(Bundle bundle) {
		super.onCreate(bundle);			
		setContentView(R.layout.list_view);
		setview();
		Toast toast = Toast.makeText(this, "Showing " + titles.length + "headlines", 1000);
		toast.show();
		setonclick();	
	}
	
	public void createbuilder(final int position){
		Builder builder = new AlertDialog.Builder(Shownews.this);
		builder.setTitle("View");
		builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();				
			}
		});
		builder.setNegativeButton("View", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub			
				String link = links[position];
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				intent.setData(Uri.parse(link));
				intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");   
				startActivity(intent);	
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void setonclick(){
		ListView list = (ListView) Shownews.this.findViewById(R.id.listView1);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				createbuilder(position);
			}
		});
	}
	
	public void setview(){
		Intent intent = getIntent();
		ListView list=(ListView) Shownews.this.findViewById(R.id.listView1);
		String news = intent.getStringExtra("com.example.stock.MainActivity");
		String[] items = news.split("\n");
		//System.out.println(items.length);
		int length = items.length;
		titles = new String[length/2];
		links = new String[length/2];
		int count = 0;
//		ArrayList<TextView> list_1 = new ArrayList<TextView>();
		while(count < items.length){
			String title = items[count];
			String link = items[count+1];
			titles[count/2] = title;
			links[count/2] = link;
			count += 2;
//			final TextView view = new TextView(Shownews.this);
//			view.setText(title.trim());
//			view.setHint(link.trim());
//			list_1.add(view);
//			view.setOnClickListener(new View.OnClickListener() {			
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					String url = (String) view.getHint();
//					Intent intent = new Intent(Intent.ACTION_VIEW);
//					intent.setData(Uri.parse(url));
//					startActivity(intent);
//				}
//			});			
		}
//		Iterator<TextView> iter = list_1.iterator();
//		TextView[] array = new TextView[list_1.size()];
//		int i = 0;
//		while(iter.hasNext()){
//			array[i++] = iter.next();
//		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(Shownews.this, android.R.layout.simple_list_item_1, titles );
		list.setAdapter(adapter);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//this.finish();
	}
	
	
}
