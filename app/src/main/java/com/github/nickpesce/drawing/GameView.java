package com.github.nickpesce.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.github.nickpesce.downup.GameActivity;

import java.util.logging.Logger;

/**
 * The view for the game. Contained by GameActivity. Where the drawing of game components takes place.
 *
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private Paint paint;
    private Canvas canvas;
    private GameActivity game;
    public GameView(Context context, AttributeSet attributes)
    {
        super(context, attributes);
        paint = new Paint();
        getHolder().addCallback(this);
        game = (GameActivity)context;
    }

    public synchronized void redraw()
    {
        canvas = null;
        try{
            canvas = getHolder().lockCanvas();
            render(canvas);
        }
        finally{
            if(canvas!=null)
                getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void render(Canvas canvas)
    {
        paint.setColor(0xFF0000);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        game.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        game.stop();
    }
}
