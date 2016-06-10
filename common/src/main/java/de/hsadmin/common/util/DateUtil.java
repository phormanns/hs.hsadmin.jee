package de.hsadmin.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	final static public DateFormat DEFAULT_DATEFORMAT = new SimpleDateFormat("dd.MM.yy");
	
	private final Calendar instance;

	private DateUtil() {
		instance = Calendar.getInstance();
		setTimeToNull(instance);
	}

	public static DateUtil getInstance(final Date dateValue) {
		final DateUtil dateUtil = new DateUtil();
		dateUtil.setDate(dateValue);
		return dateUtil;
	}

	public void setDate(final Date dateValue) {
		instance.setTime(dateValue);
		setTimeToNull(instance);
	}

	public Date yearsLater(final int years) {
		instance.add(Calendar.YEAR, years);
		return instance.getTime();
	}

	private void setTimeToNull(final Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

}
