package com.example.chao4kakaluote.music.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.chao4kakaluote.music.R;
import com.example.chao4kakaluote.music.Util.Utility;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
public class Login extends AppCompatActivity
{
    public static String LoginUrl="http://192.168.1.109:8080/test/first/test";
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
        Login();
    }
    public void Requestregist()
    {
        String username=userName.getText().toString();
        String pwd=password.getText().toString();
        //先检查用户名和密码是否为空
        if(!isValid(username) || !isValid(pwd))
            Toast.makeText(Login.this,"用户名和密码不能为空，空格或回车",Toast.LENGTH_SHORT).show();
        else
            {
            String argsName[] = {"type", "userName", "password"};
            String args[] = {"regist", username, pwd};
            Utility.sendPostRequest(LoginUrl, argsName, args, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Login.this, "请求失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String text = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Login.this, text, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
    public void RequestLogin() {
        String username = userName.getText().toString();
        String pwd = password.getText().toString();
        if(!isValid(username) || !isValid(pwd))
            Toast.makeText(Login.this, "用户名和密码不能为空，空格或回车", Toast.LENGTH_SHORT).show();
        else {
            String argsName[] = {"type", "userName", "password"};
            String args[] = {"login", username, pwd};
            Utility.sendPostRequest(LoginUrl, argsName, args, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            Toast.makeText(Login.this, "请求失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String text = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Login.this, text, Toast.LENGTH_SHORT).show();
                            Log.d("login",text);
                        }
                    });
                }
            });
        }
    }
    public String getRealString(String s)
    {
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<s.length();i++)
            if(s.charAt(i)!='\n' && s.charAt(i)!='\r')
            builder.append(s.charAt(i));
        return builder.toString();
    }
    public void Login()
    {
        Intent intent=new Intent(Login.this,MainActivity.class);
        startActivity(intent);
    }
    public boolean isValid(String s)
    {
        if(s==null || s=="")
            return false;
        for(int i=0;i<s.length();i++)
            if(s.charAt(i)==' '|| s.charAt(i)=='\n' || s.charAt(i)=='\r')
                return false;
        return true;
    }
}
