package com.github.nickpesce.engine;

import com.github.nickpesce.downup.GameActivity;

/**
 * Main control of the game. Originator of all update and render calls. Main game thread
 */
public class GameLoop
{
    private GameActivity game;
    private boolean running = true;
    private int fps, tps;
    public static final int BASE_SPEED = 60;
    public static final int MAX_FPS = 60;
    private int actualSpeed = BASE_SPEED;
    private double d = 1.0/actualSpeed;
    /**
     * Time between ticks in seconds
     */
    public static final double SKIP_TIME = 1.0 / BASE_SPEED;

    public GameLoop(GameActivity game)
    {
        this.game = game;
    }

    public void startGame()
    {
        game.init();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                startLoop();
            }
        }).start();
    }

    public void startGame(int tps)
    {
        actualSpeed = tps;
        startGame();
    }

    public void stop()
    {
        running = false;
    }


    private void startLoop()
    {
        running = true;
        int skippedFrames = 0;//ie. updates in a row
        d = 1.0/actualSpeed;
        double next = (System.nanoTime() / 1000000000.0);
        double lastFrameTime = 0, lastTickTime = 0;
        double interpolation;
        while(running)
        {
            double curr = (System.nanoTime() / 1000000000.0);
            if(next<=curr && (skippedFrames < 10 || !game.isDisplaying()) && game.isUpdating())
            {
                tps=((int)(1.0/(curr-lastTickTime)));
                lastTickTime = curr;
                skippedFrames++;
                next+=d;
               // game.fineUpdate(0);
                game.update();
            }else if(game.isDisplaying() && (curr-lastFrameTime)>=1.0/MAX_FPS)
            {
                skippedFrames = 0;
                fps = ((int)(1.0/(curr-lastFrameTime))+1);
                if(!game.isUpdating())
                {
                    interpolation = 0;
                    next = curr;
                }
                else
                    interpolation = 1-((next-curr)*actualSpeed);
               // if((curr-lastFrameTime)<(1.0/actualSpeed))
                   // game.fineUpdate(interpolation);
                game.render(interpolation);
                lastFrameTime = curr;
            }
        }
    }

    public int getActualSpeed()
    {
        return actualSpeed;
    }

    public void setActualSpeed(int speed)
    {
        actualSpeed = speed;
        d = 1.0/actualSpeed;
    }
}