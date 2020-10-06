package com.example.pet;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatHolder extends RecyclerView.ViewHolder
{
    View view;
    TextView textView;
    public ChatHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        textView = view.findViewById(R.id.textView_text);
    }

    public void Bind(MyMessage1 myMessage)
    {
        textView.setText(myMessage.getText());
    }
}
