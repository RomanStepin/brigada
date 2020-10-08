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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.recycler_chat)
    RecyclerView recycler_chat;
    @BindView(R.id.button_send_message)
    Button button_send_message;
    @BindView(R.id.materialTextView_message)
    EditText materialTextView_message;

    ChatViewModel chatViewModel;

    FirebaseDatabase db1;
    DatabaseReference ref1;

    Boolean need_scroll = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        button_send_message.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recycler_chat.setLayoutManager(linearLayoutManager);

        db1 = FirebaseDatabase.getInstance();
        ref1 = db1.getReference("messages"); // Key
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                need_scroll = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ChatAdapter chatAdapter = new ChatAdapter(MyMessage1.class, R.layout.message_layout, ChatHolder.class, ref1);

        recycler_chat.setAdapter(chatAdapter);


        recycler_chat.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                    if (need_scroll) {
                        recycler_chat.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                        need_scroll = false;
                    }
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {
                if (need_scroll) {
                    recycler_chat.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                    need_scroll = false;
                }
            }
        });



    }

    public static void startInNewTask(Context context)
    {
        Intent starterIntent = new Intent(context, ChatActivity.class);
        starterIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starterIntent);
    }

    @Override
    public void onClick(View v) {
        if (!materialTextView_message.getText().toString().equals("")) {
            Calendar rightNow = Calendar.getInstance();
            long sinceMidnight = (rightNow.getTimeInMillis()) % (24 * 60 * 60 * 1000);
            SharedPreferences sharedPreferences = ((App)getApplication()).getPrefs();
            Integer avatar = sharedPreferences.getInt("avatar", 1);
            String name = sharedPreferences.getString("name", "loh");
            MyMessage1 myMessage1 = new MyMessage1(materialTextView_message.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), name, sinceMidnight, avatar);
            ref1.push().setValue(myMessage1);
            materialTextView_message.setText("");

        }
    }
}



