package de.fatmike.floodit;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class Playfield extends View {

	private final Square[][] grid = new Square[ MainActivity.GRID_SIZE ][ MainActivity.GRID_SIZE ];
	private float squareSize = 0f;

	protected Playfield( final Context context ) {
		super( context );
	}

	protected Playfield( final Context context, final AttributeSet attrs ) {
		super( context, attrs );
	}

	protected Playfield( final Context context, final AttributeSet attrs, final int defStyle ) {
		super( context, attrs, defStyle );
	}

	protected void init() {

		// Populate the grid with random colored squares
		for( int row = 0; row < grid.length; row++ ) {

			for( int col = 0; col < MainActivity.GRID_SIZE; col++ ) {
				grid[ row ][ col ] = new Square( squareSize, MainActivity.getRandomColor() );
			}
		}
	}

	@Override
	protected void onDraw( final Canvas canvas ) {

		// The coordinates of each square
		float x = 0;
		float y = 0;

		for( final Square[] row : grid ) {

			for( int col = 0; col < MainActivity.GRID_SIZE; col++ ) {

				row[ col ].draw( canvas, x, y );

				x += squareSize;
			}

			x = 0;

			y += squareSize;
		}
	}

	@Override
	protected void onLayout( final boolean changed, final int l, final int t, final int r, final int b ) {

		super.onLayout( changed, l, t, r, b );

		squareSize = ( r - l ) / Float.valueOf( MainActivity.GRID_SIZE );

		init();
	}

	@Override
	protected void onMeasure( final int widthMeasureSpec, final int heightMeasureSpec ) {

		// Set height equal to width
		super.onMeasure( widthMeasureSpec, widthMeasureSpec );
	}

	protected int getReferenceColor() {
		return grid[ 0 ][ 0 ].getColor();
	}

	protected boolean isFilled() {

		boolean filled = true;
		TEST: for( int row = 0; row < grid.length; row++ ) {

			for( int col = 0; col < MainActivity.GRID_SIZE; col++ ) {

				if( grid[ row ][ col ].getColor() != grid[ 0 ][ 0 ].getColor() ) {

					filled = false;
					break TEST;
				}
			}
		}

		return filled;
	}

	/**
	 * Recursively fill all adjacent squares of the same color
	 */
	protected void fill( final int row, final int col, final int newColor ) {

		if( grid[ row ][ col ].getColor() == getReferenceColor() ) {

			grid[ row ][ col ].setColor( newColor );

			if( row < MainActivity.GRID_SIZE - 1 ) {
				fill( row + 1, col, newColor );
			}
			if( col < MainActivity.GRID_SIZE - 1 ) {
				fill( row, col + 1, newColor );
			}
			if( row > 0 ) {
				fill( row - 1, col, newColor );
			}
			if( col > 0 ) {
				fill( row, col - 1, newColor );
			}
		}
	}

//	@Override
//	public Dimension getPreferredSize() {
//		return new Dimension( FloodIt.SQUARE_SIZE * FloodIt.GRID_SIZE, FloodIt.SQUARE_SIZE * FloodIt.GRID_SIZE );
//	}
}
