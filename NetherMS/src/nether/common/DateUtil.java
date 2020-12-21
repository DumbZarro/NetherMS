package nether.common;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	private static DateFormat dateFormat;
	
	static {
		dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINA);
	}
	
	/**
	 * 日期转字符串
	 * @param date
	 * @return
	 */
	public static String Date2String(Date date) {
		return (date==null || date.equals(""))?"未明":dateFormat.format(date);
	}
	
	/**
	 * 字符串转日期
	 * @param str
	 * @return
	 */
	public static Date String2Date(String str) {
		try {
			return dateFormat.parse(str);			
		} catch (Exception e) {
			return null;
		}
	}
}
