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
    {slow, points, gem, extra, freeze}

    private int n;
    private int y;
    private int width, height;
    private Type type;
    private int color;
    private Rect rect;

    public Objective(int n, Type type, int y, int width, int height)
    {
        this.n = n;
        this.type = type;
        switch(type)
        {
            case slow:
                this.color = Color.GREEN;
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
            case freeze:
                this.color = Color.CYAN;
                break;
            default:
                this.color = Color.BLACK;
                break;

        }
        this.y = y;
        this.width = width;
        this.height = height;
        rect = new Rect((width * n), y, (width * (n+1)), y+height);
    }

    public void hit(Entity e)
    {
        switch(type)
        {
            case points:
                e.getGame().addToScore(50000);
                break;
            case slow:
                e.setaY(e.getaY()/4.0);
                break;
            case extra:

                break;
            case gem:
                break;
            case freeze:
                e.freeze(5000);
        }
    }
    public boolean includesPoint(int locY)
    {
        return locY < y && locY > y+height;
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
        canvas.drawRect(rect, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(120);
        canvas.drawText(type.name(), ((width) * n) +gameView.getOffsetX(), y + paint.getTextSize() +  gameView.getOffsetY(), paint);
    }

    public static Objective getNewRandomObjective(GameActivity game, int n, boolean top)
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

        return new Objective(n, type, y, (game.WIDTH/game.getNumItems()), width);
    }
}
