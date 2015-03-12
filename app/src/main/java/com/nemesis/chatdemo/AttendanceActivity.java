package com.nemesis.chatdemo;

import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


public class AttendanceActivity extends ActionBarActivity {

    Button btnShowLocation;

    // GPSTracker class
    GPSTracker gps;
    private Toolbar toolbar;
    private ProgressBar circle;
    private boolean isSearching;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar3);
        SpannableString s = new SpannableString("E Attendance");

        if(toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(s);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
        circle = (ProgressBar) findViewById(R.id.marker_progress);
        circle.setVisibility(View.INVISIBLE);

        // Show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Create class object

                gps = new GPSTracker(AttendanceActivity.this);


                // Check if GPS enabled
                if(gps.canGetLocation()) {

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();

                        if (latitude == 0 && longitude == 0)

                        // \n is for new line
                        {   circle.setVisibility(View.VISIBLE);

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                            circle.setVisibility(View.INVISIBLE);
                        }

                } else {
                    // Can't get location.
                    // GPS or network is not enabled.
                    // Ask user to enable GPS/network in settings.
                    gps.showSettingsAlert();
                }

                //AsyncPlotTask task=new AsyncPlotTask();
                //task.execute();
            }
        });
    }


    public class AsyncPlotTask extends AsyncTask<Void, Void, Void> {

        private double latitude=0, longitude=0;

        @Override
        protected Void doInBackground(Void... gpsTrackers) {




            gps = new GPSTracker(AttendanceActivity.this);


            // Check if GPS enabled
            if(gps.canGetLocation()) {

                //while(true)
                //{
                    //gps=null;
                    //gps = new GPSTracker(AttendanceActivity.this);
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                   /* if(latitude!=0 && longitude!=0 )
                        break;
                }*/



            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                gps.showSettingsAlert();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            gps = new GPSTracker(AttendanceActivity.this);
            if(!gps.canGetLocation()) {
                gps.showSettingsAlert();
                super.cancel(true);
            }
            else
            circle.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_SHORT).show();
            circle.setVisibility(View.INVISIBLE);
            super.onPostExecute(aVoid);
        }
    }//AsyncPlotTask


    public class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            if (location.getProvider().equals("gps")) {
                //FROM GPS
                if(isSearching){//GPS called first time after Enable
                    isSearching=false;
                }
            } else {
                //FROM NETWORK

            }
        }

        public void onProviderDisabled(String provider) {

        }

        public void onProviderEnabled(String provider) {
            isSearching = true;
            // GPS IS SEARCHING
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }
}

