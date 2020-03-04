package com.win.share.util;

/**
 * 
 * @author zhenzi
 * 2011-8-9 下午01:58:09
 */
public final class CometConstant {
    /**通道标识：消息发送者*/
    public static final String APP_CHANNEL_SENDER = "sender";

    //jiayuan
    /**jy adv login client*/
    public static final String JY_ADV_LOGIN_CLIENT = "jy_adv_login_client";

    /**jy_adv_stop_order */
    public static final String JY_ADV_STOP_ORDER = "jy_adv_stop_order";

    /**jy adv search client*/
    public static final String JY_ADV_SEARCH_CLIENT = "jy_adv_search_client";

    /**jy adv search client*/
    public static final String JY_ADV_SEARCH_NEW_REG_CLIENT = "jy_adv_search_new_reg_client";

    //百合
    /**bh adv login client*/
    public static final String BH_ADV_LOGIN_CLIENT = "bh_adv_login_client";

    /**bh_adv_stop_order */
    public static final String BH_ADV_STOP_ORDER = "bh_adv_stop_order";

    /**bh adv search client*/
    public static final String BH_ADV_SEARCH_CLIENT = "bh_adv_search_client";

    //有缘
    /**yy adv login client*/
    public static final String YY_ADV_LOGIN_CLIENT = "yy_adv_login_client";

    /**yy_adv_stop_order */
    public static final String YY_ADV_STOP_ORDER = "yy_adv_stop_order";

    /**yy adv search client*/
    public static final String YY_ADV_SEARCH_CLIENT = "yy_adv_search_client";

    //知己
    /**zj adv login client*/
    public static final String ZJ_ADV_LOGIN_CLIENT = "zj_adv_login_client";

    /**zj_adv_stop_order */
    public static final String ZJ_ADV_STOP_ORDER = "zj_adv_stop_order";

    /**zj adv search client*/
    public static final String ZJ_ADV_SEARCH_CLIENT = "zj_adv_search_client";

    /**client_stop_order */
    public static final String CLIENT_STOP_ORDER = "client_stop_order";

    //////////////////////////////////////////////////////////////////////////////////

    /**心跳*/
    public static final String HEAT_BEAT = "heat_beat";

    /**停止指令*/
    public static final String STOP_ORDER = "stop_order";

    /**服务端拒绝连接*/
    public static final String SERVER_REFUSE = "server_refuse";

    /**其它类型的新消息*/
    public static final String OTHER_NEW_MESSAGE = "other_new_message";

    /**客户端第一次发起连接*/
    public static final String CLIENT_FIRST_CONNECT = "501";

    /*public static final String DISCARD_MESSAGE = "203";//当客户端断开连接后，服务端会记录下来丢弃消息的开始时间

    public static final String CONNECT_REACH_MAX_TIME = "101";//连接到达最大时间，服务端主动断开

    public static final String SERVER_DEPLOY = "102";//服务端在发布

    public static final String SERVER_REHASH = "103";//服务端负载不均衡了，断开所有的客户端重连

    public static final String CLIENT_KICKOFF = "104";//对于重复的连接，服务端用新的连接替换掉旧的连接

    

    public static final String RECONNECT = "500";//客户端主动重连,或者出现了异常需要重连
    
    public static final String ERR_MSG_HEADER = "errmsg";

    public static final String PARAM_APPKEY = "app_key";

    public static final String PARAM_USERID = "user";

    public static final String PARAM_CONNECT_ID = "id";

    public static final String PARAM_TIMESTAMP = "timestamp";

    public static final String PARAM_SIGN = "sign";

    //code
    public static final String CONNECT_SUCCESS = "200";//连接成功的code
    */
    public static final String SERVER_KICKOFF = "105";//由于消息量太大，而isv接收的速度太慢，服务端断开isv的连接

}
