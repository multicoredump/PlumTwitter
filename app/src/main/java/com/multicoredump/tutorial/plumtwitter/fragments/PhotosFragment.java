package com.multicoredump.tutorial.plumtwitter.fragments;


import android.support.v4.app.Fragment;

public class PhotosFragment extends Fragment {

//    private FragmentPhotosBinding binding;
//    private ProfilePhotosAdapter mAdapter;
//    private User user;
//    private TwitterClient client;
//    private ArrayList<Tweet> mTweetList;
//    private EndlessRecyclerViewScrollListener scrollListener;
//
//    public PhotosFragment() {}
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_photos, container, false);
//
//        mTweetList = new ArrayList<>();
//
//        //Fetch user infor from bundle and fetch tweets using screenname
//        Bundle args = getArguments();
//        user = args.getParcelable("user");
//        client = TwitterApplication.getRestClient();
//
//        //Setup grid manager
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
//        binding.rvPhotos.setLayoutManager(gridLayoutManager);
//        mAdapter = new ProfilePhotosAdapter(mTweetList, getActivity());
//        binding.rvPhotos.setAdapter(mAdapter);
//        binding.rvPhotos.setItemAnimator(new DefaultItemAnimator());
//
//        //Endless pagination
//        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                //Handle fetching in a thread with delay to avoid error "API Limit reached" = 429
//                Handler handler = new Handler();
//                handler.postDelayed(() -> fetchPhotos(false, getMaxId()), 1000);
//            }
//        };
//        binding.rvPhotos.addOnScrollListener(scrollListener);
//
//        //Swipe to refresh
//        binding.swipeContainer.setOnRefreshListener(() -> {
//            //Check internet
//            if (!NetworkUtility.isOnline()) {
//                //Snackbar.make(getView(), R.string.connection_error, Snackbar.LENGTH_LONG).show();
//                binding.swipeContainer.setRefreshing(false);
//                return;
//            }
//            //Fetch first page
//            fetchPhotos(true, 0);
//        });
//
//        fetchPhotos(true, 0);
//
//        //Click on photos to open a new Screen
//        return binding.getRoot();
//    }
//
//    private long getMaxId() {
//        return mTweetList.get(mTweetList.size() - 1).getUid();
//    }
//
//    private void fetchPhotos(boolean fRequest, long maxId) {
//        binding.progressBar.setVisibility(View.VISIBLE);
//
//        client.getUsersTimeline(fRequest, maxId - 1, user.getScreenName(), new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                if (fRequest)
//                    mTweetList.clear();
//
//                ArrayList<Tweet> newTweet = new ArrayList<>();
//                Gson gson = new Gson();
//                for(int i = 0; i < response.length(); i++) {
//                    try {
//                        Tweet tweet = gson.fromJson(response.getJSONObject(i).toString(),Tweet.class);
//                        newTweet.add(tweet);
//                    } catch (JSONException e) {
//                    }
//                }
//
//                mTweetList.addAll(newTweet);
//                mAdapter.notifyDataSetChanged();
//                binding.swipeContainer.setRefreshing(false);
//                binding.progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
//                //Snackbar.make(binding.cLayout, R.string.error_fetch, Snackbar.LENGTH_LONG).show();
//                binding.swipeContainer.setRefreshing(false);
//                binding.progressBar.setVisibility(View.GONE);
//            }
//        });
//    }
}
