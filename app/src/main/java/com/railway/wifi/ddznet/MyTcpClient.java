package com.crhsi.wifi.ddznet;

import android.util.Log;

import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientAddress;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.util.CharsetUtil;


/**
 * Created by ylkjcjq on 2017/11/1.
 */

public class MyTcpClient {

    public SocketClient socketClient;
    private String url, port;
    private SocketClientDelegate mSocketClientDelegate;

    public MyTcpClient(){}

    public MyTcpClient(String url, String port){
        this.url = url;
        this.port =  port;
    }

    public void setSocketClientDelegate(SocketClientDelegate mSocketClientDelegate){
        this.mSocketClientDelegate = mSocketClientDelegate;
    }

    private void connect(){
        socketClient = new SocketClient(new SocketClientAddress(url, port));
        socketClient.setCharsetName(CharsetUtil.UTF_8); // 设置编码为UTF-8
        socketClient.getAddress().setConnectionTimeout(15 * 1000); // 连接超时时长，单位毫秒
        // 对应removeSocketClientDelegate
        socketClient.registerSocketClientDelegate(mSocketClientDelegate);
        socketClient.connect();
    }

    public void close() {
        Log.v("mytcpclient","close");
        socketClient.disconnect();
    }

}
