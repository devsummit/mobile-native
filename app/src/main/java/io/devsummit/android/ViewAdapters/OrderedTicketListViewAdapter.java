package io.devsummit.android.ViewAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.devsummit.android.Helpers.OrderStatusHelper;
import io.devsummit.android.Models.orderedtickets.Datum;
import io.devsummit.android.R;

/**
 * Created by wlisrausr on 10/2/17.
 */

public class OrderedTicketListViewAdapter extends RecyclerView.Adapter<OrderedTicketListViewAdapter.OrderedTicketListViewHolder> {
    private List<Datum> mDataset;
    private String orderStatus;

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrderedTicketListViewAdapter(List<Datum> myDataset) {
        mDataset = myDataset;
    }

    public String toRupiahFormat(Number nominal) {
        NumberFormat format = NumberFormat.getInstance();

        return "Rp " + format.format(nominal);
    }

    public String dateFormat(String inputDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date formattedDate = null;

        try {
            formattedDate = format.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("MMM dd, yyyy (hh:mm)");
        String outputDate = format.format(formattedDate);

        return outputDate;
    }

    @Override
    public OrderedTicketListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // TODO: inflate your view and create viewholder, most likely looks like this though
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.ordered_ticket_row,
                parent,
                false);

        OrderedTicketListViewHolder vh = new OrderedTicketListViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(OrderedTicketListViewHolder holder, int position) {
        orderStatus = OrderStatusHelper.convertOrderStatus(mDataset.get(position).getPayment());

        if (!orderStatus.equals("Not paid")) {
            holder.orderAction.setVisibility(LinearLayout.GONE);
        }

        // TODO: all your bind operations
        holder.orderId.setText(mDataset.get(position).getId().toString());
        holder.orderDate.setText(dateFormat(mDataset.get(position).getCreatedAt().toString()));
        holder.orderAmount.setText(toRupiahFormat(mDataset.get(position).getAmount()));
        holder.orderStatus.setText(orderStatus);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class OrderedTicketListViewHolder extends RecyclerView.ViewHolder {
        // TODO: whatever views you need to bind
        public TextView orderId;
        public TextView orderDate;
        public TextView orderAmount;
        public TextView orderStatus;
        public LinearLayout orderAction;

        public OrderedTicketListViewHolder(View itemView) {
            super(itemView);
            orderId = (TextView) itemView.findViewById(R.id.orderId);
            orderDate = (TextView) itemView.findViewById(R.id.orderDate);
            orderAmount = (TextView) itemView.findViewById(R.id.orderAmount);
            orderStatus = (TextView) itemView.findViewById(R.id.orderStatus);
            orderAction = (LinearLayout) itemView.findViewById(R.id.orderAction);
        }
    }
}
