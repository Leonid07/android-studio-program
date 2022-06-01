package com.progect.in_service.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel_mas extends ViewModel {

    private MutableLiveData<String> mText;

    public GalleryViewModel_mas() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}