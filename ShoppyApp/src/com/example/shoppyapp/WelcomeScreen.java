package com.example.shoppyapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class WelcomeScreen extends Activity {
	
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        
        // example for usage of the local database
        LocalDatabaseHandler db = new LocalDatabaseHandler(this);
        db.addUser(new User(42, "Mehldau", "fj3fjh"));
        String nameOfUser = db.getUser(42).name;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome_screen, menu);
		return true;
	}

}
