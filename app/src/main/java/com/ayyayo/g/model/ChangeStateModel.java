package com.ayyayo.g.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChangeStateModel {

	public int state_id;
	public Date scheduled_on;
	public Date suggested_join_date;
	public String currency;
	public String offered_ctc_raw;
	public String offered_ctc;
	public String final_ctc_raw;
	public String final_ctc;
	public String comments;
	public List<RequestBodyModel> CtcVisibilities = new ArrayList<>();
	public ArrayList<Integer> reasonList;
	public String state_file;
}
