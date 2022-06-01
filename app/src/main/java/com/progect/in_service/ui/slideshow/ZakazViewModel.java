package com.progect.in_service.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ZakazViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ZakazViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}