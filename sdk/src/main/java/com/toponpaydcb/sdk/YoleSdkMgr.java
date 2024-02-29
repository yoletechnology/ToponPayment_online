package com.toponpaydcb.sdk;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.toponpaydcb.sdk.callback.PaymentQueryCallBackFunction;
import com.toponpaydcb.sdk.data.InitSdkData;
import com.toponpaydcb.sdk.dcb.PaymentView;
import com.toponpaydcb.sdk.callback.CallBackFunction;

import java.util.ArrayList;
import java.util.List;

public class YoleSdkMgr extends YoleSdkBase{

    private String TAG = "Yole_YoleSdkMgr";
    private static  YoleSdkMgr _instance = null;

    public static final String RETURN_INFO = "com.toponpaydcb.sdk.info";

    public static YoleSdkMgr getsInstance() {
        if(YoleSdkMgr._instance == null)
        {
            YoleSdkMgr._instance = new YoleSdkMgr();
        }
        return YoleSdkMgr._instance;
    }
    private YoleSdkMgr() {
        Log.e(TAG,"YoleSdkMgr");
    }

    public boolean getIsInitSuccess()
    {
        return this.isSdkInitSuccess;
    }

    public void onlineInit(Activity activity ,String amount,String orderNumber,CallBackFunction callback) {

        this._activity = activity;
        this.onlineInitCallBack = callback;
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    request.onlineInit(
                            user.getAppkey(),
                            amount,
                            orderNumber,
                            "订单描述",
                            "https://apiweb.yolesdk.com/index.html#/game/onlinePayResult?appkey="+user.getAppkey(),
                            "RU",//user.getCountryCode(),
                            user.getCurrency(),
                            user.getSecretkey()
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void onlineInitResult(boolean result,String info, String billingNumber) {
        if(this.onlineInitCallBack != null)
            this.onlineInitCallBack.onCallBack(result,info,billingNumber);
        this.onlineInitCallBack = null;
    }
    public void startonlineActivity(String webUrl)
    {
        Intent i = new Intent(_activity, PaymentView.class);
        i.putExtra(YoleSdkMgr.RETURN_INFO, webUrl);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        _activity.startActivity(i);
    }
    //查询支付结果
    public void getPaymentQuery(Activity activity, String billingNumber, PaymentQueryCallBackFunction callBack)
    {

        this._activity = activity;
        this.paymentQueryCallBack = callBack;

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    request.getPaymentQuery(
                            user.getAppkey(),
                            billingNumber,
                            user.getSecretkey()
                            );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void paymentQueryResult(boolean result,String info) {
        if(this.paymentQueryCallBack != null)
            this.paymentQueryCallBack.onCallBack(result,info);
        this.paymentQueryCallBack = null;
    }





}
