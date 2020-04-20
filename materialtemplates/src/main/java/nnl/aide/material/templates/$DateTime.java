package nnl.aide.material.templates;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class $DateTime
{
	public static final long TICKS_PER_SECOND = 1000L;
	public static final long TICKS_PER_MINUTE = 60000L;
	public static final long TICKS_PER_HOUR = 3600000L;
	public static final long TICKS_PER_DAY = 86400000L;
	private Date date;
	private static $DateTime _instance;
	private static TimeZone zeroTimeZone = new SimpleTimeZone(0, "13256");
	private Calendar cal;
	private SimpleDateFormat dateFormat;
	private SimpleDateFormat timeFormat;
	private TimeZone timeZone;

	private static $DateTime getInstance()
	{
		if (_instance == null)
			_instance = new $DateTime();

		return _instance;
	}

	private $DateTime()
	{
		this.cal = Calendar.getInstance(Locale.US);
		this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		this.dateFormat.setLenient(false);
		this.timeFormat = new SimpleDateFormat("HH:mm:ss");
		this.timeFormat.setLenient(false);
		this.date = new Date();
		this.timeZone = TimeZone.getDefault();
	}

	public static long getNow()
	{
		return System.currentTimeMillis();
	}

	public static String date(long ticks)
	{
		$DateTime d = getInstance();
		d.date.setTime(ticks);
		return d.dateFormat.format(d.date);
	}

	public static String time(long ticks)
	{
		$DateTime d = getInstance();
		d.date.setTime(ticks);
		return d.timeFormat.format(d.date);
	}

	public static String getTimeFormat()
	{
		return getInstance().timeFormat.toPattern();
	}

	public static void setTimeFormat(String pattern) {
		getInstance().timeFormat.applyPattern(pattern);
	}

	public static String getDateFormat()
	{
		return getInstance().dateFormat.toPattern(); }

	public static void setDateFormat(String pattern) {
		getInstance().dateFormat.applyPattern(pattern);
	}

	public static long dateParse(String date)
	{
		try{
			return getInstance().dateFormat.parse(date).getTime();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	public static String getDeviceDefaultDateFormat()
	{
		SimpleDateFormat sdf = (SimpleDateFormat)DateFormat.getDateInstance();
		return sdf.toPattern();
	}

	public static String getDeviceDefaultTimeFormat()
	{
		SimpleDateFormat sdf = (SimpleDateFormat)DateFormat.getTimeInstance();
		return sdf.toPattern();
	}

	public static long timeParse(String time)
	{
		try{
			SimpleDateFormat tf = getInstance().timeFormat;
			tf.setTimeZone(zeroTimeZone);
			long time2 = tf.parse(time).getTime();
			tf.setTimeZone(getInstance().timeZone);
			long offsetInMinutes = Math.round(getTimeZoneOffset() * 60.0D);
			long dayStartInUserTimeZone = System.currentTimeMillis() + offsetInMinutes * 60000L;
			dayStartInUserTimeZone -= dayStartInUserTimeZone % 86400000L;
			dayStartInUserTimeZone -= offsetInMinutes * 60000L;
			return (dayStartInUserTimeZone + time2 % 86400000L);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	public static long dateTimeParse(String date, String time)
	{
		SimpleDateFormat df = getInstance().dateFormat;
		SimpleDateFormat tf = getInstance().timeFormat;
		df.setTimeZone(zeroTimeZone);
		tf.setTimeZone(zeroTimeZone);
		try
		{
			long dd = dateParse(date);
			long tt = tf.parse(time).getTime();
			long total = dd + tt;

			int endShift = (int)(getTimeZoneOffsetAt(total) * 3600000.0D);
			total -= endShift;
			int startShift = (int)(getTimeZoneOffsetAt(total) * 3600000.0D);
			total += endShift - startShift;
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}

		finally {
			tf.setTimeZone(getInstance().timeZone);
			df.setTimeZone(getInstance().timeZone);
		}
	}

	public static void setTimeZone(int offsetHours)
	{
		getInstance().timeZone = new SimpleTimeZone(offsetHours * 3600 * 1000, "");
		getInstance().cal.setTimeZone(getInstance().timeZone);
		getInstance().dateFormat.setTimeZone(getInstance().timeZone);
		getInstance().timeFormat.setTimeZone(getInstance().timeZone);
	}

	public static double getTimeZoneOffset()
	{
		return (getInstance().timeZone.getOffset(System.currentTimeMillis()) / 3600000.0D);
	}

	public static double getTimeZoneOffsetAt(long date)
	{
		double d = getInstance().timeZone.getOffset(date) / 3600000.0D;
		return d;
	}

	public static int getYear(long ticks)
	{
		getInstance().cal.setTimeInMillis(ticks);
		return getInstance().cal.get(1);
	}

	public static int getMonth(long ticks)
	{
		getInstance().cal.setTimeInMillis(ticks);
		return (getInstance().cal.get(2) + 1);
	}

	public static int getDayOfMonth(long ticks)
	{
		getInstance().cal.setTimeInMillis(ticks);
		return getInstance().cal.get(5);
	}

	public static int getDayOfYear(long ticks)
	{
		getInstance().cal.setTimeInMillis(ticks);
		return getInstance().cal.get(6);
	}

	public static int getDayOfWeek(long ticks)
	{
		getInstance().cal.setTimeInMillis(ticks);
		return getInstance().cal.get(7);

	}

	public static int getHour(long ticks)
	{
		getInstance().cal.setTimeInMillis(ticks);
		return getInstance().cal.get(11);
	}

	public static int getSecond(long ticks)
	{
		getInstance().cal.setTimeInMillis(ticks);
		return getInstance().cal.get(13);
	}

	public static int getMinute(long ticks)
	{
		getInstance().cal.setTimeInMillis(ticks);
		return getInstance().cal.get(12);
	}

	public static long add(long ticks, int years, int months, int days)
	{
		Calendar c = getInstance().cal;
		c.setTimeInMillis(ticks);
		c.add(1, years);
		c.add(2, months);
		c.add(6, days);
		return c.getTimeInMillis();
	}
}

