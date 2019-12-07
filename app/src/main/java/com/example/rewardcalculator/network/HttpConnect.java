package com.example.rewardcalculator.network;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.example.rewardcalculator.Constant.NETWORK_ERROR;
import static com.example.rewardcalculator.Constant.NETWORK_FAILED;
import static com.example.rewardcalculator.Constant.NETWORK_SUCCESS;

public class HttpConnect {

    private static volatile HttpConnect connect;
    private Callback callback;
    private OkHttpClient okHttpClient;

    private HttpConnect() {
        okHttpClient = new OkHttpClient();
    }

    public static HttpConnect getInstance() {
        if (connect == null) {
            synchronized (HttpConnect.class) {
                if (connect == null) {
                    connect = new HttpConnect();
                }
            }
        }
        return connect;
    }

    public void getRemoteConnect(final Handler handler, String url, RequestMethod method, int timeout, RequestBody requestBody) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        OkHttpClient client = okHttpClient.newBuilder().connectTimeout(timeout, TimeUnit.MILLISECONDS).build();
        Request request;
        switch (method) {
            case Post:
                request = new Request.Builder().url(url).post(requestBody).build();
                break;
            case Get:
            default:
                request = new Request.Builder().url(url).build();
                break;
        }

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            String result = null;

            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage(NETWORK_ERROR, e);
                message.sendToTarget();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int responseCode = response.code();
                if (responseCode == 200) {
                    ResponseBody body = response.body();
                    this.result = body.string();
                    response.close();
                    Message messagex = handler.obtainMessage(NETWORK_SUCCESS, this.result);
                    messagex.sendToTarget();
                } else {
                    Log.e("Reward", "NetworkConnectFailed!!!");
                    this.result = response.body().string();
                    response.close();
                    Message message = handler.obtainMessage(NETWORK_FAILED, responseCode, 0, this.result);
                    message.sendToTarget();
                }
            }
        });
    }


}
