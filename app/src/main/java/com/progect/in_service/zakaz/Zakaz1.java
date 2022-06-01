package com.progect.in_service.zakaz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.progect.in_service.MainActivityMas;
import com.progect.in_service.R;

public class Zakaz1 extends Fragment {

    private Button next;
    private Button back;
    private TextView tx;
    private TextView tx1;
    private TextView tx2;

    FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();
    private FirebaseFirestore firebaseFirestore;

    private zakazViewModel ZakazViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ZakazViewModel = ViewModelProviders.of(this).get(zakazViewModel.class);
        View v = inflater.inflate(R.layout.zakaz_1, container, false);

        next = (Button) v.findViewById(R.id.back);
        back = (Button) v.findViewById(R.id.next);
        tx = (TextView) v.findViewById(R.id.textView);
        tx1 = (TextView) v.findViewById(R.id.textView3);
        tx2 = (TextView) v.findViewById(R.id.textView4);

        fStore = FirebaseFirestore.getInstance();
        final DocumentReference documentReference = fStore.collection(user.getPhoneNumber()).document(user.getPhoneNumber());
        documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                tx.setText(documentSnapshot.getString("name"));
                tx1.setText(documentSnapshot.getString("lastName"));
                tx2.setText(documentSnapshot.getString("secondName"));
            }
        });

        View.OnClickListener btNext = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivityMas.class);
                startActivity(intent);
            }
        };
        next.setOnClickListener(btNext);

        View.OnClickListener btBack = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivityMas.class);
                startActivity(intent);
            }
        };
        back.setOnClickListener(btBack);

        return v;
    }
}
