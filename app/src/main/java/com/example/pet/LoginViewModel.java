package com.example.pet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public abstract class LoginViewModel extends ViewModel
{
    public abstract void loginUser(String email, String password);
    public abstract void checkCurrentUser();
    public abstract void emailVerified();

    public abstract LiveData<Exception> loginError();
    public abstract LiveData<Boolean> loginConfirmed();
}
