package com.example.pet;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public abstract class LoginViewModel extends AndroidViewModel
{
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

}


