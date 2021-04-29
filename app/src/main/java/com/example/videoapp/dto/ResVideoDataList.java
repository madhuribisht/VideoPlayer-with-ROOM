package com.example.videoapp.dto;

import com.example.videoapp.entity.VideoDataList;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResVideoDataList {
    private Integer resultCount;
    private List<VideoDataList> results;
}
