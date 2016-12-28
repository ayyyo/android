package com.ayyayo.g.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;

import com.ayyayo.g.App;
import com.ayyayo.g.R;
import com.ayyayo.g.common.BranchHelper;
import com.ayyayo.g.common.SharedPreferencesUtility;
import com.ayyayo.g.common.constants.IntentConstant;
import com.ayyayo.g.connectivity.QuezxConnection;
import com.ayyayo.g.database.DatabaseHandler;
import com.ayyayo.g.listener.ResultCallBack;
import com.ayyayo.g.model.ReasonModel;
import com.ayyayo.g.model.StateModel;
import com.ayyayo.g.ui.activity.BaseActivity;
import com.ayyayo.g.App;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SetupActivity extends BaseActivity {

	private static final int RETRY_DELAY = 2 * 1000; // milli seconds
	@Inject
	QuezxConnection quezxConnection;
	@Inject
	SharedPreferencesUtility sharedPreferencesUtility;
	@Inject
	DatabaseHandler databaseHandler;
	@Inject
	BranchHelper branchHelper;

	private boolean forward;
	private int maxStateTry = 5;
	private int maxReasonTry = 5;
	private int pendingRequest = 0;
	private ResultCallBack<List<StateModel>> possibleStateResultCallback = new ResultCallBack<List<StateModel>>() {
		@Override
		public void onResultCallBack(List<StateModel> stateModelList, Exception e) {
			if (e == null && stateModelList != null) {
				sharedPreferencesUtility.saveStates(stateModelList);
				launchActivity();
			} else {
				Snackbar.make(rootView, getString(R.string.internet_error), Snackbar.LENGTH_SHORT).show();
				if (maxStateTry > 0) {
					maxStateTry--;
					new Handler().postDelayed(new Runnable() {
						public void run() {
							quezxConnection.getAllPossibleState(possibleStateResultCallback);
						}
					}, RETRY_DELAY);
				} else {
					operationFailed();
				}
			}
		}
	};
	private ResultCallBack<List<ReasonModel>> reasonCallBack = new ResultCallBack<List<ReasonModel>>() {
		@Override
		public void onResultCallBack(List<ReasonModel> reasonList, Exception e) {
			if (e == null && reasonList != null) {
				sharedPreferencesUtility.saveReasons(reasonList);
				launchActivity();
			} else {
				Snackbar.make(rootView, getString(R.string.internet_error), Snackbar.LENGTH_SHORT).show();
				if (maxReasonTry > 0) {
					maxReasonTry--;
					new Handler().postDelayed(new Runnable() {
						public void run() {
							quezxConnection.getReasons(reasonCallBack);
						}
					}, RETRY_DELAY);
				} else {
					operationFailed();
				}
			}
		}
	};

	private void operationFailed() {
		sharedPreferencesUtility.setCacheState(false);
		finish();
	}

	private void launchActivity() {
		pendingRequest--;
		if (pendingRequest > 0) return;
		sharedPreferencesUtility.setCacheState(true);
		if (forward) {
			openForwardActivity();
			return;
		}
		startActivity(new Intent(SetupActivity.this, App.class));
		finish();
	}

	private void openForwardActivity() {
		if (getIntent().getStringExtra(IntentConstant.TYPE)
				.equalsIgnoreCase(IntentConstant.TYPE_BRANCH)) {
			if (branchHelper.openActivity(this, getIntent().getStringExtra(IntentConstant.DATA),
					getIntent().getStringExtra(IntentConstant.ACTIVITY))) {
				finish();
				return;
			}
		}
		forward = false;
		launchActivity();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup);
		ButterKnife.bind(this);

		forward = getIntent().getBooleanExtra(IntentConstant.FORWARD, false);

		pendingRequest++;
		quezxConnection.getAllPossibleState(possibleStateResultCallback);
		pendingRequest++;
		quezxConnection.getReasons(reasonCallBack);
	}

	@Override
	protected void setupComponent() {
		App.getInternetComponent().inject(this);
	}
}
