package io.devsummit.android.ViewAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.devsummit.android.R;
import io.devsummit.android.Models.notification.Datum;

/**
 * Created by ganesh on 26/09/17.
 */

public class NotificationViewAdapter extends RecyclerView.Adapter<NotificationViewAdapter.NotificationViewHolder> {
    private List<Datum> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public NotificationViewAdapter(List<Datum> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // TODO: inflate your view and create viewholder, most likely looks like this though
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.notification_row,
                viewGroup,
                false);

        NotificationViewHolder vh = new NotificationViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder viewHolder, int i) {
        // TODO: all your bind operations
        viewHolder.messageTextView.setText(mDataset.get(i).getMessage());
        viewHolder.titleTextView.setText(mDataset.get(i).getType());
        viewHolder.createdTextView.setText(mDataset.get(i).getCreatedAt());
        viewHolder.senderTextView.setText(mDataset.get(i).getSender().getFirstName());
    }

    @Override
    public int getItemCount() {
        // TODO: return total item count of your views
        return mDataset.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        // TODO: whatever views you need to bind
        public TextView messageTextView;
        public TextView titleTextView;
        public TextView createdTextView;
        public TextView senderTextView;

        public NotificationViewHolder(View v) {
            super(v); // done this way instead of view tagging
            messageTextView = (TextView) v.findViewById(R.id.message_text);
            titleTextView = (TextView) v.findViewById(R.id.title_text);
            createdTextView = (TextView) v.findViewById(R.id.created_at_text);
            senderTextView = (TextView) v.findViewById(R.id.sender_text);
        }
    }
}

