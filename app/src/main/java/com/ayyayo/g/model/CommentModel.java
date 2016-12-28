package com.ayyayo.g.model;

import com.ayyayo.g.model.inner.InterviewFollowUp;

import java.util.Calendar;
import java.util.List;

public class CommentModel {
	public int user_id;
	public String body;
	public Calendar created_at;
	public String currency;
	public String final_ctc;
	public int id;
	public String offered_ctc;
	public Calendar scheduled_on;
	public int state_id = -1;
	public Calendar suggested_join_date;
	public UserModel user;
	public List<InterviewFollowUp> InterviewFollowUps;
}
