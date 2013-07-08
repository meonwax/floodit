package de.meonwax.floodit;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity {

	@Override
	public void onCreate( final Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );

		addPreferencesFromResource( R.xml.preferences );
	}
}
