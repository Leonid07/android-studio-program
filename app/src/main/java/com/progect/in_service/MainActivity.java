package com.progect.in_service;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.progect.in_service.MessageService.APIService;
import com.progect.in_service.MessageService.Token;
import com.progect.in_service.redactorProfil.RedactorProfil;
import com.progect.in_service.regestrationsNewMan.RegestrationsMaster;

public class MainActivity extends AppCompatActivity {
    private APIService apiService;
    private AppBarConfiguration mAppBarConfiguration;

    private Button btMaster;

    private Button btTV;
    private Button btVK;
    private Button btInst;
    private ImageButton imageButton;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;
    private FirestoreRecyclerAdapter adapter;

    private FirebaseAuth mAuth;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore fStore;
    private TextView txName;
    private TextView txNameMid;
    private TextView txNameSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        setSupportActionBar(toolbar);
        firebaseFirestore = FirebaseFirestore.getInstance();
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_contactus)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        btMaster = (Button) findViewById(R.id.bt_master);
        btTV = (Button) findViewById(R.id.bt_TV);
        btVK = (Button) findViewById(R.id.bt_VK);
        btInst = (Button) findViewById(R.id.bt_Inst);

        firebaseFirestore.collection(user.getPhoneNumber()).document(user.getPhoneNumber()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.getString("mas").length() == 2) {
                    btMaster.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, RegestrationsMaster.class);
                            startActivity(intent);
                        }
                    });
                }
                if (document.getString("mas").length() == 3) {
                    btMaster.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, MainActivityMas.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

        View.OnClickListener tbTVOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/login"));
                startActivity(intent);
            }
        };
        btTV.setOnClickListener(tbTVOnClick);
        View.OnClickListener tbVKOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com"));
                startActivity(intent);
            }
        };
        btVK.setOnClickListener(tbVKOnClick);
        final View.OnClickListener tbInstOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/"));
                startActivity(intent);
            }
        };
        btInst.setOnClickListener(tbInstOnClick);

        imageButton = findViewById(R.id.imageButton);
        txName = (TextView) findViewById(R.id.textName);
        txNameMid = (TextView) findViewById(R.id.textLast);
        txNameSec = (TextView) findViewById(R.id.textSecond);
        fStore = FirebaseFirestore.getInstance();

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

        downLoadWithBytes();

        UpdateToken();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RedactorProfil.class);
                startActivity(intent);
            }
        });
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

    private void UpdateToken() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference(user.getPhoneNumber()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }

    public void downLoadWithBytes() {
        final StorageReference imageRefl = storageReference.child("images/" + user.getPhoneNumber() + " ava");

        final long MAXBYTES = 128 * 128 * 5;
        imageRefl.getBytes(MAXBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageButton.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}