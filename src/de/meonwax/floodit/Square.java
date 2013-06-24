package de.meonwax.floodit;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Square {

	private final float size;
	private final Paint paint = new Paint();

	protected Square( final float size, final int color ) {

		this.size = size;

		paint.setColor( color );
		paint.setStyle( Style.FILL_AND_STROKE );
	}

	protected void setColor( final int color ) {
		paint.setColor( color );
	}

	protected int getColor() {
		return paint.getColor();
	}

	protected void draw( final Canvas canvas, final float x, final float y ) {
		canvas.drawRect( x, y, x + size, y + size, paint );
	}
}