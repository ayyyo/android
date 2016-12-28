package com.ayyayo.g.model;

public class FilterItemModel {
	public int id;
	public String name;
	public String value;
	public int type;
	public boolean isSelected;

	public FilterItemModel(int id, String name, String value, int type, boolean isSelected) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.type = type;
		this.isSelected = isSelected;
	}
}
