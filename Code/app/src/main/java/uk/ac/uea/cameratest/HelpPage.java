package uk.ac.uea.cameratest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Activity that displays app information for confused users when clicked
 */
public class HelpPage extends Activity implements View.OnClickListener {

    /**
     * Sets up content from app previous state
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);

        Button buttonBack = (Button) findViewById(R.id.button5);
        buttonBack.setOnClickListener(this);

    }

    /**
     * Dictates events when buttpons are clicked
     * @param v
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                onClickBack();
                break;
            case R.id.button3:
                onClickBack();
                break;
            case R.id.button5:
                onClickBack();
                break;
        }
    }

    /**
     * Dictates events when the hardware button "back" is clicked
     */
    private void onClickBack() {
        Intent getMapsPage = new Intent(this,CameraActivity.class);

        startActivity(getMapsPage);
    }

    /**
     * Creates options menu from menu object
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help_page, menu);
        return true;
    }

    /**
     * Defines behaviour when a menu item is clicked
     * @param item
     * @return
     */
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
