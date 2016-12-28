package com.ayyayo.g.model;

import java.util.Map;

public class KeyValuePair<F, S> implements Map.Entry<F, S> {

	private F first;
	private S second;

	public KeyValuePair(F first, S second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public F getKey() {
		return first;
	}

	@Override
	public S getValue() {
		return second;
	}

	@Override
	public S setValue(S s) {
		second = s;
		return s;
	}
}
