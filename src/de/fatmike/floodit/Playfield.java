package de.fatmike.floodit;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class Playfield extends View {

	private final Square[][] grid = new Square[ MainActivity.GRID_SIZE ][ MainActivity.GRID_SIZE ];

	int turnCount = 0;

	public Playfield( final Context context ) {
		super( context );
		init();
	}

	public Playfield( final Context context, final AttributeSet attrs ) {
		super( context, attrs );
		init();
	}

	public Playfield( final Context context, final AttributeSet attrs, final int defStyle ) {
		super( context, attrs, defStyle );
		init();
	}

	public void init() {

		// Populate the grid with random colored squares
		for( int row = 0; row < grid.length; row++ ) {

			for( int col = 0; col < MainActivity.GRID_SIZE; col++ ) {
				grid[ row ][ col ] = new Square( MainActivity.getRandomColor() );
			}
		}
	}

	@Override
	protected void onDraw( final Canvas canvas ) {

		// The coordinates of each square
		int x = 0;
		int y = 0;

		for( Square[] row : grid ) {

			for( int col = 0; col < MainActivity.GRID_SIZE; col++ ) {

				row[ col ].draw( canvas, x, y );

				x += MainActivity.SQUARE_SIZE;
			}

			x = 0;

			y += MainActivity.SQUARE_SIZE;
		}
	}


//	/**
//	 * Process one game turn
//	 */
//	public void process( Color newColor ) {
//
//		Color referenceColor = grid[ 0 ][ 0 ].color;
//
//		if( referenceColor != newColor ) {
//
//			fill( 0, 0, referenceColor, newColor );
//
//			turnCount++;
//		}
//
//		// Check if full grid is filled...
//		boolean completed = true;
//		TEST: for( int row = 0; row < grid.length; row++ ) {
//
//			for( int col = 0; col < FloodIt.GRID_SIZE; col++ ) {
//
//				if( grid[ row ][ col ].color != grid[ 0 ][ 0 ].color ) {
//
//					completed = false;
//					break TEST;
//				}
//			}
//		}
//
//		// ...and display a message if game was completed
//		if( completed ) {
//
//			repaint();
//
//			JOptionPane.showMessageDialog( this, "Congratulations. You needed " + turnCount + " turns.", "Completed", JOptionPane.PLAIN_MESSAGE );
//
//			// Restart the game
//			init();
//		}
//	}
//
//	/**
//	 * Recursively fill all adjacent squares of the same color
//	 */
//	private void fill( int row, int col, Color referenceColor, Color newColor ) {
//
//		if( grid[ row ][ col ].color == referenceColor ) {
//
//			grid[ row ][ col ].color = newColor;
//
//			if( row < FloodIt.GRID_SIZE - 1 ) {
//				fill( row + 1, col, referenceColor, newColor );
//			}
//			if( col < FloodIt.GRID_SIZE - 1 ) {
//				fill( row, col + 1, referenceColor, newColor );
//			}
//			if( row > 0 ) {
//				fill( row - 1, col, referenceColor, newColor );
//			}
//			if( col > 0 ) {
//				fill( row, col - 1, referenceColor, newColor );
//			}
//		}
//	}
//
//	@Override
//	public Dimension getPreferredSize() {
//		return new Dimension( FloodIt.SQUARE_SIZE * FloodIt.GRID_SIZE, FloodIt.SQUARE_SIZE * FloodIt.GRID_SIZE );
//	}
}
