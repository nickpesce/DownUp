package com.github.nickpesce.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.github.nickpesce.component.Entity;
import com.github.nickpesce.component.Objective;
import com.github.nickpesce.downup.GameActivity;

/**
 * The view for the game. Contained by GameActivity. Where the drawing of game components takes place.
 *
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private Paint paint;
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
    public boolean onTouchEvent(@NonNull MotionEvent event)
    {
        int x = (int) (event.getX()/scale)+offX;
        int y = (int)(event.getY()/scale)+offY;
        if(!(x < 0 || x > GameActivity.WIDTH || y < 0 || y > GameActivity.HEIGHT))
            game.onTouch(x, y);
        return super.onTouchEvent(event);
    }

    public double getScale()
    {
        return scale;
    }

    public int getOffsetX()
    {
        return offX;
    }

    public int getOffsetY()
    {
        return offY;
    }

    public synchronized void redraw(double interpolation)
    {
        this.interpolation = interpolation;
        Canvas canvas = null;
        try{
            canvas = getHolder().lockCanvas();
            render(canvas);
        }
        finally{
            if(canvas !=null)
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
        paint.setColor(Color.GRAY);
        canvas.drawRect(offX, offY, (float) (GameActivity.WIDTH * scale)+offX, (float) (GameActivity.HEIGHT * scale)+offY, paint);
        paint.setColor(Color.BLACK);
        for(int i = 0; i < 4; i++)
        {
            int x = (int)(((GameActivity.WIDTH/4.0)*i) * scale) + offX;
            canvas.drawLine(x, offY, x, offY + (int)(GameActivity.HEIGHT * scale), paint);
        }

        for(Objective o : game.getObjectives())
            o.draw(canvas, this);
        for(Entity e : game.getItems())
            e.getSprite().draw(canvas);

        paint.setTextSize((int)(120*scale));
        canvas.drawText("score: " + game.getScore(), offX, (int)(120*scale)+offY, paint);

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

            offX = (int)(((width - (desiredWidth*scale)))/2.0);
        }else if(actualRatio < desiredRatio)
        {
            scale = (double)width/desiredWidth;
            offY = (int)(((height - (desiredHeight*scale)))/2);
        }else
        {
            scale = (double)width/desiredWidth;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        game.stop();
    }
}
