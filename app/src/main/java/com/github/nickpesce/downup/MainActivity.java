package com.github.nickpesce.downup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import nickpesce.github.com.downup.R;

public class MainActivity extends AppCompatActivity {

    /**
     * The start button on the main menu
     */
    private Button bStart;
    private NumberPicker numberPickerSpeed, numberPickerColumns;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberPickerSpeed = (NumberPicker)findViewById(R.id.npSpeed);
        numberPickerSpeed.setMaxValue(10);
        numberPickerSpeed.setValue(5);
        numberPickerSpeed.setMinValue(1);
        numberPickerSpeed.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        numberPickerColumns = (NumberPicker)findViewById(R.id.npColumns);
        numberPickerColumns.setMaxValue(10);
        numberPickerColumns.setValue(4);
        numberPickerColumns.setMinValue(2);
        numberPickerColumns.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        bStart = (Button)findViewById(R.id.button_start);
        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                i.putExtra("speed", numberPickerSpeed.getValue());
                i.putExtra("columns", numberPickerColumns.getValue());
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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
