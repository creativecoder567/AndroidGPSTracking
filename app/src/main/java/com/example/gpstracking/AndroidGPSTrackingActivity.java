package com.example.gpstracking;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AndroidGPSTrackingActivity extends Activity {
	
	Button btnShowLocation;
	
	// GPSTracker class
	GPSTracker gps;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
        
        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {		
				// create class object
		        gps = new GPSTracker(AndroidGPSTrackingActivity.this);

				// check if GPS enabled		
		        if(gps.canGetLocation()){
		        	
		        	double latitude = gps.getLatitude();
		        	double longitude = gps.getLongitude();
		        	
		        	// \n is for new line
		        	//Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
					LocationAddress locationAddress = new LocationAddress();
					locationAddress.getAddressFromLocation(latitude , longitude , getApplicationContext(), new GeocoderHandler());

				}else{
		        	// can't get location
		        	// GPS or Network is not enabled
		        	// Ask user to enable GPS/network in settings
		        	gps.showSettingsAlert();
		        }
				
			}
		});
    }
	private class GeocoderHandler extends Handler {
		@Override
		public void handleMessage(Message message) {
			String locationAddress;
			switch (message.what) {
				case 1:
					Bundle bundle = message.getData();
					locationAddress= bundle.getString("address");
					break;
				default:
					locationAddress= null;
			}
			//tvAddress.setText(locationAddress);
			Toast.makeText(AndroidGPSTrackingActivity.this, ""+locationAddress, Toast.LENGTH_SHORT).show();
		}
	}
}