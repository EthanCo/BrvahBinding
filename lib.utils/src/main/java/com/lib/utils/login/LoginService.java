package com.lib.utils.login;

import android.util.Log;

import com.lib.utils.security.Base64;
import com.lib.utils.security.MD5;

/**
 * LoginService
 *
 * @author EthanCo
 * @since 2017/9/22
 */
public class LoginService {
    public static final String TOKEN = "token";
    public static final String HAS_ACCOUNT = "hasAccount";
    public static final String PWD = "pwd";
    public static final String MEMBERID = "memberId";
    public static final String ACCOUNT = "account";
    public static final String THIRDTYPE = "third_type";
    public static final String THIRDLOGINTIME = "third_login_time";


    public static final int TYPE_NORMAL = 0;//自建账号
    public static final int TYPE_WEIXIN = 1;//微信
    public static final int TYPE_QQ = 2;//qq
    public static final int TYPE_SINA = 3;//新浪微博

    private String mAccount;
    private String mToken;

    //第三方登录类型
    private int thirdPartyType = 0;
    private String thirdPartyUnionid;
    private long thirdPartyExpire;

    //微信登录信息
//    private WXGetToeknResponse wxTokenResponse;

    //微博登录信息
//    private String WBUnionID;
//    private long WBEXPIRE;

    //qq登录信息
//    private String QQUnionId;
//    private long QQEXPIRE;


    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        this.mToken = token;
    }

    public String token = "";
    public String tokenBase64 = "";
    public String tokenMd5 = "";
    public long deviceId = 0L;

    public String getTokenBase64() {
        return tokenBase64;
    }

    public void setTokenBase64(String tokenBase64) {
        this.tokenBase64 = tokenBase64;
    }

    private LoginService() {
    }

    private static class SingleTonHolder {
        private static LoginService sInstance = new LoginService();
    }

    public static LoginService getInstance() {
        return SingleTonHolder.sInstance;
    }

    private long lastTime;

    public boolean login(String account, String token, long deviceId, int type) {
        this.token = token;
        this.deviceId = deviceId;
        this.tokenMd5 = MD5.md5(token);
        this.mAccount = account;
        this.tokenBase64 = new String(Base64.encode(token.getBytes()));
        this.thirdPartyType = type;
        if (type == TYPE_NORMAL) {
            lastTime = System.currentTimeMillis();
        }
        return true;
    }

    public void logout() {
        this.mAccount = "";
        this.token = "";
        this.deviceId = 0L;
        this.tokenMd5 = "";
        this.tokenBase64 = "";
        lastTime = 0;
    }

    public synchronized LoginStatus isLogin() {
        long currTime = System.currentTimeMillis();
        long dTime = currTime - lastTime;
        Log.i("Z-Login", "currTime:" + currTime + " lastTime:" + lastTime + " dTime:" + dTime + " thirdtype " + thirdPartyType);
        LoginStatus status = LoginStatus.LOGOUT;
        switch (thirdPartyType) {
            case TYPE_NORMAL:
                if (lastTime > 0 && lastTime <= currTime) {
                    status = LoginStatus.LOGIN;
                }
                break;

            case TYPE_WEIXIN:
            case TYPE_SINA:
            case TYPE_QQ:
                if (lastTime > 0 && thirdPartyExpire > dTime) {
                    status = LoginStatus.LOGIN;
                } else if (thirdPartyExpire <= dTime) {
                    status = LoginStatus.EXPIRED;
                }
                break;
        }
        return status;
    }

    public enum LoginStatus {
        LOGIN, //1已登录
        LOGOUT, //未登录
        EXPIRED //登录过期
    }

    public String getmAccount() {
        return mAccount;
    }

    public void setmAccount(String mAccount) {
        this.mAccount = mAccount;
    }


    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }


    public String getThirdPartyUnionid() {
        return thirdPartyUnionid;
    }

    public void setThirdPartyUnionid(String thirdPartyUnionid) {
        this.thirdPartyUnionid = thirdPartyUnionid;
    }

    public long getThirdPartyExpire() {
        return thirdPartyExpire;
    }

    public void setThirdPartyExpire(long thirdPartyExpire) {
        this.thirdPartyExpire = thirdPartyExpire;
    }

    public int getThirdPartyType() {
        return thirdPartyType;
    }

    public void setThirdPartyType(int thirdPartyType) {
        this.thirdPartyType = thirdPartyType;
    }
}
