package com.progect.in_service.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.progect.in_service.R;
import com.progect.in_service.ui.gallery.DialogFragment.SignOut;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();
    ArrayList<DataList> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = v.findViewById(R.id.RV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        arrayList = new ArrayList<DataList>();

        DataList dataList2 = new DataList(user.getPhoneNumber().toString());
        arrayList.add(dataList2);
        DataList dataList = new DataList("О приложении");
        arrayList.add(dataList);
        DataList dataList1 = new DataList("Выйти из аккаунта");
        arrayList.add(dataList1);

        DataAdapter dataAdapter = new DataAdapter(new Myclick() {
            @Override
            public void onMyClick(View view, int Possition) {

                String name2 = arrayList.get(Possition).getName();
                if (name2 == user.getPhoneNumber().toString()) {
                }

                String name = arrayList.get(Possition).getName();
                if (name == "О приложении") {
                    Intent i = new Intent(getActivity(), Oapp.class);
                    i.putExtra("name", name);
                    startActivity(i);
                }
                String name1 = arrayList.get(Possition).getName();
                if (name1 == "Выйти из аккаунта") {
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    SignOut myDialogFragment = new SignOut();
                    myDialogFragment.show(manager, "myDialog");
                }
            }
        }, getActivity().getApplicationContext(), arrayList);

        recyclerView.setAdapter(dataAdapter);

        return v;
    }
}