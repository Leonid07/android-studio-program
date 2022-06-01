package com.progect.in_service.regestrationsNewMan.reg;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.progect.in_service.LoginAndReg;
import com.progect.in_service.MainActivityMas;
import com.progect.in_service.regestrationsNewMan.RegestrationsMaster;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NextReg extends DialogFragment {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    FusedLocationProviderClient fusedLocationProvider;
    private FirebaseFirestore firebaseFirestore;
    FirebaseStorage storage;
    StorageReference storageReference;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = "Если вы нажмёте да, то будет сохронено текущее место положение";
        String button1String = "да";
        String button2String = "нет";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message); // сообщение
        builder.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();
                myRef = FirebaseDatabase.getInstance().getReference();
                firebaseFirestore = FirebaseFirestore.getInstance();
                fusedLocationProvider = LocationServices.getFusedLocationProviderClient(getActivity());
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    Map<String, Object> client = new HashMap<>();
                    client.put("mas", "yes");
                    firebaseFirestore.collection(user.getPhoneNumber()).document(user.getPhoneNumber()).update(client);
                    fusedLocationProvider.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            final Location location = task.getResult();
                            if (location != null) {
                                try {
                                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                                    Map<String, Object> client = new HashMap<>();
                                    client.put("shir", location.getLatitude());
                                    client.put("dolg", location.getLongitude());
                                    firebaseFirestore.collection(user.getPhoneNumber()).document(user.getPhoneNumber()).update(client);

                                    Intent intent = new Intent(getActivity(), MainActivityMas.class);
                                    startActivity(intent);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
        builder.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setCancelable(true);

        return builder.create();
    }
}
