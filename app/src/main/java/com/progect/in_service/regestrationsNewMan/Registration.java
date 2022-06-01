package com.progect.in_service.regestrationsNewMan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.progect.in_service.MainActivity;
import com.progect.in_service.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private AutoCompleteTextView txName;
    private AutoCompleteTextView txSecondName;
    private AutoCompleteTextView txLastName;
    private Button NexReg;
    private Button mButton;
    private ImageView avaterkaImage;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();
    private Button Btn_new_task;

    private FirebaseFirestore firebaseFirestore;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filePath;
    private StorageReference ref;
    private final int PICK_IMAGE_REQUEST = 71;

    static final int GALLERY_REQUEST = 1;
    public String urlImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_regestrationnewman);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        myRef = FirebaseDatabase.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        txName = (AutoCompleteTextView) findViewById(R.id.nameMan1);
        txSecondName = (AutoCompleteTextView) findViewById(R.id.secondName1);
        txLastName = (AutoCompleteTextView) findViewById(R.id.lastName1);
        NexReg = (Button) findViewById(R.id.NexReg);
        mButton = (Button) findViewById(R.id.changeImage);
        avaterkaImage = (ImageView) findViewById(R.id.imageView3);

//        firebaseFirestore.collection(user.getPhoneNumber()).document(user.getPhoneNumber()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                DocumentSnapshot document = task.getResult();
//                if (document.getString("secondName").length() >= 1) {
//                    Intent intent = new Intent(Registration.this, MainActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });

        View.OnClickListener tbNewTaskOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txName.getText().length() >= 1 && txLastName.getText().length() >= 1 && txSecondName.getText().length() >= 1) {

                    Map<String, Object> client = new HashMap<>();
                    client.put("secondName", txSecondName.getText().toString());
                    client.put("name", txName.getText().toString());
                    client.put("lastName", txLastName.getText().toString());
                    client.put("telephone", user.getPhoneNumber());
                    client.put("dolg", "000".toString());
                    client.put("mas", "no".toString());
                    client.put("FirebaseDatabase", FirebaseAuth.getInstance().getCurrentUser().getUid().trim());
                    firebaseFirestore.collection(user.getPhoneNumber()).document(user.getPhoneNumber()).set(client);

                    uploadImage();
                    Intent intent = new Intent(Registration.this, MainActivity.class);
                    startActivity(intent);
                }
                if (txName.getText().length() == 0) {
                    Toast.makeText(Registration.this, "Укажите имя", Toast.LENGTH_SHORT).show();
                }
                if (txLastName.getText().length() == 0) {
                    Toast.makeText(Registration.this, "Укажите отчество", Toast.LENGTH_SHORT).show();
                }
                if (txSecondName.getText().length() == 0) {
                    Toast.makeText(Registration.this, "Укажите фамилию", Toast.LENGTH_SHORT).show();
                }
                if (txName.getText().length() == 0 && txLastName.getText().length() == 0 && txSecondName.getText().length() == 0) {
                    Toast.makeText(Registration.this, "Заполните графы фамилия, имя, отчество", Toast.LENGTH_SHORT).show();
                }
            }
        };
        NexReg.setOnClickListener(tbNewTaskOnClick);

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        System.exit(0);
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
                avaterkaImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                            Toast.makeText(Registration.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Registration.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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