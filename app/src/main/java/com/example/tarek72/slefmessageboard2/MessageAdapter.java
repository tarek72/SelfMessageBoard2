package com.example.tarek72.slefmessageboard2;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    final List<Message> data;
    private final OnClickListener listener;

    public MessageAdapter(List<Message> input, OnClickListener listener) {
        this.data = input;
        this.listener = listener;
    }

    public void addMessage(Message msg) {
        data.add(msg);
        notifyItemInserted(data.size() - 1);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout, parent, false);
        MessageViewHolder holder = new MessageViewHolder(view);
        holder.setOnClickListener(listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message msg = data.get(position);
        holder.timestamp.setText(DateUtils.getRelativeTimeSpanString(holder.itemView.getContext(), msg.getTimestamp()));
        holder.content.setText(msg.getContent());
        holder.name.setText(msg.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeItem(Message msg) {
        for(int i = 0, size = data.size(); i < size; i++) {
            if (msg.equals(data.get(i))) {
                data.remove(i);
                notifyItemRemoved(i);
                return;
            }
        }
        throw new IllegalArgumentException("item is not in dataset");
    }


    class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView content;
        TextView timestamp;
        OnClickListener listener;

        MessageViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            content = itemView.findViewById(R.id.textViewMessageContent);
            timestamp = itemView.findViewById(R.id.textViewTimeStamp);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        listener.onClick(data.get(getAdapterPosition()));
                        return true;
                    }
                    return false;
                }
            });
        }

        void setOnClickListener(OnClickListener onClickListener) {
            this.listener = onClickListener;
        }
    }

    interface OnClickListener {
        void onClick(Message message);
    }
}

