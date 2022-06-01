package com.progect.in_service.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.progect.in_service.R;

public class SlideshowFragment_mas extends Fragment {

    private ZakazViewModel_mas slideshowViewModel_mas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel_mas =
                ViewModelProviders.of(this).get(ZakazViewModel_mas.class);
        View root = inflater.inflate(R.layout.fragment_slideshow_mas, container, false);
        return root;
    }
}