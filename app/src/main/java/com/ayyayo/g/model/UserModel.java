package com.ayyayo.g.model;


import com.ayyayo.g.model.inner.WhatBlocked;

import java.util.Date;

public class UserModel {
	public int id;
	public String name;
	public int client_id;
	public int group_id;
	public String email_id;
	public int admin_flag;
	public boolean admin;
	public String user_type;
	public String company_name;
	public int percRevenueShare;
	public int terminationFlag;
	public int consultantSurvey;
	public Date consultantSurveyTime;
	public boolean isBlocked;
	public WhatBlocked[] whatBlocked;
}
