package de.meonwax.floodit;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener {

	public final static int GRID_SIZE = 17;
	public final static int ATTRACT_MODE_DELAY = 100;

	// Available color palettes
	private static List<int[]> AVAILABLE_COLORS = new LinkedList<int[]>();
	static {
		AVAILABLE_COLORS.add( new int[] { 0xff33b5e5, 0xffaa66cc, 0xff99cc00, 0xffffbb33, 0xffff4444, 0xff6f006f } );
		AVAILABLE_COLORS.add( new int[] { Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.MAGENTA, 0xff6f006f } );
	}

	// Actual color palette in use
	private int[] colors;

	private Playfield playfield;

	private Button[] colorButtons;

	private int turnCount;

	private Chronometer chronometer;

	private SoundPool soundPool;
	private int[] waterSounds;
	private int failSound;

	private boolean inAttractMode = false;

	private final Random rnd = new Random();

	@Override
	public void onCreate( final Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		setContentView( R.layout.main_layout );

		playfield = (Playfield)findViewById( R.id.playfield );
		chronometer = (Chronometer)findViewById( R.id.chronometer );

		restartGame();

		initSounds();
	}

	/**
	 * Retrieve a random color out of the available colors
	 */
	public int getRandomColor() {
		return colors[ new Random().nextInt( colors.length ) ];
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

	private void initColors() {

		// Determine which color palette to use
		colors = AVAILABLE_COLORS.get( Integer.valueOf( PreferenceManager.getDefaultSharedPreferences( this ).getString( getString( R.string.pref_palette ), "0" ) ) );

		// (Re)set everything
		colorButtons = new Button[ colors.length ];

		final LinearLayout buttonBox = (LinearLayout)findViewById( R.id.buttonBox );
		buttonBox.removeAllViews();

		// Create the color buttons
		for( int i = 0; i < colors.length; i++ ) {

			final Button colorButton = new ColorButton( this, colors[ i ] );
			colorButton.setOnClickListener( this );

			buttonBox.addView( colorButton );

			colorButtons[ i ] = colorButton;
		}
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

				activateAttractMode();

				new FinishedDialog().show( getSupportFragmentManager(), FinishedDialog.class.getName() );
			}
		}
		else if( sound ) {
			soundPool.play( failSound, 1, 1, 1, 0, 1f );
		}
	}

	private void restartGame() {

		stopAttractMode();

		initColors();

		playfield.init();
		playfield.invalidate();

		resetTurn();

		chronometer.stop();
		chronometer.setBase( SystemClock.elapsedRealtime() );
	}

	private void activateAttractMode() {

		inAttractMode = true;

		final Handler handler = new Handler();
		handler.postDelayed( new Runnable() {

			public void run() {

				if( inAttractMode ) {

					playfield.init();
					playfield.invalidate();

					handler.postDelayed( this, ATTRACT_MODE_DELAY );
				}
			}
		}, ATTRACT_MODE_DELAY );
	}

	private void stopAttractMode() {
		inAttractMode = false;
	}

	private void updateTurn() {
		turnCount++;
		( (TextView)findViewById( R.id.turn ) ).setText( String.valueOf( turnCount ) );
	}

	private void resetTurn() {
		turnCount = 0;
		( (TextView)findViewById( R.id.turn ) ).setText( String.valueOf( turnCount ) );
	}

	protected int getTurnCount() {
		return turnCount;
	}

	protected float getElapsedTime() {
		return ( SystemClock.elapsedRealtime() - chronometer.getBase() ) / 1000f;
	}



	/****************************
	 * Listener implementations
	 ***************************/

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

		return super.onOptionsItemSelected( item );
	}

	@Override
	public void onClick( final View view ) {

		if( !inAttractMode ) {

			for( int i = 0; i < colorButtons.length; i++ ) {

				// Determine the clicked color and start to process with it
				if( view == colorButtons[ i ] ) {
					process( colors[ i ] );
					break;
				}
			}
		}

		playfield.invalidate();
	}
}
