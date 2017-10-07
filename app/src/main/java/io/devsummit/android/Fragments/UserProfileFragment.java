package io.devsummit.android.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.devsummit.android.Activities.LoginActivity;
import io.devsummit.android.Helpers.UserAuthenticationHelper;
import io.devsummit.android.Models.login.ProfileData;
import io.devsummit.android.R;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by ganesh on 27/09/17.
 */

public class UserProfileFragment extends Fragment {
    private static final String ARG_TEXT = "arg_text";
    private static final String ARG_COLOR = "arg_color";

    private View mContent;
    private TextView mNameText;
    private TextView mRoleText;
    private CircleImageView profileImage;
    private Button mLogoutButton;
    private UserAuthenticationHelper authHelper;

    public static Fragment newInstance() {
        Fragment frag = new UserProfileFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        authHelper = new UserAuthenticationHelper(getContext());
        return inflater.inflate(R.layout.user_profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get a Realm instance for this thread

        // initialize views
        mContent = view.findViewById(R.id.user_profile_content);
        mNameText = view.findViewById(R.id.profile_name);
        mRoleText = view.findViewById(R.id.profile_role);
        profileImage = view.findViewById(R.id.profile_image);
        mLogoutButton = view.findViewById(R.id.btn_logout);

    }

    @Override
    public void onStart() {
        super.onStart();
        final Realm realm = Realm.getDefaultInstance();
        final ProfileData currentProfile =  realm.where(ProfileData.class).findFirst();

        HashMap<Integer, String> role = new HashMap<>();
        role.put(2, "Attendee");
        role.put(3, "Booth");
        role.put(4, "Speaker");
        role.put(5, "Hackaton");
        role.put(6, "Ambassador");
        role.put(7, "User");
        role.put(8, "Partner");

        if(currentProfile!=null  && currentProfile.getFirstName() != null) {
            mNameText.setText(currentProfile.getFirstName() + " " + currentProfile.getLastName());
            mRoleText.setText(role.get(currentProfile.getRoleId()));
            Picasso.with(getContext())
                    .load(currentProfile.getPhotos().get(0).getUrl())
                    .placeholder(R.drawable.empty_profile_grey)
                    .into(profileImage);
        }
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authHelper.removeAccessToken();
                authHelper.removeProfileData();
                Intent i = new Intent(getContext(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }
}
