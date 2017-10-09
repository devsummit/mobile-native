package io.devsummit.android.ViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.devsummit.android.Helpers.TextHelper;
import io.devsummit.android.Models.userfeed.FeedData;
import io.devsummit.android.R;

/**
 * Created by wlisrausr on 04/10/17.
 */

public class FeedListViewAdapter extends RecyclerView.Adapter<FeedListViewAdapter.FeedListViewHolder> {
    private List<FeedData> mDataset;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public FeedListViewAdapter(Context context, List<FeedData> myDataset) {
        mDataset = myDataset;
        this.context = context;
    }

    @Override
    public FeedListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // TODO: inflate your view and create viewholder, most likely looks like this though
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.user_feed_row,
                viewGroup,
                false);

        FeedListViewHolder vh = new FeedListViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final FeedListViewHolder viewHolder, int i) {
        // TODO: all your bind operations
        viewHolder.firstName.setText(TextHelper.capitalizeFirstLetter(
                mDataset.get(i).getUser().getFirstName().toString()
        ));
        viewHolder.lastName.setText(TextHelper.capitalizeFirstLetter(
                mDataset.get(i).getUser().getLastName().toString()
        ));
        viewHolder.createdAt.setText(TextHelper.dateFormat(mDataset.get(i).getCreatedAt().toString()));
        viewHolder.feedMessage.setText(mDataset.get(i).getMessage().toString());

        Object attachmentUrl = mDataset.get(i).getAttachment();
        List imgProfileUrl = mDataset.get(i).getUser().getPhotos();

        viewHolder.feedAttachment.setVisibility(View.GONE);

        if (attachmentUrl != null) {
            viewHolder.feedAttachment.setVisibility(View.VISIBLE);

            Picasso.with(context)
                    .load(mDataset.get(i).getAttachment().toString())
                    .into(viewHolder.feedAttachment);
        }

        if (imgProfileUrl.size() > 0) {
            Picasso.with(context)
                    .load(mDataset.get(i).getUser().getPhotos().get(0).getUrl().toString())
                    .placeholder(R.drawable.empty_profile_grey)
                    .into(viewHolder.imageProfile);
        }
    }

    @Override
    public int getItemCount() {
        // TODO: return total item count of your views
        return mDataset.size();
    }

    public static class FeedListViewHolder extends RecyclerView.ViewHolder {
        // TODO: whatever views you need to bind
        public TextView feedMessage;
        public TextView firstName;
        public TextView lastName;
        public TextView createdAt;
        public ImageView feedAttachment;
        public CircleImageView imageProfile;

        public FeedListViewHolder(View v) {
            super(v); // done this way instead of view tagging

            feedMessage = (TextView) v.findViewById(R.id.feedMessage);
            firstName = (TextView) v.findViewById(R.id.firstNameText);
            lastName = (TextView) v.findViewById(R.id.lastNameText);
            createdAt = (TextView) v.findViewById(R.id.createdAtText);
            feedAttachment = (ImageView) v.findViewById(R.id.feedAttachment);
            imageProfile = (CircleImageView) v.findViewById(R.id.imageProfile);
        }
    }
}
