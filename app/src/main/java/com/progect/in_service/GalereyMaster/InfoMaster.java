package com.progect.in_service.GalereyMaster;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.progect.in_service.MessageService.APIService;
import com.progect.in_service.ProductModel;
import com.progect.in_service.R;
import com.progect.in_service.imagePartfolio.Activity;
import com.progect.in_service.ui.MainHome.HomeFragment;

public class InfoMaster extends AppCompatActivity {

    private Button button;
    private FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore fStore;
    private ImageView imageView3;
    private TextView textView1, textView2, textView3,textView4;
    private FirebaseAuth mAuth;
    private APIService apiService;

    FusedLocationProviderClient fusedLocationProvider;
    private RecyclerView mFirestoreList;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_master);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        imageView3 = (ImageView) findViewById(R.id.imageView3);
        button = findViewById(R.id.master_job);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent qwe = new Intent(InfoMaster.this, MasterImageAdaptor.class);
                startActivity(qwe);
            }
        });
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView9);
        textView4.setVisibility(View.INVISIBLE);

        fStore = FirebaseFirestore.getInstance();

        final DocumentReference documentReference = fStore.collection(user.getPhoneNumber()+"Z").document(user.getPhoneNumber()+"Z".toString());
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot
                                        documentSnapshot, @Nullable FirebaseFirestoreException e) {
                textView4.setText(documentSnapshot.getString("ID_Master"));

                textView1.setText(documentSnapshot.getString("secondName"));
                textView2.setText(documentSnapshot.getString("name"));
                textView3.setText(documentSnapshot.getString("lastName"));

                downLoadWithBytes(textView4.getText().toString());
            }
        });
    }

    public void downLoadWithBytes(String qwe) {
        final StorageReference imageRefl = storageReference.child("images/" + qwe + " ava");

        final DocumentReference documentReference = fStore.collection(qwe).document(qwe.toString());
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot
                                        documentSnapshot, @Nullable FirebaseFirestoreException e) {
                textView1.setText(documentSnapshot.getString("secondName"));
                textView2.setText(documentSnapshot.getString("name"));
                textView3.setText(documentSnapshot.getString("lastName"));
            }
        });

        final long MAXBYTES = 512 * 512 * 5;
        imageRefl.getBytes(MAXBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView3.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
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
}