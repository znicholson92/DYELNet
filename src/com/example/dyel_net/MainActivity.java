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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.apache.http.params.*;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.*;
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
		
		String result = con.ReadQuery("select * from user");
		Log.w("DEBUGGING PRINT", "point 3");
		AlertDialog dialog = new AlertDialog.Builder(this).create();
	    dialog.setTitle(result);
	    dialog.show();
	}
	
	private class connection
	{
		private String username;
		private String password;
		
		public connection(String un, String pw)
		{
			username = un;
			password = pw;
		}
		
		public String ReadQuery(String SQL)
		{
			//String url = "http://web.engr.illinois.edu/~dyel-net/readquery.php?user=" + username + "&pw=" + password + "&sql=" + SQL;
		    String host = "http://web.engr.illinois.edu/~dyel-net/readquery.php?";
			String params = "user=" + username + "&pw=" + password + "&sql=" + SQL;
			
	        DefaultHttpClient httpclient = new DefaultHttpClient();
	        
	        try
	        {
            Log.w("DEBUGGING PRINT", "point 1");
            HttpPost post = new HttpPost(host+params);
            HttpGet get = new HttpGet(host+params);
            Log.w("DEBUGGING PRINT", "point 2");
            HttpResponse response = httpclient.execute(get);
            Log.w("DEBUGGING PRINT", "point 3");
            HttpEntity entity = response.getEntity();
            Log.w("DEBUGGING PRINT", "point 4");
            String htmlResponse = EntityUtils.toString(entity);
            Log.w("DEBUGGING PRINT", "point 5");
    	    return htmlResponse;
    	    
	        } catch (ClientProtocolException e) {
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
			 	String params = "user=" + username + "&pw=" + password + "&sql=" + SQL;
				
				
		        DefaultHttpClient httpclient = new DefaultHttpClient();
		        
		        try
		        {
	            HttpHost targetHost = new HttpHost("http://web.engr.illinois.edu/~dyel-net/writequery.php?");
	            HttpGet targetGet = new HttpGet(params);
	            httpclient.execute(targetHost, targetGet);
	            
		        } catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	
	/*private class DYEL_NET extends AsyncTask<String, Void, String>
	{
		//http://web.engr.illinois.edu/~dyel-net/readquery.php?user=dyel-net_admin&pw=teamturtle&sql=select%20*%20from%20user
		@Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	    }
		
		 @Override
		    protected String doInBackground(String... args) {
		        
		        String url = "http://web.engr.illinois.edu/~dyel-net/readquery.php?user=dyel-net_admin&pw=teamturtle&sql=select * from user";
		    
	            
		        DefaultHttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost(url);
		        
		        try
		        {
	            HttpHost targetHost = new HttpHost("http://web.engr.illinois.edu/~dyel-net/");
	            HttpGet targetGet = new HttpGet("readquery.php?user=dyel-net_admin&pw=teamturtle&sql=select * from user");
	            HttpResponse response = httpclient.execute(targetHost, targetGet);
	            HttpEntity entity = response.getEntity();
	            String htmlResponse = EntityUtils.toString(entity);
	          
	    	    
		        } catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   
		        
	            

		        /*try {
		            post.setEntity(new StringEntity("client_id=" + client_id + "&"
		                    + "client_secret=" + clientSecretKey, HTTP.UTF_8));

		            HttpResponse response = httpClient.execute(post);
		            int i = response.getStatusLine().getStatusCode();
		            System.out.println("HTTP Post status AllPerk Redeemption API: "
		                    + i);

		            BufferedReader in = new BufferedReader(new InputStreamReader(
		                    response.getEntity().getContent()));

		            // SB to make a string out of the inputstream
		            StringBuffer sb = new StringBuffer("");
		            String line = "";
		            String NL = System.getProperty("line.separator");
		            while ((line = in.readLine()) != null) {
		                sb.append(line + NL);
		            }
		            in.close();

		            // the json string is stored here
		            String result = sb.toString();
		            System.out.println("Result Body: " + result);
		            return result;

		        } catch (Exception e) {
		            // TODO: handle exception
		        }
		        return null;
		    }
		
		
	}*/
	
	/*private class JsonReadTask extends AsyncTask<String, Void, String> {
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
		     e.printStackTrace();
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
		 /*
		 // build hash set for list view
		 public void ListDrawer() {
		  List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		  
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
		 }*/
		 
		 
		 /*
		 class LoadAllIcons extends AsyncTask<String, String, String> {

		
			    @Override
			    protected void onPreExecute() {
			        super.onPreExecute();
			        pDialog = new ProgressDialog(AllIconsActivity.this);
			        pDialog.setMessage("Loading icons. Please wait...");
			        pDialog.setIndeterminate(false);
			        pDialog.setCancelable(false);
			        pDialog.show();
			    }

			    
			    // getting All products from url
			     
			    protected String doInBackground(String... args) {
			        // Building Parameters
			        List<NameValuePair> params = new ArrayList<NameValuePair>();
			        // getting JSON string from URL
			        JSONObject json = jParser.makeHttpRequest(url_all_icons, "GET", params);

			        // Check your log cat for JSON reponse
			        Log.d("All Icons: ", json.toString());

			        try {
			            // Checking for SUCCESS TAG
			            int success = json.getInt(TAG_SUCCESS);

			            if (success == 1) {
			                // products found
			                // Getting Array of Products
			                TB_MainContent = json.getJSONArray(TAG_TB_MainContent);

			                // looping through All Products
			                for (int i = 0; i < TB_MainContent.length(); i++) {
			                    JSONObject c = TB_MainContent.getJSONObject(i);

			                    // Storing each json item in variable
			                    String id = c.getString(TAG_ID);
			                    String iconname = c.getString(TAG_ICONNAME);

			                    // creating new HashMap
			                    HashMap<String, String> map = new HashMap<String, String>();

			                    // adding each child node to HashMap key => value
			                    map.put(TAG_ID, id);
			                    map.put(TAG_ICONNAME, iconname);

			                    // adding HashList to ArrayList
			                    iconsList.add(map);
			                }
			            } else {
			                // no products found
			                // Launch Add New product Activity
			                Intent i = new Intent(getApplicationContext(),
			                        NewIconActivity.class);
			                // Closing all previous activities
			                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			                startActivity(i);
			            }
			        } catch (JSONException e) {
			            e.printStackTrace();
			        }

			        return null;
			    }

			    
			    //After completing background task Dismiss the progress dialog
			    
			    protected void onPostExecute(String file_url) {
			        // dismiss the dialog after getting all products
			        pDialog.dismiss();
			        // updating UI from Background Thread
			        runOnUiThread(new Runnable() {
			            public void run() {
			                
			                 //Updating parsed JSON data into ListView
			                 
			                 adapter = new SimpleAdapter(
			                        AllIconsActivity.this, iconsList,
			                        R.layout.screen_list, new String[] { TAG_ID,
			                                TAG_ICONNAME},
			                        new int[] { R.id.id, R.id.iconname });
			                // updating listview
			                gridView.setAdapter(adapter);
			            }
			        });

			    }
		 }*/
		 
		 /*
		 private class getRedeemData extends AsyncTask<String, Void, String> {

			    @Override
			    protected void onPreExecute() {
			        super.onPreExecute();
			        pdia = new ProgressDialog(AllPerksActivity.this);
			        pdia.setMessage("Loading products. Please wait...");
			        pdia.show();
			    }

			    @Override
			    protected String doInBackground(String... args) {
			        HttpParams params = new BasicHttpParams();
			        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
			                HttpVersion.HTTP_1_1);
			        HttpClient httpClient = new DefaultHttpClient(params);
			        HttpPost post = new HttpPost(
			                "MY API HERE..!!");

			        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

			        try {
			            post.setEntity(new StringEntity("client_id=" + client_id + "&"
			                    + "client_secret=" + clientSecretKey, HTTP.UTF_8));

			            HttpResponse response = httpClient.execute(post);
			            int i = response.getStatusLine().getStatusCode();
			            System.out.println("HTTP Post status AllPerk Redeemption API: "
			                    + i);

			            BufferedReader in = new BufferedReader(new InputStreamReader(
			                    response.getEntity().getContent()));

			            // SB to make a string out of the inputstream
			            StringBuffer sb = new StringBuffer("");
			            String line = "";
			            String NL = System.getProperty("line.separator");
			            while ((line = in.readLine()) != null) {
			                sb.append(line + NL);
			            }
			            in.close();

			            // the json string is stored here
			            String result = sb.toString();
			            System.out.println("Result Body: " + result);
			            return result;

			        } catch (Exception e) {
			            // TODO: handle exception
			        }
			        return null;
			    }

			    @Override
			    protected void onPostExecute(String result) {
			        JSONObject jObject;
			        try {
			            jObject = new JSONObject(result);

			            JSONArray jSearchData = jObject.getJSONArray("rewards");

			            for (int i = 0; i < jSearchData.length(); i++) {

			                JSONObject objJson = jSearchData.getJSONObject(i);

			                String rewardID = objJson.getString("rewardID");
			                String rewardType = objJson.getString("rewardType");
			                String rewardTitle = objJson.getString("rewardTitle");

			                System.out.println("Reward ID: " + rewardID);
			                System.out.println("Reward Type: " + rewardType);
			                System.out.println("Reward Tittle: " + rewardTitle);
			            }
			        } catch (Exception e) {
			            // TODO: handle exception
			        }
			        pdia.dismiss();
			    }
			}*/

}
