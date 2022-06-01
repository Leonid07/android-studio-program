package com.progect.in_service;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainFragment extends AppCompatActivity {
    public FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore firebaseFirestore;

    private FirebaseAuth mAuth;

    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        fStore = FirebaseFirestore.getInstance();
        final DocumentReference documentReference = fStore.collection(user.getPhoneNumber()).document(user.getPhoneNumber().toString());
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot
                                        documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(user.getPhoneNumber() == documentSnapshot.getString("telephone")) {
                    Intent qwe = new Intent(MainFragment.this,MainActivity.class);
                    startActivity(qwe);
                }
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainFragment.this, LoginAndReg.class);
                startActivity(i);
                finish();
            }
        }, 5 * 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}