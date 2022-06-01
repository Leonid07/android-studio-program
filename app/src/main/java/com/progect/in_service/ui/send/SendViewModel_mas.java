package com.progect.in_service.ui.send;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SendViewModel_mas extends ViewModel {

    private MutableLiveData<String> mText;

    public SendViewModel_mas() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}