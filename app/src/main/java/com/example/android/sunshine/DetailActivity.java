package com.example.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView mweatherTextView;
    private String weatherData;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.detail, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share){
            shareWeatherdata();
        }
        return true;

    }

    private void shareWeatherdata() {
        ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(this)
                .setChooserTitle("Share my Weather")
                .setType("text/plain")
                .setText(weatherData);
        Intent shareIntent = intentBuilder.getIntent();

        if(shareIntent.resolveActivity(getPackageManager()) != null)
            startActivity(shareIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mweatherTextView = (TextView) findViewById(R.id.tv_weatherDataDetail);

        Intent startActivityIntent = getIntent();

        if(startActivityIntent.hasExtra(Intent.EXTRA_TEXT)){
            weatherData = startActivityIntent.getStringExtra(Intent.EXTRA_TEXT);
            mweatherTextView.setText(weatherData);
        }

    }
}
