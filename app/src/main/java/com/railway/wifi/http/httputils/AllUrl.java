package com.railway.wifi.http.httputils;


/**
 * Created by cjq on 16/9/26.
 */

public class AllUrl {

    private static AllUrl mAllUrl;

    public static AllUrl getInstance() {
        if (mAllUrl == null) {
            mAllUrl = new AllUrl();
        }
        return mAllUrl;
    }

    //登录账户
    public String getLoginUrl() {
        return Url.BASE_URL + "/local/webservice?fun=login";
    }

    //强制IP下线
    public String getShutDownIpUrl() {
        return Url.BASE_URL + "/local/webservice?fun=shutdownIp";
    }

    //获取设备所有终端连接设备状态
    public String getAllDevicesUrl() {
        return Url.BASE_URL + "/local/webservice?fun=getOnlineIps";
    }

    //判断是否登录
    public String getIsLogUrl() {
        return Url.BASE_URL + "/local/webservice?fun=isLogin";
    }


    public String getRefreshTokenUrl() {
        return Url.BASE_URL + "";
    }

}
