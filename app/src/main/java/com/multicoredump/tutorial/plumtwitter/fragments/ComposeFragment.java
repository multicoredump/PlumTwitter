package com.multicoredump.tutorial.plumtwitter.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.multicoredump.tutorial.plumtwitter.R;
import com.multicoredump.tutorial.plumtwitter.application.PlumTwitterApplication;
import com.multicoredump.tutorial.plumtwitter.model.Tweet;
import com.multicoredump.tutorial.plumtwitter.model.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;


public class ComposeFragment extends DialogFragment {

    private static final String ARG_USER = "user";

    private User user;

    private OnPostTweetListener mListener;

    @BindView(R.id.etBody)
    EditText etBody;

    @BindView(R.id.btTweet)
    Button btTweet;

    @BindView(R.id.tvCharCount)
    TextView tvCharCount;

    @BindView(R.id.ivProfile)
    ImageView ivProfile;

    public interface OnPostTweetListener {
        void onSuccess(Tweet tweet);
    }

    public ComposeFragment() {
        // Required empty public constructor
    }

    public static ComposeFragment newInstance(User user) {
        ComposeFragment fragment = new ComposeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, Parcels.wrap(user));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = Parcels.unwrap(getArguments().getParcelable(ARG_USER));
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

//        Glide.with(getActivity())
//                .load(user.getProfileImageURL())
//                .into(ivProfile);

        btTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etBody.getText().toString();

                if (!message.isEmpty()) {

                    PlumTwitterApplication.getTwitterClient().postTweet(message, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            List<Tweet> tweets = Tweet.fromJSONArray(response);
                            mListener.onSuccess(tweets.get(0));
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            mListener.onSuccess(Tweet.fromJson(response));
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {

                        }
                    });
                }
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
