package com.li.bean;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * 封住请求返回的参数 ddddd
 * @author Legend、
 */
public class Result {
    //result.getcontent()是gzip的转换成string的方法   t
    //EntityUtils.toString(new GzipDecompressingEntity(responseEntity)); 
    //String t = EntityUtils.toString(new GzipDecompressingEntity(result.getHttpEntity()), "utf-8");
    private String cookie;

    private int statusCode;

    private HashMap<String, Header> headerAll;

    private HttpEntity httpEntity;

    private DefaultHttpClient client;

    private String charSet;

    /**请求后得到的网页源码内容，不经处理的原样返回*/
    private String content;

    /**请求后得到的网页源码内容，替换掉回车的*/
    private String contentStr;

    /**请求后得到的网页源码内容，按回车分割*/
    private String contentArr[];

    private HttpResponse response;

    public static void main(String[] args) {
        String cookieSrc = "JSESSIONID=abc5HWw1KhVo3-x-noR_v; ipOfflineCityCode=10101208; guideFlag77205061=1; ipCityCode=10101208; isFirstLoadPage=1; guideFlag77412917=1; guideFlag80260345=1; firstCookie_80260345=1; guideFlag108892209=1; zxr_index_108892209=5; hasShowTip=1; popFlag520#108892209=1; popFlag520#79195836=1; zxr_index_79195836=7; zxr_index_108891482=4; popFlag520#80174114=1; zxr_index_80174114=2; popFlag520#8071859=1; zxr_index_8071859=3; popFlag520#80766663=1; zxr_index_80766663=3; validate_77466447=yes; smail_77466447=yes; zxr_index_77466447=2; validate_80238091=yes; smail_80238091=yes; zxr_index_80238091=2; Hm_lvt_2c8ad67df9e787ad29dbd54ee608f5d2=1510073775,1510074150,1510153614,1510406134; otherinfo=%5E%7Eisnew%3D1%5E%7E; dgpw=0; login_health=62b257fce772676ac60840883893aea7757612f0cd41de3bf5c789c666fb76a0c79c41414ba3c0e578b9b7f1daed474fd5d4abb5c1eb5ee5ed8fbd012a4fc03d; rmpwd=%5E%7Eloginmode%3D9%5E%7Elogininfo%3D15539735169%5E%7E; preLG_80260345=2017-11-02+23%3A56%3A16; preLG_80766663=2017-11-11+19%3A49%3A07; preLG_78565286=2015-05-08+14%3A05%3A38; preLG_78087724=2015-04-26+12%3A30%3A26; preLG_80260733=2017-10-11+23%3A58%3A11; preLG_78508350=2015-05-06+23%3A12%3A08; preLG_39216386=2015-10-17+21%3A05%3A18; preLG_40552149=2015-10-26+20%3A16%3A10; preLG_79564903=2015-06-02+23%3A49%3A48; preLG_78116880=2016-05-11+23%3A22%3A00; preLG_78503297=2017-08-31+00%3A12%3A40; preLG_78585518=2015-05-08+22%3A55%3A25; preLG_80766260=2017-11-08+00%3A55%3A17; preLG_78087236=2016-05-11+23%3A20%3A25; preLG_79193458=2015-05-24+12%3A54%3A07; preLG_78197967=2015-04-28+23%3A49%3A08; preLG_77036402=2015-11-24+10%3A32%3A11; sid=0SkOGbnVbdPk4dIvKiTS; CHANNEL=^~refererHost=^~channelId=903404^~subid=2^~; Hm_lpvt_2c8ad67df9e787ad29dbd54ee608f5d2=1510424175; isSignOut=%5E%7ElastLoginActionTime%3D1510424192603%5E%7E; preLG_108892209=2017-11-11+20%3A50%3A53; mid=%5E%7Emid%3D80238091%5E%7E; loginactiontime=%5E%7Eloginactiontime%3D1510424192604%5E%7E; logininfo=%5E%7Elogininfo%3D15539735169%5E%7E; hds=2; live800=%5E%7EinfoValue%3DuserId%253D80238091%2526name%253D80238091%2526memo%253D%5E%7E; ooo=%5E%7Esex%3D1%5E%7EworkCity%3D10115085%5E%7Eage2%3D46%5E%7Eage1%3D31%5E%7E; bottomRemind=%5E%7EisAuthGzt%3Dfalse%5E%7EvisPhoto%3Dno%5E%7E; isvalideEmail=%5E%7EvalideEmail%3D0%5E%7E; __xsptplus14=14.4.1510422478.1510422604.3%234%7C%7C%7C%7C%7C%23%23tCzJK3SrpQNmGWX0wH0lT3N2HeUMb8t5%23; preLG_79195836=2015-05-24+13%3A58%3A47; preLG_80174114=2015-06-19+21%3A44%3A13; LOGIN_FIRST80174114=%5E%7Elogincount%3D%5E%7E; sina=; sinaccess=; kaixinaccess=; pptflogin=; baidu=; preLG_8071859=2016-10-28+11%3A43%3A33; p=%5E%7Eworkcity%3D10115085%5E%7Elh%3D80238091%5E%7Esex%3D0%5E%7Enickname%3D%E4%BC%9A%E5%91%9880238091%5E%7Emt%3D1%5E%7Eage%3D43%5E%7Edby%3Ddf94d59d274aa1a3%5E%7E; LOGIN_FIRST80766663=%5E%7Elogincount%3D%5E%7E; preLG_77466447=2017-11-11+22%3A34%3A58; LOGIN_FIRST77466447=%5E%7Elogincount%3D%5E%7E; preLG_80238091=2015-06-21+20%3A19%3A46";
        cookieSrc = "ipOfflineCityCode=10101208; __xsptplus14=14.7.1510416301.1510421318.29%234%7C%7C%7C%7C%7C%23%236VnfWHrK9uWFvV9H6JMEl7qC4hoFzYnN%23; gr_user_id=9d2cf586-d35a-48e8-8b7c-1eca8de1f4d8; rmpwd=%5E%7Eloginmode%3D9%5E%7Elogininfo%3D18374124335%5E%7E; remember=2aeaa33fc1dccf32f5462d8c592fce4e; ipCityCode=10101208; Hm_lvt_2c8ad67df9e787ad29dbd54ee608f5d2=1510242020,1510313760,1510403495; login_health=7ec593470c687ca074dfd2a06963fbdb27a92f41bc2d40b46e8c036a93511449a587006f8801e7e7b41b860ee0248ed436c167239f7ff8c35f1a60e9eecf6e8c; preLG_79471197=2015-05-31+16%3A36%3A03; otherinfo=%5E%7Eisnew%3D1%5E%7E; dgpw=0; isFirstLoadPage=1; preLG_78149533=2015-04-27+21%3A37%3A39; preLG_77465404=2015-04-12+12%3A11%3A14; guideFlag77465404=1; firstCookie_77465404=1; sid=G65E80ISX5FTsWg3THJh; CHANNEL=^~refererHost=^~channelId=903404^~subid=2^~; Hm_lpvt_2c8ad67df9e787ad29dbd54ee608f5d2=1510421620; isSignOut=%5E%7ElastLoginActionTime%3D1510421744051%5E%7E; p=%5E%7Eworkcity%3D10122011%5E%7Elh%3D80236716%5E%7Esex%3D0%5E%7Enickname%3D%E5%9C%9F%E8%B1%86%5E%7Emt%3D1%5E%7Eage%3D44%5E%7Edby%3Ddf9406336671e639%5E%7E; JSESSIONID=abcjvBvo-SEfaerVpeR_v; preLG_79446071=2015-05-30+23%3A48%3A22; mid=%5E%7Emid%3D80236716%5E%7E; loginactiontime=%5E%7Eloginactiontime%3D1510421744051%5E%7E; logininfo=%5E%7Elogininfo%3D18374124335%5E%7E; hds=2; live800=%5E%7EinfoValue%3DuserId%253D80236716%2526name%253D80236716%2526memo%253D%5E%7E; ooo=%5E%7Esex%3D1%5E%7EworkCity%3D10122011%5E%7Eage2%3D46%5E%7Eage1%3D31%5E%7E; bottomRemind=%5E%7EisAuthGzt%3Dfalse%5E%7EvisPhoto%3Dno%5E%7E; isvalideEmail=%5E%7EvalideEmail%3D0%5E%7E; popFlag520#79446071=1; zxr_index_79446071=8; comedGame79446071=1; LOGIN_FIRST79446071=%5E%7Elogincount%3D%5E%7E; sina=; sinaccess=; kaixinaccess=; pptflogin=; baidu=; preLG_77466447=2016-03-21+16%3A51%3A06; guideFlag77466447=1; zxr_index_77466447=4; popFlag520#77466447=1; validate_77466447=yes; smail_77466447=yes; LOGIN_FIRST77466447=%5E%7Elogincount%3D%5E%7E; preLG_80236716=2015-06-21+19%3A38%3A38; validate_80236716=yes; smail_80236716=yes; zxr_index_80236716=2";
        String dd = new Result().getCookieByName(cookieSrc, "p");

        System.out.println(dd);
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public HashMap<String, Header> getHeaders() {
        return headerAll;
    }

    public void setHeaders(Header[] headers) {
        headerAll = new HashMap<String, Header>();
        for (Header header : headers) {
            headerAll.put(header.getName(), header);
        }
    }

    public HttpEntity getHttpEntity() {
        return httpEntity;
    }

    public void setHttpEntity(HttpEntity httpEntity) {
        this.httpEntity = httpEntity;
    }

    public DefaultHttpClient getClient() {
        return client;
    }

    public void setClient(DefaultHttpClient client) {
        this.client = client;
    }

    /**
     * 请求后得到的网页源码内容，不经处理的原样返回
     * @return
     * @author liangyh
     */
    public String getContent() {
        try {
            this.content = EntityUtils.toString(this.httpEntity, charSet == null ? "utf-8" : charSet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 返回经过replaceAll("(\r\n)*", "")的一行str
     * @return
     * @author liangyh
     */
    public String getContentStr() {
        try {
            this.contentStr = EntityUtils.toString(this.httpEntity, charSet == null ? "utf-8" : charSet).replaceAll(
                    "(\r\n)*", "");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return contentStr;
    }

    public void setContentStr(String contentStr) {
        this.contentStr = contentStr;
    }

    /**
     * 返回经过split("\n")分割的多行arr
     * @return
     * @author liangyh
     */
    public String[] getContentArr() {
        try {
            String jsonRet = EntityUtils.toString(httpEntity, charSet == null ? "utf-8" : charSet);
            contentArr = jsonRet.split("\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return contentArr;
    }

    /**
     * 返回经过split("\n")分割的多行arr
     * @param srcContent 未分割的content string
     * @return
     * @author liangyh
     */
    public String[] getContentArr(StringBuffer srcContent) {
        try {
            String jsonRet = EntityUtils.toString(httpEntity, charSet == null ? "utf-8" : charSet);
            srcContent.append(jsonRet);
            contentArr = jsonRet.split("\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return contentArr;
    }

    public void setContentArr(String[] contentArr) {
        this.contentArr = contentArr;
    }

    /**
     * 取得header 中的location，如果有的话
     * @return
     * @author: lyh
     */
    public String getLocation() {
        Header header = null;
        HashMap<String, Header> headHashMap = this.getHeaders();
        if (headHashMap != null) {
            header = headHashMap.get("Location");
        }

        return (header == null ? "" : header.getValue());
    }

    /**
     * 根据cookie name 取得相应的cookie值
     * @param cookieSrc  可以为空
     * @param cookieName
     * @return
     * @author: lyh
     */
    public String getCookieByName(String cookieSrc, String cookieName) {
        try {
            // AuthCookie=4BFFD62B611D896E6BAE9E3BED7CC2F7194799A74533144728747ABD9B7EB686BF0E83120CCB011E99A623C400A34B158AEDBBADC89F6FEFA057F29D2CB9D58DA6007AE70E7860D5AFB8B7FAAA11EAAB657CA808FA71C17F927D90F620F527C575645DB4D988DEA8;Domain=baihe.com;Expires=Mon%2C+31-Dec-2063+14%3A26%3A07+GMT;GCEmail=13499588150%40mobile.baihe.com;GCUserID=87228091;Hm_lpvt_67f7e4ac926d21bbfd588ec02b69b70a=1389536766;Hm_lvt_67f7e4ac926d21bbfd588ec02b69b70a=1389536766;JSESSIONID=D0007F9FFEC7785927DBD7D24626D641;LoginEmail=13499588150%40mobile.baihe.com;PHPSESSID=33f699b8ce117963656261d84ae38f49;Path=%2F;Token=87228091%7CCA33255E178C5F26716A5D42BA4451F4;gender=false;logintime=1389536766;msgcount=16;name=PKDXe;pass=gU02AgFCl%2Fyc8jF7qIubEQ%23%23;uid=87228091;userBaihe_channel=3g;userCity_level=1
            String cookie = StringUtils.isEmpty(cookieSrc) ? this.getCookie() : cookieSrc;
            if (!cookie.contains(";")) {
                return "";
            }

            String match = cookieName + "=";
            String arr[] = cookie.split(";");
            for (String temp : arr) {
                if (temp.contains(match)) {
                    if (temp.startsWith(" " + match) || temp.startsWith(match)) {
                        return temp.split(match)[1];
                    }
                    continue;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 打印所有相关信息，打印完毕后系统退出
     * @param exit true=执行完毕后系统退出，false=不退出
     * @param printInLine 按照一行行的打印出来
     * @author: lyh
     */
    public void printAll(boolean exit, boolean printInLine) {
        System.err.println("Cookie:" + this.getCookie());
        Header header = this.getHeaders().get("Location");
        System.err.println("Location:" + (header == null ? "" : header.getValue()));
        if (printInLine) {
            String temp[] = this.getContentArr();
            for (String str : temp) {
                System.out.println(str);
            }
        }
        else {
            System.err.println("content:" + this.getContentStr());
        }

        if (exit) {
            System.exit(0);
        }
    }

    /**
     * 打印所有相关信息
     * @author: lyh
     */
    public void printAll() {
        System.err.println(Thread.currentThread().getName()+"->"+"Cookie:" + this.getCookie());
        Header header = this.getHeaders().get("Location");
        System.err.println(Thread.currentThread().getName()+"->"+"Location:" + (header == null ? "" : header.getValue()));
        String temp[] = this.getContentArr();
        for (String str : temp) {
            System.out.println(Thread.currentThread().getName()+"->Content:"+str);
        }
    }

    /**
     * 打印cookie,location相关信息，打印完毕后系统退出
     * @param exit true=执行完毕后系统退出，false=不退出
     * @author: lyh
     */
    public void printTwo(boolean exit) {
        System.err.println("Cookie:" + this.getCookie());
        Header header = this.getHeaders().get("Location");
        System.err.println("Location:" + (header == null ? "" : header.getValue()));

        if (exit) {
            System.exit(0);
        }
    }

    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

}
