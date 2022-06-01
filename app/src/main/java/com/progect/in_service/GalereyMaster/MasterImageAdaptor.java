package com.progect.in_service.GalereyMaster;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.progect.in_service.MessageService.APIService;
import com.progect.in_service.R;
import com.progect.in_service.imagePartfolio.ImageAdapter;
import com.progect.in_service.imagePartfolio.Upload;

import java.util.ArrayList;
import java.util.List;

public class MasterImageAdaptor extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Upload> mUploads;
    private FirebaseAuth mAuth;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    private FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_images);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();
        mAdapter = new ImageAdapter(MasterImageAdaptor.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mStorage = FirebaseStorage.getInstance();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        firebaseFirestore.collection(user.getPhoneNumber() + "Z")
                .document(user.getPhoneNumber() + "Z").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        mDatabaseRef = FirebaseDatabase.getInstance().getReference(document.getString("ID_Master").toString() + "galerey");

                        mDatabaseRef = FirebaseDatabase.getInstance().getReference(user.getPhoneNumber().toString() + "galerey");

                        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                mUploads.clear();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    Upload upload = postSnapshot.getValue(Upload.class);
                                    upload.setKey(postSnapshot.getKey());
                                    mUploads.add(upload);
                                }
                                mAdapter.notifyDataSetChanged();
                                mProgressCircle.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(MasterImageAdaptor.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                mProgressCircle.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
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