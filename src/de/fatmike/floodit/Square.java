package de.fatmike.floodit;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Square {

	float size;
	Paint paint = new Paint();

	public Square( float size, int color ) {

		this.size = size;

		paint.setColor( color );
		paint.setStyle( Style.FILL_AND_STROKE );
	}

	public void draw( Canvas canvas, int x, int y ) {
		canvas.drawRect( x, y, x + size, y + size, paint );
	}
}