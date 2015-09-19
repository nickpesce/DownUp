package com.github.nickpesce.downup;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.nickpesce.drawing.GameView;
import com.github.nickpesce.engine.GameLoop;

import nickpesce.github.com.downup.R;

public class GameActivity extends Activity {

    private GameView gameView;
    private GameLoop loop;
    private boolean paused;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameView = (GameView)findViewById(R.id.game_canvas);
        loop = new GameLoop(this);
        paused = false;
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

    }

    public boolean isUpdating()
    {
        return !paused;
    }

    public boolean isDisplaying()
    {
        return true;
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

}
