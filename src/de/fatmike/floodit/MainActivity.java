package de.fatmike.floodit;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	public final static int GRID_SIZE = 17;
	public final static int[] COLORS = new int[] { Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.MAGENTA, 0xff6f006f };

	private Playfield playfield;
	private final Button[] colorButtons = new Button[ COLORS.length ];
	private int turnCount = 0;

	private Chronometer chronometer;

	@Override
	public void onCreate( final Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		setContentView( R.layout.main_layout );

		playfield = (Playfield)findViewById( R.id.playfield );

		chronometer = (Chronometer)findViewById( R.id.chronometer );

		// Create the color buttons
		for( int i = 0; i < COLORS.length; i++ ) {

			final int color = COLORS[ i ];

			final Button colorButton = new Button( this );

			colorButton.setBackgroundColor( color );
			colorButton.setLayoutParams( new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f ) );
			colorButton.setOnClickListener( this );

			final LinearLayout buttonBox = (LinearLayout)findViewById( R.id.buttonBox );
			buttonBox.addView( colorButton );

			colorButtons[ i ] = colorButton;
		}
	}

	/**
	 * Retrieve a random color out of the available colors
	 */
	public static int getRandomColor() {
		return COLORS[ new Random().nextInt( COLORS.length ) ];
	}

	private void process( final int newColor ) {

		final int referenceColor = playfield.getReferenceColor();

		if( referenceColor != newColor ) {

			// Start timer on first turn
			if( turnCount == 0 ) {
				chronometer.setBase( SystemClock.elapsedRealtime() );
				chronometer.start();
			}

			playfield.fill( 0, 0, referenceColor, newColor );

			turnCount++;
		}

		// Check if full grid is filled...
		final boolean completed = playfield.isFilled();

		// ...and display a message if game was completed
		if( completed ) {

			chronometer.stop();

			Toast.makeText( this, "Congratulations. You needed " + String.format( "%.02f", ( SystemClock.elapsedRealtime() - chronometer.getBase() ) / 1000f ) + " seconds for " + turnCount + " turns.", Toast.LENGTH_LONG ).show();

			// restartGame();
		}
	}

	private void restartGame() {

		playfield.init();
		playfield.invalidate();

		turnCount = 0;

		chronometer.stop();
		chronometer.setBase( SystemClock.elapsedRealtime() );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {

		getMenuInflater().inflate( R.menu.main_menu, menu );

		return true;
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {

		if( item.getItemId() == R.id.menu_new_game ) {

			restartGame();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick( final View view ) {

		for( int i = 0; i < colorButtons.length; i++ ) {

			// Determine the clicked color and start to process with it
			if( view == colorButtons[ i ] ) {
				process( COLORS[ i ] );
				break;
			}
		}

		playfield.invalidate();
	}
}
