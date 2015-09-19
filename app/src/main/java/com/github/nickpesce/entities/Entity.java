package com.github.nickpesce.entities;

import android.graphics.drawable.Drawable;

/**
 * Abstract superclass for all entities in game. Entities have location, velocity, acceleration, and a sprite. They can all  be updated.
 */
public abstract class Entity
{
    private double x, y, vX, vY, aX, aY;
    private Sprite sprite;

    public Entity(double x, double y, Sprite sprite)
    {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void update()
    {
        x += vX;
        y += vY;
        vX += aX;
        vY += aY;
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
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
