package com.progect.in_service.Fragment_Bar_Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.progect.in_service.R;

public class Fragment_Bar_Main extends Fragment {
    private ToggleButton toggleButtonn;
    private Fragment_Bar_Main_ViewModel Fragment_Bar_Main_ViewModel;

    private TextView tx;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Fragment_Bar_Main_ViewModel = ViewModelProviders.of(this).get(Fragment_Bar_Main_ViewModel.class);
        View v = inflater.inflate(R.layout.fragment_slideshow, container, false);
        return v;

    }
}
