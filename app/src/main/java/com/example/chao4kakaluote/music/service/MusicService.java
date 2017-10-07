package com.example.chao4kakaluote.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.chao4kakaluote.music.activity.MusicPlay;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/9/27.
 */
public class MusicService extends Service
{
    private static MediaPlayer player;
    private static Timer timer;
    private static String musicPath=null;
    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d("bind1","onBind");
        return new MusicControl();
    }
    public void setPath(String path)
    {
        musicPath=path;
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d("musicService","onCreate");
        player=new MediaPlayer();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d("startCommand","startCommand");
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d("DestroyService","destroy");
        player.stop();
        player.release();
        player=null;
    }
    public void play()
    {
        try
        {
            if(player==null)
                player=new MediaPlayer();
            player.reset();
            player.setDataSource(musicPath);
            player.prepare();
            player.start();
        //    if(MusicPlay.handler!=null)
         //   addTimer();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public void pausePlay()
    {
        player.pause();
    }
    public void continuePlay()
    {
        player.start();
    }
    public void seekTo(int progress)
    {
        player.seekTo(progress);
    }
    public void addTimer()
    {
        if(timer==null)
        {
            timer=new Timer();
            timer.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    int duration=player.getDuration();
                    int currentPosition=player.getCurrentPosition();
                    Log.d("schedule1",String.valueOf(Thread.currentThread().getId()));
                    Message msg= MusicPlay.handler.obtainMessage();
                    Bundle bundle=new Bundle();
                    bundle.putInt("duration",duration);
                    bundle.putInt("currentPosition",currentPosition);
                    msg.setData(bundle);
                    msg.what=0;
                    MusicPlay.handler.sendMessage(msg);
                }
            },5,500);
        }
    }
    public class MusicControl extends Binder
    {
        public void play()
        {
            MusicService.this.play();
        }
        public void pausePlay()
        {
            MusicService.this.pausePlay();
        }
        public void continuePlay()
        {
            MusicService.this.continuePlay();
        }
        public void seekTo(int progress)
        {
            MusicService.this.seekTo(progress);
        }
        public void setPath(String path)
        {
            MusicService.this.setPath(path);
        }
    }

}