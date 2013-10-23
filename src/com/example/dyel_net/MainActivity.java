package com.example.dyel_net;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.app.Dialog;
import android.content.DialogInterface;
import android.app.*;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.*;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {
	private String jsonResult;
	//private String url = "http://web.engr.illinois.edu/~dyel-net/get_stiff_rick.php";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public void connectToDatabase(View v)
	{	
		connection con = new connection("dyel-net_admin", "teamturtle");
        con.execute("read", "select * from user");
	}
	
	private class connection extends AsyncTask<String, Void, Boolean>
	{
		private String username;
		private String password;
		
		public connection(String un, String pw)
		{
			username = un;
			password = pw;
		}
		
		protected Boolean doInBackground(String... params) 
		{
			        String result = "default";
					
			        if(params[0] == "read")
			        {
		        		result = ReadQuery(params[1]);
			        }
			        else if(params[0] == "write")
			        {
			        	WriteQuery(params[1]);
			        }
			            	
			        Log.w("ASYNC", result);
			        
			        return true; 
		}
		
		public String ReadQuery(String SQL)
		{
			try {
				String host = "http://web.engr.illinois.edu/~dyel-net/readquery.php";
				List<BasicNameValuePair> nvps = null;
	        	HttpParams httpParameters = new BasicHttpParams();
	        
	        	int timeoutConnection = 20000;
	        	HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
	        	int timeoutSocket = 20000;
	        	HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

	        	HttpClient httpclient = new DefaultHttpClient(httpParameters);
	        	HttpPost httpPost = new HttpPost(host);
	        	HttpResponse response;
	        	nvps = new ArrayList<BasicNameValuePair>();  
	        	nvps.add(new BasicNameValuePair("user", username ));
	        	nvps.add(new BasicNameValuePair("pw", password ));
	        	nvps.add(new BasicNameValuePair("sql", SQL));
	        	httpPost.setEntity(new UrlEncodedFormEntity(nvps));
	        	response = httpclient.execute(httpPost);
	        	String htmlresponse;
				htmlresponse = EntityUtils.toString(response.getEntity());
				return htmlresponse;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        return "ERROR";
	        
		}
		
		public void WriteQuery(String SQL)
		{		        
			try {
				String host = "http://web.engr.illinois.edu/~dyel-net/writequery.php";
				List<BasicNameValuePair> nvps = null;
				HttpParams httpParameters = new BasicHttpParams();
				int timeoutConnection = 20000;
				HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
				int timeoutSocket = 20000;
				HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

				HttpClient httpclient = new DefaultHttpClient(httpParameters);
				HttpPost httpPost = new HttpPost(host);
				nvps = new ArrayList<BasicNameValuePair>();  
				nvps.add(new BasicNameValuePair("user", username ));
				nvps.add(new BasicNameValuePair("pw", password ));
				nvps.add(new BasicNameValuePair("sql", SQL));
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				httpclient.execute(httpPost);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		}
		
	}

}
