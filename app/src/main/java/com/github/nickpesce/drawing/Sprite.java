package com.github.nickpesce.drawing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.github.nickpesce.component.Entity;

/**
 * Created by Nick on 9/19/2015.
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
     * The entity that this sprite is representing
     */
    private Entity entity;
    public Sprite(Bitmap bitmap, Entity e, int width, int height, int n, int t)
    {
        this.bitmap = bitmap;
        this.width = width;
        this.height = height;
        this.entity = e;
        this.n = n;
        this.t = t;
        nextT = System.currentTimeMillis() + t;
    }

    public Sprite(Bitmap bitmap, Entity e, int width, int height)
    {
        this(bitmap, e, width, height, 1, 0);
    }

    public void update()
    {
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
        canvas.drawBitmap(bitmap, new Rect(currSection*width, 0, (currSection+1) * width, height), entity.getRect(), null);
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
