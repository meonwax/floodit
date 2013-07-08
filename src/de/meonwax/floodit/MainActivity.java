package de.meonwax.floodit;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	public final static int GRID_SIZE = 17;
	public final static int[] COLORS = new int[] { Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.MAGENTA, 0xff6f006f };

	private Playfield playfield;
	private final Button[] colorButtons = new Button[ COLORS.length ];
	private int turnCount;

	private Chronometer chronometer;

	private SoundPool soundPool;
	private int[] waterSounds;
	private int failSound;

	private final Random rnd = new Random();

	@Override
	public void onCreate( final Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		setContentView( R.layout.main_layout );

		playfield = (Playfield)findViewById( R.id.playfield );

		chronometer = (Chronometer)findViewById( R.id.chronometer );

		resetTurn();

		initSounds();

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

	private void initSounds() {

		soundPool = new SoundPool( 2, AudioManager.STREAM_MUSIC, 100 );
		waterSounds = new int[ 4 ];

		waterSounds[ 0 ] = soundPool.load( this, R.raw.water0, 1 );
		waterSounds[ 1 ] = soundPool.load( this, R.raw.water1, 2 );
		waterSounds[ 2 ] = soundPool.load( this, R.raw.water2, 3 );
		waterSounds[ 3 ] = soundPool.load( this, R.raw.water3, 4 );

		failSound = soundPool.load( this, R.raw.fail, 4 );
	}

	private void process( final int newColor ) {

		final boolean sound = PreferenceManager.getDefaultSharedPreferences( this ).getBoolean( getString( R.string.pref_sound ), true );

		final int referenceColor = playfield.getReferenceColor();

		if( referenceColor != newColor ) {

			if( sound ) {

				// Play a random water sound
				soundPool.play( waterSounds[ rnd.nextInt( 3 ) ], 1, 1, 1, 0, 1f );
			}

			// Start timer on first turn
			if( turnCount == 0 ) {
				chronometer.setBase( SystemClock.elapsedRealtime() );
				chronometer.start();
			}

			playfield.fill( 0, 0, referenceColor, newColor );

			updateTurn();

			// Check if full grid is filled...
			final boolean completed = playfield.isFilled();

			// ...and display a message if game was completed
			if( completed ) {

				chronometer.stop();

				Toast.makeText( this, "Congratulations. You needed " + String.format( "%.02f", ( SystemClock.elapsedRealtime() - chronometer.getBase() ) / 1000f ) + " seconds for " + turnCount + " turns.", Toast.LENGTH_LONG ).show();

				// restartGame();
			}
		}
		else if( sound ) {
			soundPool.play( failSound, 1, 1, 1, 0, 1f );
		}

	}

	private void restartGame() {

		playfield.init();
		playfield.invalidate();

		resetTurn();

		chronometer.stop();
		chronometer.setBase( SystemClock.elapsedRealtime() );
	}

	private void updateTurn() {
		turnCount++;
		( (TextView)findViewById( R.id.turn ) ).setText( String.valueOf( turnCount ) );;
	}

	private void resetTurn() {
		turnCount = 0;
		( (TextView)findViewById( R.id.turn ) ).setText( String.valueOf( turnCount ) );;
	}

	@Override
	public boolean onCreateOptionsMenu( final Menu menu ) {

		getMenuInflater().inflate( R.menu.main_menu, menu );

		return true;
	}

	@Override
	public boolean onOptionsItemSelected( final MenuItem item ) {

		if( item.getItemId() == R.id.menu_new_game ) {

			restartGame();
			return true;
		}
		else if( item.getItemId() == R.id.menu_settings ) {

			startActivity( new Intent( this, SettingsActivity.class ) );
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
