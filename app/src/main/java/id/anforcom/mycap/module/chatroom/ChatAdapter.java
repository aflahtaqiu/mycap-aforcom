package id.anforcom.mycap.module.chatroom;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.anforcom.mycap.R;
import id.anforcom.mycap.model.Chat;

/**
 * Created by aflah on 13/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatAdapterViewHolder> {
    private final Context context;
    private List<Chat> items;

    ChatAdapter(List<Chat> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ChatAdapterViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ChatAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ChatAdapterViewHolder holder, int position) {
        Chat item = items.get(position);

        holder.tvNamaUser.setText(item.getUsername());
        holder.tvMessageBody.setText(item.getMessage());
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    class ChatAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_message_name)
        TextView tvNamaUser;

        @BindView(R.id.text_message_body)
        TextView tvMessageBody;

        ChatAdapterViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}   