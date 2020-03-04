package com.win.share.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class DateUtils {
	private static Logger logger = LogManager.getLogger(DateUtils.class);

	public static void main(String[] args) throws Exception {
		DateUtils.checkRobTime("15:46:59.333", 0.2,true);

	}

	/**
	 * 
	 * @param robTime 需要检测等待的时间点,如15:46:59.333，只需要时间，不需要日期，日期是自动取当前日期的
	 * @param sleepSecond 未到时候循环等待中的秒数
	 * @param debug 是否打印循环等待中的秒数信息
	 * @return
	 * @throws Exception
	 */
	public static boolean checkRobTime(String robTime, double sleepSecond,boolean debug) throws Exception {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = sdf.format(calendar.getTime());
		robTime = currentDate + " " + robTime;
		logger.info("loop check robTime:" + robTime);
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date robDate = sdf.parse(robTime);

		while (true) {
			calendar = Calendar.getInstance();
			Date curDate = calendar.getTime();
			if (robDate.before(curDate)) {
				logger.info("it is rob time, action!!!!! ");
				return true;
			}
			double d = 1000 * sleepSecond;
			long l = (long) d;
			Thread.sleep(l);
			if (debug) {
				System.out.println("未到时间,等待" + l+"毫秒");
			}
		}
	}

}
