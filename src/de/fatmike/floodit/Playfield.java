package de.fatmike.floodit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Playfield extends View {

	public Playfield( final Context context ) {
		super( context );
	}

	public Playfield( final Context context, final AttributeSet attrs ) {
		super( context, attrs );
	}

	public Playfield( final Context context, final AttributeSet attrs, final int defStyle ) {
		super( context, attrs, defStyle );
	}

	@Override
	protected void onDraw( final Canvas canvas ) {

		final Paint paint = new Paint();
		paint.setColor( Color.RED );
		canvas.drawLine( 0, 0, 20, 20, paint );

		paint.setColor( Color.GREEN );
		canvas.drawLine( 20, 0, 0, 20, paint );
	}
}
