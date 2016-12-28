package com.ayyayo.g.model;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

public class ColorImageModel {
	private int colorResource;
	private int imageResource;

	public ColorImageModel(@ColorRes int colorResource, @DrawableRes int imageResource) {
		this.colorResource = colorResource;
		this.imageResource = imageResource;
	}

	public int getColorResource() {
		return colorResource;
	}

	public int getImageResource() {
		return imageResource;
	}
}
