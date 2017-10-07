package com.example.chao4kakaluote.music.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.chao4kakaluote.music.R;
import com.example.chao4kakaluote.music.service.MusicService;

public class MusicPlay extends AppCompatActivity
{
    private TextView currentTime;
    private TextView totalTime;
    private Button start;
    private Button pause;
    private Button continue_play;
    private ServiceConnection connection;
    private MusicService.MusicControl musicBinder;
    private static SeekBar progressBar;
    public static Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            Bundle b=msg.getData();
            int duration=b.getInt("duration");
            int currentPosition=b.getInt("currentPosition");
            progressBar.setMax(duration);
            progressBar.setProgress(currentPosition);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
        Log.d("MusicPlay",String.valueOf(Thread.currentThread().getId()));
        init();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        initService();
        setClick();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
    }

    public void init()
    {
        currentTime=(TextView)findViewById(R.id.currentTime);
        totalTime=(TextView)findViewById(R.id.totalTime);
        start=(Button)findViewById(R.id.play_music);
        pause=(Button)findViewById(R.id.pause_play);
        continue_play=(Button)findViewById(R.id.continue_play);
        progressBar=(SeekBar)findViewById(R.id.music_progress);
    }
    public void initService()
    {
       connection=new ServiceConnection() {
           @Override
           public void onServiceConnected(ComponentName componentName, IBinder iBinder)
           {
                   musicBinder=(MusicService.MusicControl)iBinder;
                   String path=Environment.getExternalStorageDirectory()+"/01.mp3";
                   Log.d("path1",path);
                   musicBinder.setPath(path);
           }
           @Override
           public void onServiceDisconnected(ComponentName componentName)
           {

           }
       };
       Intent intent=new Intent(this,MusicService.class);
       bindService(intent,connection,BIND_AUTO_CREATE);
        Intent intent1=new Intent(this,MusicService.class);
       startService(intent1);
    }
    public void setClick()
    {
        start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                     musicBinder.play();
            }
        });
        pause.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                     musicBinder.pausePlay();
            }
        });
        continue_play.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                     musicBinder.continuePlay();
            }
        });
    }

}
