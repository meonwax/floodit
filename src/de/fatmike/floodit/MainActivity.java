package de.fatmike.floodit;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	/*
	 * Game config constants
	 */
	public final static int GRID_SIZE = 17;

	public final static int[] COLORS = new int[] { Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.MAGENTA, 0xff6f006f };

	@Override
	public void onCreate( final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.main );

		final Button newGameButton = (Button)findViewById( R.id.new_game );
		newGameButton.setOnClickListener( this );
	}

	/**
	 * Retrieve a random color out of the available colors
	 */
	public static int getRandomColor() {
		return COLORS[ new Random().nextInt( COLORS.length ) ];
	}

	@Override
	public void onClick( final View v ) {

		final Playfield playfield = (Playfield)findViewById( R.id.playfield );

		if( v.getId() == R.id.new_game ) {
			playfield.init();
		}
//		else {
//
//			for( int i = 0; i < colorButtons.length; i++ ) {
//
//				// Determine the clicked color and start to process with it
//				if( source == colorButtons[ i ] ) {
//					playfield.process( COLORS[ i ] );
//					break;
//				}
//			}
//		}

		playfield.invalidate();
	}
}
