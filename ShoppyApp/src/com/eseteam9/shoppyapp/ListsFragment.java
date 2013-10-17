package com.eseteam9.shoppyapp;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ListsFragment extends ListFragment{
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	private ShoppingListAdapter adapter;
	
	public ListsFragment() {
	}
	
	//Displays Lists
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ListView lv = getListView();
		registerForContextMenu(lv);
		LocalDatabaseHandler db = new LocalDatabaseHandler(getActivity());
		this.adapter = new ShoppingListAdapter(getActivity(), R.id.listoverview,  db.getAllShoppingLists());
		setListAdapter(adapter);
	}
	
	//Creates ContextMenu (from arrays.xml) when pressing&holding an item
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	  if (v.getId()==getListView().getId()) {
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
	    int chosen = (Integer)menuInfo.targetView.findViewById(R.id.listname).getTag();
	    LocalDatabaseHandler db = new LocalDatabaseHandler(getActivity());
	    switch (item.getItemId()) {
		  case 0:
		    db.deleteShoppingList(chosen);
		    updateView();
		    return true;
		    
		  default:
		    return super.onContextItemSelected(item);
		}
	}
	
	public void updateView(){
		LocalDatabaseHandler db = new LocalDatabaseHandler(getActivity());
		adapter.clear();
		adapter.addAll(db.getAllShoppingLists());
		adapter.notifyDataSetChanged();
        db.close();
	}
	
}