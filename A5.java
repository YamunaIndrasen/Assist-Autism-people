package com.example.autism;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class A5 extends Activity {

	TextView lat_tv, lng_tv, spd_tv, time_tv, count_tv;
	RefreshHandler mRedrawHandler = new RefreshHandler();
	LocationManager locationManager;
	LocationListener locationListener;
	android.location.Location location;
	TimerTask timerTask;
	final Handler handler = new Handler();
	Timer timer = new Timer();

	int interval;

	int cnt = 0;
	SmsManager sms;
	DatabaseHandler dbs=new DatabaseHandler(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a5);
		sms=SmsManager.getDefault();

		interval = getIntent().getIntExtra("interval", 1);

		lat_tv = (TextView) findViewById(R.id.textView6);
		lng_tv = (TextView) findViewById(R.id.textView7);
		spd_tv = (TextView) findViewById(R.id.textView8);
		time_tv = (TextView) findViewById(R.id.textView9);
		count_tv = (TextView) findViewById(R.id.textView10);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		locationListener = new LocationListener() {
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}

			
			public void onLocationChanged(android.location.Location arg0) {
				location = arg0;
				updateUI();
			}
		};

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, locationListener);
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

		if (locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
			location = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		} else {
			location = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}

		sendGpsData();
	}

	class RefreshHandler extends Handler {

		public void handleMessage(Message msg) {
			updateUI();
		}

		public void sleep(long delayMillis) {

			this.removeMessages(0);

			sendMessageDelayed(obtainMessage(0), delayMillis);
		}
	};
	private void updateUI() {
		if (location != null) {
			lat_tv.setText(location.getLatitude() + "");
			lng_tv.setText(location.getLongitude() + "");
			spd_tv.setText(location.getSpeed() * 3.6 + "");
			time_tv.setText(new Date(location.getTime()).toString().substring(
					0, 20));
			Geocoder geo=new Geocoder(getApplicationContext(), Locale.getDefault());
			String res=null;
			try {
				List<Address> list=geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
				if (list != null && list.size() > 0) {
                    Address address = list.get(0);
                    // sending back first address line and locality
                    res = address.getAddressLine(0) + ", " + address.getLocality();
                }
				
				SQLiteDatabase db=dbs.getReadableDatabase();
				Cursor c=db.rawQuery("SELECT UserPhone FROM UserDetails", null);
				if(c.moveToFirst())
				{
					String phone=c.getString(c.getColumnIndex("UserPhone"));
					sms.sendTextMessage(phone, null, "Address:"+res, null, null);
					System.out.println("Success Database");
					Toast.makeText(getBaseContext(), "Datatbase Access"+phone, Toast.LENGTH_LONG).show();
					Intent  i1=new Intent(A5.this,A3.class);
					startActivity(i1);
				}
				Log.i("JO", res);
			}catch (Exception e) {
				e.printStackTrace();
			} 
		}
		count_tv.setText(cnt + "");
	}

	public void sendGpsData() {
		timerTask = new TimerTask() {
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						try {
							cnt++;
							updateUI();
							postRequest();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					
				});
			}

			private void postRequest() throws Exception {
				URL url = null;
				HttpURLConnection urlConnection = null;
				OutputStream os = null;
				try {
					url = new URL(
							"http://174.143.148.125:8080/mobile/FirstServ");
					urlConnection = (HttpURLConnection) url.openConnection();
					urlConnection.setDoOutput(true);
					urlConnection.setChunkedStreamingMode(0);
					urlConnection.setRequestProperty("Content-Type",
							"text/plain");

					String params = currentLocation();

					os = urlConnection.getOutputStream();
					byte postmsg[] = params.getBytes();
					for (int i = 0; i < postmsg.length; i++) {
						os.write(postmsg[i]);
					}
					os.flush();
				} finally {
					// Clean up
					if (os != null) {
						os.close();
					}
					if (urlConnection != null) {
						urlConnection.disconnect();
					}
				}
			}

			private String currentLocation() {
				String result = "";

				if (location != null) {
					result += location.getLatitude() + ",";
					result += location.getLongitude() + ",";
					result += location.getTime();
				}

				return result;
			}
			
		};

		timer.schedule(timerTask, 1 * 1000 * 60, interval * 1000 * 60);
		//timer.scheduleAtFixedRate(timerTask, delay, period)
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if(timer != null) {
			timer.cancel();
		}

		if ((locationListener != null) && (locationManager != null)) {
			locationManager.removeUpdates(locationListener);
		}
	}



}
