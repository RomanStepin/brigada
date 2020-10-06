package com.example.pet;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationModelViewImpl extends RegistrationModelView
{
    MutableLiveData<Exception> registrationError = new MutableLiveData<>();
    MutableLiveData<Boolean> registrationSuccessful = new MutableLiveData<>();

    @Override
    public void registrationNewUser(String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnFailureListener(e -> {
                    registrationError.postValue(e);
                })
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            user.sendEmailVerification().addOnCompleteListener(task1 -> {
                                registrationSuccessful.postValue(true);
                            });
                        }
                    }
                });
    }

    @Override
    public MutableLiveData<Exception> registrationError() {
        return registrationError;
    }

    @Override
    public MutableLiveData<Boolean> registrationSuccessful() {
        return registrationSuccessful;
    }
}
