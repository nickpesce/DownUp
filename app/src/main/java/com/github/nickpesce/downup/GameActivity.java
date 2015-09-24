package com.github.nickpesce.downup;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.nickpesce.component.Entity;
import com.github.nickpesce.component.Objective;
import com.github.nickpesce.drawing.GameView;
import com.github.nickpesce.drawing.ImageHelper;
import com.github.nickpesce.drawing.Sprite;
import com.github.nickpesce.engine.GameLoop;

import java.util.ArrayList;

import nickpesce.github.com.downup.R;

public class GameActivity extends Activity {

    public static final int WIDTH = 2160, HEIGHT = 3840;
    private GameView gameView;
    private GameLoop loop;
    private boolean paused;
    private Entity[] items;
    private Bitmap[] upBitmaps, downBitmaps;
    private ArrayList<Objective> objectives;
    private int score;
    private long startTime;
    private int numItems;
    private int speedMultiplier;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameView = (GameView)findViewById(R.id.game_canvas);
        loop = new GameLoop(this);
        paused = false;
        startTime = System.currentTimeMillis();
        numItems = getIntent().getIntExtra("columns", 4);
        speedMultiplier = getIntent().getIntExtra("speed", 5);
        if(Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }

    public void onTouch(int x, int y)
    {
        int n = (int)((x/(WIDTH+.01/*To prevent n == numItems*/))*numItems);
        items[n].tryReverse();
    }

    public void addToScore(int s)
    {
        score += s;
    }

    public int getScore()
    {
        return score;
    }

    public int getSpeedMultiplier()
    {
        return speedMultiplier;
    }

    public int getNumItems()
    {
        return numItems;
    }

    public void init()
    {
        objectives = new ArrayList<Objective>();
        upBitmaps = new Bitmap[1];
        downBitmaps = new Bitmap[2];

        int entitySize = WIDTH/numItems;
        downBitmaps[0] = ImageHelper.getScaledBitmapFromResource(this, R.drawable.anchor, entitySize, entitySize);
        downBitmaps[1] =  ImageHelper.getScaledBitmapFromResource(this, R.drawable.dumbbell, entitySize, entitySize);

        //upBitmaps[0] = ImageHelper.getScaledBitmapFromResource(this, R.drawable.bubble, entitySize, entitySize);
        upBitmaps[0] =ImageHelper.getScaledBitmapFromResource(this, R.drawable.balloon, entitySize*4 , entitySize);

        items = new Entity[numItems];
        for(int i = 0; i < numItems; i++)
        {
            items[i] = new Entity(this, i*entitySize, HEIGHT/2.0 - entitySize, entitySize, entitySize, i);
        }
    }

    public Sprite getRandomUpSprite(int w, int h)
    {
        return new Sprite(getRandomUpBitmap(), w, h, 4, 50);
    }

    public Sprite getRandomDownSprite(int w, int h)
    {
        return new Sprite(getRandomDownBitmap(), w, h);
    }

    public Bitmap getRandomUpBitmap()
    {
        return upBitmaps[(int)(Math.random() * upBitmaps.length)];
    }

    public Bitmap getRandomDownBitmap()
    {
        return downBitmaps[(int)(Math.random() * downBitmaps.length)];
    }

    public ArrayList<Objective> getObjectives()
    {
        return objectives;
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        loop.stop();
    }

    public void start()
    {
        loop.startGame();

    }

    public void stop()
    {
        loop.stop();
    }

    public void gameOver()
    {
        finish();
    }

    public void render(double interpolation)
    {
        gameView.redraw(interpolation);
    }

    public void update()
    {
        for(Entity e : items)
            e.update();
    }

    public boolean isUpdating()
    {
        return !paused;
    }

    public boolean isDisplaying()
    {
        return true;
    }

    public Entity[] getItems()
    {
        return items;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public GameView getGameView()
    {
        return gameView;
    }
}
