package de.meonwax.floodit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;

public class FinishedDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog( final Bundle savedInstanceState ) {

		final MainActivity mainActivity = (MainActivity)getActivity();

		final AlertDialog.Builder builder = new AlertDialog.Builder( mainActivity );

		builder.setTitle( getString( R.string.congratulations ) );
		builder.setMessage( Html.fromHtml( String.format( getString( R.string.win ), mainActivity.getElapsedTime(), mainActivity.getTurnCount() ) ) );
		builder.setPositiveButton( getString( R.string.ok ), null );

		final Dialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside( false );

		return dialog;
	}
}
