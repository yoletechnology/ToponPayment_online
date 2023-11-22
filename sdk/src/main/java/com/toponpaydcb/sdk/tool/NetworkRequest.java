package com.toponpaydcb.sdk.tool;

import android.util.Log;

import com.toponpaydcb.sdk.YoleSdkMgr;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class NetworkRequest {
    public String TAG = "Yole_NetworkRequest";
    public void onlineInit(String appKey,String amount,String orderNumber,String companyCode,String companyPage,String countryCode,String currency,String secretkey) {

        Log.d(TAG, "onlineInit countryCode:"+countryCode);
        if(countryCode.length() <= 0 )
        {
            YoleSdkMgr.getsInstance().onlineInitResult(false,"countryCode 无效","");
            return;
        }

        JSONObject requestBody = new JSONObject ();
        try {
            requestBody.put("amount",amount);
            requestBody.put("orderNumber",orderNumber);
            requestBody.put("companyCode",companyCode);
            requestBody.put("companyPage",companyPage);
            requestBody.put("countryCode",countryCode);
            requestBody.put("currency",currency);
            Log.d(TAG, "onlineInit-content:"+requestBody.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "onlineInit-requestBody-error:"+e.toString());
        }
        EncodeBaseData data = NetUtil.RestApiRequest(requestBody.toString(),secretkey);


        JSONObject formBody = new JSONObject ();
        try {
            formBody.put("appKey",appKey);
            formBody.put("sign",data.sign);
            formBody.put("content",data.content);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "onlineInit-formBody-error:"+e.toString());
        }


        String res = NetUtil.sendPost("api/online/transaction/init",formBody);
        Log.d(TAG, "onlineInit"+res);
        YoleSdkMgr.getsInstance().user.decodeOnlineInit(res);
    }
    public void getPaymentQuery(String appKey,String billingNumber,String secretkey) {

        Log.d(TAG, "getPaymentQuery billingNumber:"+billingNumber);
        if(billingNumber.length() <= 0 )
        {
            YoleSdkMgr.getsInstance().paymentQueryResult(false,"billingNumber 无效");
            return;
        }

        JSONObject requestBody = new JSONObject ();
        try {
            requestBody.put("billingNumber",billingNumber);
            Log.d(TAG, "getPaymentQuery-content:"+requestBody.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "getPaymentQuery-requestBody-error:"+e.toString());
        }
        EncodeBaseData data = NetUtil.RestApiRequest(requestBody.toString(),secretkey);


        JSONObject formBody = new JSONObject ();
        try {
            formBody.put("appKey",appKey);
            formBody.put("sign",data.sign);
            formBody.put("content",data.content);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "onlineInit-formBody-error:"+e.toString());
        }


        String res = NetUtil.sendPost("api/RUPayment/transaction/query",formBody);
        Log.d(TAG, "getPaymentQuery"+res);
        YoleSdkMgr.getsInstance().user.decodePaymentQuery(res);
    }


}
