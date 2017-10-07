package com.example.chao4kakaluote.music.activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.chao4kakaluote.music.R;
import com.example.chao4kakaluote.music.service.DownloadSvc;
public class notifiction extends AppCompatActivity
{

    private DownloadSvc foregroundService=null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifiction);
        Intent intent=new Intent(this,DownloadSvc.class);
        startService(intent);
    }
    @Override
    protected void onDestroy()
    {
        Intent intent=new Intent(this,DownloadSvc.class);
        stopService(intent);
        super.onDestroy();
    }
}
