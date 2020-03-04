package com.li.utli;

import java.net.ProtocolException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.RedirectHandler;
import org.apache.http.protocol.HttpContext;

/**
 * http://www.iteye.com/topic/690784
 * 上个星期做了一个爬虫，主要是实现手机上不通过标准浏览器的方式实现Facebook认证过程，期间遇到个问题需要手动处理redirect。 
HttpClient4.0的GET方法完全redirect，POST方法部分支持redirect，也就是说，我们在大部分情况下爬网页时中间的一些redirect过程可以当作是透明的，输入一个URL得到的是redirect后的最终结果页。 
刚好，我需要redirect过程中的一个临时页面的一些信息，而HttpClient4.0 "自作主张"地帮我忽略了，如何手动处理呢? 
结过查看其内部源码，HttpClient默认是通过DefaultRedirectHandler来管理跳转的，该类继承自接口，该接口有两个方法 

public URI getLocationURI(HttpResponse response, HttpContext context) 
throws ProtocolException; 
public boolean isRedirectRequested(HttpResponse response, 
HttpContext context); 

其中isRedirectRequested是用于判断当前的请求是否需要redirect。我们只需要定义一个自己的RedirectHandler来处理redirect就可以了，如下: 
public class DummyRedirectHandler implements RedirectHandler { 

public URI getLocationURI(HttpResponse response, HttpContext context) 
throws ProtocolException { 
// TODO Auto-generated method stub 
return null; 
} 

public boolean isRedirectRequested(HttpResponse response, 
HttpContext context) { 
// 由于我们需要手动处理所有的redirect，所以直接return false 
return false; 
} 

} 

AbstractHttpClient类setRedirectHandler方法用于设置自定义RedirectHandler实现 

httpclient.setRedirectHandler(new DummyRedirectHandler()); 

然后通过搬运捕获Header("Location")，可以取得跳转中间过程的URL，希望能帮到像我这样做爬虫天天在网上的童鞋。 
 * <b>Application describing:</b> <br>
 * @author lyh
 * @version $Revision$
 */
public class DummyRedirectHandler implements RedirectHandler {

    public boolean isRedirectRequested(HttpResponse response, HttpContext context) {
        // 由于我们需要手动处理所有的redirect，所以直接return false 
        return false;
    }

    @Override
    public URI getLocationURI(HttpResponse arg0, HttpContext arg1) throws org.apache.http.ProtocolException {
        // TODO Auto-generated method stub
        return null;
    }

}