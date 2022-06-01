package com.progect.in_service.ui.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.progect.in_service.R;

public class SendFragment_mas extends Fragment {

    private SendViewModel_mas sendViewModel_mas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel_mas =
                ViewModelProviders.of(this).get(SendViewModel_mas.class);
        View root = inflater.inflate(R.layout.fragment_send_mas, container, false);
        return root;
    }
}