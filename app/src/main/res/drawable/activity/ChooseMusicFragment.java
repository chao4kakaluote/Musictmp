package com.example.administrator.music.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.music.Adapter.MusicAdapter;
import com.example.administrator.music.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */
public class ChooseMusicFragment extends Fragment
{
         private MusicAdapter adapter;
         private ListView musicList;
         private List<com.example.administrator.music.db.Music> list=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view =inflater.inflate(R.layout.choose_music,container,false);
        musicList=(ListView)view.findViewById(R.id.music_list);
        if(ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        else {
            init();
            musicList.setAdapter(adapter);
        }
        return view;
    }

    public void init()
    {
        DataSupport.deleteAll(com.example.administrator.music.db.Music.class);
        com.example.administrator.music.Util.Utility.initMusicList();
        list= DataSupport.findAll(com.example.administrator.music.db.Music.class);
//        if(list.size()==0) {
//            Utility.initMusicList();
//            list=DataSupport.findAll(Music.class);
//        }
        for(int i=0;i<list.size();i++)
        {
            Log.d("MusicName",list.get(i).getMusicName());
        }
        Log.d("ListSize",String.valueOf(list.size()));
        adapter=new MusicAdapter(getContext(),R.layout.music_item,list);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case 1:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                    init();
                    musicList.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(getContext(),"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                }
        }
    }
}
