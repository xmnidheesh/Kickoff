package com.jn.kickoff.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

public class UtilValidate {

	public static boolean isEmpty(int value) {
		if (value == 0)
			return true;

		return false;
	}

	public static boolean isEmpty(String value) {
		if (value == null)
			return true;

		if (value.trim().length() == 0) {
			return true;
		}

		return false;
	}

	public static boolean isEmpty(Long value) {
		if (value == null)
			return true;

		if (value == 0L) {
			return true;
		}

		return false;
	}

	public static boolean isEmpty(Object[] value) {
		if (value == null)
			return true;

		if (value.length == 0) {
			return true;
		}

		return false;
	}

	public static boolean isEmpty(byte[] value) {
		if (value == null)
			return true;

		if (value.length == 0) {
			return true;
		}

		return false;
	}

	public static boolean isEmpty(Collection<?> value) {
		if (value == null)
			return true;

		if (value.size() == 0) {
			return true;
		}

		return false;
	}

	public static boolean isEmpty(Map<?, ?> value) {
		if (value == null)
			return true;

		if (value.size() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(Collection<?> o) {
		return !isEmpty(o);
	}

	public static boolean isNotEmpty(Long o) {
		return !isEmpty(o);
	}

	public static boolean isNotEmpty(String o) {
		return !isEmpty(o);
	}

	public static boolean isNotEmpty(Object[] o) {
		return !isEmpty(o);
	}

	public static boolean isNotEmpty(byte[] o) {
		return !isEmpty(o);
	}

	public static boolean isNotEmpty(Map<?, ?> o) {
		return !isEmpty(o);
	}

	public static boolean isNull(Object o) {
		return (o == null);
	}

	public static boolean isNotNull(Object o) {
		return !isNull(o);
	}

	public static boolean isValidDate(String date) {
		SimpleDateFormat spdf = new SimpleDateFormat("dd-MM-yyyy");
		Date cDate = null;
		
		// Calendar calendar=Calendar.getInstance();
		
		try {

			cDate = spdf.parse(date);
			

		} catch (ParseException e) {

			return false;
		}
		if (!spdf.format(cDate).equals(date) ) {

			return false;
		}
		return true;
	}

	public static boolean afterToday(String date) {
		SimpleDateFormat spdf = new SimpleDateFormat("dd-MM-yy");
		Date cDate = null;
		Date todays_date = new Date();
		try {
			cDate = spdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		if (!cDate.after(todays_date)) {
			return false;
		}
		return true;

	}

	public static boolean isValidemail(String email) {
		
		final Pattern EMAIL_ADDRESS_PATTERN = Pattern
				.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
						+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
						+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
		try {
			return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
		} catch (NullPointerException exception) {
			return false;
		}

	}

}
