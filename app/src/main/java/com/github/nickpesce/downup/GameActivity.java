package com.github.nickpesce.downup;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
    private Sprite[] upSprites, downSprites;

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
        upSprites = new Sprite[1];
        downSprites = new Sprite[1];
        downSprites[0] = new Sprite(ImageHelper.getScaledBitmapFromResource(this, R.drawable.anchor, 400, 300), 400, 300);
        upSprites[0] = new Sprite(ImageHelper.getScaledBitmapFromResource(this, R.drawable.anchor, 400, 300), 400, 300);

        items = new Entity[4];
        items[0] = new Entity(this, 100, 60, 400, 300, getRandomDownSprite());
        items[1] = new Entity(this, 600, 60, 400, 300, getRandomDownSprite());
        items[2] = new Entity(this, 1100, 60, 400, 300, getRandomDownSprite());
        items[3] = new Entity(this, 1600, 60, 400, 300, getRandomDownSprite());
    }

    public Sprite getRandomUpSprite()
    {
        return upSprites[(int)(Math.random() * upSprites.length)];
    }

    public Sprite getRandomDownSprite()
    {
        return downSprites[(int)(Math.random() * downSprites.length)];
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
