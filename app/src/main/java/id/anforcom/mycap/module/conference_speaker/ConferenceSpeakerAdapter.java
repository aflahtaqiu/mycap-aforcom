package id.anforcom.mycap.module.conference_speaker;

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
import id.anforcom.mycap.model.User;

/**
 * Created by aflah on 13/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public class ConferenceSpeakerAdapter extends RecyclerView.Adapter<ConferenceSpeakerAdapter.ConferenceSpeakerViewHolder> {
    private final Context context;
    private List<User> items;

    ConferenceSpeakerAdapter(List<User> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ConferenceSpeakerViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_conference_listener, parent, false);
        return new ConferenceSpeakerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ConferenceSpeakerViewHolder holder, int position) {
        User item = items.get(position);

        holder.tvNamaListener.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    class ConferenceSpeakerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_nama_listener)
        TextView tvNamaListener;

        ConferenceSpeakerViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}   