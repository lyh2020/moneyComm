package com.win.share.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class WinUtils {
    private static final Logger log = LogManager.getLogger(WinUtils.class);

    public static final Gson gson = new Gson();

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    /**
     * if (logger.isDebugEnabled()) {
            logger.debug(logInfo);
        }
     * @param logger
     * @param logInfo
     * @author: lyh
     */
    public static void debug(Logger logger, String logInfo) {
        if (logger.isDebugEnabled()) {
            logger.debug(logInfo);
        }
    }

    /**
     *  Thread.sleep(1000 * second);
     * @param second  单位为秒
     * @param sysExit  sleep后系统是否退出  true=是
     * @throws Exception
     * @author liangyh
     */
    public static void sleep(int second, boolean sysExit) {
        try {
            if (sysExit) {
                System.out.println(second + "秒后自动关闭本窗口");
                for (int i = second; i > 0; i--) {
                    Thread.sleep(1000);
                    System.out.println(i - 1);
                }
                System.exit(0);
            }
            else {
                Thread.sleep(1000 * second);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 等待线程池执行完毕，或者设定的时间超时
     * @param pool
     * @param waitMinute 超时时间设定 单位分钟
     * @throws Exception
     * @author liangyh
     */
    public static void waitTimeout(ExecutorService pool, int waitMinute) throws Exception {
        pool.shutdown();
        pool.awaitTermination(waitMinute, TimeUnit.MINUTES);
        while (!pool.isTerminated()) {
        }
    }
}
