package com.example.administrator.music.Util;

import android.os.Environment;
import android.util.Log;

import com.example.administrator.music.R;
import com.example.administrator.music.db.Music;

import java.io.File;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/9/26.
 */
public class Utility
{
    public static String localMusicPath= Environment.getExternalStorageDirectory().toString();
    public static boolean initMusicList()
    {
        try
        {
            Log.d("musicPath",localMusicPath);
            File mfile=new File(localMusicPath);
            File[] files=mfile.listFiles();
            Log.d("filesSize",String.valueOf(files.length));
            for(int i=0;i<files.length;i++)
            {
                if(checkIsMusicFile(files[i].getPath()))
                {
                    Music music=new Music();
                    music.setMusicName(files[i].getName());
                    music.setImageId(R.drawable.shenglue);
                    Log.d("fileName",files[i].getName());
                    music.save();
            }
        }
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean checkIsMusicFile(String fName) {
        boolean isMusicFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("mp3") || FileEnd.equals("mp4")
                || FileEnd.equals("wmv") ) {
            isMusicFile = true;
        } else
        {
            isMusicFile = false;
        }
        return isMusicFile;
    }
    public static void sendRequest(String url, Callback callback)
    {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }
    public static void sendPostRequest(String url,String argsName[],String args[],Callback callback)
    {
        OkHttpClient client=new OkHttpClient();
        FormBody.Builder form=new okhttp3.FormBody.Builder();
        for(int i=0;i<argsName.length;i++)
        {
            form.add(argsName[i],args[i]);
        }
        RequestBody formBody=form.build();
        Request request=new Request.Builder().url(url).post(formBody).build();
        client.newCall(request).enqueue(callback);
    }
}