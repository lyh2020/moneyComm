package com.win.share.vo;

import java.util.HashMap;
import java.util.Map;

/**
 *  内存session对象
 * <b>Application describing:</b> <br>
 * @author liangyh
 * @version $Revision$
 */
public class CometSession {
    public static Map<String, CometSession> sessionMap = new HashMap<String, CometSession>();

    /**key=clientId,value=ConnectionId，主要是方便根据对方得到另外一个值*/
    public static Map<String, String> clientIdConnIdMap = new HashMap<String, String>();

    private static CometSession u = new CometSession();//只用于client端的，服务器端是多个，每个都要保存在sessionMap

    /**
     * 只用于client端的，服务器端是多个，每个都要保存在sessionMap
     * @return
     * @author: lyh
     */
    public static CometSession getInstance() {
        if (u == null) {
            u = new CometSession();
        }
        return u;
    }

    private Integer clientId;

    private String idSecret;

    private String serverPath;

    private int clientSearchThreads;

    /**连接id*/
    private String cid;

    private String connectUrl;

    /**client 请求 server的参数：一般会有clientId,idSecret,cid*/
    private Map<String, String> reqParams;

    public static Map<String, String> blankMap = new HashMap<String, String>();

    private Baihe baihe;

    private JiaYuan jiaYuan;

    private Youyuan youyuan;

    private Zhiji zhiji;

    public String getIdSecret() {
        return idSecret;
    }

    public void setIdSecret(String idSecret) {
        this.idSecret = idSecret;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public Map<String, String> getReqParams() {
        return reqParams;
    }

    public void setReqParams(Map<String, String> reqParams) {
        this.reqParams = reqParams;
    }

    public String getConnectUrl() {
        return connectUrl;
    }

    public void setConnectUrl(String connectUrl) {
        this.connectUrl = connectUrl;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public JiaYuan getJiaYuan() {
        return jiaYuan;
    }

    public void setJiaYuan(JiaYuan jiaYuan) {
        this.jiaYuan = jiaYuan;
    }

    public int getClientSearchThreads() {
        return clientSearchThreads;
    }

    public void setClientSearchThreads(int clientSearchThreads) {
        this.clientSearchThreads = clientSearchThreads;
    }

    public Baihe getBaihe() {
        return baihe;
    }

    public void setBaihe(Baihe baihe) {
        this.baihe = baihe;
    }

    public Youyuan getYouyuan() {
        return youyuan;
    }

    public void setYouyuan(Youyuan youyuan) {
        this.youyuan = youyuan;
    }

    public Zhiji getZhiji() {
        return zhiji;
    }

    public void setZhiji(Zhiji zhiji) {
        this.zhiji = zhiji;
    }

}
