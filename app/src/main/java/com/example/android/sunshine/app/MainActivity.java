package com.example.android.sunshine.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
//    private final String FORECASTFRAGMENT_TAG = "FFTAG";
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    private String mLocation;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLocation = Utility.getPreferredLocation(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            if (findViewById(R.id.weather_detail_container) != null) {
                // The detail container view will be present only in the large-screen layouts
                // (res/layout-sw600dp). If this view is present, then the activity should be
                // in two-pane mode.
                mTwoPane = true;
                // In two-pane mode, show the detail view in this activity by
                // adding or replacing the detail fragment using a
                // fragment transaction.
                if (savedInstanceState == null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.weather_detail_container, new DetailFragment(), DETAILFRAGMENT_TAG)
                            .commit();
                }
            } else {
                mTwoPane = false;

        }
        Log.i(LOG_TAG,"App state = onCreate");

    }

    @Override
    protected void onPause() {
        Log.i(LOG_TAG,"App state = onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(LOG_TAG,"App state = onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(LOG_TAG,"App state = onDestroy");
        super.onDestroy();
    }

/*    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(LOG_TAG,"App state = onSaveInstanceState");
        super.onSaveInstanceState();
    }*/

    @Override
    protected void onResume() {
        Log.i(LOG_TAG,"App state = onResume");
        super.onResume();
        String location = Utility.getPreferredLocation(this);
//        update the location in our second pane using the fragment manager
        if (location != null && !location.equals(mLocation)){
            ForecastFragment ff = (ForecastFragment)getSupportFragmentManager().
                    findFragmentById(R.id.fragment_forecast);
            if (null != ff){
                ff.onLocationChanged();;
            }
            mLocation = location;
        }
    }

    @Override
    protected void onStart(){
        Log.i(LOG_TAG,"App state = onStart");
        super.onStart();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Intent intent = new Intent(this, com.example.android.sunshine.app.SettingsActivity.class);
            startActivity(intent);

            /* Or do this way below as used in DetailActivity.java */
//            startActivity(new Intent(this, com.example.android.sunshine.app.SettingsActivity.class));
            return true;
        }

        if (id == R.id.action_map){
            openPreferredLocationInMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocationInMap(){
        //        refactored in Lesson to 5 towards ForecastAdapter
/*      SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String location = sharedPrefs.getString(getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));*/
        String location = Utility.getPreferredLocation(this);

        // Using the URI scheme for showing a location found on a map.  This super-handy
        // intent can is detailed in the "Common Intents" page of Android's developer site:
        // http://developer.android.com/guide/components/intents-common.html#Maps

        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q",location)
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        } else {
            Log.d(LOG_TAG, "Couldn't Call " + location + ", no receiving apps installed!");

        }


    }


}
