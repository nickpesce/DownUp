package com.github.nickpesce.component;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.github.nickpesce.downup.GameActivity;
import com.github.nickpesce.drawing.GameView;

/**
 * A section of a column that will give a reward if the item is "reversed" while on top of it. Removed when the item in that column is reversed.
 */
public class Objective
{
    public enum Type
    {slow, points, gem, extra}

    private int n;
    private int y;
    private int width;
    private Type type;
    private int color;
    private Rect rect;

    public Objective(int n, Type type, int y, int width)
    {
        this.n = n;
        this.type = type;
        switch(type)
        {
            case slow:
                this.color = Color.CYAN;
                break;
            case points:
                this.color = Color.RED;
                break;
            case gem:
                this.color = Color.YELLOW;
                break;
            case extra:
                this.color = Color.BLUE;
                break;
            default:
                this.color = Color.BLACK;
                break;

        }
        this.y = y;
        this.width = width;
        rect = new Rect((int)((GameActivity.WIDTH/4.0) * n), y, (int)((GameActivity.WIDTH/4.0) * (n+1)), y+width);
    }

    public void hit(Entity e)
    {
        e.setaY(0);
        e.setvY(0);
    }
    public boolean includesPoint(int locY)
    {
        return locY < y && locY > y+width;
    }

    public boolean isTouching(Rect other)
    {
        return Rect.intersects(other, rect);
    }

    public Type getType()
    {
        return type;
    }

    public int getColumnNumber()
    {
        return n;
    }

    public void draw(Canvas canvas, GameView gameView)
    {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(gameView.applyTransformation(rect),  paint);
    }

    public static Objective getNewRandomObjective(int n, boolean top)
    {
        int width = (int)(Math.random() * (GameActivity.HEIGHT/10.0) + 20);//At least 20. Max height/10 + 20
        Type type = Type.values()[(int)(Math.random()*Type.values().length)];
        int y;
        if(top)
        {
            y = (int)(Math.random() * (GameActivity.HEIGHT/7.0));//y value in top seventh of screen
        }
        else
        {
            y = GameActivity.HEIGHT - (int)(Math.random() * (GameActivity.HEIGHT/7.0)) - width;//y value in bottom seventh of screen
        }

        return new Objective(n, type, y, width);
    }
}
