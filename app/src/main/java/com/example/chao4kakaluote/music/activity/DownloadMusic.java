package com.example.chao4kakaluote.music.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.chao4kakaluote.music.R;
import com.example.chao4kakaluote.music.service.DownLoadService;
import com.example.chao4kakaluote.music.service.DownloadSvc;

public class DownloadMusic extends AppCompatActivity {


    public ServiceConnection connection=null;
    public static String downloadUrl="http://192.168.1.109:8080/test/res/orange.png";
    public DownloadSvc.DownloadBinder binder=null;
    Button start=null;
    Button pause=null;
    Button cancel=null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_music);

        connection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder)
            {
                Log.d("serviceConnected","connected");
                binder=(DownloadSvc.DownloadBinder)iBinder;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName)
            {
            }
        };
        Intent intent=new Intent(DownloadMusic.this,DownloadSvc.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
        initButton();
    }
    public void initButton()
    {
         start=(Button)findViewById(R.id.startDownload);
         pause=(Button)findViewById(R.id.pauseDownload);
         cancel=(Button)findViewById(R.id.cancelDownload);
        start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                binder.startDownload(downloadUrl);
            }
        });
        pause.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                binder.pauseDownload();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                binder.cancelDownload();
            }
        });

    }

    @Override
    protected void onDestroy()
    {
        unbindService(connection);
        super.onDestroy();
    }
}
