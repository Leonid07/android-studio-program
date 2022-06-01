package com.progect.in_service.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.progect.in_service.R;

import java.util.ArrayList;

public class GalleryFragment_mas extends Fragment {
    RecyclerView recyclerView;

    ArrayList<DataList> arrayList;
    private GalleryViewModel_mas galleryViewModel_mas;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {galleryViewModel_mas = ViewModelProviders.of(this).get(GalleryViewModel_mas.class);
        final View v = inflater.inflate(R.layout.fragment_gallery_mas, container, false);
        recyclerView = v.findViewById(R.id.RV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        arrayList = new ArrayList<DataList>();

        DataList dataList = new DataList("О приложении");
        arrayList.add(dataList);


        DataAdapter dataAdapter = new DataAdapter(new Myclick() {
            @Override
            public void onMyClick(View view, int Possition) {

                String name = arrayList.get(Possition).getName();
                if (name == "О приложении") {
                    Intent i = new Intent(getActivity(), Oapp.class);
                    i.putExtra("name", name);
                    startActivity(i);
                }
            }
        }, getActivity().getApplicationContext(), arrayList);

        recyclerView.setAdapter(dataAdapter);
        return v;
    }
}