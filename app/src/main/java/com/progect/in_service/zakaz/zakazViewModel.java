package com.progect.in_service.zakaz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class zakazViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public zakazViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}
