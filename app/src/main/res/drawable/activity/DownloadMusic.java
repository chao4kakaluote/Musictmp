package com.example.administrator.music.activity;

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

import com.example.administrator.music.R;
import com.example.administrator.music.service.DownLoadService;

public class DownloadMusic extends AppCompatActivity {

    public DownLoadService.DownloadBinder downloadBinder=null;
    public ServiceConnection connection=null;
    public static Handler handler=null;
    public static String downloadUrl="http://172.25.107.133:8080/de/res/orange.png";
    Button button=null;
    SeekBar DownloadProgress=null;
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
                  downloadBinder=(DownLoadService.DownloadBinder)iBinder;
                DownloadProgress=(SeekBar)findViewById(R.id.downloadProgress);
                DownloadProgress.setMax((int)downloadBinder.getLength(downloadUrl) );
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName)
            {

            }
        };
        Intent intent=new Intent(com.example.administrator.music.activity.DownloadMusic.this,DownLoadService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
      //  startService(intent);
        button=(Button)findViewById(R.id.startDownload);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new Thread()
                {
                    @Override
                    public void run() {
                        super.run();
                        final String result=downloadBinder.start_download(downloadUrl);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                               Toast.makeText(com.example.administrator.music.activity.DownloadMusic.this,result,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }.start();
            //    Toast.makeText(DownloadMusic.this,result,Toast.LENGTH_SHORT).show();
            }
        });

       handler=new Handler()
       {
           @Override
           public void handleMessage(Message msg)
           {
               super.handleMessage(msg);
               Bundle bundle=msg.getData();
               int progress=bundle.getInt("progress");
               DownloadProgress.setProgress(progress);
           }
       };
    }

}
