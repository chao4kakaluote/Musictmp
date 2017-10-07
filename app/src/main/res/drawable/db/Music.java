package com.example.administrator.music.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/9/26.
 */

public class Music extends DataSupport
{
    private int id;
    private int ImageId;
    private String musicName;
    private String words;
    public void setId(int id) {
        this.id = id;
    }
    public void setMusicName(String musicName) {
        String name=musicName.substring(0,musicName.lastIndexOf('.'));
        this.musicName=name;
    }
    public void setWords(String words) {
        this.words = words;
    }

    public int getId() {
        return id;
    }
    public String getMusicName() {
        return musicName;
    }
    public String getWords() {
        return words;
    }

    public int getImageId() {
        return ImageId;
    }
    public void setImageId(int imageId) {
        ImageId = imageId;
    }


}
