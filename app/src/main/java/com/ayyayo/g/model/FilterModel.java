package com.ayyayo.g.model;

import java.util.ArrayList;
import java.util.List;

public class FilterModel {
	public int id;
	public String name;
	public List<String> selected;

	public FilterModel(int id, String name) {
		this.id = id;
		this.name = name;
		this.selected = new ArrayList<>();
	}
}
