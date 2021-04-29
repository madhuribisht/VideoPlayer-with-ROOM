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
import com.example.videoapp.adapter.HistoryListAdapter;
import com.example.videoapp.entity.VideoDataList;
import com.example.videoapp.roomDB.VideoDatabase;
import com.example.videoapp.view.ui.fragmentLoader.LoadingViewFragment;
import java.util.ArrayList;
import java.util.List;

public class HistoryListFragment extends LoadingViewFragment implements View.OnClickListener {
    private SwipeRefreshLayout historySwipeContainer;
    private RecyclerView historyListRV;
    private Handler handler;
    private ArrayList<VideoDataList> videoDataLists;
    private VideoDatabase videoDatabase;
    private HistoryListAdapter historyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return getLoaderView(getContext(), inflater.inflate(R.layout
                .frag_history_list, container, false));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            layoutID(view);
            adapter();
            setHasOptionsMenu(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void adapter() {
        if (videoDataLists == null) {
            videoDataLists = new ArrayList<>();
        }
        historyAdapter = new HistoryListAdapter(videoDataLists, getActivity());
        historyListRV.setAdapter(historyAdapter);
    }

    private void layoutID(View view) throws Exception {
        try {
            videoDatabase = VideoDatabase.getInstance(getActivity());
            historyListRV = view.findViewById(R.id.video_history_list_rv);
            historySwipeContainer = view.findViewById(R.id.historySwipeContainer);
            historySwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // Your code to refresh the list here.
                    // Make sure you call swipeContainer.setRefreshing(false)
                    // once the network request has completed successfully.
                    try {

                        getVideoData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Stop animation (This will be after 3 seconds)
                            historySwipeContainer.setRefreshing(false);
                        }
                    }, 4000);
                }
            });
            historySwipeContainer.setColorSchemeResources(R.color.themecombodarkgrey,
                    R.color.colorthemecombo,
                    R.color.themecombobrown,
                    R.color.dark_combo);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            historyListRV.setLayoutManager(linearLayoutManager);
            retry_tv.setOnClickListener(this);
            if (handler == null) {
                handler = new Handler();
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.retry_tv) {
            try {
                showProgress(LoadingViewFragment.PROGRESS_TYPE_TEXT_AND_CIRCLE, getString(R.string
                        .loading_msg), LoadingViewFragment.STYLE_WAVE);
                getVideoData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getVideoData() throws Exception {
        try {
            if (videoDataLists != null) {
                videoDataLists.clear();
            } else {
                videoDataLists = new ArrayList<>();
            }

            List<VideoDataList> myDataLists = videoDatabase.getVideoDao().getAllVideoData();
            if (myDataLists != null && !myDataLists.isEmpty()) {
                videoDataLists.addAll(myDataLists);
                historyAdapter.notifyDataSetChanged();
                showContent();
            } else {
                showError();
            }

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            showProgress(LoadingViewFragment.PROGRESS_TYPE_TEXT_AND_CIRCLE, getString(R.string
                    .loading_msg), LoadingViewFragment.STYLE_WAVE);
            getVideoData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}