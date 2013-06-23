package de.fatmike.floodit;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	/*
	 * Game config constants
	 */
//	public final static int SQUARE_SIZE = 30;

	public final static int GRID_SIZE = 14;

	public final static int[] COLORS = new int[] { Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.MAGENTA, 0xff6f006f };

	@Override
	public void onCreate( final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.main );
	}

	public static float getSquareSize( View view ) {
		return view.getResources().getDisplayMetrics().widthPixels / Float.valueOf( GRID_SIZE );
	}

	/**
	 * Retrieve a random color out of the available colors
	 */
	public static int getRandomColor() {
		return COLORS[ new Random().nextInt( COLORS.length ) ];
	}

//	/**
//	 * The button click handler
//	 */
//	@Override
//	public void actionPerformed( ActionEvent e ) {
//
//		Object source = e.getSource();
//
//		if( source == newGameButton ) {
//			playfield.init();
//		}
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
//
//		// Just paint the grid again after setting new square colors
//		playfield.repaint();
//	}
}
