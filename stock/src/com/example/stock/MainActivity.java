package com.example.stock;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.FacebookException;
import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.widget.WebDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.*;

public class MainActivity extends Activity {
	
	AutoCompleteTextView text;
	 Button bt1;
	 ListView list_view;
	 static String com;
	 static StringBuilder sb = new StringBuilder();
	 private String pic_path;
	 private String symbol;
	 private String name;
	 private String price;
	 private String change;
	 
	 
	 class GetJson extends AsyncTask<String,Integer,String>{
		 Drawable dr_1;
		 
		private Drawable LoadImageFromURL(String url) {
			try {
				InputStream is = (InputStream) new URL(url).getContent();
				Drawable d = Drawable.createFromStream(is, "src name");
				return d;
			} catch (Exception e) {
				System.out.println("Excecption is=" + e);
				return null;
			}
		}
		
		private void SetImage(String url){
			Drawable dr = LoadImageFromURL(url);
			dr_1 = dr;
		}
				
		@SuppressWarnings("finally")
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String path = "http://cs-server.usc.edu:17721/examples/servlet/getstock?symbol=" + params[0];
			StringBuilder builder = new StringBuilder();
			pic_path = "http://chart.finance.yahoo.com/t?s="
							+ params[0] +"&lang=enUS&amp;width=300&height=180";
			InputStream input=null;
			try {				
				SetImage(pic_path);
				URL url=new URL(path);
				input = url.openStream();
				InputStreamReader reader=new InputStreamReader(input);
				BufferedReader reader_1 = new BufferedReader(reader);
				String line=null;
				while((line=reader_1.readLine())!=null){
					builder.append(line);
				}
				input.close();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e2){
				e2.printStackTrace();
			} catch (IOException e3){
				e3.printStackTrace();
			}finally{
				return builder.toString();
			}		
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//System.out.println(result);
			Activity ac=MainActivity.this;
			if(result != ""){
				try{
					JSONObject json = new JSONObject(result);
					JSONObject result_1 = json.getJSONObject("result");
					JSONObject news = result_1.getJSONObject("News");
					JSONObject quote = result_1.getJSONObject("Quote");
					ac.findViewById(R.id.table).setVisibility(View.VISIBLE);
					ac.findViewById(R.id.ll1).setVisibility(View.VISIBLE);
					ac.findViewById(R.id.imageView1)
							.setVisibility(View.VISIBLE);

					TextView view = null;
					view = (TextView) ac.findViewById(R.id.textView5);
					view.setText(quote.getString("PreviousClose"));
					view = (TextView) ac.findViewById(R.id.textView6);
					view.setText(quote.getString("Open"));
					view = (TextView) ac.findViewById(R.id.textView8);
					view.setText(quote.getString("Bid"));

					view = (TextView) ac.findViewById(R.id.textView10);
					if (quote.getString("Ask").equals("0.00"))
						view.setText("");
					else
						view.setText(quote.getString("Ask"));

					view = (TextView) ac.findViewById(R.id.textView12);
					view.setText(quote.getString("OneyrTargetPrice"));
					view = (TextView) ac.findViewById(R.id.textView14);
					view.setText(quote.getString("DaysLow") + "-"
							+ quote.getString("DaysHigh"));
					view = (TextView) ac.findViewById(R.id.textView16);
					view.setText(quote.getString("YearLow") + "-"
							+ quote.getString("YearHigh"));
					view = (TextView) ac.findViewById(R.id.textView18);
					view.setText(quote.getString("Volume"));
					view = (TextView) ac.findViewById(R.id.textView20);
					view.setText(quote.getString("AverageDailyVolume"));
					view = (TextView) ac.findViewById(R.id.textView22);
					view.setText(quote.getString("MarketCapitalization"));
					view = (TextView) ac.findViewById(R.id.textView2);

					ImageView image = (ImageView) MainActivity.this.findViewById(R.id.imageView1); 
					image.setImageDrawable(dr_1);
					
					view = (TextView) ac.findViewById(R.id.extra);
					view.setText(quote.getString("LastTradePriceOnly"));
					
					symbol = result_1.getString("Name");
					name = result_1.getString("Symbol");
					price = quote.getString("LastTradePriceOnly");
					change = quote.getString("Change") + "(" +quote.getString("ChangeinPercent") + ")";
					
					view = (TextView) ac.findViewById(R.id.textView2);
					view.setText(result_1.getString("Name") +"("+ result_1.getString("Symbol") + ")");
					
					if(quote.getString("ChangeType").equals("-")){ 
						view=(TextView) ac.findViewById(R.id.textView3);
						view.setText(change);
						view.setPadding(10,0, 0, 0);
						ImageView img_1 = (ImageView) MainActivity.this.findViewById(R.id.imageView2);
						img_1.setImageResource(R.drawable.down_r);
						img_1.setBackgroundColor(Color.TRANSPARENT);
						view.setTextColor(Color.RED);
					}else if(quote.getString("ChangeType").equals("+")){
						System.out.println(quote.getString("ChangeType"));
						view=(TextView) ac.findViewById(R.id.textView3);
						view.setText(change);
						
						ImageView img_1 = (ImageView) MainActivity.this.findViewById(R.id.imageView2);
						img_1.setImageResource(R.drawable.up);
						img_1.setBackgroundColor(Color.TRANSPARENT);
						view.setTextColor(Color.GREEN);
					}else{
						LinearLayout layout_1 = (LinearLayout) MainActivity.this.findViewById(R.id.ld13);
						layout_1.setVisibility(View.INVISIBLE);
					}
					
					JSONArray items = news.getJSONArray("Item");
					
					for(int i = 0; i < items.length(); i++){
						JSONObject item = items.getJSONObject(i);
						sb.append(item.getString("Title") + "\n "+item.getString("Link") + "\n");
					}
					
					LinearLayout table = (LinearLayout) MainActivity.this.findViewById(R.id.table);
					
					 
				} catch(JSONException e){
					e.printStackTrace();
				}
			}else{	
				Builder builder=new AlertDialog.Builder(MainActivity.this);
				builder.setMessage("Please input a valid symbol!");
				builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}); 
				AlertDialog dialog = builder.create();
				ac.findViewById(R.id.table).setVisibility(View.INVISIBLE);	
				ac.findViewById(R.id.imageView1).setVisibility(View.INVISIBLE);
				ac.findViewById(R.id.ll1).setVisibility(View.INVISIBLE);
				dialog.show();
			}
		}	
	}
	
	 
	class Autosuggestion extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
// 			AutoCompleteTextView at = (AutoCompleteTextView) MainActivity.this.findViewById(R.id.editText1);
			if(params[0].length() == 0) return null;
			int index = params[0].length()-1;
			if(params[0].charAt(index) == ')'){
				params[0] = params[0].split(",")[0];
				//at.setText(params[0]);
			}
			String path = "http://autoc.finance.yahoo.com/autoc?query=" + params[0] + "&callback=YAHOO.Finance.SymbolSuggest.ssCallback";
			try{
				URL url = new URL(path);
				InputStream input = url.openStream();
				InputStreamReader reader =  new InputStreamReader(input);
				int i = 0;
				StringBuilder sb = new StringBuilder();
				while((i=reader.read()) != -1){
					sb.append((char)i);
				}
				input.close();
				return sb.toString();
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result == null) return;
			ArrayList<String> list = new ArrayList<String>();			
			result = result.substring(39, result.length()-1);			
			if(result.length()>38){
			try {
				JSONObject resultset = new JSONObject(result);
				JSONArray set = resultset.getJSONObject("ResultSet").getJSONArray("Result");
				for(int i = 0; i < set.length(); i++){
					JSONObject obj = set.getJSONObject(i);
					String item = obj.getString("symbol") +", " 
								  + obj.getString("name") + "("
								  + obj.getString("exch") +")";
					list.add(item);
				}
			AutoCompleteTextView at = (AutoCompleteTextView) MainActivity.this.findViewById(R.id.editText1);			
			String[] array = new String[list.size()];
			Iterator iter = list.iterator();
			int count = 0;
			while(iter.hasNext()){
				array[count++] = (String) iter.next();
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_dropdown_item_1line, array);
			at.setAdapter(adapter);				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(Exception e1){
				e1.printStackTrace();
			}
			
		}
		}		 
	 }
		
	
	public void onButton1Click(View  view){
		
		//text ....
		//Toast.makeText(this, "you click me!",1000).show();
		AutoCompleteTextView text = (AutoCompleteTextView)MainActivity.this.findViewById(R.id.editText1);
		com = text.getText().toString();
		Builder builder=new AlertDialog.Builder(MainActivity.this);
		builder.setMessage("Please input a symbol!");
		builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		}); 
		AlertDialog dialog = builder.create();
		System.out.println(text.getText().toString());
		if(com.equals("")){
			dialog.show();
			Activity ac = MainActivity.this;
			ac.findViewById(R.id.table).setVisibility(View.INVISIBLE);	
			ac.findViewById(R.id.imageView1).setVisibility(View.INVISIBLE);
			ac.findViewById(R.id.ll1).setVisibility(View.INVISIBLE);
		}else{
			//dialog.show();	
			GetJson json=new GetJson();
			json.execute(text.getEditableText().toString());
				
		}
	}
	private void InitAll(){
		AutoCompleteTextView at = (AutoCompleteTextView) MainActivity.this.findViewById(R.id.editText1);
		at.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				new Autosuggestion().execute(s.toString());
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}			
		});
		at.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				AutoCompleteTextView at = (AutoCompleteTextView) MainActivity.this.findViewById(R.id.editText1);
				TextView tx = (TextView)view;
				String text = tx.getText().toString();
//				System.out.println(text.split(",")[0]);
//				System.out.println(tx == null);
				at.setText(text.split(",")[0]);
				new GetJson().execute(at.getText().toString());			
			}
		});
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}
	
	private void OpenSession(){	
		 Session.openActiveSession(this, true, new Session.StatusCallback() {
				@Override
				public void call(Session session, SessionState state,
						Exception exception) {
					if(session.isOpened()){
						System.out.println(session.getState());
					}else{
						
					}
				}
			  });
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		InitAll();
		OpenSession();
//		
//		 try {
//		        PackageInfo info = getPackageManager().getPackageInfo(
//		                "com.example.stock", 
//		                PackageManager.GET_SIGNATURES);
//		        for (Signature signature : info.signatures) {
//		            MessageDigest md = MessageDigest.getInstance("SHA");
//		            md.update(signature.toByteArray());
//		            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//		            }
//		    } catch (NameNotFoundException e) {
//		    		e.printStackTrace();
//		    } catch (NoSuchAlgorithmException e) {
//		    		e.printStackTrace();
//		    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void shownews(View view){
		Intent intent=new Intent(this,Shownews.class);
		intent.putExtra("com.example.stock.MainActivity", sb.toString());
		startActivity(intent);
	}
	
	private void Feed() {
	    try{
	        Bundle params = new Bundle();
	        params.putString("name", symbol);//title 
	        params.putString("caption", "Stock Information of "
	        				+ symbol
	        				+ "(" + name + ")");//caption 
	        params.putString("description", "Last Trade Price:"
	        				+ price + "\n"
	        				+ "Change:" + change);
	        params.putString("picture", pic_path);
	        params.putString("app_id", "1426155367639577");
	        params.putString("link",  "http://finance.yahoo.com/q;_ylt=AjYxKpIh6L.aoN385saI6wup_8MF;_yl"
	        				+ "c=X1MDMjE0MjQ3ODk0OARfcgMyBGZyA3VoM19maW5hbmNlX3dlYl9ncwRmcjIDc2EtZ3AEZ3B"
	        				+ "yaWQDBG5fZ3BzAzEwBG9yaWdpbgNmaW5hbmNlLnlhaG9vLmNvbQRwb3MDMQRwcXN0cgMEcXVl"
	        				+ "cnkDQUEsBHNhYwMxBHNhbwMx?p=http%3A%2F%2Ffinance.yahoo.com%2Fq%3Fs%3DAA%26"
	        				+ "ql%3D0&type=2button&fr=uh3_finance_web_gs&uhb=uhb2&s="+name);

	        WebDialog feedDialog = (
	                new WebDialog.FeedDialogBuilder(MainActivity.this,
	                    Session.getActiveSession(),
	                    params))
	                .setOnCompleteListener(new WebDialog.OnCompleteListener() {
						
						@Override
						public void onComplete(Bundle values, FacebookException error) {
							// TODO Auto-generated method stub
							if(values != null){
							if(values.getString("post_id") != null){
							Toast toast = Toast.makeText(MainActivity.this, "Posted Sucessfully:\n" + values.getString("post_id"), 1000 );
							toast.show();
							} else{
								Toast toast = Toast.makeText(MainActivity.this, "Publish cancelled", 1000);
								toast.show();
							}
							}else{
								Toast toast = Toast.makeText(MainActivity.this, "Publish cancelled", 1000);
								toast.show();
							}
						}
					})
	                .build();
	            feedDialog.show();
	}
	catch(Exception e){
	    e.printStackTrace();
	}
	}
	
	public void facebook(View view){	
		Session session = Session.getActiveSession();
		if(session.isOpened()){
			Feed();
		}
	}
	}
