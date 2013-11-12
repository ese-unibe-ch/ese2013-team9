package com.eseteam9.shoppyapp;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class DisplayNotificationsFragment extends DisplayFragment{
	private ListAdapter adapter;
	private Notification[] notifications; 
	private ListView lv;

	
	public DisplayNotificationsFragment() {
	}
	
	//Displays Lists
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		User me = new UserHandler(getActivity()).get();
		this.notifications = new OnlineDatabaseHandler(getActivity()).getNotifications(me.email);
        View view =  inflater.inflate(R.layout.fragment_display_notifications,container, false);
        lv = (ListView)view.findViewById(R.id.notifications);
		registerForContextMenu(lv);
		lv.setClickable(true);
	
		if (notifications != null){
			ArrayList<Notification> notif = new ArrayList<Notification>(Arrays.asList(notifications));
			this.adapter = new NotificationAdapter(getActivity(), R.id.notifications, notif);
			lv.setAdapter(adapter);
		}
        
		return view;
		
	}

	//Creates ContextMenu (from arrays.xml) when pressing&holding an item
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	  if (v.getId()==R.id.listoverview) {
	    menu.setHeaderTitle("Options");
	    String[] menuItems = getResources().getStringArray(R.array.context_menu);
	    for (int i = 0; i<menuItems.length; i++) {
	      menu.add(Menu.NONE, i, i, menuItems[i]);
	    }
	  }
	}
	
	//Reads what is selected from ContextMenu
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo(); 
	    Notification notification = notifications[menuInfo.position];
	    
	    switch (item.getItemId()) {
		  case 0:
		    return true;
		  case 1:
			  return true;
		  default:
		    return super.onContextItemSelected(item);
		}
	}
}