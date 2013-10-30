package com.example.dyel_net;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
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
import java.util.ArrayList;
import java.util.Date;
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

public class UserActivity extends FragmentActivity{
	String username;	
	String firstName;
	String lastName;
	Date DOB;
	String location;
	
	/*public void CreateUser(String username, String firstName, String lastName, int year, int month, int day, String location)
	{
		
	}
	
*/	//public void deleteUser
	//public void editUser
	
	 public void CreateUser(View view){
		 /*TO DO*/
	 }
	 
	 public void showDatePickerDialog(View v) {
		    DialogFragment newFragment = new DatePickerFragment();
		    newFragment.show(getSupportFragmentManager(), "datePicker");
	 }
	
}
