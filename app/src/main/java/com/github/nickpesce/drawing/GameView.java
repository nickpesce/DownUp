package com.github.nickpesce.drawing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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

import nickpesce.github.com.downup.R;

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
    private Bitmap background;
    private Matrix transformation;

    public GameView(Context context, AttributeSet attributes)
    {
        super(context, attributes);
        paint = new Paint();
        getHolder().addCallback(this);
        requestFocus();
        game = (GameActivity)context;
        transformation = new Matrix();
       // background = ImageHelper.getScaledBitmapFromResource(context, R.drawable.background, GameActivity.WIDTH/10, GameActivity.HEIGHT/10);
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
        canvas.setMatrix(transformation);
        paint.setColor(0xFF0000);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawPaint(paint);


        paint.setColor(Color.GRAY);
        canvas.drawRect(0, 0, GameActivity.WIDTH, GameActivity.HEIGHT, paint);
       // canvas.drawBitmap(background, offX, offY, (int)(GameActivity.WIDTH*scale), (int)(GameActivity.HEIGHT*scale), paint);

        paint.setColor(Color.BLACK);
        for(int i = 0; i < game.getNumItems(); i++)
        {
            int x = (int)(((double)GameActivity.WIDTH/game.getNumItems())*i);
            canvas.drawLine(x,0, x, GameActivity.HEIGHT, paint);
        }
        paint.setColor(Color.GREEN);
        paint.setAlpha(20);
        canvas.drawRect(0, GameActivity.HEIGHT - GameActivity.HEIGHT/5, GameActivity.WIDTH , GameActivity.HEIGHT, paint);
        canvas.drawRect(0, 0, GameActivity.WIDTH, GameActivity.HEIGHT/5, paint);

        for(Objective o : game.getObjectives())
            o.draw(canvas, this);
        for(Entity e : game.getItems())
            e.getSprite().draw(canvas);

        paint.setColor(Color.BLACK);
        paint.setTextSize(120);
        canvas.drawText("score: " + game.getScore(), 0, 120, paint);

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
        transformation.postScale((float)scale, (float)scale);
        transformation.postTranslate(offX, offY);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        game.stop();
    }
}
