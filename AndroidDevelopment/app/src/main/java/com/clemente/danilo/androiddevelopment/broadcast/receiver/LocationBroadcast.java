package com.clemente.danilo.androiddevelopment.broadcast.receiver;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.clemente.danilo.androiddevelopment.MapsActivity;
import com.clemente.danilo.androiddevelopment.activity.R;
import com.clemente.danilo.androiddevelopment.model.PlaceMarket;
import com.clemente.danilo.androiddevelopment.service.RetrofitMaps;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HP on 27/08/2017.
 */

public class LocationBroadcast extends BroadcastReceiver {
    private static final int PROXIMITY_RADIUS = 50;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    private Location mLastLocation;
    Marker mCurrLocationMarker;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (intent.getAction().matches(LocationManager.KEY_LOCATION_CHANGED)) {
            LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            long minTime;     //This should probably be fairly long
            float minDistance; //This should probably be fairly big
            String provider;   //GPS_PROVIDER or NETWORK_PROVIDER
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, new com.google.android.gms.location.LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        mLastLocation = location;
                        if (mCurrLocationMarker != null) {
                            mCurrLocationMarker.remove();
                        }

                        updateMarkets(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                    }
                });
            }
        }

    }

    private void updateMarkets(String latitude, String longitude) {
        String url = "https://maps.googleapis.com/maps/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitMaps service = retrofit.create(RetrofitMaps.class);

        Call<PlaceMarket> call = service.getNearbyPlaces("store", latitude + "," + longitude, PROXIMITY_RADIUS);
        call.enqueue(new Callback<PlaceMarket>() {

            @Override
            public void onResponse(Call<PlaceMarket> call, Response<PlaceMarket> response) {
                if (response.body().getResults().size() > 0) {
                    Intent intent = new Intent(context, MapsActivity.class);
                    PendingIntent pending = PendingIntent.getActivity(context, 0, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setContentTitle(context.getString(R.string.nearby_market)).addAction(R.drawable.splash, "", pending).setContentIntent(pending);
                    NotificationManager notificationmanager = (NotificationManager) context
                            .getSystemService(Context.NOTIFICATION_SERVICE);
                    // Build Notification with Notification Manager
                    notificationmanager.notify(0, builder.build());
                }
            }

            @Override
            public void onFailure(Call<PlaceMarket> call, Throwable throwable) {

            }
        });
    }


}
