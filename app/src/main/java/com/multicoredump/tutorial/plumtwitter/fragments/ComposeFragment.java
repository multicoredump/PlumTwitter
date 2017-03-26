package com.multicoredump.tutorial.plumtwitter.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.multicoredump.tutorial.plumtwitter.R;
import com.multicoredump.tutorial.plumtwitter.application.PlumTwitterApplication;
import com.multicoredump.tutorial.plumtwitter.model.User;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ComposeFragment extends DialogFragment {

    private static final String ARG_USER = "user";
    private static final String ARG_REPLY_TO_USER = "replyToUser";

    private static final int MAX_ALLOWED_CHAR_COUNT = 140;

    private User user;
    private User replyToUser;

    private OnPostTweetListener mListener;

    @BindView(R.id.etBody)
    EditText etBody;

    @BindView(R.id.btTweet)
    Button btTweet;

    @BindView(R.id.tvCharCount)
    TextView tvCharCount;

    @BindView(R.id.ivProfile)
    ImageView ivProfile;

    @BindView(R.id.ibCancel)
    ImageButton ibCancel;

    public interface OnPostTweetListener {
        JsonHttpResponseHandler getJsonHttpResponseHandler();
    }

    public ComposeFragment() {
        // Required empty public constructor
    }

    public static ComposeFragment newInstance(User user, User replyToUser) {
        ComposeFragment fragment = new ComposeFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, Parcels.wrap(user));
        args.putParcelable(ARG_REPLY_TO_USER, Parcels.wrap(replyToUser));
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // save current user
        if (getArguments() != null) {
            user = Parcels.unwrap(getArguments().getParcelable(ARG_USER));
            replyToUser = Parcels.unwrap(getArguments().getParcelable(ARG_REPLY_TO_USER));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.rounded_corner_dialog);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compose, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // load profile image
        Glide.with(getActivity())
                .load(user.getProfileBiggerImageURL())
                .into(ivProfile);

        ibCancel.setColorFilter(getResources().getColor(R.color.colorPrimary));

        // add user screenname for reply 
        if (replyToUser != null) {
            etBody.setText(String.format("@%s ", replyToUser.getScreenName()));
            etBody.setSelection(etBody.getText().toString().length());
        }

        // Update char count
        etBody.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int remaining = MAX_ALLOWED_CHAR_COUNT - charSequence.length();

                if (remaining < 0 ) {
                    // show char count in red
                    tvCharCount.setTextColor(Color.RED);
                    // disable tweet button
                    btTweet.setEnabled(false);
                } else {
                    tvCharCount.setTextColor(Color.DKGRAY);
                    btTweet.setEnabled(true);
                }

                tvCharCount.setText(String.valueOf(remaining));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // post tweet
        btTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etBody.getText().toString();

                if (!message.isEmpty()) {
                    PlumTwitterApplication.getTwitterClient().postTweet(message, mListener.getJsonHttpResponseHandler());
                }
                dismiss();
            }
        });

        // cancel dialog
        ibCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Todo save draft
                dismiss();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPostTweetListener) {
            mListener = (OnPostTweetListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
