package com.github.nickpesce.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.github.nickpesce.component.Entity;
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
    private double scale;
    private int offX;
    private int offY;
    private double interpolation;
    public GameView(Context context, AttributeSet attributes)
    {
        super(context, attributes);
        paint = new Paint();
        getHolder().addCallback(this);
        requestFocus();
        game = (GameActivity)context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int x = (int) (event.getX()/scale)+offX;
        int y = (int)(event.getY()/scale)+offY;
        if(!(x < 0 || x > 2160 || y < 0 || y > 3840))
            game.onTouch(x, y);
        return super.onTouchEvent(event);
    }

    public synchronized void redraw(double interpolation)
    {
        this.interpolation = interpolation;
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
        if(canvas == null)return;
        paint.setColor(0xFF0000);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawPaint(paint);
        paint.setColor(Color.WHITE);
        canvas.drawRect(offX, offY, (float) (2160.0f * scale), (float) (3840 * scale), paint);

        for(Entity e : game.getItems())
            e.getSprite().draw(canvas);
    }

    /**
     * Transforms the rect from 2160x3840 to desired screen resolution. Assumes not moving
     * @param rect The dest rect. location on 4k screen`
     * @return New rect transformed to be correct for the actual screen
     */
    public Rect applyTransformation(Rect rect)
    {
        return new Rect((int)((rect.left * scale) + offX), (int)((rect.top * scale) + offY),  (int)((rect.right*scale) + offX), (int)((rect.bottom*scale)+offY));
    }

    /**
     * Transforms the rect from 2160x3840 to desired screen resolution. Also applies interpolation with given speeds.
     * @param rect The dest rect. location on 4k screen`
     * @return New rect transformed to be correct for the actual screen
     */
    public Rect applyTransformation(Rect rect, double vX, double vY)
    {
        return new Rect((int)((rect.left * scale) + offX + (vX*interpolation)), (int)((rect.top * scale) + offY + (vY*interpolation)),  (int)((rect.right*scale) + offX + (vX*interpolation)), (int)((rect.bottom*scale)+offY + (vY*interpolation)));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        game.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        int desiredHeight = GameActivity.HEIGHT;
        int desiredWidth = GameActivity.WIDTH;
        double desiredRatio = (double)desiredWidth/desiredHeight;
        double actualRatio = (double)width/height;
        if(actualRatio > desiredRatio)
        {
            scale = (double)height/desiredHeight;

            offX = (int)(((width - (desiredWidth*scale)))/2);
        }else if(actualRatio < desiredRatio)
        {
            scale = (double)width/desiredWidth;
            offY = (int)(((height - (desiredHeight*scale)))/2);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        game.stop();
    }
}
