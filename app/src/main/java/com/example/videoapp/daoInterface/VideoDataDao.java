package com.example.videoapp.daoInterface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.videoapp.entity.VideoDataList;
import java.util.List;

@Dao
public interface VideoDataDao {
    @Insert
    public void addData(VideoDataList videoDataList);

    @Query("select * from videodatalist")
    public List<VideoDataList> getAllVideoData();

    @Delete
    public void delete(VideoDataList videoDataList);

    @Query("UPDATE VideoDataList SET historyDate=:historyDate WHERE trackId = :trackId")
    void update(String historyDate, Long trackId);


    @Query("SELECT count(*)!=0 FROM videodatalist WHERE trackId = :trackId ")
    boolean containsPrimaryKey(Long trackId);


    /*check for empty database or not*/
    @Query("SELECT * FROM VideoDataList LIMIT 1")
    VideoDataList getVideoDTO();

}
