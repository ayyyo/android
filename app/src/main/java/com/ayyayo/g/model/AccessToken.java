package com.ayyayo.g.model;

import java.util.Calendar;

public class AccessToken {
	private static final String LOG_TAG = AccessToken.class.getSimpleName();
	public String access_token;
	public int expires_in;
	public String refresh_token;
	public String message;
	public String token_type;
	public long start_time;

	public boolean isValid () {
		long current_time = Calendar.getInstance().getTimeInMillis();
		return current_time < ( start_time + expires_in * 1000 );
	}
}
