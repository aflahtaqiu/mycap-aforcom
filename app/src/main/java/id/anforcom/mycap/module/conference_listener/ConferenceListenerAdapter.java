package id.anforcom.mycap.module.conference_listener;

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


public class ConferenceListenerAdapter extends RecyclerView.Adapter<ConferenceListenerAdapter.COnferenceListenerViewHolder> {
    private final Context context;
    private List<Chat> items;

    ConferenceListenerAdapter(List<Chat> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public COnferenceListenerViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_messages_recieve, parent, false);
        return new COnferenceListenerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(COnferenceListenerViewHolder holder, int position) {
        Chat item = items.get(position);
        holder.tvNamaUser.setVisibility(View.GONE);
        holder.tvMessageBody.setText(item.getMessage());
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    class COnferenceListenerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_message_name)
        TextView tvNamaUser;

        @BindView(R.id.text_message_body)
        TextView tvMessageBody;

        public COnferenceListenerViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}   