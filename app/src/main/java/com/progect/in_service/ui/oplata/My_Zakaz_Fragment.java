package com.progect.in_service.ui.oplata;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.progect.in_service.ProductModel;
import com.progect.in_service.R;

public class My_Zakaz_Fragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;
    private FirestoreRecyclerAdapter adapter;

    private FirebaseAuth mAuth;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    public static class ProductsView extends RecyclerView.ViewHolder {

        private TextView txName;
        private TextView txDescription;
        private TextView id;
        private TextView txPOPO;
        private Button bt;

        public ProductsView(@NonNull View itemView) {
            super(itemView);
            txName = itemView.findViewById(R.id.name1);
            txDescription = itemView.findViewById(R.id.description);
            id = itemView.findViewById(R.id.masID);
            bt = itemView.findViewById(R.id.button7);
            id.setVisibility(View.INVISIBLE);
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
        View r = inflater.inflate(R.layout.fragment_my_zakaz, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = r.findViewById(R.id.RVtask2);

        final Query query = firebaseFirestore.collection(user.getPhoneNumber() + "myZakaz");
        FirestoreRecyclerOptions<ProductModel> options = new FirestoreRecyclerOptions.Builder<ProductModel>()
                .setQuery(query, ProductModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<ProductModel, My_Zakaz_Fragment.ProductsView>(options) {
            @NonNull
            @Override
            public My_Zakaz_Fragment.ProductsView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poiuyt, parent, false);
                return new My_Zakaz_Fragment.ProductsView(view);
            }


            @Override
            protected void onBindViewHolder(@NonNull final My_Zakaz_Fragment.ProductsView holder, int position, @NonNull final ProductModel model) {
                holder.txDescription.setText(model.getDescription());
                holder.txName.setText(model.getName());

                firebaseFirestore.collection(user.getPhoneNumber() + "myZakaz")
                        .document(user.getPhoneNumber()).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot document = task.getResult();
                                if (document.getString("OtvetMas").length() == 5) {
                                    System.out.println(document.getString("OtvetMas"));
                                    holder.bt.setText("Одобренно");
                                    holder.bt.setBackgroundResource(R.drawable.sw_thumb);
                                }
                            }
                        });
            }
        };
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(getContext()));
        mFirestoreList.setAdapter(adapter);

        return r;
    }
}