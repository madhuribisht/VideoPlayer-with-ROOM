package com.example.videoapp.view.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.videoapp.R;
import com.example.videoapp.adapter.VideoListAdapter;
import com.example.videoapp.dto.ResVideoDataList;
import com.example.videoapp.entity.VideoDataList;
import com.example.videoapp.network.RetrofitClient;
import com.example.videoapp.roomDB.VideoDatabase;
import com.example.videoapp.utils.CommonUtil;
import com.example.videoapp.utils.NetworkUtils;
import com.example.videoapp.view.ui.fragmentLoader.LoadingViewFragment;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.videoapp.utils.CommonUtil.isNullOrEmpty;

public class VideoListFragment extends LoadingViewFragment implements View.OnClickListener {
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView videoListRV;
    private Handler handler;
    private VideoListAdapter videoListAdapter;
    private ArrayList<VideoDataList> list = new ArrayList<>();
    private VideoDatabase videoDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return getLoaderView(getContext(), inflater.inflate(R.layout
                .frag_video_list, container, false));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            layoutID(view);
            setAdapter();
            if (!NetworkUtils.isNetworkAvailable(getActivity())) {
                try {
                    showError(getString(R.string.err_msg_no_internet_connect));
                } catch (Exception e) {
                    return;
                }
                return;
            }
            setHasOptionsMenu(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void layoutID(View view) throws Exception {
        try {
            videoDatabase = VideoDatabase.getInstance(getActivity());
            videoListRV = view.findViewById(R.id.video_list_rv);
            swipeContainer = view.findViewById(R.id.swipeContainer);
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // Your code to refresh the list here.
                    // Make sure you call swipeContainer.setRefreshing(false)
                    // once the network request has completed successfully.
                    try {

                        loadVideoListData(false, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Stop animation (This will be after 3 seconds)
                            swipeContainer.setRefreshing(false);
                        }
                    }, 4000);
                }
            });
            swipeContainer.setColorSchemeResources(R.color.themecombodarkgrey,
                    R.color.colorthemecombo,
                    R.color.themecombobrown,
                    R.color.dark_combo);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            videoListRV.setLayoutManager(linearLayoutManager);
            retry_tv.setOnClickListener(this);
            if (handler == null) {
                handler = new Handler();
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private void setAdapter() throws Exception {
        try {
            if (getActivity() != null) {
                videoListAdapter = new VideoListAdapter(list, getActivity(),videoDatabase);
                videoListRV.setAdapter(videoListAdapter);
            }
            loadVideoListData(false, true);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private void loadVideoListData(boolean isRetry, boolean showprogress) throws Exception {

        try {

            if (showprogress) {
                showProgress();
            }

            if (handler == null) {
                handler = new Handler();
            }
            NetworkUtils.setNetworkMode(handler);
            if (!isRetry) {
                requestVideoListData(showprogress);
            }
        } catch (Exception e) {
            throw new Exception(e);
        }

    }

    private void requestVideoListData(boolean showprogress) throws Exception {
        try {
            if (showprogress) {
                showProgress();
            }

            Call<ResVideoDataList> call = RetrofitClient.getInstance(getActivity()).getRestApi().getVideoDataList();
            call.enqueue(new Callback<ResVideoDataList>() {
                @Override
                public void onResponse(Call<ResVideoDataList> call, Response<ResVideoDataList> response) {
                    if (!CommonUtil.isUIThreadRunning(VideoListFragment.this, getActivity())) {
                        return;
                    }

                    if (response != null && response.body() != null && !isNullOrEmpty(response.body().getResults())) {

                        try {
                            handleSuccessVideoList(response.body(), showprogress);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        showError();
                    }
                }

                @Override
                public void onFailure(Call<ResVideoDataList> call, Throwable t) {
                    showError();
                }
            });
        } catch (Exception e) {
            throw new Exception(e);
        }

    }
    private void handleSuccessVideoList(ResVideoDataList resVideoList, boolean showcontent) throws Exception {

        try {
            if (resVideoList == null || isNullOrEmpty(resVideoList.getResults())) {
                showError(getString(R.string.no_data_found));
            } else {
                videoListAdapter.clear();
                videoListAdapter.addAll(resVideoList.getResults());
                if (showcontent) {
                    showContent();
                } else {
                    swipeContainer.setRefreshing(false);
                }

            }
        } catch (Exception e) {
            throw new Exception(e);
        }

    }
    @Override
    public void onClick(View v) {
        if (!NetworkUtils.isNetworkAvailable(getActivity())) {
            try {
                showError(getString(R.string.err_msg_no_internet_connect));
            } catch (Exception e) {
                return;
            }
            return;
        }
        if (v.getId() == R.id.retry_tv) {
            try {
                showProgress(LoadingViewFragment.PROGRESS_TYPE_TEXT_AND_CIRCLE, getString(R.string
                        .loading_msg), LoadingViewFragment.STYLE_WAVE);
                loadVideoListData(false, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
