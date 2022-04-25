package com.lang.protobufdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.protobuf.ProtoConverterFactory;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lang.protobufdemo.entity.Login;
import com.lang.protobufdemo.retrofit.LoginService;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "http://10.0.9.45:8088/";
    private TextView username;
    private TextView pwd;
    private String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        pwd = findViewById(R.id.pwd);
    }

    public void login(View view) {

        String username_txt = username.getText().toString();
        String pwd_txt = pwd.getText().toString();

        if (TextUtils.isEmpty(username_txt) || TextUtils.isEmpty(pwd_txt)) {
            Toast.makeText(this, "please fill username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        //序列化封装
        Login.LoginRequest loginBuild = Login.LoginRequest.newBuilder().setUsername(username_txt).setPassword(pwd_txt).build();

        setupAdapter().login(loginBuild).enqueue(new retrofit2.Callback<Login.LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<Login.LoginResponse> call, @NonNull Response<Login.LoginResponse> response) {
                Login.LoginResponse loginResult = response.body();
                Log.i(TAG, "login result:" + loginResult.getCode() + "--");
                Log.i(TAG, "login result :" + loginResult.getMsg());

            }

            @Override
            public void onFailure(@NonNull Call<Login.LoginResponse> call, @NonNull Throwable t) {

            }
        });
/*
        RequestBody requestBody = FormBody.create(MediaType.get("application/octet-stream"), loginBuild.toByteArray());

        // TODO: Need replace localhost to exactly IP address if we want to run app in emulator
        Request request = new Request.Builder().url(BASE_URL + "login").post(requestBody).build();

        Log.i(TAG, "requestBody:" + loginBuild.toByteArray());
//        Request request = new Request.Builder().url("https://time.geekbang.org/column/article/120928").build();
        Call call = new OkHttpClient().newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //解析数据
                byte[] t = response.body().bytes();
                Login.LoginResponse loginResult = Login.LoginResponse.parseFrom(t);

                Log.i(TAG, "response.body:" + Arrays.toString(t));
                Log.i(TAG, "login result:" + loginResult.getCode() + "--");
                Log.i(TAG, "login result :" + loginResult.getMsg());
            }
        });
 */
    }

    private LoginService setupAdapter() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ProtoConverterFactory.create())
                .build();
        return retrofit.create(LoginService.class);
    }
}
