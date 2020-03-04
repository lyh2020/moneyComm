/*
 * Comet4J Copyright(c) 2011, http://code.google.com/p/comet4j/ This code is
 * licensed under BSD license. Use it as you wish, but keep this copyright
 * intact.
 */
package com.win.share.vo;

/**
 * 用于封装向客户端发送信息的数据格式
 */

public class CometMessage {

    /** 应用模块标识 */
    private String channel;

    private long time;// 发送时间

    private Object data;// 包含数据

    private String actionFlag;//动作标识:heat_beat

    public CometMessage(Object anData, String aChannel) {
        data = anData;
        channel = aChannel;
        time = System.currentTimeMillis();
    }

    public CometMessage(String actionFlag, Object anData, String aChannel) {
        this.actionFlag = actionFlag;
        data = anData;
        channel = aChannel;
        time = System.currentTimeMillis();
    }

    /**
     * 获取发送时间
     * @return
     */

    public long getTime() {
        return time;
    }

    /**
     * 设置发送时间
     * @param time
     */

    public void setTime(long time) {
        this.time = time;
    }

    /**
     * 获取被发送数据
     * @return
     */

    public Object getData() {
        return data;
    }

    /**
     * 设置被发送数据
     * @param data
     */

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 获取通道标识
     * @return
     */

    public String getChannel() {
        return channel;
    }

    /**
     * 设置通道标识
     * @param channel
     */

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void destroy() {
        data = null;
    }

    public String getActionFlag() {
        return actionFlag;
    }

    public void setActionFlag(String actionFlag) {
        this.actionFlag = actionFlag;
    }

}
