package com.example.rewardcalculator.network;


public abstract class NetworkRequestListener {

    public NetworkRequestListener() {
    }

    public abstract void onSuccess(String var1);

    public abstract void onFailure(int errorCode, String errorMsg);

    public void onError(Exception e) {
    }

}
