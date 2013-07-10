package de.meonwax.floodit;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	@Override
	public void onCreate( final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		addPreferencesFromResource( R.xml.preferences );
	}

	@Override
	protected void onResume() {
		super.onResume();
		PreferenceManager.getDefaultSharedPreferences( this ).registerOnSharedPreferenceChangeListener( this );
	}

	@Override
	protected void onPause() {
		super.onPause();
		PreferenceManager.getDefaultSharedPreferences( this ).unregisterOnSharedPreferenceChangeListener( this );
	}

	@Override
	public void onSharedPreferenceChanged( final SharedPreferences sharedPreferences, final String key ) {

		if( key.equals( getString( R.string.pref_palette ) ) ) {
			Toast.makeText( this, getString( R.string.palette_changed ), Toast.LENGTH_LONG ).show();
		}
	}
}
