package com.example.pet;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.DiffUtil;

public class MessagesDiffUtilCallback extends DiffUtil.ItemCallback<MyMessage>
{

    @Override
    public boolean areItemsTheSame(@NonNull MyMessage oldItem, @NonNull MyMessage newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull MyMessage oldItem, @NonNull MyMessage newItem) {
        return (oldItem.author.equals(newItem.author) && oldItem.text.equals(newItem.text) && oldItem.time == newItem.time);
    }
}
