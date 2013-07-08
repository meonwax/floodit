package de.meonwax.floodit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class FinishedDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog( final Bundle savedInstanceState ) {

		final MainActivity mainActivity = (MainActivity)getActivity();

		final AlertDialog.Builder builder = new AlertDialog.Builder( mainActivity );
		//		builder.setMessage( "Congratulations. You needed " + String.format( "%.02f", mainActivity.getElapsedTime() ) + " seconds for " + mainActivity.getTurnCount() + " turns." );
		builder.setMessage( String.format( getString( R.string.win ), mainActivity.getElapsedTime(), mainActivity.getTurnCount() ) );

		return builder.create();
	}
}
