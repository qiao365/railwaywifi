package com.crhsi.wifi.ddznet;

import android.support.annotation.NonNull;

import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.helper.SocketResponsePacket;

/**
 * Created by ylkjcjq on 2017/11/1.
 */

public class MyClient implements SocketClientDelegate {

    public MyTcpClient mMyTcpClient = null;

    public boolean start(String ip, String port) {

        if (mMyTcpClient != null) {
            mMyTcpClient.close();
            mMyTcpClient = null;
        }
        mMyTcpClient = new MyTcpClient(ip, port);
        mMyTcpClient.setSocketClientDelegate(this);
        return true;
    }

    @Override
    public void onConnected(SocketClient client) {
//        SocketPacket packet = client.sendData(new byte[]{0x02}); // 发送消息
//        packet = socketClient.sendString("sy hi!"); // 发送消息
//        socketClient.cancelSend(packet); // 取消发送，若在等待发送队列中则从队列中移除，若正在发送则无法取消
    }

    @Override
    public void onDisconnected(SocketClient client) {
        client.connect();
    }

    @Override
    public void onResponse(SocketClient client, @NonNull SocketResponsePacket responsePacket) {
        byte[] data = responsePacket.getData(); // 获取接收的byte数组，不为null
        String message = responsePacket.getMessage(); // 获取按默认设置的编码转化的String，可能为null

        //消息发给GameActivity处理
//        Message msg = new Message();
//        msg.what = 1; //消息code
//        msg.obj = obj; //消息内容
//        msg.arg1 = msgid;
//        GameActivity.getGameActivityInstance().m_handler.sendMessage(msg);
    }

}
