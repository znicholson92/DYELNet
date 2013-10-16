package com.example.dyel_net;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {
	private String jsonResult;
	private String url = "http://web.engr.illinois.edu/~dyel-net/get_stiff_rick.php";
	private ListView listView1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView1 = (ListView) findViewById(R.id.listView1);
		accessWebService();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public void connectToDatabase()
	{
		AlertDialog dialog = new AlertDialog.Builder(this).create();
	    dialog.setTitle("Title");
	    dialog.show();
	}
	
	private class JsonReadTask extends AsyncTask<String, Void, String> {
		  @Override
		  protected String doInBackground(String... params) {
		   HttpClient httpclient = new DefaultHttpClient();
		   HttpPost httppost = new HttpPost(params[0]);
		   try {
		    HttpResponse response = httpclient.execute(httppost);
		    jsonResult = inputStreamToString(
		      response.getEntity().getContent()).toString();
		   }
		 
		   catch (ClientProtocolException e) {
		    e.printStackTrace();
		   } catch (IOException e) {
		    e.printStackTrace();
		   }
		   return null;
		  }
		 
		  private StringBuilder inputStreamToString(InputStream is) {
		   String rLine = "";
		   StringBuilder answer = new StringBuilder();
		   BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		 
		   try {
		    while ((rLine = rd.readLine()) != null) {
		     answer.append(rLine);
		    }
		   }
		 
		   catch (IOException e) {
		    // e.printStackTrace();
		    Toast.makeText(getApplicationContext(),
		      "Error..." + e.toString(), Toast.LENGTH_LONG).show();
		   }
		   return answer;
		  }
		 
		  @Override
		  protected void onPostExecute(String result) {
		   ListDrawer();
		  }
		 }// end async task
		 
		 public void accessWebService() {
		  JsonReadTask task = new JsonReadTask();
		  // passes values for the urls string array
		  task.execute(new String[] { url });
		 }
		 
		 // build hash set for list view
		 public void ListDrawer() {
		  List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		 //{"data":[{"username":"testuser","firstname":"Stiff","lastname":"Rick","dateofbirth":"1990-01-01","sex":"m","location":null}]} 
		  try {
		   JSONObject jsonResponse = new JSONObject(jsonResult);
		   JSONArray jsonMainNode = jsonResponse.optJSONArray("data");
		 
		   for (int i = 0; i < jsonMainNode.length(); i++) {
		    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
		    String username = jsonChildNode.optString("username");
		    String firstname = jsonChildNode.optString("firstname");
		    String lastname = jsonChildNode.optString("lastname");
		    String dob = jsonChildNode.optString("dateofbirth");
		    String sex = jsonChildNode.optString("sex");
		    String location = jsonChildNode.optString("location");
		    
		    resultList.add(createResult(username, "Username"));
		    resultList.add(createResult(firstname, "First Name"));
		    resultList.add(createResult(lastname, "Last Name"));
		    resultList.add(createResult(dob, "Date of Birth"));
		    resultList.add(createResult(sex, "sex"));
		    resultList.add(createResult(location, "location"));
		   }
		  } catch (JSONException e) {
		   Toast.makeText(getApplicationContext(), "Error" + e.toString(),
		     Toast.LENGTH_SHORT).show();
		  }
		 
		  SimpleAdapter simpleAdapter = new SimpleAdapter(this, resultList,
		    android.R.layout.simple_list_item_1,
		    new String[] { "results" }, new int[] { android.R.id.text1 });
		  listView1.setAdapter(simpleAdapter);
		  
		 }
		 
		 private HashMap<String, String> createResult(String name, String data) {
		  HashMap<String, String> resultMap = new HashMap<String, String>();
		  resultMap.put(name, data);
		  return resultMap;
		 }
	
	

}
