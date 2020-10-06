package com.example.pet;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

public class ChatAdapter extends FirebaseRecyclerAdapter<MyMessage1, ChatHolder>
{


    public ChatAdapter(Class<MyMessage1> modelClass, int modelLayout, Class<ChatHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(ChatHolder chatHolder, MyMessage1 myMessage1, int i) {
        chatHolder.Bind(myMessage1);
    }


}

