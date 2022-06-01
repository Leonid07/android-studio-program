package com.progect.in_service.ui.tools;

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

public class ToolsFragment_mas extends Fragment {

    private ToolsViewModel_mas toolsViewModel_mas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel_mas =
                ViewModelProviders.of(this).get(ToolsViewModel_mas.class);
        View root = inflater.inflate(R.layout.fragment_tools_mas, container, false);
        return root;
    }
}