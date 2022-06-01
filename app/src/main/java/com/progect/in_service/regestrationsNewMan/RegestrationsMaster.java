package com.progect.in_service.regestrationsNewMan;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.progect.in_service.MainActivityMas;
import com.progect.in_service.R;
import com.progect.in_service.imagePartfolio.Activity;
import com.progect.in_service.regestrationsNewMan.reg.NextReg;

import java.util.HashMap;
import java.util.Map;

public class RegestrationsMaster extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private Button NexReg, Resume, button9;
    private Spinner spinner;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();
    int PLACE_PICKER_REQUEST = 1;
    private Button Btn_new_task;
    FusedLocationProviderClient fusedLocationProvider;
    private FirebaseFirestore firebaseFirestore;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filePath;
    private StorageReference ref;
    private final int PICK_IMAGE_REQUEST = 71;
    private EditText textView16;
    static final int GALLERY_REQUEST = 1;
    public String urlImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_regestrationsmanmas);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        myRef = FirebaseDatabase.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(RegestrationsMaster.this);
        spinner = (Spinner) findViewById(R.id.spinner3);
        NexReg = (Button) findViewById(R.id.NexReg55);
        Resume = (Button) findViewById(R.id.Resume);
        textView16 = (EditText) findViewById(R.id.textView16);

        button9 = (Button) findViewById(R.id.button9);

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(RegestrationsMaster.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        Resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent qwe = new Intent(RegestrationsMaster.this, Activity.class);
                startActivity(qwe);
            }
        });

//        Map<String, Object> client = new HashMap<>();
//        client.put("adres", textView16.getText().toString());
//        firebaseFirestore.collection(user.getPhoneNumber()).document(user.getPhoneNumber()).update(client);

        firebaseFirestore.collection(user.getPhoneNumber())
                .document(user.getPhoneNumber()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        if (document.getString("dolg").length() >= 3) {
                            NexReg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (document.getString("mas").length() == 3) {
                                        Intent intent = new Intent(RegestrationsMaster.this, MainActivityMas.class);
                                        startActivity(intent);
                                    }
                                    if (document.getString("mas").length() == 2) {
                                        FragmentManager manager = getSupportFragmentManager();
                                        NextReg myDialogFragment = new NextReg();
                                        myDialogFragment.show(manager, "myDialog");
                                    }
//                                    if (ActivityCompat.checkSelfPermission(RegestrationsMaster.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                                        if (ActivityCompat.checkSelfPermission(RegestrationsMaster.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                                                ActivityCompat.checkSelfPermission(RegestrationsMaster.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                                            // TODO: Consider calling
//                                            //    ActivityCompat#requestPermissions
//                                            // here to request the missing permissions, and then overriding
//                                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                            //                                          int[] grantResults)
//                                            // to handle the case where the user grants the permission. See the documentation
//                                            // for ActivityCompat#requestPermissions for more details.
//                                            return;
//                                        }
//                                        Map<String, Object> client = new HashMap<>();
//                                        client.put("mas", "yes");
//                                        firebaseFirestore.collection(user.getPhoneNumber()).document(user.getPhoneNumber()).update(client);
//                                        fusedLocationProvider.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Location> task) {
//                                                final Location location = task.getResult();
//                                                if (location != null) {
//                                                    try {
//                                                        Geocoder geocoder = new Geocoder(RegestrationsMaster.this, Locale.getDefault());
//                                                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                                    Map<String, Object> client = new HashMap<>();
//                                                        client.put("shir", location.getLatitude());
//                                                        client.put("dolg", location.getLongitude());
                                    client.put("profession", spinner.getSelectedItem().toString());
                                    client.put("adress", textView16.getText().toString());
                                    firebaseFirestore.collection(user.getPhoneNumber()).document(user.getPhoneNumber()).update(client);

//                                                        Intent intent = new Intent(RegestrationsMaster.this, MainActivityMas.class);
//                                                        startActivity(intent);

//                                                    } catch (IOException e) {
//                                                        e.printStackTrace();
//                                                    }
//                                                }
//                                            }
//                                        });
//                                    } else {
//                                        ActivityCompat.requestPermissions(RegestrationsMaster.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
//                                    }
                                }
                            });
                        } else {
                            NexReg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Map<String, Object> client = new HashMap<>();
                                    client.put("profession", spinner.getSelectedItem().toString());
                                    firebaseFirestore.collection(user.getPhoneNumber()).document(user.getPhoneNumber()).update(client);
                                    Intent intent = new Intent(RegestrationsMaster.this, MainActivityMas.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });
        Spinner spinner = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.typeWork, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        myRef = FirebaseDatabase.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK /*&& data.getData() != null*/) {
                Place place = PlacePicker.getPlace(data, this);
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);

                Map<String, Object> client = new HashMap<>();
                client.put("shir", String.valueOf(place.getLatLng().latitude));
                client.put("dolg", String.valueOf(place.getLatLng().longitude));
                client.put("mas", "yes");
                firebaseFirestore.collection(user.getPhoneNumber()).document(user.getPhoneNumber()).update(client);

                Toast.makeText(RegestrationsMaster.this, "Местоположение сохранено",
                        Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(MapsActivity.this, RegestrationsMaster.class);
//                startActivity(intent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}