package de.fatmike.floodit;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Square {

	Paint paint = new Paint();

	public Square( int color ) {

		paint.setColor( color );
		paint.setStyle( Style.FILL_AND_STROKE );
	}

	public void draw( Canvas canvas, int x, int y ) {
		canvas.drawRect( x, y, x + MainActivity.SQUARE_SIZE, y + MainActivity.SQUARE_SIZE, paint );
	}
}