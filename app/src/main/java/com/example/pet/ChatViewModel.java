package com.example.pet;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public abstract class ChatViewModel extends AndroidViewModel
{
    public ChatViewModel(@NonNull Application application) {
        super(application);
    }

}

