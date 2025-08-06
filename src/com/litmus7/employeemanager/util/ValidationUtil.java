package com.litmus7.employeemanager.util;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class ValidationUtil {
	public static boolean isNumber(String number) {
		return number!=null && Pattern.matches("[0-9]*", number);
	}
	public static boolean isName(String string) {
		return string!=null && string.length()>0;
	}
	public static boolean isMobileNumber(String string) {
		return string!=null && Pattern.matches("[0-9]{10}", string);
	}
	public static boolean isEmail(String string) {
		return string!=null && Pattern.matches("[a-zA-Z._]*[@][a-zA-Z._]*[.][a-zA-Z]*", string);
	}
	public static boolean isPastDate(String string) {
		// TODO Auto-generated method stub
		return string!=null && Pattern.matches("[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])", string) 
				&& LocalDate.parse(string).isBefore(LocalDate.now());
	}
	public static boolean isBoolean(String string) {
		// TODO Auto-generated method stub
		return string!=null && (string.toLowerCase().equals("true") || string.toLowerCase().equals("false"));
	}

}
