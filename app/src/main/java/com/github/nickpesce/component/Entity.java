package com.github.nickpesce.component;

import android.graphics.Rect;

import com.github.nickpesce.downup.GameActivity;
import com.github.nickpesce.drawing.Sprite;
import com.github.nickpesce.engine.GameLoop;

import java.util.Iterator;

/**
 * The up/down items in the game. The things that are moving. anchors, balloons, etc.
 */
public class Entity
{
    //All in terms of 3840x2160
    private GameActivity game;
    private int width;
    private int height;
    private double x;
    private double y;
    private double vX;
    private double vY;
    private double aX;
    private double aY;
    private Sprite upSprite, downSprite, currentSprite;
    private int n;
    private long freezeUntil;

    public Entity(GameActivity game, double x, double y, int width, int height, int n)
    {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.aY = getRandomAcceleration(Math.random() > 0.5);
        this.n = n;
        upSprite = new Sprite(game.getUpBitmap(), width, height);
        downSprite = new Sprite(game.getDownBitmap(), width, height);

        if(aY > 0)
            currentSprite = downSprite;
        else
            currentSprite = upSprite;
    }

    private double getRandomAcceleration(boolean positive)
    {
        if(positive)
            return (Math.random()+.1)*(4.0/ GameLoop.BASE_SPEED)*Math.log10(game.getScore()+10)*game.getSpeedMultiplier();
        else
            return -(Math.random()+.1)*(4.0/GameLoop.BASE_SPEED)*Math.log10(game.getScore()+10)*game.getSpeedMultiplier();
    }

    public void freeze(int ms)
    {
        freezeUntil = System.currentTimeMillis() + ms;
    }
    public void update()
    {
        if(freezeUntil < System.currentTimeMillis()) {
            x += vX;
            y += vY;
            vX += aX;
            vY += aY;
            if (y+height <= 0)
                hitTop();
            else if (y >= GameActivity.HEIGHT)
                hitBottom();
        }
        currentSprite.update(getDrawRect());
    }

    private void hitBottom()
    {
        game.gameOver();
    }

    private void hitTop()
    {
        game.gameOver();
    }

    public void tryReverse()
    {
        if(aY<0)
        {
            if(y < GameActivity.HEIGHT/5)
                reverse();
        }
        else
        {
            if(y+height > GameActivity.HEIGHT - GameActivity.HEIGHT/5)
               reverse();

        }
    }

    private void reverse()
    {
        vY = 0;
        if(aY<0)
        {
            this.aY = getRandomAcceleration(true);
            currentSprite =downSprite;
            game.addToScore((int)(GameActivity.HEIGHT - y));
        }
        else
        {
            this.aY = getRandomAcceleration(false);
            currentSprite = upSprite;
            game.addToScore((int) (y));
        }
        currentSprite.update(getDrawRect());

        //Check all objectives. If in same row, remove and apply effect if in zone.
        Iterator<Objective> it = game.getObjectives().iterator();
        while(it.hasNext())
        {
            Objective o = it.next();
            if(o.getColumnNumber() == n)
            {
                if(o.isTouching(getRect()))
                    o.hit(this);
                it.remove();
            }
        }
        if(Math.random() < .2)
        {
            game.getObjectives().add(Objective.getNewRandomObjective(game, n, aY < 0));
        }

    }

    public GameActivity getGame()
    {
        return game;
    }


    public Rect getRect()
    {
        return new Rect((int)x, (int)y, (int)(x +  width), (int)(y+height));
    }

    public Rect getDrawRect()
    {
        return new Rect((int)x, (int)y, (int)(x +  width + (vX*game.getInterpolation())), (int)(y+height + (vY *game.getInterpolation())));
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getvX() {
        return vX;
    }

    public void setvX(double vX) {
        this.vX = vX;
    }

    public double getvY() {
        return vY;
    }

    public void setvY(double vY) {
        this.vY = vY;
    }

    public double getaX() {
        return aX;
    }

    public void setaX(double aX) {
        this.aX = aX;
    }

    public double getaY() {
        return aY;
    }

    public void setaY(double aY) {
        this.aY = aY;
    }

    public Sprite getSprite() {
        return currentSprite;
    }

}
