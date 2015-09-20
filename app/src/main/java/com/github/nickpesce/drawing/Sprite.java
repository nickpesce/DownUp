package com.github.nickpesce.drawing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.github.nickpesce.component.Entity;

/**
 * Custom drawable class with optional animations.
 */
public class Sprite extends Drawable
{
    /**
     * Main bitmap that contains all of the sprites for the animation
     */
    private Bitmap bitmap;
    /**
     * width of each section of the bitmap.
     */
    private int width;
    /**
     * height of each section of the bitmap
     */
    private int height;
    /**
     * the number of sections of the animation
     */
    private int n;
    /**
     * The duration in milliseconds of each segment of the animation
     */
    private int t;
    /**
     * Time(UNIX) when the sprite section will change.
     */
    private long nextT;
    /**
     * The current section of the bitmap being displayed
     */
    private int currSection;
    /**
     * the scaled location on the screne for the sprite to be drawn. Obtained thorugh the update method.
     */
    private Rect drawLocation;
    public Sprite(Bitmap bitmap, int width, int height, int n, int t)
    {
        this.bitmap = bitmap;
        this.width = width;
        this.height = height;
        this.n = n;
        this.t = t;
        nextT = System.currentTimeMillis() + t;
    }

    public Sprite(Bitmap bitmap, int width, int height)
    {
        this(bitmap, width, height, 1, 0);
    }

    public void update(Rect drawLocation)
    {
        this.drawLocation = drawLocation;
        if(System.currentTimeMillis() > nextT)
        {
            currSection++;
            if(currSection > n-1)
                currSection = 0;
            nextT += t;
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        if(drawLocation == null)return;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawBitmap(bitmap, new Rect(currSection * width, 0, (currSection + 1) * width, height), drawLocation, paint);
        //Paint paint = new Paint();
        //paint.setColor(Color.BLUE);
        //paint.setStyle(Paint.Style.FILL);
        //canvas.drawRect(drawLocation, paint);
    }

    @Override
    public void setAlpha(int alpha)
    {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter)
    {

    }

    @Override
    public int getOpacity()
    {
        return 0;
    }
}
