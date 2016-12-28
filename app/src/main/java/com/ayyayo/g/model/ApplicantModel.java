package com.ayyayo.g.model;

import java.util.Calendar;
import java.util.List;

public class ApplicantModel {
	public String applicant_score;
	public String client_name;
	public String consultant_username;
	public Calendar created_on;
	public String eng_mgr_name;
	public String exp_location;
	public int id;
	public String interview_follow_up_class;
	public String interview_follow_up_status;
	public Calendar interview_time;
	public String latest_comment;
	public String mobile;
	public String name;
	public String state_name;
	public Calendar updated_on;
	public String edu_degree;
	public String email;
	public String exp_designation;
	public String exp_employer;
	public String exp_salary;
	public String expected_ctc;
	public String notice_period;
	public String total_exp;
	public int state_id;
	public List<String> skills;
	public JobModel _root_;

	public ApplicantModel() {
	}

	public ApplicantModel(int id) {
		this.id = id;
	}
}
