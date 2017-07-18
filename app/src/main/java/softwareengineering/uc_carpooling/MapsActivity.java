package softwareengineering.uc_carpooling;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    private GoogleMap mMap;
    private LocationRequest locationRequest;
    private GoogleApiClient client;
    private DatabaseReference mDatabase;
    private FirebaseUser user;

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        client = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");



        user = FirebaseAuth.getInstance().getCurrentUser();



    }

    public static Intent createIntent(Context context) {
        return new Intent(context, MapsActivity.class);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        // Create the LocationRequest object
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1000);

        displayMarkers();


    }

    private void handleNewLocation(Location location) {
        Log.d("New Location", location.toString());
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        LatLng rideRequestLocation = new LatLng(latitude, longitude);

        MarkerOptions markerOp = new MarkerOptions().position(rideRequestLocation).title("Ride Request Marker").
                snippet("Date: " + RequestRide.date +  "  " +
                          "  Destination: " + RequestRide.destination);

        Marker marker = mMap.addMarker(markerOp);
        marker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(rideRequestLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        mDatabase.child(user.getUid()).child("Location").child("Latitude").setValue(markerOp.getPosition().latitude);
        mDatabase.child(user.getUid()).child("Location").child("Longitude").setValue(markerOp.getPosition().longitude);
        mDatabase.child(user.getUid()).child("RideRequest").child("Destination").setValue(markerOp.getSnippet());

    }

    private void displayMarkers() {
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

                double latitude = 0f;
                double longitude = 0f;
                String userSnippet = "";

                latitude = dataSnapshot.child("Location").child("Latitude").getValue(Double.class);
                longitude =  dataSnapshot.child("Location").child("Longitude").getValue(Double.class);
                userSnippet = dataSnapshot.child("RideRequest").child("Destination").getValue(String.class);

                LatLng newLocation = new LatLng(latitude, longitude);
                MarkerOptions newMarker = new MarkerOptions().position(newLocation).snippet(userSnippet);
                Marker marker = mMap.addMarker(newMarker);
                marker.showInfoWindow();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }





    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(client);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client,
                    locationRequest, (com.google.android.gms.location.LocationListener) this);
        }
        else {
            handleNewLocation(location);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("Connection Failed", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        client.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (client.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,
                    (com.google.android.gms.location.LocationListener) this);
            client.disconnect();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
