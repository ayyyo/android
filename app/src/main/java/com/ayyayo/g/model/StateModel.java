package com.ayyayo.g.model;

import com.ayyayo.g.model.inner.ApplicantStateConfig;
import com.ayyayo.g.model.inner.StateIdModel;

import java.util.Calendar;
import java.util.List;

public class StateModel {
	public int id;
	public String name;
	public ApplicantStateConfig config;
	public int lft;
	public int parent_id;
	public int rght;
	public int status;
	public Calendar updated_on;
	public List<StateIdModel> Childs;
	public List<StateIdModel> Actions;
	public String FollowUpOptions;
}
