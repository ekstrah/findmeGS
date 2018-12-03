package com.example.kimtaeheon.p2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

public class OneFragment extends Fragment implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {


    private MapView mapView = null;
    private LocationManager locationManager;
    double latitude = 0.0;
    double longitude = 0.0;
    CommunicationManager communicationManager;

    public OneFragment() {


    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        communicationManager=CommunicationManager.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_one, container, false);
        startLocationService();
        mapView = (MapView)layout.findViewById(R.id.map);
        mapView.getMapAsync(this);

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //액티비티가 처음 생성될 때 실행되는 함수

        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng Current = new LatLng(latitude, longitude);

        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(Current);

        markerOptions.title("현재위치");

        markerOptions.snippet("GPS");

        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(Current));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
    private void startLocationService()
    {
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            else
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,1, locationListener);
            }
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,1, locationListener);
        }
    }
    private void stopLocationService()
    {
        locationManager.removeUpdates(locationListener);
    }
    private final LocationListener locationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            stopLocationService();
        }
        public void onProviderDisabled(String provider)
        {

        }
        public void onProviderEnabled(String provider)
        {

        }
        public void onStatusChanged(String provider, int status,Bundle extras)
        {

        }
    };
    public void setMarkers(ArrayList<Marking> markings,GoogleMap googleMap)
    {
        for(int i=0;i<markings.size();i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(markings.get(i).latitude,markings.get(i).longitude));
            markerOptions.title(markings.get(i).name);
            googleMap.addMarker(markerOptions);
        }
        googleMap.setOnMarkerClickListener(this);
    }
    @Override
    public boolean onMarkerClick(Marker marker)
    {
        //communicationManager.OccurMarkingTouch(maker);
        return true;
    }
}

class Marking{
    static enum Type {STORE, PRODOUCT};

    String name;
    double latitude;
    double longitude;
    Type type;
}

