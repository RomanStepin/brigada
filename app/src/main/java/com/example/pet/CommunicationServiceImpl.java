package com.example.pet;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommunicationServiceImpl implements CommunicationService
{
    Retrofit retrofit;
    ServerAPI serverAPI;

    public CommunicationServiceImpl() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://bragada.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serverAPI = retrofit.create(ServerAPI.class);
    }

}
