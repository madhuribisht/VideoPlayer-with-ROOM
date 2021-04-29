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
import com.example.videoapp.roomDB.VideoDatabase;
import com.example.videoapp.utils.CommonUtil;
import com.example.videoapp.view.ui.VideoDetailsActivity;
import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListHolder> {

    private List<VideoDataList> videoDataList;
    private Context context;
    private VideoDatabase videoDatabase;

    public VideoListAdapter(final List<VideoDataList> itemList, Context context,VideoDatabase videoDatabase) {
        this.videoDataList = itemList;
        this.context = context;
        this.videoDatabase = videoDatabase;
    }

    @Override
    public VideoListHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View menuItemView = layoutInflater.inflate(R.layout.item_video_list, parent, false);
        return new VideoListHolder(menuItemView);
    }

    @Override
    public void onBindViewHolder(final VideoListHolder holder, final int position) {
        if (videoDataList != null) {
            VideoDataList item = videoDataList.get(position);
            int lastPosition = -1;
            if (item != null) {
                if (!CommonUtil.isNullOrEmpty(item.getTrackName())) {
                    holder.songName.setText(item.getTrackName());
                }
                if (!CommonUtil.isNullOrEmpty(item.getArtworkUrl100())) {
                    Glide.with(context).load(item.getArtworkUrl100())
                            .placeholder(R.drawable.no_image)
                            .error(R.drawable.no_image)
                            .into(holder.thumbnailProfile);
                }
                if (position > lastPosition) {

                    Animation animation = AnimationUtils.loadAnimation(context,
                            (position > lastPosition) ? R.anim.up_bottom
                                    : R.anim.down_from_top);
                    holder.itemView.startAnimation(animation);
                    lastPosition = position;
                }
                holder.videoListLinear.setOnClickListener(v -> {
                    try {
                        Intent videoDetailsIntent = new Intent(context, VideoDetailsActivity.class);
                        videoDetailsIntent.putExtra(BaseConstant.VIDEO_URL, item.getPreviewUrl());
                        videoDetailsIntent.putExtra(BaseConstant.VIDEO_THUMBNAIL, item.getArtworkUrl100());
                        videoDetailsIntent.putExtra(BaseConstant.SONG_NAME, item.getTrackName());
                        videoDetailsIntent.putExtra(BaseConstant.ARTIST_NAME, item.getArtistName());
                        videoDetailsIntent.putExtra(BaseConstant.DATE, item.getReleaseDate());
                        videoDetailsIntent.putExtra(BaseConstant.PRICE, item.getTrackPrice());
                        item.setHistoryDate(CommonUtil.getCurrDate());
                        if (videoDatabase.getVideoDao().getVideoDTO() != null) {
                            if (videoDatabase.getVideoDao().containsPrimaryKey(item.getTrackId())) {
                                videoDatabase.getVideoDao().update(CommonUtil.getCurrDate(),item.getTrackId());
                            } else {
                                videoDatabase.getVideoDao().addData(item);
                            }
                        } else {
                            videoDatabase.getVideoDao().addData(item);
                        }
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

    /*animation*/
    public void clear() {
        videoDataList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<VideoDataList> list) {
        videoDataList.addAll(list);
        notifyDataSetChanged();
    }

    public class VideoListHolder extends RecyclerView.ViewHolder {
        private TextView songName;
        private LinearLayout videoListLinear;
        private ImageView thumbnailProfile;

        VideoListHolder(final View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.song_name);
            thumbnailProfile = itemView.findViewById(R.id.thumbnail_profile);
            videoListLinear = itemView.findViewById(R.id.videolist_parent_ll);
        }
    }
}
