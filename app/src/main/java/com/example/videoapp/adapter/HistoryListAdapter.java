package com.example.videoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.videoapp.R;
import com.example.videoapp.constant.BaseConstant;
import com.example.videoapp.entity.VideoDataList;
import com.example.videoapp.utils.CommonUtil;
import com.example.videoapp.view.ui.VideoDetailsActivity;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryListHolder> {

    private List<VideoDataList> videoDataList;
    private Context context;

    public HistoryListAdapter(final List<VideoDataList> itemList, Context context) {
        this.videoDataList = itemList;
        this.context = context;
    }

    @Override
    public HistoryListHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View menuItemView = layoutInflater.inflate(R.layout.item_history_list, parent, false);
        return new HistoryListHolder(menuItemView);
    }

    @Override
    public void onBindViewHolder(final HistoryListHolder holder, final int position) {
        if (videoDataList != null) {
            VideoDataList item = videoDataList.get(position);
            int lastPosition = -1;
            if (item != null) {
                if (!CommonUtil.isNullOrEmpty(item.getTrackName())) {
                    holder.songName.setText(item.getTrackName());
                }
                if (!CommonUtil.isNullOrEmpty(item.getArtistName())) {
                    holder.artistName.setText(item.getArtistName());
                }
                if (item.getTrackTimeMillis() != null) {
                    Long minutes = TimeUnit.MILLISECONDS.toMinutes(item.getTrackTimeMillis());
                    Long seconds = (TimeUnit.MILLISECONDS.toSeconds(item.getTrackTimeMillis()) % 60);
                    holder.videoDuration.setText(minutes + BaseConstant.COLON + seconds);
                }
                if (!CommonUtil.isNullOrEmpty(item.getArtworkUrl100())) {
                    Glide.with(context).load(item.getArtworkUrl100())
                            .placeholder(R.drawable.no_image)
                            .error(R.drawable.no_image)
                            .into(holder.videoPreview);
                }
                if (position > lastPosition) {

                    Animation animation = AnimationUtils.loadAnimation(context,
                            (position > lastPosition) ? R.anim.up_bottom
                                    : R.anim.down_from_top);
                    holder.itemView.startAnimation(animation);
                    lastPosition = position;
                }
                holder.historyListLinear.setOnClickListener(v -> {
                    try {
                        Intent videoDetailsIntent = new Intent(context, VideoDetailsActivity.class);
                        videoDetailsIntent.putExtra(BaseConstant.VIDEO_URL, item.getPreviewUrl());
                        videoDetailsIntent.putExtra(BaseConstant.VIDEO_THUMBNAIL, item.getArtworkUrl100());
                        videoDetailsIntent.putExtra(BaseConstant.SONG_NAME, item.getTrackName());
                        videoDetailsIntent.putExtra(BaseConstant.ARTIST_NAME, item.getArtistName());
                        videoDetailsIntent.putExtra(BaseConstant.DATE, item.getReleaseDate());
                        videoDetailsIntent.putExtra(BaseConstant.PRICE, item.getTrackPrice());
                        v.getContext().startActivity(videoDetailsIntent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });

            }
        }
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if (videoDataList != null) {
            ret = videoDataList.size();
        }
        return ret;
    }

    public class HistoryListHolder extends RecyclerView.ViewHolder {
        private TextView songName, artistName, videoDuration;
        private LinearLayout historyListLinear;
        private ImageView videoPreview;

        HistoryListHolder(final View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.song_name);
            artistName = itemView.findViewById(R.id.artist_name);
            videoDuration = itemView.findViewById(R.id.video_duration);
            videoPreview = itemView.findViewById(R.id.video_preview);
            historyListLinear = itemView.findViewById(R.id.historylist_parent_ll);
        }
    }
}
