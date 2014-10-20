package com.example.stock_display;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Session;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

import com.facebook.*;
import com.facebook.widget.WebDialog;

public class MainActivity extends ActionBarActivity {
    AutoCompleteTextView symbol;
    Button newsbtn, facebookbtn;
    public String URL = "cs-server.usc.edu:17721/examples/servlet/getstock?symbol=";
    public String URL1 = "http://chart.finance.yahoo.com/t?s=";
    public String URL2 = "&lang=enUS&amp;width=300&height=180";
    static final String EXTRA_MESSAGE = "NEWS_ITEMS";
    JSONArray ptr;
    String sym,comname,path,total_change,price;
	
    public AlertDialog CreateDialogBuilder(){
    	Builder builder=new AlertDialog.Builder(MainActivity.this);
		builder.setMessage("The input company symbol is empty!"); 
		builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		}); 
		AlertDialog dia = builder.create();
		return dia;
    }
    
	public void SearchClick(View view){
		String com = symbol.getText().toString();
		if(com.equals("")){
			AlertDialog dia = CreateDialogBuilder();
			dia.show();
		}else{
			FetchInfo task = new FetchInfo();
			task.execute(com);			
		}
	}
	
	public void NewsClick(View view){
		Intent intent = new Intent(MainActivity.this,NewsActivity.class);
		String news = ptr.toString();
		intent.putExtra("com.example.stock_display.MainActivity", news);
		startActivity(intent);
//		System.out.print(ptr.length());
	}
	
	public void FBClick(View view){
		try{
			Activity org = this;
			ComInfo info = new ComInfo();
	        Bundle fuck = new Bundle();
	       
	        fuck.putString("link",  "http://finance.yahoo.com/q;_ylt=AjYxKpIh6L.aoN385saI6wup_8MF;_yl"
	        				+ "c=X1MDMjE0MjQ3ODk0OARfcgMyBGZyA3VoM19maW5hbmNlX3dlYl9ncwRmcjIDc2EtZ3AEZ3B"
	        				+ "yaWQDBG5fZ3BzAzEwBG9yaWdpbgNmaW5hbmNlLnlhaG9vLmNvbQRwb3MDMQRwcXN0cgMEcXVl"
	        				+ "cnkDQUEsBHNhYwMxBHNhbwMx?p=http%3A%2F%2Ffinance.yahoo.com%2Fq%3Fs%3DAA%26"
	        				+ "ql%3D0&type=2button&fr=uh3_finance_web_gs&uhb=uhb2&s="+comname);
	        
	        fuck.putString("name", sym);//title 
	        fuck.putString("caption", "Stock Information of "
	        				+ sym
	        				+ "(" + comname + ")");//caption 
	        fuck.putString("description", "Last Trade Price:"
	        				+ price + "\n"
	        				+ "Change:" + total_change);
	        fuck.putString("picture", path);
	        fuck.putString("app_id", "278891065617562");

	        WebDialog feed = (
	                new WebDialog.FeedDialogBuilder(MainActivity.this,
	                    Session.getActiveSession(),
	                    fuck))
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

	            feed.show();
	}
	catch(Exception e){
	    e.printStackTrace();
	}
	}
	private void GetElements(){
		 Activity ac = MainActivity.this;
		 symbol = (AutoCompleteTextView) ac.findViewById(R.id.com);	
	}
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		GetElements();
		
		 Session.openActiveSession(this, true, new Session.StatusCallback() {

		      // callback when session changes state
		      @Override
		      public void call(Session session, SessionState state, Exception exception) {
		        if (session.isOpened()) {

		          // make request to the /me API
//		          Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
//
//		            // callback after Graph API response with user object
//		            @Override
//		            public void onCompleted(GraphUser user, Response response) {
//		              if (user != null) {
//		                TextView welcome = (TextView) findViewById(R.id.welcome);
//		                welcome.setText("Hello " + user.getName() + "!");
//		              }
//		            }
//		          });
		        }
		      }
		    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
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
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		  super.onActivityResult(requestCode, resultCode, data);
		  Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
		}

	
	class FetchInfo extends AsyncTask<String,Integer,String>{
		
		private Drawable stock;	
		public String URL = "http://cs-server.usc.edu:17721/examples/servlet/getstock?symbol=";
	    public String URL1 = "http://chart.finance.yahoo.com/t?s=";
	    public String URL2 = "&lang=enUS&amp;width=300&height=180";

		private Drawable GetImage(String url) throws IOException{
			URL image_path = null;
			InputStream stream = null;
			try{
			image_path = new URL(url);
			stream = image_path.openStream();
			Drawable d = Drawable.createFromStream(stream, "stock_pic");
			return d;
			}catch (IOException e){
				e.printStackTrace();
				return null;
			}finally{
				if(stream != null)
					stream.close();
			}
		}
		
	protected String doInBackground(String... params) {
		
		String path = URL + params[0];
		StringBuilder builder = new StringBuilder();
		String path_pic = URL1 + params[0] + URL2;
		try {
			InputStream input = new URL(path).openStream();
			BufferedReader json_reader = new BufferedReader(new InputStreamReader(input));
			String info = null;
			while((info=json_reader.readLine())!=null){
				builder.append(info);
			}
			input.close();
			json_reader.close();
			stock = GetImage(path_pic);
			return builder.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Activity org = MainActivity.this;
		TextView view = null;
		if(result != null){
		try {
			JSONObject json_obj = new JSONObject(result);
			JSONObject root = json_obj.getJSONObject("result");
			ComInfo info = new ComInfo();
			JSONObject detail = root.getJSONObject(info.quoteTag);
			JSONObject news = root.getJSONObject(info.NewsTag);
//			PanelView panel_view = new PanelView();
//			panel_view.ask = (TextView) org.findViewById(R.id.ask);
//			panel_view.
			view = (TextView) org.findViewById(R.id.textView2);
			
			view.setText(root.getString(info.CompanyNameTag)
						+ "(" + root.getString(info.CompanySymbolTag) + ")");
			
			view = (TextView) org.findViewById(R.id.textView3);
			view.setText(detail.getString(info.LastTradePriceOnlyTag));
			
			String change = detail.getString(info.ChangeTag) + "(" +detail.getString(info.ChangeinPercentTag) + ")";
			view = (TextView) org.findViewById(R.id.textView4);
			ImageView gallery = (ImageView) org.findViewById(R.id.imageView1);
			
			if(detail.getString(info.ChangeTypeTag).equals("-")){
				view.setText(change);
				view.setTextColor(Color.RED);
				gallery.setImageResource(R.drawable.down_r);
			}else{
				view.setText(change);
				view.setTextColor(Color.GREEN);
				gallery.setImageResource(R.drawable.up_g);
			}
			
			view = (TextView) org.findViewById(R.id.prev);
			view.setText(detail.getString(info.PreviousCloseTag));
			
			view = (TextView) org.findViewById(R.id.bid);
			view.setText(detail.getString(info.BidTag));
			
			view = (TextView) org.findViewById(R.id.ask);
			view.setText(detail.getString(info.AskTag));
			
			view = (TextView) org.findViewById(R.id.yt);
			view.setText(detail.getString(info.OneyrTargetPriceTag));
			
			view = (TextView) org.findViewById(R.id.dr);
			view.setText(detail.getString(info.DaysLowTag) + "-" + detail.getString(info.DaysHighTag));
			
			view = (TextView) org.findViewById(R.id.wr);
			view.setText(detail.getString(info.YearLowTag) + "-" + detail.getString(info.YearHighTag));
			
			view = (TextView) org.findViewById(R.id.volume);
			view.setText(detail.getString(info.VolumeTag));
			
			view = (TextView) org.findViewById(R.id.av);
			view.setText(detail.getString(info.AverageDailyVolumeTag));
			
			view = (TextView) org.findViewById(R.id.mc);
			view.setText(detail.getString(info.MarketCapitalizationTag));
			
			gallery = (ImageView) org.findViewById(R.id.imageView2);
			gallery.setImageDrawable(stock);
			
			JSONObject news_list = root.getJSONObject(info.NewsTag);
			ptr = news_list.getJSONArray(info.ItemTag);
			
			//System.out.println(ptr.length());
			
			LinearLayout layout = (LinearLayout) MainActivity.this.findViewById(R.id.total);
			layout.setVisibility(View.VISIBLE);
			
			sym = root.getString(info.CompanyNameTag);
			comname = root.getString(info.CompanySymbolTag);
			price = detail.getString(info.LastTradePriceOnlyTag);
			total_change = detail.getString(info.ChangeTag) + "(" + detail.getString(info.ChangeinPercentTag) +")";
			path = root.getString(info.StockChartImageURLTag);
			
			
			//System.out.println(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}else{
			LinearLayout layout = (LinearLayout) MainActivity.this.findViewById(R.id.total);
			layout.setVisibility(View.INVISIBLE);
		}
	}
	}
	

}


