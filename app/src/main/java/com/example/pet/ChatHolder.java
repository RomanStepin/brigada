package com.example.pet;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

public class ChatHolder extends RecyclerView.ViewHolder
{
    View view;
    TextView textView_text;
    TextView textView_time;
    TextView textView_name;
    ImageView imageView_avatar;

    public ChatHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        textView_text = view.findViewById(R.id.textView_text);
        textView_time = view.findViewById(R.id.textView_time);
        textView_name = view.findViewById(R.id.textView_name);
        imageView_avatar = view.findViewById(R.id.imageView_avatar);
    }

    public void Bind(MyMessage1 myMessage)
    {
        textView_text.setText(myMessage.getText());
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(myMessage.getTime());
        textView_time.setText(formattedDate);
        textView_name.setText(myMessage.getName());
        imageView_avatar.setImageResource(myMessage.getAvatar());
    }
}
