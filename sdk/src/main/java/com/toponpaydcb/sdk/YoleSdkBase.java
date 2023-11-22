package com.toponpaydcb.sdk;

import android.app.Activity;
import android.content.Context;

import com.toponpaydcb.sdk.callback.CallBackFunction;
import com.toponpaydcb.sdk.callback.InitCallBackFunction;
import com.toponpaydcb.sdk.callback.PaymentQueryCallBackFunction;
import com.toponpaydcb.sdk.data.UserInfo;
import com.toponpaydcb.sdk.data.init.YoleInitConfig;
import com.toponpaydcb.sdk.tool.NetworkRequest;

import java.util.Timer;
import java.util.TimerTask;

public class YoleSdkBase {
    private String TAG = "Yole_YoleSdkBase";
    protected Context context =  null;
    protected boolean isDebugger = false;
    protected boolean isSdkInitSuccess = true;
    /**各种网络接口**/
    public NetworkRequest request = null;
    /**用户信息(通过用户设置 和 请求的返回。组装成的数据)**/
    public UserInfo user =  null;

    public CallBackFunction onlineInitCallBack = null;
    public PaymentQueryCallBackFunction paymentQueryCallBack = null;
    public static YoleSdkBase instance = null;
    public Activity _activity = null;

    /**sdk初始化的主接口*/
    public void initSdk(Context _var1, YoleInitConfig _config, InitCallBackFunction _initBack)
    {
        context = _var1;
        instance = this;
        this.init(_var1,_config);



        (new Timer()).schedule(new TimerTask() {
            @Override
            public void run() {
                instance.isSdkInitSuccess = true;
                _initBack.success(null);
            }
        },1000);


    }
    /**创建sdk内的各个功能模块*/
    protected void init(Context var1,YoleInitConfig _config)
    {
        request = new NetworkRequest();
        isDebugger = _config.isDebug();
        user = new UserInfo(var1,_config);
    }



}
