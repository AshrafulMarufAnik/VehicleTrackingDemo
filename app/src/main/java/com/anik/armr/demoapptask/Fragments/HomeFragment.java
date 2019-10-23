package com.anik.armr.demoapptask.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anik.armr.demoapptask.Interface.RetrofitInterface;
import com.anik.armr.demoapptask.ModelClass.Vehicle;
import com.anik.armr.demoapptask.R;
import com.anik.armr.demoapptask.RetrofitAPIClient.ApiClientInstance;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private View view;
    private BottomSheetBehavior bottomSheetBehavior;
    private Button BTN1, BTN2;
    private TextView lastActiveTV, descriptionTV, speedTV, engineTV, gpsTV, gpsStatusTV, gsmTV, locationNameTV;
    private ImageView carMarker;
    private FloatingActionButton currentLocationFAB;

    private GoogleMap gMap;
    private String setLocationAddress;

    private RetrofitInterface retrofitInterface;
    private final String CONTENT_TYPE = "application/json";
    private String SESSION_ID = "73e2f077-042a-4b03-8248-d1c6b47d691e";

    private final Handler handler = new Handler();
    private Runnable runnable;
    private final int delay_time = 30000;

    private LatLng myLatLng;
    private double speed, heading, lat, lon;
    private String location_name;
    private ArrayList<Vehicle> vehicleList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_container);
        mapFragment.getMapAsync(this);

        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(getActivity());
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Please enable permissions", Toast.LENGTH_SHORT).show();
        }
        final Task<Location> myLocation = fusedLocationProviderClient.getLastLocation();
        myLocation.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    speed = location.getSpeed();
                    heading = location.getBearing();
                    location_name = getAddress(location.getLatitude(), location.getLongitude());
                    //getVehicleLocationData(myLatLng,heading,speed,location_name);
                }
            }
        });

        currentLocationFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                //getVehicleLocationData(myLatLng, heading, speed, location_name);
                handler.postDelayed(runnable, delay_time);
            }
        }, delay_time);

        super.onResume();
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Please Enable Permissions", Toast.LENGTH_SHORT).show();
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(true);
        getCurrentLocation();

        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

                return false;
            }
        });
    }

    private void getVehicleLocationData(LatLng myLatLng, double heading, double speed, String location_name) {
        retrofitInterface = new ApiClientInstance().getInstance().getApiData();

        Call<ArrayList<Vehicle>> call = retrofitInterface.getVehicleLocationData(CONTENT_TYPE, SESSION_ID, myLatLng.latitude, myLatLng.longitude, heading, speed, location_name);
        call.enqueue(new Callback<ArrayList<Vehicle>>() {
            @Override
            public void onResponse(Call<ArrayList<Vehicle>> call, Response<ArrayList<Vehicle>> response) {
                if (response.isSuccessful()) {
                    vehicleList = response.body();
                    int listSize = vehicleList.size();
                    int i = 0;
                    while (i < listSize) {
                        Vehicle currentVehicle = vehicleList.get(i);
                        //LatLng vehicleLatLng = new LatLng(Double.parseDouble(currentVehicle.getLat()), Double.parseDouble(currentVehicle.getLon()));
                        //gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vehicleLatLng, 15));
                        //gMap.addMarker(new MarkerOptions()
                                //.position(vehicleLatLng)
                                //.title(currentVehicle.getLocationName())
                                //.rotation(currentVehicle.getHeading())
                                //.snippet(currentVehicle.getDescription()));
                        //lastActiveTV.setText(currentVehicle.getLastTimestamp());
                        //descriptionTV.setText(currentVehicle.getDescription());
                        //speedTV.setText(currentVehicle.getSpeed());
                        //engineTV.setText(currentVehicle.getEngine().toString());
                        //gsmTV.setText(currentVehicle.getGsmSignal().toString());
                        //gpsStatusTV.setText(currentVehicle.getGpsSignal().toString());
                        //gpsStatusTV.setText(currentVehicle.getGpsSattelites().toString());
                        //setLocationAddress = getAddress(Double.parseDouble(currentVehicle.getLat()), Double.parseDouble(currentVehicle.getLon()));
                        //locationNameTV.setText(currentVehicle.getLocationName());

                    }
                } else {
                    Toast.makeText(getContext(), "Data parsing problem", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Vehicle>> call, Throwable t) {
                Toast.makeText(getContext(), "ERROR: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getCurrentLocation() {
        carMarker.setVisibility(View.GONE);
        gMap.clear();
        FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(getActivity());
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Task location = fusedLocationProviderClient.getLastLocation();
        if (location != null) {
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Location currentLocation = (Location) task.getResult();
                        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        gMap.addMarker(new MarkerOptions().position(latLng).title(getAddress(currentLocation.getLatitude(), currentLocation.getLongitude())).snippet("Your Location"));
                        gMap.addCircle(new CircleOptions().center(latLng));
                        setLocationAddress = getAddress(currentLocation.getLatitude(), currentLocation.getLongitude());
                    }
                }
            });
        }
    }

    public String getAddress(double lat, double lng) {
        String address = "";

        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        List<Address> addressList;

        try {
            addressList = geocoder.getFromLocation(lat, lng, 1);
            if (addressList.size() > 0) {
                address = addressList.get(0).getAddressLine(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return address;
    }

    private void init(View view) {
        currentLocationFAB = view.findViewById(R.id.currentLocationFAB);
        carMarker = view.findViewById(R.id.carMarkerIV);
        lastActiveTV = view.findViewById(R.id.vehicle_last_active);
        descriptionTV = view.findViewById(R.id.vehicle_description);
        speedTV = view.findViewById(R.id.speedTV);
        engineTV = view.findViewById(R.id.engineStatusTV);
        gpsTV = view.findViewById(R.id.gpsSatellitesTV);
        gpsStatusTV = view.findViewById(R.id.gpsPositioningTV);
        gsmTV = view.findViewById(R.id.GsmSignalTV);

        vehicleList = new ArrayList<>();
    }
}
