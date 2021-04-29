package com.example.videoapp.roomDB;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.videoapp.constant.BaseConstant;
import com.example.videoapp.daoInterface.VideoDataDao;
import com.example.videoapp.entity.VideoDataList;

@Database(entities = { VideoDataList.class }, version = 1, exportSchema = false)
public abstract class VideoDatabase extends RoomDatabase {
    public abstract VideoDataDao getVideoDao();
    private static VideoDatabase videoDB;

    public static VideoDatabase getInstance(Context context) {
        if (null == videoDB) {
            videoDB = buildDatabaseInstance(context);
        }
        return videoDB;
    }

    private static VideoDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                VideoDatabase.class,
                BaseConstant.DB_NAME)
                .allowMainThreadQueries().build();
    }

}
