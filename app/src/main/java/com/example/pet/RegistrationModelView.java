package com.example.pet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public abstract class RegistrationModelView extends ViewModel
{
    public abstract void registrationNewUser(String email, String password);

    public abstract LiveData<Exception> registrationError();
    public abstract LiveData<Boolean> registrationSuccessful();
}
