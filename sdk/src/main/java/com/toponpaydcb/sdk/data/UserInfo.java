package com.toponpaydcb.sdk.data;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.toponpaydcb.sdk.data.init.YoleInitConfig;
import com.toponpaydcb.sdk.tool.FileSave;
import com.toponpaydcb.sdk.tool.NetUtil;
import com.toponpaydcb.sdk.tool.PhoneInfo;
import com.toponpaydcb.sdk.YoleSdkMgr;
import com.toponpaydcb.sdk.callback.CallBackFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserInfo extends UserInfoBase{
    private static String TAG = "Yole_UserInfo";

    public UserInfo(Context var1, YoleInitConfig _config)
    {
        act = var1;
        config = _config;
        info = new PhoneInfo(act);
        String[] data = FileSave.readContent("PhoneNumber.text",var1);
        phoneNumber = data.length > 0 ? data[0] : "";
        Log.d(TAG, "NetUtil init:appkey="+config.getAppKey()+"cpCode="+config.getCpCode());
    }
    public YoleInitConfig getConfig(){return super.config;}
    public  String getSmsNumber(){return super.getSmsNumber();}
    public  String getSmsCode(){return super.getSmsCode();}
    public  String getCpCode(){return super.getCpCode();}
    public  String getAppkey(){return super.getAppkey();}
    public  String getMcc(){return super.getMcc();}
    public  String getMnc(){return super.getMnc();}
    public  String getCountryCode(){return super.getCountryCode();}
    public  String getCurrency(){return super.getCurrency();}
    public  String getSymbol(){return super.getSymbol();}
    public  String getPackageName()
    {
        return super.getPackageName();
    }
    public  String getAppName()
    {
        return super.getAppName();
    }
    public  Drawable getIcon()
    {
        return super.getIcon();
    }
    public  String getVersionName()
    {
        return super.getVersionName();
    }
    public  String getPhoneModel()
    {
        return super.getPhoneModel();
    }
    public  String getGaid()
    {
        return super.getGaid();
    }
    public  String getPhoneNumber(){return super.getPhoneNumber();}
    public  String getPayOrderNum()
    {
        return super.getPayOrderNum();
    }
    public  String getAmount()
    {
        return super.getAmount();
    }
    public  String getLanguage()
    {
        return super.getLanguage();
    }

    public  void decodeOnlineInit(String res)
    {
        Log.e(TAG, "OnlineInit:"+res);
        if(res.length() <= 0)
        {
            YoleSdkMgr.getsInstance().onlineInitResult(false,"","");
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(res);
            String status = jsonObject.getString("status");
            String errorCode = jsonObject.getString("errorCode");
            String message = jsonObject.getString("message");

            if(status.indexOf("SUCCESS") ==  -1)
            {
                YoleSdkMgr.getsInstance().onlineInitResult(false,res,"");
            }
            else
            {
                String content = jsonObject.getString("content");
                JSONObject contentJsonObject = new JSONObject(content);
                String content_sign = contentJsonObject.getString("sign");
                String content_content = contentJsonObject.getString("content");
                String decode_content = NetUtil.decodeBase64(content_content);
                JSONObject decode_contentJsonObject = new JSONObject(decode_content);
                String url = decode_contentJsonObject.getString("url");
                Log.e(TAG, "OnlineInit:webUrl="+url);

                YoleSdkMgr.getsInstance().startonlineActivity(url);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            YoleSdkMgr.getsInstance().onlineInitResult(false,e.toString(),"");
        }
    }
    public  void decodePaymentQuery(String res)
    {
        Log.e(TAG, "decodePaymentQuery:"+res);
        if(res.length() <= 0)
        {
            YoleSdkMgr.getsInstance().paymentQueryResult(false,"");
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(res);
            String status = jsonObject.getString("status");
            String errorCode = jsonObject.getString("errorCode");
            String message = jsonObject.getString("message");

            if(status.indexOf("SUCCESS") ==  -1)
            {
                YoleSdkMgr.getsInstance().paymentQueryResult(false,res);
            }
            else
            {
                String content = jsonObject.getString("content");
                JSONObject contentJsonObject = new JSONObject(content);
                String content_sign = contentJsonObject.getString("sign");
                String content_content = contentJsonObject.getString("content");
                String decode_content = NetUtil.decodeBase64(content_content);
                JSONObject decode_contentJsonObject = new JSONObject(decode_content);
                String paymentStatus = decode_contentJsonObject.getString("paymentStatus");
                String paymentDatetime = decode_contentJsonObject.getString("paymentDatetime");
                Log.e(TAG, "decodePaymentQuery:paymentStatus="+paymentStatus+";paymentDatetime="+paymentDatetime);

                YoleSdkMgr.getsInstance().paymentQueryResult(true,decode_contentJsonObject.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
            YoleSdkMgr.getsInstance().paymentQueryResult(false,e.toString());
        }
    }





}
