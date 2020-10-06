package com.example.pet;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.List;

public class ChatViewModelImpl extends ChatViewModel
{
    CommunicationService communicationService = ((App)getApplication()).getCommunicationService();

    public ChatViewModelImpl(@NonNull Application application) {
        super(application);
    }

}
