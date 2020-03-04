package com.li.utli;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.li.bean.Result;
import com.win.ip.ProxyIp;

/**
 * 发送请求  不是静态的
 * @author Legend、
 *
 */

public class SendRequest2 {
Logger logger=LogManager.getLogger(SendRequest2.class);
	
	
    /**是否使用ip代理*/
    private boolean useProxy;

    /**是否使用随机浏览器请求头*/
    private boolean useRandomBrowserHeader;

    private static List<String> browserHeaders;

    private String proxyIp;

    private int proxyPort;

    private int timeoutConnection;

    private int timeoutSocket;//应该比timeoutConnection大

    /**是否设置user agent为手机访问*/
    private boolean is3g;

    /**是否打印出如：sendGet:useProxy=false,is3g=false*/
    public static boolean printIsUseProxy = true;

    public static void main(String[] args) throws Exception {

    }

    static {
        initBrowserHeaders();
    }

    public SendRequest2() {
        initBrowserHeaders();
    }

    private static void initBrowserHeaders() {
        try {
            if (browserHeaders == null) {
                browserHeaders = new ArrayList<String>();
                //String file = SendRequest2.class.getResourceAsStream("browser_header.txt");
                InputStream input = SendRequest2.class.getResourceAsStream("browser_header.txt");
                List<String> list = IOUtils.readLines(input, "utf-8");
                IOUtils.closeQuietly(input);
                for (String temp : list) {
                    if (temp == null || temp.length() < 5) {
                        continue;
                    }
                    browserHeaders.add(temp);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 每次得到的都是新new一个
     * @param proxyIp
     * @param timeoutConnection  单位秒
     * @param timeoutSocket 单位秒，应该比timeoutConnection大
     * @return
     * @author liangyh
     */
    public static SendRequest2 getInstance(ProxyIp proxyIp, int timeoutConnection, int timeoutSocket) {
        SendRequest2 send = new SendRequest2();
        send.setTimeoutConnection(1000 * timeoutConnection);
        send.setTimeoutSocket(1000 * timeoutSocket);
        if (proxyIp != null && proxyIp.getIp() != null && !StringUtils.isBlank(proxyIp.getIp())) {
            if (printIsUseProxy) {
                System.out.println("SendRequest2.getInstance():" + proxyIp.toString());
            }
            send.setUseProxy(true);
            send.setProxyIp(proxyIp.getIp().trim());
            send.setProxyPort(proxyIp.getPort());
        }
        return send;
    }

    /**
     * 每次得到的都是新new一个
     * @param proxyIp
     * @param timeoutConnection  单位秒
     * @param timeoutSocket 单位秒，应该比timeoutConnection大
     * @return
     * @author liangyh
     */
    public static SendRequest2 getInstance(ProxyIp proxyIp, boolean useRandomBrowserHeader, int timeoutConnection,
            int timeoutSocket) {
        SendRequest2 send = new SendRequest2();
        send.setTimeoutConnection(1000 * timeoutConnection);
        send.setTimeoutSocket(1000 * timeoutSocket);
        if (proxyIp != null && proxyIp.getIp() != null && !StringUtils.isBlank(proxyIp.getIp())) {
            System.out.println("SendRequest2.getInstance():" + proxyIp.toString());
            send.setUseProxy(true);
            send.setProxyIp(proxyIp.getIp().trim());
            send.setProxyPort(proxyIp.getPort());
        }

        if (useRandomBrowserHeader) {
            try {
                send.setUseRandomBrowserHeader(useRandomBrowserHeader);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return send;
    }

    /**
     * 快速测试一个url是否可用，可以使用多线程。已设置15秒为超时时间
     * @param url
     * @return true=可用
     * @author liangyh
     */
    public static boolean isUsefulUrl(String url, String charset) {
        try {
            //URL url2 = new URL(url);

            //方法1，比较快速点
            /*url2.openStream();
            System.out.println(url + "，链接存在");
            return true;*/

            //方法2，比较快速点
            /* HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
             connection.setConnectTimeout(500);
             int state = connection.getResponseCode();
             connection.disconnect();
             if (state == 200) {
                 System.out.println(url + "，链接可用");
                 return true;
             }
             System.out.println(url + "，连接打不开!");
             return false;*/

            //方法3，还是这个比较实用点啊
            SendRequest2 request2 = getInstance(null, 15, 30);
            Map<String, String> blankMap = new HashMap<String, String>();
            Result result = request2.sendGet(url, blankMap, blankMap, charset);
            int state = result.getStatusCode();
            if (state == 200) {
                System.out.println(url + "，链接可用");
                return true;
            }
            System.out.println(url + "，连接打不开!");
            return false;
        }
        catch (Exception e1) {
            System.out.println(url + "，连接打不开!");
        }
        return false;
    }

    /*static CookieSpecFactory csf = new CookieSpecFactory() {
        public CookieSpec newInstance(HttpParams params) {
            return new BrowserCompatSpec() {
                @Override
                public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
                    // Oh, I am easy
                }
            };
        }
    };*/

    /**
     * {方法功能中文描述}
     * @return
     * @author: lyh
     */
    public HttpParams setTimeOut() {
        HttpParams httpParameters = new BasicHttpParams();

        // Set the timeout in milliseconds until a connection is established.  
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

        // Set the default socket timeout (SO_TIMEOUT)   
        // in milliseconds which is the timeout for waiting for data.  
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        return httpParameters;
    }

    //这是模拟get请求
    public Result sendGet(String url, Map<String, String> headers, Map<String, String> paramsMap, String charSet,
            boolean duan) throws ClientProtocolException, IOException {
        if (printIsUseProxy) {
            System.out.println("sendGet:useProxy=" + useProxy + ",is3g=" + is3g);
        }

        //实例化一个Httpclient的
        DefaultHttpClient client = new DefaultHttpClient(setTimeOut());
        client.setRedirectHandler(new DummyRedirectHandler());
        HttpClientParams.setCookiePolicy(client.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
        client.getParams().setParameter("http.protocol.single-cookie-header", false);

        if (useProxy) {

            //HttpHost proxy = new HttpHost("localhost", 1080);
            //client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);

            HttpHost proxy = new HttpHost(proxyIp, proxyPort);
            client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
        }

        if (is3g) {
            /*client.getParams()
            .setParameter(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");*/

            headers.put(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");
        }

        if (useRandomBrowserHeader && browserHeaders != null && browserHeaders.size() > 0) {
            /*client.getParams()
            .setParameter(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");*/
            System.out.println("useRandomBrowserHeader:" + useRandomBrowserHeader + ",browserHeaders.size():"
                    + browserHeaders.size());
            Random rnd = new Random();
            headers.put("User-Agent", browserHeaders.get(rnd.nextInt(browserHeaders.size() - 1 - 0)));
        }

        //如果有参数的就拼装起来
        url = url + (null == paramsMap ? "" : assemblyParameter(paramsMap));
        //这是实例化一个get请求
        HttpGet hp = new HttpGet(url);
        //hp.set
        //如果需要头部就组装起来
        if (null != headers)
            hp.setHeaders(assemblyHeader(headers));
        //执行请求后返回一个HttpResponse

        HttpResponse response = client.execute(hp);

        //如果为true则断掉这个get请求
        if (duan)
            hp.abort();
        //返回一个HttpEntity
        HttpEntity entity = response.getEntity();
        //封装返回的参数
        Result result = new Result();
        //设置返回的cookie
        result.setCookie(assemblyCookie(client.getCookieStore().getCookies()));
        //设置返回的状态
        result.setStatusCode(response.getStatusLine().getStatusCode());
        //设置返回的头部信息
        result.setHeaders(response.getAllHeaders());
        //设置返回的信息
        result.setHttpEntity(entity);
        result.setClient(client);//add by winhong
        result.setResponse(response);//add by winhong

        return result;
    }

    public Result sendGet(String url, Map<String, String> headers, Map<String, String> paramsMap, String charSet)
            throws Exception {
        return sendGet(url, headers, paramsMap, charSet, false);
    }

    //这是模拟post请求
    public Result sendPost(String url, Map<String, String> headers, Map<String, String> paramsMap, String charSet)
            throws ClientProtocolException, IOException {
        if (printIsUseProxy) {
            System.out.println("sendPost:useProxy=" + useProxy + ",is3g=" + is3g);

        }

        //实例化一个Httpclient的
        DefaultHttpClient client = new DefaultHttpClient(setTimeOut());
        client.setRedirectHandler(new DummyRedirectHandler());
        /*HttpClientParams.setCookiePolicy(client.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
        client.getParams().setParameter("http.protocol.single-cookie-header", true);*/
        // client.getCookieSpecs().register("easy", csf);
        //client.getParams().setParameter(ClientPNames.COOKIE_POLICY, "easy");
        //HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 2000);

        if (useProxy) {

            //HttpHost proxy = new HttpHost("localhost", 1080);
            //client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);

            HttpHost proxy = new HttpHost(proxyIp, proxyPort);
            client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
        }

        if (is3g) {
            /*client.getParams()
            .setParameter(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");*/

            headers.put(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");
        }

        if (useRandomBrowserHeader && browserHeaders != null && browserHeaders.size() > 0) {
            /*client.getParams()
            .setParameter(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");*/

            Random rnd = new Random();
            headers.put("User-Agent", browserHeaders.get(rnd.nextInt(browserHeaders.size() - 1 - 0)));
        }

        //实例化一个post请求
        HttpPost post = new HttpPost(url);

        //设置需要提交的参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (String temp : paramsMap.keySet()) {
            list.add(new BasicNameValuePair(temp, paramsMap.get(temp)));
        }
        post.setEntity(new UrlEncodedFormEntity(list, charSet));

        //设置头部
        if (null != headers)
            post.setHeaders(assemblyHeader(headers));

        //实行请求并返回
        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();

        //封装返回的参数
        Result result = new Result();
        //设置返回状态代码
        result.setStatusCode(response.getStatusLine().getStatusCode());
        //设置返回的头部信息
        result.setHeaders(response.getAllHeaders());
        //设置返回的cookie信息
        result.setCookie(assemblyCookie(client.getCookieStore().getCookies()));
        //设置返回到信息
        result.setHttpEntity(entity);
        result.setClient(client);//add by winhong
        result.setResponse(response);//add by winhong
        return result;
    }

    /**
     * json body 方式 post
     * @param url
     * @param headers
     * @param paramsMap
     * @param charSet
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @author liangyh
     */
    public Result sendJsonPost(String url, Map<String, String> headers, String json, String charSet)
            throws ClientProtocolException, IOException {
        if (printIsUseProxy) {
            //System.out.println("sendJsonPost:useProxy=" + useProxy + ",is3g=" + is3g + ",json:" + json);

        }
        //实例化一个Httpclient的
        DefaultHttpClient client = new DefaultHttpClient(setTimeOut());
        client.setRedirectHandler(new DummyRedirectHandler());
        if (useProxy) {
            HttpHost proxy = new HttpHost(proxyIp, proxyPort);
            client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
        }

        if (is3g) {
            /*client.getParams()
            .setParameter(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");*/
            headers.put(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");
        }

        if (useRandomBrowserHeader && browserHeaders != null && browserHeaders.size() > 0) {
            /*client.getParams()
            .setParameter(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");*/

            Random rnd = new Random();
            headers.put("User-Agent", browserHeaders.get(rnd.nextInt(browserHeaders.size() - 1 - 0)));
        }

        //实例化一个post请求
        HttpPost post = new HttpPost(url);
        //new StringRequestEntity(json, "application/x-www-form-urlencoded", charSet);
        post.setEntity((HttpEntity) new StringEntity(json, "application/x-www-form-urlencoded", charSet));

        //设置头部
        if (null != headers)
            post.setHeaders(assemblyHeader(headers));

        //实行请求并返回
        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();

        //封装返回的参数
        Result result = new Result();
        //设置返回状态代码
        result.setStatusCode(response.getStatusLine().getStatusCode());
        //设置返回的头部信息
        result.setHeaders(response.getAllHeaders());
        //设置返回的cookie信息
        result.setCookie(assemblyCookie(client.getCookieStore().getCookies()));
        //设置返回到信息
        result.setHttpEntity(entity);
        result.setClient(client);//add by winhong
        result.setResponse(response);//add by winhong
        return result;
    }

    /**
     * 字节数据 方式 post
     * @param url
     * @param headers
     * @param paramsMap
     * @param charSet
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @author liangyh
     */
    public Result sendByteDataPost(String url, Map<String, String> headers, byte[] postData)
            throws ClientProtocolException, IOException {
        if (printIsUseProxy) {
            System.out.println("sendJsonPost:useProxy=" + useProxy + ",is3g=" + is3g);

        }
        //实例化一个Httpclient的
        DefaultHttpClient client = new DefaultHttpClient(setTimeOut());
        client.setRedirectHandler(new DummyRedirectHandler());
        if (useProxy) {
            HttpHost proxy = new HttpHost(proxyIp, proxyPort);
            client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
        }

        if (is3g) {
            /*client.getParams()
            .setParameter(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");*/
            headers.put(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");
        }

        if (useRandomBrowserHeader && browserHeaders != null && browserHeaders.size() > 0) {
            /*client.getParams()
            .setParameter(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");*/

            Random rnd = new Random();
            headers.put("User-Agent", browserHeaders.get(rnd.nextInt(browserHeaders.size() - 1 - 0)));
        }

        //实例化一个post请求
        HttpPost post = new HttpPost(url);
        //new StringRequestEntity(json, "application/x-www-form-urlencoded", charSet);
        //All Known Implementing Classes:AbstractHttpEntity, BasicHttpEntity, BufferedHttpEntity, ByteArrayEntity, EntityTemplate, FileEntity, HttpEntityWrapper, InputStreamEntity, SerializableEntity, StringEntity
        post.setEntity(new ByteArrayEntity(postData));

        //设置头部
        if (null != headers)
            post.setHeaders(assemblyHeader(headers));

        //实行请求并返回
        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();

        //封装返回的参数
        Result result = new Result();
        //设置返回状态代码
        result.setStatusCode(response.getStatusLine().getStatusCode());
        //设置返回的头部信息
        result.setHeaders(response.getAllHeaders());
        //设置返回的cookie信息
        result.setCookie(assemblyCookie(client.getCookieStore().getCookies()));
        //设置返回到信息
        result.setHttpEntity(entity);
        result.setClient(client);//add by winhong
        result.setResponse(response);//add by winhong
        return result;
    }

    /**
     * 带附件的post请求，佳缘
     * @param url
     * @param headers
     * @param paramsMap
     * @param charSet
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @author liangyh
     */
    public Result sendPostMultiple(String url, Map<String, String> headers, Map<String, String> paramsMap,
            String charSet, String filePath) throws ClientProtocolException, IOException {
        if (printIsUseProxy) {
            System.out.println("sendPostMultiple:useProxy=" + useProxy + ",is3g=" + is3g);

        }

        //实例化一个Httpclient的
        DefaultHttpClient client = new DefaultHttpClient(setTimeOut());
        client.setRedirectHandler(new DummyRedirectHandler());
        /*HttpClientParams.setCookiePolicy(client.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
        client.getParams().setParameter("http.protocol.single-cookie-header", true);*/
        // client.getCookieSpecs().register("easy", csf);
        //client.getParams().setParameter(ClientPNames.COOKIE_POLICY, "easy");
        //HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 2000);

        // 一个本地的文件
        FileBody filebody = new FileBody(new File("E:\\jocky\\123.jpg"));//"d:/BIMG1181.JPG"
        // 一个字符串
        StringBody comment = new StringBody("A binary file of some kind");
        // 多部分的实体
        MultipartEntity reqEntity = new MultipartEntity();
        // 增加
        StringBody serv_host = new StringBody("wap.jiayuan.com");
        //reqEntity.addPart("serv_host", serv_host);
        reqEntity.addPart("file", filebody);
        //reqEntity.addPart("v", new StringBody("2"));
        //reqEntity.addPart("token", new StringBody(RandomUtils.nextDouble() + ""));
        //reqEntity.addPart("pin", new StringBody(filePath));

        // 设置

        if (useProxy) {

            //HttpHost proxy = new HttpHost("localhost", 1080);
            //client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);

            HttpHost proxy = new HttpHost(proxyIp, proxyPort);
            client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
        }

        if (is3g) {
            /*client.getParams()
            .setParameter(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");*/

            headers.put(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");
        }

        if (useRandomBrowserHeader && browserHeaders != null && browserHeaders.size() > 0) {
            /*client.getParams()
            .setParameter(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");*/

            Random rnd = new Random();
            headers.put("User-Agent", browserHeaders.get(rnd.nextInt(browserHeaders.size() - 1 - 0)));
        }

        //实例化一个post请求
        HttpPost post = new HttpPost(url);

        //设置需要提交的参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (String temp : paramsMap.keySet()) {
            list.add(new BasicNameValuePair(temp, paramsMap.get(temp)));
        }

        //post.setEntity(new UrlEncodedFormEntity(list, charSet));
        post.setEntity(reqEntity);

        //设置头部
        if (null != headers)
            post.setHeaders(assemblyHeader(headers));

        //实行请求并返回
        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();

        //封装返回的参数
        Result result = new Result();
        //设置返回状态代码
        result.setStatusCode(response.getStatusLine().getStatusCode());
        //设置返回的头部信息
        result.setHeaders(response.getAllHeaders());
        //设置返回的cookie信息
        result.setCookie(assemblyCookie(client.getCookieStore().getCookies()));
        //设置返回到信息
        result.setHttpEntity(entity);
        result.setClient(client);//add by winhong
        result.setResponse(response);//add by winhong
        return result;
    }

    /** 
     * 重写验证方法，取消检测ssl 
     */
    private static TrustManager truseAllManager = new X509TrustManager() {
        public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub  
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub  
        }

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub  
            return null;
        }
    };

    //这是模拟 HTTPS  get请求
    public Result sendGetSSL(String url, Map<String, String> headers, Map<String, String> paramsMap, String charSet,
            boolean duan) throws Exception {
        if (printIsUseProxy) {
            System.out.println("sendGetSSL:useProxy=" + useProxy + ",is3g=" + is3g);

        }

        //实例化一个Httpclient的
        DefaultHttpClient client = new DefaultHttpClient(setTimeOut());
        client.setRedirectHandler(new DummyRedirectHandler());
        HttpClientParams.setCookiePolicy(client.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
        client.getParams().setParameter("http.protocol.single-cookie-header", false);

        SSLContext sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(null, new TrustManager[] { truseAllManager }, null);
        SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme https = new Scheme("https", sf, 443);
        client.getConnectionManager().getSchemeRegistry().register(https);

        if (useProxy) {

            //HttpHost proxy = new HttpHost("localhost", 1080);
            //client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);

            HttpHost proxy = new HttpHost(proxyIp, proxyPort);
            client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
        }

        if (is3g) {
            /*client.getParams()
                    .setParameter(
                            "User-Agent",
                            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");*/
            headers.put(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");
        }

        if (useRandomBrowserHeader && browserHeaders != null && browserHeaders.size() > 0) {
            /*client.getParams()
            .setParameter(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");*/

            Random rnd = new Random();
            headers.put("User-Agent", browserHeaders.get(rnd.nextInt(browserHeaders.size() - 1 - 0)));
        }

        logger.info("real request begin:");
        //如果有参数的就拼装起来
        url = url + (null == paramsMap ? "" : assemblyParameter(paramsMap));
        //这是实例化一个get请求
        HttpGet hp = new HttpGet(url);
        //如果需要头部就组装起来
        if (null != headers)
            hp.setHeaders(assemblyHeader(headers));
        //执行请求后返回一个HttpResponse

        HttpResponse response = client.execute(hp);

        //如果为true则断掉这个get请求
        if (duan)
            hp.abort();
        //返回一个HttpEntity
        HttpEntity entity = response.getEntity();
        //封装返回的参数
        Result result = new Result();
        //设置返回的cookie
        result.setCookie(assemblyCookie(client.getCookieStore().getCookies()));
        //设置返回的状态
        result.setStatusCode(response.getStatusLine().getStatusCode());
        //设置返回的头部信息
        result.setHeaders(response.getAllHeaders());
        //设置返回的信息
        result.setHttpEntity(entity);
        result.setClient(client);//add by winhong
        result.setResponse(response);//add by winhong
        logger.info("real request end.");
        return result;
    }

    //这是模拟HTTPS  SSL   post请求
    public Result sendPostSSL(String url, Map<String, String> headers, Map<String, String> paramsMap, String charSet)
            throws Exception {
        if (printIsUseProxy) {
            System.out.println("sendPostSSL:useProxy=" + useProxy + ",is3g=" + is3g);

        }

        //实例化一个Httpclient的
        DefaultHttpClient client = new DefaultHttpClient(setTimeOut());
        client.setRedirectHandler(new DummyRedirectHandler());
        /*HttpClientParams.setCookiePolicy(client.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
        client.getParams().setParameter("http.protocol.single-cookie-header", true);*/
        // client.getCookieSpecs().register("easy", csf);
        //client.getParams().setParameter(ClientPNames.COOKIE_POLICY, "easy");
        //HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 2000);

        SSLContext sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(null, new TrustManager[] { truseAllManager }, null);
        SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme https = new Scheme("https", sf, 443);
        client.getConnectionManager().getSchemeRegistry().register(https);

        if (useProxy) {

            //HttpHost proxy = new HttpHost("localhost", 1080);
            //client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);

            HttpHost proxy = new HttpHost(proxyIp, proxyPort);
            client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
        }

        if (is3g) {
            /*client.getParams()
                    .setParameter(
                            "User-Agent",
                            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");*/
            headers.put(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");
        }

        if (useRandomBrowserHeader && browserHeaders != null && browserHeaders.size() > 0) {
            /*client.getParams()
            .setParameter(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");*/

            Random rnd = new Random();
            headers.put("User-Agent", browserHeaders.get(rnd.nextInt(browserHeaders.size() - 1 - 0)));
        }

        //实例化一个post请求
        HttpPost post = new HttpPost(url);

        //设置需要提交的参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (String temp : paramsMap.keySet()) {
            list.add(new BasicNameValuePair(temp, paramsMap.get(temp)));
        }
        post.setEntity(new UrlEncodedFormEntity(list, charSet));

        //设置头部
        if (null != headers)
            post.setHeaders(assemblyHeader(headers));

        //实行请求并返回
        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();

        //封装返回的参数
        Result result = new Result();
        //设置返回状态代码
        result.setStatusCode(response.getStatusLine().getStatusCode());
        //设置返回的头部信息
        result.setHeaders(response.getAllHeaders());
        //设置返回的cookie信息
        result.setCookie(assemblyCookie(client.getCookieStore().getCookies()));
        //设置返回到信息
        result.setHttpEntity(entity);
        result.setClient(client);//add by winhong
        result.setResponse(response);//add by winhong

        return result;
    }

    //这是模拟HTTPS  SSL   post请求
    public Result sendPostSSLJson(String url, Map<String, String> headers, String json, String charSet)
            throws Exception {
        if (printIsUseProxy) {
            System.out.println("sendPostSSL:useProxy=" + useProxy + ",is3g=" + is3g + ",ip=" + this.getProxyIp()
                    + ",url=" + url);

        }

        //实例化一个Httpclient的
        DefaultHttpClient client = new DefaultHttpClient(setTimeOut());
        client.setRedirectHandler(new DummyRedirectHandler());
        /*HttpClientParams.setCookiePolicy(client.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
        client.getParams().setParameter("http.protocol.single-cookie-header", true);*/
        // client.getCookieSpecs().register("easy", csf);
        //client.getParams().setParameter(ClientPNames.COOKIE_POLICY, "easy");
        //HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 2000);

        SSLContext sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(null, new TrustManager[] { truseAllManager }, null);
        SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme https = new Scheme("https", sf, 443);
        client.getConnectionManager().getSchemeRegistry().register(https);

        if (useProxy) {

            //HttpHost proxy = new HttpHost("localhost", 1080);
            //client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);

            HttpHost proxy = new HttpHost(proxyIp, proxyPort);
            client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
        }

        if (is3g) {
            /*client.getParams()
                    .setParameter(
                            "User-Agent",
                            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");*/
            headers.put(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");
        }

        if (useRandomBrowserHeader && browserHeaders != null && browserHeaders.size() > 0) {
            /*client.getParams()
            .setParameter(
                    "User-Agent",
                    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");*/

            Random rnd = new Random();
            headers.put("User-Agent", browserHeaders.get(rnd.nextInt(browserHeaders.size() - 1 - 0)));
        }

        HttpPost post = new HttpPost(url);
        //new StringRequestEntity(json, "application/x-www-form-urlencoded", charSet);
        post.setEntity((HttpEntity) new StringEntity(json, "application/x-www-form-urlencoded", charSet));

        //设置头部
        if (null != headers)
            post.setHeaders(assemblyHeader(headers));

        //实行请求并返回
        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();

        //封装返回的参数
        Result result = new Result();
        //设置返回状态代码
        result.setStatusCode(response.getStatusLine().getStatusCode());
        //设置返回的头部信息
        result.setHeaders(response.getAllHeaders());
        //设置返回的cookie信息
        result.setCookie(assemblyCookie(client.getCookieStore().getCookies()));
        //设置返回到信息
        result.setHttpEntity(entity);
        result.setClient(client);//add by winhong
        result.setResponse(response);//add by winhong

        return result;
    }

    //这是组装头部
    public static Header[] assemblyHeader(Map<String, String> headers) {
        Header[] allHeader = new BasicHeader[headers.size()];
        int i = 0;
        for (String str : headers.keySet()) {
            allHeader[i] = new BasicHeader(str, headers.get(str));
            i++;
        }
        return allHeader;
    }

    //这是组装cookie
    public static String assemblyCookie(List<Cookie> cookies) {
        StringBuffer sbu = new StringBuffer();
        for (Cookie cookie : cookies) {
            sbu.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
        }
        if (sbu.length() > 0)
            sbu.deleteCharAt(sbu.length() - 1);
        return sbu.toString();
    }

    //这是组装参数
    public static String assemblyParameter(Map<String, String> parameters) {
        String para = "?";
        for (String str : parameters.keySet()) {
            para += str + "=" + parameters.get(str) + "&";
        }
        return para.substring(0, para.length() - 1);
    }

    public boolean isUseProxy() {
        return useProxy;
    }

    public void setUseProxy(boolean useProxy) {
        this.useProxy = useProxy;
    }

    public String getProxyIp() {
        return proxyIp;
    }

    public void setProxyIp(String proxyIp) {
        this.proxyIp = proxyIp;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public int getTimeoutConnection() {
        return timeoutConnection;
    }

    public void setTimeoutConnection(int timeoutConnection) {
        this.timeoutConnection = timeoutConnection;
    }

    public int getTimeoutSocket() {
        return timeoutSocket;
    }

    public void setTimeoutSocket(int timeoutSocket) {
        this.timeoutSocket = timeoutSocket;
    }

    public boolean isIs3g() {
        return is3g;
    }

    public void setIs3g(boolean is3g) {
        this.is3g = is3g;
    }

    public boolean isUseRandomBrowserHeader() {
        return useRandomBrowserHeader;
    }

    public void setUseRandomBrowserHeader(boolean useRandomBrowserHeader) {
        this.useRandomBrowserHeader = useRandomBrowserHeader;
    }
}
