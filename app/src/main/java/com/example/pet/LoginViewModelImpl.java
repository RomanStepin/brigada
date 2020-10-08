package com.example.pet;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModelImpl extends LoginViewModel
{

    MutableLiveData<Exception> loginError = new MutableLiveData<>();
    MutableLiveData<Boolean> loginConfirmed = new MutableLiveData<>();

    @Override
    public void loginUser(String email, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                                if (auth.getCurrentUser().isEmailVerified())
                                    loginConfirmed.postValue(true);
                                else
                                    loginConfirmed.postValue(false);
                        }
                    })
                    .addOnFailureListener(e -> {
                        loginError.postValue(e);
                    });

    }

    @Override
    public void checkCurrentUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null)
        {
            if (user.isEmailVerified())
                loginConfirmed.postValue(true);
        }
    }

    @Override
    public void emailVerified() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification();
        }
    }

    @Override
    public LiveData<Exception> loginError() {
        return loginError;
    }

    @Override
    public LiveData<Boolean> loginConfirmed() {
        return loginConfirmed;
    }
}
