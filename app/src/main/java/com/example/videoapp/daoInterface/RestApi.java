package com.example.videoapp.daoInterface;

import com.example.videoapp.constant.BaseConstant;
import com.example.videoapp.dto.ResVideoDataList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RestApi {
    @GET(BaseConstant.GET_VIDEO_LIST)
    Call<ResVideoDataList> getVideoDataList();
}
