package com.github.nickpesce.downup;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.nickpesce.component.Entity;
import com.github.nickpesce.drawing.GameView;
import com.github.nickpesce.drawing.ImageHelper;
import com.github.nickpesce.drawing.Sprite;
import com.github.nickpesce.engine.GameLoop;

import nickpesce.github.com.downup.R;

public class GameActivity extends Activity {

    private GameView gameView;
    private GameLoop loop;
    private boolean paused;
    private Entity[] items;
    private Bitmap[] upBitmaps, downBitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameView = (GameView)findViewById(R.id.game_canvas);
        loop = new GameLoop(this);
        paused = false;
    }

    public void init()
    {
        upBitmaps = new Bitmap[2];
        downBitmaps = new Bitmap[2];

        downBitmaps[0] = ImageHelper.getScaledBitmapFromResource(this, R.drawable.anchor, 400, 400);
        downBitmaps[1] =  ImageHelper.getScaledBitmapFromResource(this, R.drawable.dumbbell, 400, 400);

        upBitmaps[0] = ImageHelper.getScaledBitmapFromResource(this, R.drawable.anchor, 400, 400);
        upBitmaps[1] =ImageHelper.getScaledBitmapFromResource(this, R.drawable.anchor, 400, 400);

        items = new Entity[4];
        items[0] = new Entity(this, 100, 60, 400, 400, getRandomDownSprite(400, 400));
        items[1] = new Entity(this, 600, 60, 400, 400, getRandomDownSprite(400, 400));
        items[2] = new Entity(this, 1100, 60, 400, 400, getRandomDownSprite(400, 400));
        items[3] = new Entity(this, 1600, 60, 400, 400, getRandomDownSprite(400, 400));
    }

    public Sprite getRandomUpSprite(int w, int h)
    {
        return new Sprite(getRandomUpBitmap(), w, h);
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

    public void render(double interpolation)
    {
        gameView.redraw();
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
