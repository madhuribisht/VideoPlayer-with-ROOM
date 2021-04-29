package com.example.videoapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(tableName="videodatalist")
public class VideoDataList implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long trackId;
    @ColumnInfo(name = "artistId")
    private Integer artistId;
    @ColumnInfo(name = "collectionId")
    private Long collectionId;
    @ColumnInfo(name = "wrapperType")
    private String wrapperType;
    @ColumnInfo(name = "kind")
    private String kind;
    @ColumnInfo(name = "artistName")
    private String artistName;
    @ColumnInfo(name = "trackName")
    private String trackName;
    @ColumnInfo(name = "collectionName")
    private String collectionName;
    @ColumnInfo(name = "trackCensoredName")
    private String trackCensoredName;
    @ColumnInfo(name = "collectionCensoredName")
    private String collectionCensoredName;
    @ColumnInfo(name = "artistViewUrl")
    private String artistViewUrl;
    @ColumnInfo(name = "trackViewUrl")
    private String trackViewUrl;
    @ColumnInfo(name = "previewUrl")
    private String previewUrl;
    @ColumnInfo(name = "artworkUrl30")
    private String artworkUrl30;
    @ColumnInfo(name = "artworkUrl60")
    private String artworkUrl60;
    @ColumnInfo(name = "artworkUrl100")
    private String artworkUrl100;
    @ColumnInfo(name = "collectionPrice")
    private Double collectionPrice;
    @ColumnInfo(name = "trackPrice")
    private Double trackPrice;
    @ColumnInfo(name = "releaseDate")
    private String releaseDate;
    @ColumnInfo(name = "collectionExplicitness")
    private String collectionExplicitness;
    @ColumnInfo(name = "trackExplicitness")
    private String trackExplicitness;
    @ColumnInfo(name = "discCount")
    private Integer discCount;
    @ColumnInfo(name = "discNumber")
    private Integer discNumber;
    @ColumnInfo(name = "trackCount")
    private Integer trackCount;
    @ColumnInfo(name = "trackNumber")
    private Integer trackNumber;
    @ColumnInfo(name = "trackTimeMillis")
    private Long trackTimeMillis;
    @ColumnInfo(name = "country")
    private String country;
    @ColumnInfo(name = "currency")
    private String currency;
    @ColumnInfo(name = "primaryGenreName")
    private String primaryGenreName;
    @ColumnInfo(name = "historyDate")
    private String historyDate;

}
