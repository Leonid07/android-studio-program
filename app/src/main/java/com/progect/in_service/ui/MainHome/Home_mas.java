package com.progect.in_service.ui.MainHome;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.iid.FirebaseInstanceId;
import com.progect.in_service.MainActivity;
import com.progect.in_service.MessageService.APIService;
import com.progect.in_service.MessageService.Client;
import com.progect.in_service.MessageService.Data;
import com.progect.in_service.MessageService.MyResponse;
import com.progect.in_service.MessageService.NotificationSender;
import com.progect.in_service.MessageService.Token;
import com.progect.in_service.ProductModel;
import com.progect.in_service.R;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home_mas extends Fragment {

    private APIService apiService;

    private TextView tx1;
    private TextView tx2;
    private TextView tx3;
    private Button tb1;
    private Button tb2;
    private ImageView im1;
    private EditText et1;
    private FrameLayout fl;

    private TextView EdText;
    private TextView HourText;

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;
    private FirestoreRecyclerAdapter adapter;

    private FirebaseAuth mAuth;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    public static class ProductsViewHolder extends RecyclerView.ViewHolder {

        private TextView txName;
        private TextView id;
        private TextView txDescription;
        private TextView txPrice;
        private Button btZak;

        private AutoCompleteTextView autoCompleteTextView;

        private TextView tt;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            txName = itemView.findViewById(R.id.name1);
            txDescription = itemView.findViewById(R.id.description);
            txPrice = itemView.findViewById(R.id.price);
            id = itemView.findViewById(R.id.masID);
            id.setVisibility(View.INVISIBLE);
            btZak = itemView.findViewById(R.id.button5);
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
        View v = inflater.inflate(R.layout.fragment_home_mas, container, false);
        final ToggleButton toggleButtonn = (ToggleButton) v.findViewById(R.id.toggleButton3);
        tx1 = v.findViewById(R.id.textView6);
        tx2 = v.findViewById(R.id.textView7);
        tx3 = v.findViewById(R.id.textView8);
        tb1 = v.findViewById(R.id.button);
        tb2 = v.findViewById(R.id.button6);
        im1 = v.findViewById(R.id.imageView2);
        et1 = v.findViewById(R.id.editText);
        fl = v.findViewById(R.id.frameLayout2);

        EdText = v.findViewById(R.id.editText);
        HourText = v.findViewById(R.id.editText2);

        fl.setVisibility(View.INVISIBLE);

        final View.OnClickListener btZakaz = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toggleButtonn.setVisibility(View.VISIBLE);
                fl.setVisibility(View.INVISIBLE);

            }
        };
        tb2.setOnClickListener(btZakaz);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = v.findViewById(R.id.RVtask2);

        final Query query = firebaseFirestore.collection("Products");
        FirestoreRecyclerOptions<ProductModel> options = new FirestoreRecyclerOptions.Builder<ProductModel>()
                .setQuery(query, ProductModel.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<ProductModel, Home_mas.ProductsViewHolder>(options) {
            @NonNull
            @Override
            public Home_mas.ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poi, parent, false);
                return new ProductsViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull final Home_mas.ProductsViewHolder holder, int position, @NonNull final ProductModel model) {
                holder.txName.setText("Название проблемы" + "\n" + model.getName());
                holder.txDescription.setText("Описание проблемы" + "\n" + model.getDescription());
                holder.txPrice.setText("Цена заказчика" + "\n" + model.getPrice());
                holder.id.setText(model.getID());

                View.OnClickListener btZakaz = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        toggleButtonn.setVisibility(View.INVISIBLE);
                        fl.setVisibility(View.VISIBLE);
                        et1.setText(model.getPrice());
                        tx1.setText(holder.txName.getText());
                        tx2.setText(holder.txDescription.getText());
                        tx3.setText("Цена заказчика" + "\n" + model.getPrice());

                    }
                };
                // отправка сообщений по токину
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

                holder.btZak.setOnClickListener(btZakaz);

                View.OnClickListener tb11 = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> client = new HashMap<>();

                        if (EdText.getText().length() >= 1 && HourText.getText().length() >= 1) {
                            client.put("condition", EdText.getText().toString());
                            client.put("timeCode", HourText.getText().toString());
                            client.put("ID_Master", user.getPhoneNumber().toString());

                            fl.setVisibility(View.INVISIBLE);
                            toggleButtonn.setVisibility(View.VISIBLE);

                            firebaseFirestore.collection("Products")
                                    .document(holder.id.getText().toString()).update(client);
                            firebaseFirestore.collection(holder.id.getText().toString() + "Z")
                                    .document(holder.id.getText().toString() + "Z").set(client);

                            Map<String, Object> client1 = new HashMap<>();
                            client1.put("name", holder.txName.getText().toString());
                            client1.put("description", holder.txDescription.getText().toString());
                            client1.put("OtvetMas", "");

                            firebaseFirestore.collection(user.getPhoneNumber() + "myZakaz")
                                    .document(user.getPhoneNumber()).set(client1);
                            Toast.makeText(getActivity(), "Условия отправлены заказчику",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (EdText.getText().length() == 0)
                            Toast.makeText(getActivity(), "Укажите свою цену", Toast.LENGTH_SHORT).show();
                        if (HourText.getText().length() == 0)
                            Toast.makeText(getActivity(), "Укажите время работы", Toast.LENGTH_SHORT).show();
                        // отправка сообщений по токену заказщику
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
                                        sendNotifications(usertoken, "Новые уведомление".trim(), "Перейдите в мои заказы".trim());
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
                        // окончание функции по отправки сообщения
                    }
                };

                tb1.setOnClickListener(tb11);
                UpdateToken();
                toggleButtonn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            holder.btZak.setClickable(true);
                        } else {
                            holder.btZak.setClickable(false);
                        }
                    }
                });
            }
        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(getContext()));
        mFirestoreList.setAdapter(adapter);
        return v;
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
}