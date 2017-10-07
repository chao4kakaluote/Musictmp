package com.example.administrator.music.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.example.administrator.music.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Login extends AppCompatActivity
{
    public static String LoginUrl="http://172.25.107.133:8080/de/servlet1/helloServlet";
    private Button regist;
    private Button login;
    private EditText userName;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        regist=(Button)findViewById(R.id.regist);
        login=(Button)findViewById(R.id.login);
        userName=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);

        regist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Requestregist();
            }
        });
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                RequestLogin();
            }
        });
    }
    public void Requestregist()
    {
        String username=userName.getText().toString();
        String pwd=password.getText().toString();
        String argsName[]={"type","userName","password"};
        String args[]={"regist",username,pwd};
        com.example.administrator.music.Util.Utility.sendPostRequest(LoginUrl,argsName,args, new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(com.example.administrator.music.activity.Login.this,"请求失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                final String text=response.body().string();
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(com.example.administrator.music.activity.Login.this,text,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public void RequestLogin()
    {
        String username=userName.getText().toString();
        String pwd=password.getText().toString();
        String argsName[]={"type","userName","password"};
        String args[]={"login",username,pwd};
        com.example.administrator.music.Util.Utility.sendPostRequest(LoginUrl,argsName,args, new Callback() {
            @Override
            public void onFailure(Call call, IOException e)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(com.example.administrator.music.activity.Login.this,"请求失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                final String text=response.body().string();
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(com.example.administrator.music.activity.Login.this,text,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
