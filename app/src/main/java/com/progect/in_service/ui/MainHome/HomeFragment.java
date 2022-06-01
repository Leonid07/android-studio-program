package com.progect.in_service.ui.MainHome;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.progect.in_service.GalereyMaster.InfoMaster;
import com.progect.in_service.MessageService.APIService;
import com.progect.in_service.MessageService.Client;
import com.progect.in_service.MessageService.Data;
import com.progect.in_service.MessageService.MyResponse;
import com.progect.in_service.MessageService.NotificationSender;
import com.progect.in_service.MessageService.Token;
import com.progect.in_service.ProductModel;
import com.progect.in_service.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private APIService apiService;

    FusedLocationProviderClient fusedLocationProvider;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseAuth mAuth;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    private int STORAGE_PERMISSION_CODE = 1;

    StorageReference storageReference;
    FirebaseStorage firebaseStorage;

    public static class ProductsView extends RecyclerView.ViewHolder {

        private TextView txName;
        private TextView txDescription;
        private TextView txPrice;
        private TextView id;
        private Button net;
        private Button da;
        private TextView textView10;
        private Button btInfo;
        private Button btinfo2;
        private TextView tx1;
        private TextView tx2;
        private TextView tx3;
        private TextView telephone;
        private Button btMap;

        private ImageButton imageButton;

        public ProductsView(@NonNull View itemView) {
            super(itemView);
            txName = itemView.findViewById(R.id.name1);
            txDescription = itemView.findViewById(R.id.description);
            txPrice = itemView.findViewById(R.id.price);
            id = itemView.findViewById(R.id.masID);
            net = itemView.findViewById(R.id.button8);
            da = itemView.findViewById(R.id.button5);
            imageButton = itemView.findViewById(R.id.imageButton);

            tx1 = itemView.findViewById(R.id.secondname_master);
            tx2 = itemView.findViewById(R.id.name_master);
            tx3 = itemView.findViewById(R.id.tx3);
            telephone = itemView.findViewById(R.id.telephone);

            btInfo = itemView.findViewById(R.id.info2);
            btinfo2 = itemView.findViewById(R.id.info3);
            textView10 = itemView.findViewById(R.id.textView10);

            btinfo2.setVisibility(View.INVISIBLE);
            id.setVisibility(View.INVISIBLE);

            tx1.setVisibility(View.INVISIBLE);
            tx2.setVisibility(View.INVISIBLE);
            tx3.setVisibility(View.INVISIBLE);
            telephone.setVisibility(View.INVISIBLE);

            btMap = itemView.findViewById(R.id.btMap);
            btMap.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View r = inflater.inflate(R.layout.fragment_home, container, false);
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(getActivity());
        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = r.findViewById(R.id.RVtask1);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
////
//        if (ContextCompat.checkSelfPermission(getContext(),
//                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(getContext(), "You have already granted this permission!",
//                    Toast.LENGTH_SHORT).show();
//        } else {
//            requestStoragePermission();
//        }
////
        final Query[] query = {firebaseFirestore.collection(user.getPhoneNumber() + "Z")};
        FirestoreRecyclerOptions<ProductModel> options = new FirestoreRecyclerOptions.Builder<ProductModel>()
                .setQuery(query[0], ProductModel.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<ProductModel, ProductsView>(options) {
            @NonNull
            @Override
            public ProductsView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poiu, parent, false);
                return new ProductsView(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final ProductsView holder, int position, @NonNull final ProductModel model) {

                holder.txName.setText("Предложенная цена" + "\n" + model.getCondition());
                holder.txDescription.setText("Время окончания работы" + "\n" + model.getTimeCode());
                holder.id.setText(model.getID_Master());

                final StorageReference imageRefl = storageReference.child("images/" + holder.id.getText() + " ava");

                final long MAXBYTES = 512 * 512*5;
                imageRefl.getBytes(MAXBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        holder.imageButton.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                holder.imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), InfoMaster.class);
                        startActivity(intent);
                    }
                });

                firebaseFirestore.collection(model.getID_Master())
                        .document(model.getID_Master()).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot document = task.getResult();
                                holder.tx1.setText(document.getString("name"));
                                holder.tx2.setText(document.getString("secondName"));
                                holder.tx3.setText(document.getString("lastName"));
                                holder.telephone.setText(document.getString("telephone"));
                            }
                        });

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

// отказ от условий заказщика

                View.OnClickListener NetZakaz = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firebaseFirestore.collection(user.getPhoneNumber() + "Z")
                                .document(user.getPhoneNumber().toString()).delete();

                        final String[] qwe = new String[1];
                        firebaseFirestore.collection(holder.id.getText().toString().trim()).document(holder.id.getText().toString().trim()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot document = task.getResult();
                                qwe[0] = document.getString("FirebaseDatabase").trim();

                                FirebaseDatabase.getInstance().getReference()
                                        .child(holder.id.getText().toString().trim())
                                        .child(qwe[0])
                                        .child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String usertoken = dataSnapshot.getValue(String.class);
                                        sendNotifications(usertoken, "Новое уведомление".trim(), "Заказщик не одобрил условия".trim());
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        });

                    }
                };
                holder.net.setOnClickListener(NetZakaz);

// принятие условий заказщика

                View.OnClickListener DaZakaz = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.textView10.setVisibility(View.INVISIBLE);
                        holder.net.setVisibility(View.INVISIBLE);
                        holder.da.setVisibility(View.INVISIBLE);
                        holder.btInfo.setVisibility(View.VISIBLE);

                        Map<String, Object> client = new HashMap<>();
                        client.put("OtvetMas", "00000");
                        firebaseFirestore.collection(model.getID_Master() + "myZakaz")
                                .document(model.getID_Master()).update(client);
                        Toast.makeText(getActivity(), "Мастер уведомлён о вашем соглашении",
                                Toast.LENGTH_SHORT).show();
                        firebaseFirestore.collection(user.getPhoneNumber());

                        final String[] qwe = new String[1];
                        firebaseFirestore.collection(holder.id.getText().toString().trim()).document(holder.id.getText().toString().trim()).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot document = task.getResult();
                                qwe[0] = document.getString("FirebaseDatabase").trim();

                                FirebaseDatabase.getInstance().getReference()
                                        .child(holder.id.getText().toString().trim())
                                        .child(qwe[0])
                                        .child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String usertoken = dataSnapshot.getValue(String.class);
                                        sendNotifications(usertoken, "Новое уведомление".trim(), "Заказщик одобрил условия".trim());
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
                        firebaseFirestore.collection("Products").document(user.getPhoneNumber().toString()).delete();
                    }
                };
                holder.da.setOnClickListener(DaZakaz);


                firebaseFirestore.collection(model.getID_Master() + "myZakaz")
                        .document(model.getID_Master()).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot document = task.getResult();
                                if (document.getString("OtvetMas").length() == 5) {
                                    holder.textView10.setVisibility(View.INVISIBLE);
                                    holder.net.setVisibility(View.INVISIBLE);
                                    holder.da.setVisibility(View.INVISIBLE);
                                    holder.btInfo.setVisibility(View.VISIBLE);
                                }
                            }
                        });

                View.OnClickListener InfoZakaz = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.btinfo2.setVisibility(View.VISIBLE);
                        holder.btInfo.setVisibility(View.INVISIBLE);
                        holder.txName.setVisibility(View.INVISIBLE);
                        holder.txDescription.setVisibility(View.INVISIBLE);
                        holder.tx1.setVisibility(View.VISIBLE);
                        holder.tx2.setVisibility(View.VISIBLE);
                        holder.tx3.setVisibility(View.VISIBLE);
                        holder.telephone.setVisibility(View.VISIBLE);
                        holder.txName.setVisibility(View.INVISIBLE);
                        holder.txDescription.setVisibility(View.INVISIBLE);
                        holder.btMap.setVisibility(View.VISIBLE);
                    }
                };
                holder.btInfo.setOnClickListener(InfoZakaz);

                View.OnClickListener InfoZakaz2 = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.btMap.setVisibility(View.INVISIBLE);
                        holder.btinfo2.setVisibility(View.INVISIBLE);
                        holder.btInfo.setVisibility(View.VISIBLE);
                        holder.tx1.setVisibility(View.INVISIBLE);
                        holder.tx2.setVisibility(View.INVISIBLE);
                        holder.tx3.setVisibility(View.INVISIBLE);
                        holder.telephone.setVisibility(View.INVISIBLE);
                        holder.txName.setVisibility(View.VISIBLE);
                        holder.txDescription.setVisibility(View.VISIBLE);
                    }
                };
                holder.btinfo2.setOnClickListener(InfoZakaz2);


                holder.btMap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (ContextCompat.checkSelfPermission(getContext(),
//                                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                            Toast.makeText(getContext(), "You have already granted this permission!",
//                                    Toast.LENGTH_SHORT).show();
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        fusedLocationProvider.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                final Location location = task.getResult();
                                if (location != null) {
                                    try {
                                        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                        firebaseFirestore.collection(model.getID_Master())
                                                .document(model.getID_Master()).get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        DocumentSnapshot document = task.getResult();

                                                        Uri uri = Uri.parse("http://maps.google.com/maps?" + "saddr=" + location.getLatitude() + "," + location.getLongitude()
                                                                + "&daddr=" + document.getDouble("shir") + "," + document.getDouble("dolg"));
                                                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                    }
                                                });
//                                            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
//                                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            startActivity(intent);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        });
//                        } else {
//                            requestStoragePermission();
//                        }
                    }
                });
            }
        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(getContext()));
        mFirestoreList.setAdapter(adapter);
        return r;
    }

    // обновления токена для отправки сообщений заказщику
    private void UpdateToken() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference(user.getPhoneNumber()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }

    // отправка сообщений заказщику
    public void sendNotifications(String usertoken, String title, String message) {
        Data data = new Data(title, message);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(getActivity(), "Failed ", Toast.LENGTH_LONG);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}