package com.ayyayo.g.model;

public class JobStateChangeModel {
	public int responseId;
	public int allocationId;

	public JobStateChangeModel (int newState, int allocation_id) {
		responseId = newState;
		this.allocationId = allocation_id;
	}
}
