package com.progect.in_service.redactorProfil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.progect.in_service.MainActivity;
import com.progect.in_service.MainActivityMas;
import com.progect.in_service.R;
import com.progect.in_service.imagePartfolio.Activity;
import com.progect.in_service.regestrationsNewMan.RegestrationsMaster;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RedactorProfilMas extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView txName;
    private TextView txNameMid;
    private TextView txNameSec;
    private ImageView image;
    private Button mButton, portfolio, newLock;
    private Button NexReg;
    int PLACE_PICKER_REQUEST = 1;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    private Uri filePath;
    private StorageReference ref;
    private final int PICK_IMAGE_REQUEST = 71;

    static final int GALLERY_REQUEST = 1;
    public String urlImage;

    private FirebaseFirestore firebaseFirestore;
    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseFirestore fStore;
    FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_redactor_man_mas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        myRef = FirebaseDatabase.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        uploadImage();

        firebaseFirestore = FirebaseFirestore.getInstance();

        portfolio = (Button) findViewById(R.id.portfolio);
        txName = (TextView) findViewById(R.id.nameMan1);
        txNameMid = (TextView) findViewById(R.id.lastName1);
        txNameSec = (TextView) findViewById(R.id.secondName1);
        image = (ImageView) findViewById(R.id.imageView3);
        mButton = (Button) findViewById(R.id.changeImage);
        NexReg = (Button) findViewById(R.id.NexReg);
        newLock = (Button) findViewById(R.id.newLock);
        fStore = FirebaseFirestore.getInstance();

        portfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RedactorProfilMas.this, Activity.class);
                startActivity(intent);
            }
        });
        newLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(RedactorProfilMas.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        final DocumentReference documentReference = fStore.collection(user.getPhoneNumber()).document(user.getPhoneNumber());
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot
                                        documentSnapshot, @Nullable FirebaseFirestoreException e) {
                txName.setText(documentSnapshot.getString("name"));
                txNameMid.setText(documentSnapshot.getString("lastName"));
                txNameSec.setText(documentSnapshot.getString("secondName"));
            }
        });

        View.OnClickListener tbNewTaskOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> client = new HashMap<>();
                client.put("secondName", txNameSec.getText().toString());
                client.put("name", txName.getText().toString());
                client.put("lastName", txNameMid.getText().toString());
                firebaseFirestore.collection(user.getPhoneNumber()).document(user.getPhoneNumber()).update(client);
                uploadImage();
                Intent intent = new Intent(RedactorProfilMas.this, MainActivityMas.class);
                startActivity(intent);
            }
        };
        NexReg.setOnClickListener(tbNewTaskOnClick);

        downLoadWithBytes();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void downLoadWithBytes() {
        final StorageReference imageRefl = storageReference.child("images/" + user.getPhoneNumber() + " ava");

        final long MAXBYTES = 512 * 512 * 5;
        imageRefl.getBytes(MAXBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                image.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.typeWork, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                firebaseFirestore.collection(user.getPhoneNumber()).document(user.getPhoneNumber()).update(client);

                Toast.makeText(RedactorProfilMas.this, "Местоположение сохранено",
                        Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(MapsActivity.this, RegestrationsMaster.class);
//                startActivity(intent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadImage() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            ref = storageReference.child("images/" + user.getPhoneNumber() + " ava");

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(RedactorProfilMas.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(RedactorProfilMas.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
            urlImage = ref.toString();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
