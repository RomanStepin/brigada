package com.example.pet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.paging.PositionalDataSource;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.recycler_chat)
    RecyclerView recycler_chat;

    ChatViewModel chatViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        recycler_chat.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase db1 = FirebaseDatabase.getInstance();
        DatabaseReference ref1 = db1.getReference("messages"); // Key
        ChatAdapter chatAdapter = new ChatAdapter(MyMessage1.class, R.layout.message_layout, ChatHolder.class, ref1);

        recycler_chat.setAdapter(chatAdapter);



    }

    public static void startInNewTask(Context context)
    {
        Intent starterIntent = new Intent(context, ChatActivity.class);
        starterIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starterIntent);
    }
}



