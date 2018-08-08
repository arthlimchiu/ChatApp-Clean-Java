package com.imakeanapp.chatapp.messages.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imakeanapp.chatapp.R;
import com.imakeanapp.domain.messages.model.Message;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {
    private static final int SENT = 0;
    private static final int RECEIVED = 1;

    private String username;
    private List<Message> chats;

    public MessagesAdapter(String username, List<Message> chats) {
        this.username = username;
        this.chats = chats;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_message_sent,
                    parent,
                    false
            );
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_message_received,
                    parent,
                    false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.bind(chats.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (chats.get(position).getSender().contentEquals(username)) {
            return SENT;
        } else {
            return RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public void updateData(List<Message> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView chatMessage;
        TextView chatSender;

        public MessageViewHolder(View itemView) {
            super(itemView);
            chatMessage = itemView.findViewById(R.id.chat_message);
            chatSender = itemView.findViewById(R.id.chat_sender);
        }

        public void bind(Message chat) {
            chatMessage.setText(chat.getMessage());

            if (!username.contentEquals(chat.getSender())) {
                chatSender.setText(chat.getSender());
            }
        }
    }
}
