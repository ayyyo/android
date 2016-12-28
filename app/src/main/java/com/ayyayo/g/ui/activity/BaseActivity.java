package com.ayyayo.g.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ayyayo.g.R;

import butterknife.BindView;

public abstract class BaseActivity extends AppCompatActivity {

	@BindView(R.id.root_view)
	protected View rootView;
	private boolean doubleBackToExitPressedOnce = true;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupComponent();
	}

	protected abstract void setupComponent();

	@Override
	public void onBackPressed() {
		if (doubleBackToExitPressedOnce) {
			super.onBackPressed();
			return;
		}

		this.doubleBackToExitPressedOnce = true;
		Snackbar.make(rootView, getString(R.string.back_exit), Snackbar.LENGTH_SHORT).show();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				doubleBackToExitPressedOnce = false;
			}
		}, 2000);
	}

	public void setDoubleBackClick(boolean status) {
		doubleBackToExitPressedOnce = !status;
	}
}
