package com.example.dyel_net;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.app.*;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public connection con;
	
	public void login(View v)
	{
		EditText un_box = (EditText) findViewById(R.id.usernameText);
		EditText pw_box = (EditText) findViewById(R.id.passwordText);

		connection con = new connection(un_box.getText().toString(), pw_box.getText().toString(), this);

		while(con.working)
		{
			ProgressDialog.show(this, "Loading", "Logging in...");
		}
		
		if(con.loggedin)
		{
			setContentView(R.layout.main_menu);
			TextView username_bar = (TextView) findViewById(R.id.username);
			username_bar.setText(un_box.getText().toString());
			
		}
		else
		{	
			AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("Invalid Login");
            dialog.show();
		}
		un_box.setText("");
		pw_box.setText("");
	}
	
	public void logout(View v)
	{
		con.logout();
		TextView username_bar = (TextView) findViewById(R.id.usernameText);
		username_bar.setText("");
		setContentView(R.layout.login);
	}
	
	public void gotoMenu(View v)
	{
		setContentView(R.layout.main_menu);
	}
	
	public void gotoTestApp(View v)
	{
		Log.w("1", "got here");
		setContentView(R.layout.activity_main);
	}
	
	public void connectToDatabase(View v)
	{
		ListView listView = (ListView) findViewById(R.id.listView1);
		connection con1 = new connection("dyel-net_admin", "teamturtle", this);
		
        con1.readQuery("select * from muscle", listView);
        
	}
	
	//Suna Added for create user
	public void gotoRegister(View v)
	{
		Log.w("1", "go to createuser.xml page");
		setContentView(R.layout.createuser);
	}
	public void CreateUser(View view){
		String username;
		String password;
		String firstName;
		String lastName;
		String DOB;
		String sex;
		//String location;
		String Query;
		
		EditText userNameET = (EditText) findViewById(R.id.edit_username);
		EditText passwordET = (EditText) findViewById(R.id.edit_password);		
		EditText firstNameET = (EditText) findViewById(R.id.edit_firstName);
		EditText lastNameET = (EditText) findViewById(R.id.edit_lastName);
		EditText dateOfBirthET = (EditText) findViewById(R.id.edit_dateOfBirth);
		EditText sexET = (EditText) findViewById(R.id.edit_sex);
		//EditText locationET = (EditText) findViewById(R.id.edit_location);

		username = userNameET.getText().toString();
		password = passwordET.getText().toString();
		firstName = firstNameET.getText().toString();
		lastName = lastNameET.getText().toString();
		DOB = dateOfBirthET.getText().toString();
		sex = sexET.getText().toString();
		//location = locationET.getText().toString();
		
		Query = "INSERT INTO  `dyel-net_main`.`user` "
				+"(`username` , `password`, `firstname` , `lastname` , `dateofbirth` , `sex`)"
				+"VALUES ( "
				+"'"+username+"', "
				+"'"+password+"', "
				+"'"+firstName+"', "
				+"'"+lastName+"', "
				+"'"+DOB+"', "
				+"'"+sex+"');";
		connection con = new connection("dyel-net_admin", "teamturtle", this);
		con.writeQuery(Query);
		////writeQuery -> boolean
		Log.w("1", "user info created");
		setContentView(R.layout.login);
	 }
	
}

