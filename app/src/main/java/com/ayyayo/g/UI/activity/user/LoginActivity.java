package com.ayyayo.g.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.ayyayo.g.App;
import com.ayyayo.g.R;
import com.ayyayo.g.common.SharedPreferencesUtility;
import com.ayyayo.g.common.UIHelper;
import com.ayyayo.g.common.constants.IntentConstant;
import com.ayyayo.g.connectivity.QuezxConnection;
import com.ayyayo.g.listener.ResultCallBack;
import com.ayyayo.g.model.UserModel;
import com.ayyayo.g.ui.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.branch.referral.Branch;

public class LoginActivity extends AppCompatActivity {

	@BindView(R.id.root_view)
	View rootView;
	@BindView(R.id.username)
	AppCompatEditText username;
	@BindView(R.id.progress_bar)
	View progressBar;

	@Inject
	QuezxConnection quezxConnection;
	@Inject
	SharedPreferencesUtility sharedPreferencesUtility;
	@Inject
	UIHelper uiHelper;

	private boolean forward;
	private ResultCallBack<UserModel> loginResultCallback = new ResultCallBack<UserModel>() {
		@Override
		public void onResultCallBack(UserModel user, Exception e) {
			if (e != null) {
				Snackbar.make(rootView, getString(R.string.internet_error), Snackbar.LENGTH_SHORT)
						.show();
			} else {
				Branch.getInstance(getApplicationContext()).setIdentity(String.valueOf(112));
				firstLogin();
			}

//			else if (user != null) {
//				Branch.getInstance(getApplicationContext()).setIdentity(String.valueOf(user.id));
//				firstLogin();
//			} else {
//				Snackbar.make(rootView, getString(R.string.invalid_username_password), Snackbar.LENGTH_SHORT).show();
//			}
			uiHelper.stopProgressBar();
		}
	};

	private void firstLogin() {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ButterKnife.bind(this);
		App.getInternetComponent().inject(this);

		forward = getIntent().getBooleanExtra(IntentConstant.FORWARD, false);

		username.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_NEXT) {
					onClickSubmit();
					return true;
				}
				return false;
			}
		});
	}

	@OnClick(R.id.login)
	public void onClickSubmit() {
		uiHelper.hideKeyboard(this);
		final String usernameText = username.getText().toString();
		if (usernameText.length() == 0) {
			username.requestFocus();
			return;
		}

		uiHelper.startProgressBar(progressBar);
		quezxConnection.loginUser(usernameText, loginResultCallback);
	}
}
