package com.example.knight.wloc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener {
    LocationManager locationmanager;
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public final static String EXTRA_MESSAGE2 = "com.example.myfirstapp.MESSAGE2";
    private double latttitude;
    private double longitude;
    private String one;
    private  String two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int i=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        while(i!=2)
        {   locationmanager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Criteria cri=new Criteria();
            String provider=locationmanager.getBestProvider(cri,false);
            if(provider!=null & !provider.equals(""))
            {
                Location location=locationmanager.getLastKnownLocation(provider);
                locationmanager.requestLocationUpdates(provider,30,1,this);
                if(location!=null)
                {
                    onLocationChanged(location);
                }
                else{
                    Toast.makeText(getApplicationContext(),"location not found",Toast.LENGTH_LONG ).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Provider is null", Toast.LENGTH_LONG).show();
            }
            i++;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latttitude=location.getLatitude();
        longitude=location.getLongitude();
    }

    public void locfind(View view)
    {
        Intent intent = new Intent(this, MapsActivity.class);
         one=Double.toString(latttitude);
         two=Double.toString(longitude);
        Toast.makeText(getApplicationContext(),"latti: "+ one+" longi:"+two,Toast.LENGTH_LONG).show();
        intent.putExtra(EXTRA_MESSAGE, one);
        intent.putExtra(EXTRA_MESSAGE2, two);
        startActivity(intent);

    }
    public void sendsms(View view)
    {

        SmsManager smsManager = SmsManager.getDefault();
        EditText editText = (EditText)findViewById(R.id.editText);
        String phoneNo= editText.getText().toString();
        phoneNo=phoneNo.trim();
        smsManager.sendTextMessage(phoneNo, null, "lattitude:"+one+" longitude"+two, null, null);
    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
