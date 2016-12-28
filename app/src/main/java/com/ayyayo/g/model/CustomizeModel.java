package com.ayyayo.g.model;

import java.util.List;

public class CustomizeModel {
	public int id;
	public String name;
	public boolean isSelected = false;
	public List<CustomizeModel> subList;

	public CustomizeModel(int id, String name, List<CustomizeModel> subList) {
		this(id, name, subList, false);
	}

	public CustomizeModel(int id, String name, List<CustomizeModel> subList, boolean isSelected) {
		this.id = id;
		this.name = name;
		this.subList = subList;
		this.isSelected = isSelected;
	}
}
