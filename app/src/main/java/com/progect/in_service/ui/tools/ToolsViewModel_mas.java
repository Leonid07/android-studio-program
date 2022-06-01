package com.progect.in_service.ui.tools;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ToolsViewModel_mas extends ViewModel {

    private MutableLiveData<String> mText;

    public ToolsViewModel_mas() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}