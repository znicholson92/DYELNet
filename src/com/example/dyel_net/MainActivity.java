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
			setContentView(R.layout.activity_main);
		}
		else
		{	
			AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("Invalid Login");
            dialog.show();
			un_box.setText("");
			pw_box.setText("");
		}
	}
	
	public void gotToMenu(View v)
	{
		setContentView(R.layout.main_menu);
	}
	
	public void connectToDatabase(View v)
	{
		ListView listView = (ListView) findViewById(R.id.listView1);
		connection con1 = new connection("dyel-net_admin", "teamturtle", this);
		
        con1.readQuery("select * from muscle", listView);
        
	}
	
}
