package com.win.share.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvVo {

    /**登录url*/
    public String loginUrl;

    /**登录账号*/
    public String userName;

    /**登录密码*/

    public String userPass;

    /**是否已经登录*/
    public boolean isLogin;

    /**登录用户ID*/
    public String uid;

    /**登录cookie*/
    public String cookie;

    /**用于个别网站，返回的是encode的，变换成decode放这里*/
    public String decodeCookie;

    /**登录提示信息返回*/
    public String loginInfoRet;

    ////////////////////////////////////////////////////////////////////////////////////

    /**搜索url*/
    public String searchUrl;

    /**在post的时候才要注意吧*/
    public String charSet = "utf-8";

    /**web site请求头参数*/
    public Map<String, String> headersWeb = new HashMap<String, String>();

    /**3g site请求头参数*/
    public Map<String, String> headersG3 = new HashMap<String, String>();

    /**设置提交所有的参数。用工具httpwatch得到需要提交哪些参数*/
    public Map<String, String> paramsMap = new HashMap<String, String>();

    /**设置提交所有的参数。用工具httpwatch得到需要提交哪些参数*/
    public Map<String, Object> paramsJson = new HashMap<String, Object>();

    /**空白的map，不put值,永远都是空白的，只是为了方便使用，可以作为blankParamsMap来使用*/
    public static Map<String, String> blankMap = new HashMap<String, String>();

    public static Map<String, String> headersWeb360 = new HashMap<String, String>();
    static {
        headersWeb360.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////adv begin
    /**为了便于Collection.sort()，所以用Long*/
    public List<Long> searchIdList = new ArrayList<Long>();

    /**总共的符合的搜索页数*/
    public int searchTotalPage;

    /**搜索线程数*/
    public Integer searchThreads;

    /**搜索的id下限*/
    public Long minId;

    /**搜索的id上限 */
    public Long maxId;

    /**数据库中的已经发送过的用户ID */
    public StringBuffer uidsInDb;

    /**需要搜索的个数，页面设置的搜索个数 */
    public Integer needSearchCount;

    /**当前已经搜索到的个数 */
    public int currentSearchCount;

    /**当前成功发信的个数 */
    public int currentSendCount;

    /**当前有多少个是属于ID不存在的情况 */
    public int currentNotExistIdCount;

    /**搜索条件 保存 */
    public String advSearchCondition;

    /**是否正在执行中，应该用这个作为通用:true=是正在执行，false=停止 */
    public boolean isAdvDoing;

    /**0=可以，-1=需要重新登录官网账号，-2=需要开通会员*/
    public int canWriteMsg;

    /**搜索的年龄最小值，如18*/
    public int searchMinAge;

    /**搜索的年龄最大值，如38*/
    public int searchMaxAge;

    /**搜索的学历，如30*/
    public int searchDegree;

    /**搜索学历以上*/
    public boolean searchDegreeAbove;

    /**搜索的月薪，如20*/
    public int searchIncome;

    /**搜索月薪以上*/
    public boolean searchIncomeAbove;

    /**搜索是有照片的*/
    public boolean searchHavePic;

    /**当出现多少个ID不存在的情况下，停止搜索*/
    public int idNotExistMaxCount;

    /**需要搜索性别*/
    public String searchGender;

    public int searchMarriage;

    public String searchProvince;

    /**不在搜索范围内的地区，也就是排除的，不要这些地区的*/
    public String excludeSearchProvince;

    public String searchCity;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////adv end

}
