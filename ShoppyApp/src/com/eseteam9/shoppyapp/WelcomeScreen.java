package com.eseteam9.shoppyapp;

import java.io.File;

import com.eseteam9.shoppyapp.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomeScreen extends Activity {

	LocalDatabaseHandler db = new LocalDatabaseHandler(this);
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        //Checks if database of program exists, otherwise continues to DisplayLists
        if (db.existsDatabase()){
            Intent intent = new Intent(this, DisplayListsActivity.class);
    	    startActivity(intent);
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome_screen, menu);
		return true;
	}
	
    private static boolean existsDatabase(Context context, String dbName) {
        File dbFile=context.getDatabasePath(dbName);
        return dbFile.exists();
    }
    
	//Called when clicking on "Save"-Button
	public void createDatabase(View view){
	    EditText editText = (EditText) findViewById(R.id.nickname);
	    String nickname = editText.getText().toString();
	    
		if (nickname.length() == 0)
	    	Toast.makeText(this, "Please enter a name", 1000).show();
		else{
		    //get Phone number if possible
		    TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		    String myNumber = tm.getLine1Number();
		    
		    //Add Entry in DB    
	        db.addUser(new User(0, nickname, myNumber));
	        
	        //Switch to DisplayListActivity
	        Intent intent = new Intent(this, DisplayListsActivity.class);
		    startActivity(intent);
		}
	}

}
