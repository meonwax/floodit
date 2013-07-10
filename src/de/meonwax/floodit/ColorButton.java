package de.meonwax.floodit;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class ColorButton extends Button {

	public ColorButton( final Context context, final AttributeSet attrs ) {
		super( context, attrs );
	}

	@SuppressWarnings( "deprecation" )
	public ColorButton( final Context context, final int color ) {

		super( context );

		final Drawable background = getResources().getDrawable( R.drawable.color_buttons );
		background.setColorFilter( color, Mode.MULTIPLY );

		if( android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN ) {
			setBackgroundDrawable( background );
		}
		else {
			setBackground( background );
		}

		setLayoutParams( new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f ) );
	}

	@Override
	protected void onMeasure( final int widthMeasureSpec, final int heightMeasureSpec ) {

		// Set height equal to width
		super.onMeasure( widthMeasureSpec, widthMeasureSpec );
	}
}
