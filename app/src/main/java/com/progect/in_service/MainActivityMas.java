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
import android.widget.ImageView;
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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.progect.in_service.redactorProfil.RedactorProfil;
import com.progect.in_service.redactorProfil.RedactorProfilMas;

import java.util.HashMap;
import java.util.Map;

public class MainActivityMas extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private Button btMasterM;

    private Button btTVM;
    private Button btVKM;
    private Button btInstM;

    private FirebaseAuth mAuth;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;
    private FirestoreRecyclerAdapter adapter;
    private ImageButton image;

    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore fStore;
    private TextView txName;
    private TextView txNameMid;
    private TextView txNameSec;

    private FirebaseFunctions mFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mas);
        Toolbar toolbar = findViewById(R.id.toolbar_mas);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout_mas);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        NavigationView navigationView = findViewById(R.id.nav_view_mas);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_mas, R.id.nav_gallery_mas, R.id.nav_slideshow_mas,
                R.id.nav_tools_mas, R.id.nav_oplata_mas)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_mas);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        btMasterM = (Button) findViewById(R.id.bt_master_mas);
        btTVM = (Button) findViewById(R.id.bt_TV_mas);
        btVKM = (Button) findViewById(R.id.bt_VK_mas);
        btInstM = (Button) findViewById(R.id.bt_Inst_mas);

        View.OnClickListener tbMasterOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityMas.this, MainActivity.class);
                startActivity(intent);
            }
        };
        btMasterM.setOnClickListener(tbMasterOnClick);
        View.OnClickListener tbTVOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/login"));
                startActivity(intent);
            }
        };
        btTVM.setOnClickListener(tbTVOnClick);
        View.OnClickListener tbVKOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com"));
                startActivity(intent);
            }
        };
        btVKM.setOnClickListener(tbVKOnClick);
        View.OnClickListener tbInstOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/"));
                startActivity(intent);
            }
        };
        btInstM.setOnClickListener(tbInstOnClick);
        image = (ImageButton) findViewById(R.id.imageButton);
        txName = (TextView) findViewById(R.id.textNameMas);
        txNameMid = (TextView) findViewById(R.id.textLastMas);
        txNameSec = (TextView) findViewById(R.id.textSecondMas);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityMas.this, RedactorProfilMas.class);
                startActivity(intent);
            }
        });

        final DocumentReference documentReference = fStore.collection(user.getPhoneNumber()).document(user.getPhoneNumber());
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                txName.setText(documentSnapshot.getString("name"));
                txNameMid.setText(documentSnapshot.getString("lastName"));
                txNameSec.setText(documentSnapshot.getString("secondName"));
            }
        });
        downLoadWithBytes();

        mFunctions = FirebaseFunctions.getInstance();
        ////////////////////////
        TextView txt = findViewById(R.id.textView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                txt.append(key + ": " + value + "\n\n");
            }
        }
        ////////////////////////
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_mas, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_mas);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }
}