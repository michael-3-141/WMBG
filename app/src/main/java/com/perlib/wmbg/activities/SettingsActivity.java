package com.perlib.wmbg.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.perlib.wmbg.R;
import com.perlib.wmbg.misc.PrefKeys;


public class SettingsActivity extends ActionBarActivity {

	EditText customMessage;
	Button btnSave;
	ToggleButton tbDeleteConfirm;
	Spinner spSwipeMode;

    SharedPreferences prefs;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_settings);

        //Setup action bar
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

        //View references
	    customMessage = (EditText) findViewById(R.id.customMessage);
	    btnSave = (Button) findViewById(R.id.btnSave);
	    tbDeleteConfirm = (ToggleButton)findViewById(R.id.tbDeleteConfirm);
	    spSwipeMode = (Spinner)findViewById(R.id.spSwipeMode);

        //Load settings and populate fields
	    prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    customMessage.setText(PrefKeys.getEmailMessage(prefs));
	    tbDeleteConfirm.setChecked(PrefKeys.isConfirmDelete(prefs));
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.swipemodes, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spSwipeMode.setAdapter(adapter);
	    spSwipeMode.setSelection(PrefKeys.getSwipeMode(prefs));
	    
	    btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                //Save all settings and open main menu activity
                prefs.edit().putString(PrefKeys.EMIAL_MESSAGE, customMessage.getText().toString())
                            .putBoolean(PrefKeys.CONFIRM_DELETE,tbDeleteConfirm.isChecked())
                            .putInt(PrefKeys.SWIPE_MODE, spSwipeMode.getSelectedItemPosition())
                    .commit();
				Intent main = new Intent(getApplicationContext(), MainMenu.class);
				startActivity(main);
			}
		});
	}
	
	
	
}
