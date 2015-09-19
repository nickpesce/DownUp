package com.github.nickpesce.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Nick on 9/18/2015.
 */
public class GameCanvas extends View{

    private Paint paint;
    public GameCanvas(Context context, AttributeSet attributes)
    {
        super(context, attributes);
        paint = new Paint();
        invalidate();
    }

    public void redraw()
    {
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        //super.onDraw(canvas);
        paint.setARGB(150, 200, 40, 50);
        canvas.drawRect(4, 6, 1, 24, paint);
    }
}
