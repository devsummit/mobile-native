package io.devsummit.devsummit.ViewAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.devsummit.devsummit.Models.userticket.Datum;
import io.devsummit.devsummit.R;

/**
 * Created by ganesh on 26/09/17.
 */

public class TicketListViewAdapter extends RecyclerView.Adapter<TicketListViewAdapter.TicketListViewHolder> {
    private List<Datum> mDataset;

    @Override
    public TicketListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // TODO: inflate your view and create viewholder, most likely looks like this though
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.user_ticket_row,
                viewGroup,
                false);

        TicketListViewHolder vh = new TicketListViewHolder(v);
        return vh;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TicketListViewAdapter(List<Datum> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public void onBindViewHolder(TicketListViewHolder viewHolder, int i) {
        // TODO: all your bind operations
        viewHolder.ticketCodeView.setText(mDataset.get(i).getTicketCode().toString());
    }

    @Override
    public int getItemCount() {
        // TODO: return total item count of your views
        return mDataset.size();
    }

    public static class TicketListViewHolder extends RecyclerView.ViewHolder {
        // TODO: whatever views you need to bind
        public TextView ticketCodeView;
        public TicketListViewHolder(View v) {
            super(v); // done this way instead of view tagging
            ticketCodeView = (TextView) v.findViewById(R.id.ticket_code_text);
        }
    }
}

