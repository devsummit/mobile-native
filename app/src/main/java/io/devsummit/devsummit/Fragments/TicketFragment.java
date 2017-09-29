package io.devsummit.devsummit.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.devsummit.devsummit.R;

/**
 * Created by ganesh on 27/09/17.
 */

public class TicketFragment extends Fragment {
    private static final String ARG_TEXT = "arg_text";
    private static final String ARG_COLOR = "arg_color";

    private View mContent;

    public static Fragment newInstance() {
        Fragment frag = new TicketFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ticket_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        // initialize views
        mContent = view.findViewById(R.id.ticket_content);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }
}
