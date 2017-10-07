package com.example.chao4kakaluote.music.service;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.chao4kakaluote.music.activity.DownloadMusic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoadService extends Service
{
    public DownLoadService()
    {

    }
    @Override
    public IBinder onBind(Intent intent)
    {
          return new DownloadBinder();
    }

    public String startDownload(String url)
    {
        //得到数据
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        try {
            Log.d("e1","error");
            Response response=client.newCall(request).execute();
            Log.d("e2","error");
            InputStream in = response.body().byteStream();
            byte[] b = new byte[1024];
            int len = 0;

            //写入到本地
            File file=new File(Environment.getExternalStorageDirectory()+"/orange.png");
            RandomAccessFile rfile=new RandomAccessFile(file,"rw");
            int total=0;
            while ((len = in.read(b)) != -1)
            {
                rfile.write(b,0,len);
                //得到下载的长度
                total+=len;
            }
            response.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
      return "download success";
    }
    private long getContentLength(String downloadUrl)
    {
         OkHttpClient client=new OkHttpClient();
         long length=0;
        Request request=new Request.Builder().url(downloadUrl).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e)
            {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
        return length;
    }
    public class DownloadBinder extends Binder
    {
        public String start_download(String url)
        {
                return startDownload(url);
        }
        public long getLength(String url)
        {
            return getContentLength(url);
        }
    }
}
