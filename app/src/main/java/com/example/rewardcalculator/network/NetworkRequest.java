package com.example.rewardcalculator.network;


import android.os.Handler;
import android.os.Message;

import java.io.IOException;

import okhttp3.RequestBody;

import static com.example.rewardcalculator.Constant.NETWORK_ERROR;
import static com.example.rewardcalculator.Constant.NETWORK_FAILED;
import static com.example.rewardcalculator.Constant.NETWORK_SUCCESS;

public class NetworkRequest {

    private int timeout = 2000;

    private MessageHandler handler;

    private volatile static NetworkRequest instance;


    private NetworkRequest() {
        handler = new MessageHandler();
    }

    public static NetworkRequest getInstance() {
        if (instance == null) {
            synchronized (NetworkRequest.class) {
                if (instance == null) {
                    instance = new NetworkRequest();
                }
            }
        }
        return instance;
    }

    public void doGet(NetworkRequestListener listener, String url) {
        HttpConnect connect = HttpConnect.getInstance();
        connect.getRemoteConnect(handler.setListener(listener), url, RequestMethod.Get,timeout,null);
    }

    public void doPost(NetworkRequestListener listener, String url, RequestBody body){
        HttpConnect connect = HttpConnect.getInstance();
        connect.getRemoteConnect(handler.setListener(listener),url,RequestMethod.Post,timeout,body);
    }

    private class MessageHandler extends Handler {

        private NetworkRequestListener listener;

        public MessageHandler setListener(NetworkRequestListener listener) {
            this.listener = listener;
            return this;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                default:
                case NETWORK_ERROR:
                    this.listener.onError((IOException) msg.obj);
                    break;
                case NETWORK_SUCCESS:
                    this.listener.onSuccess((String) msg.obj);
                    break;
                case NETWORK_FAILED:
                    int errorCode = msg.arg1;
                    String result = (String) msg.obj;
                    this.listener.onFailure(errorCode, result);
            }

        }
    }


}
