package com.progect.in_service.Fragment_Bar_Main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Fragment_Bar_Main_ViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public Fragment_Bar_Main_ViewModel() {
        mText = new MutableLiveData<>();
    }
}
