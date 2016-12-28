package com.ayyayo.g.common;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.ayyayo.g.R;
import com.ayyayo.g.common.constants.ActivityRequestCodes;
import com.ayyayo.g.listener.FunctionCallBack;
import com.ayyayo.g.ui.dialoge.CustomDatePickerDialog;
import com.ayyayo.g.ui.dialoge.CustomTimePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class UIHelper {
	private View progressBar;
	private ProgressDialog progressDialog;

	public String convertCountToRead(int count) {
		if (count > 99999) {
			return ( (double) Math.round((double) count / 1000) / 100 ) + "L";
		} else if (count > 999) {
			return ( (double) Math.round((double) count / 10) / 100 ) + "K";
		}
		return count + "";
	}

	public String dateFormat(Date date) {
		return new SimpleDateFormat("dd MMM yyyy", Locale.US).format(date);
	}

	public String timeFormat(Date date) {
		return new SimpleDateFormat("hh:mm aaa", Locale.US).format(date);
	}

	public String dateTimeFormat(String time) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			return dateTimeFormat(format.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String dateTimeFormat(Date date) {
		return new SimpleDateFormat("dd MMM yyyy, hh:mm aaa", Locale.US).format(date);
	}

	public String getISOFormat(Date time) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		return format.format(time);
	}

	public Calendar getCalenderFromISOFormat(String time) {
		Calendar c = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			c.setTime(format.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c;
	}

	public void hideKeyboard(Context context) {
		View view = ( (AppCompatActivity) context ).getCurrentFocus();
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	public void startProgressBar(Context context) {
		startProgressBar(context, "");
	}

	public void startProgressBar(Context context, String msg) {
		stopProgressBar();
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage(msg);
		progressDialog.show();
		progressDialog.setCancelable(false);
	}

	public void stopProgressBar () {
		if (progressBar != null) {
			progressBar.setVisibility(View.GONE);
			progressBar = null;
		}
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public void startProgressBar(View progressBar) {
		startProgressBar(progressBar, "");
	}

	public void startProgressBar(View progressBar, String message) {
		this.progressBar = progressBar;
		( (TextView) progressBar.findViewById(R.id.loading_message) ).setText(message);
		progressBar.setVisibility(View.VISIBLE);
	}

	public ArrayList<String> getCurrencyList() {
		ArrayList<String> currencyList = new ArrayList<>();
		currencyList.add("INR India Rupees");
		currencyList.add("USD United States Dollars");
		return currencyList;
	}

	public void expand(final View v) {
		v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		final int targetHeight = v.getMeasuredHeight();

		// Older versions of android (pre API 21) cancel animations for views with a height of 0.
		v.getLayoutParams().height = 1;
		v.setVisibility(View.VISIBLE);
		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				v.getLayoutParams().height = interpolatedTime == 1
						? LayoutParams.WRAP_CONTENT
						: (int) ( targetHeight * interpolatedTime );
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp/ms
		a.setDuration((int) ( targetHeight / v.getContext().getResources().getDisplayMetrics().density ));
		v.startAnimation(a);
	}

	public void collapse(final View v) {
		final int initialHeight = v.getMeasuredHeight();

		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if (interpolatedTime == 1) {
					v.setVisibility(View.GONE);
				} else {
					v.getLayoutParams().height = initialHeight - (int) ( initialHeight * interpolatedTime );
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp/ms
		a.setDuration((int) ( initialHeight / v.getContext().getResources().getDisplayMetrics().density ));
		v.startAnimation(a);
	}

	public void openDatePicker(FragmentManager fragmentManager, final FunctionCallBack<Calendar> functionCallBack) {
		CustomDatePickerDialog newFragment = new CustomDatePickerDialog();
		newFragment.setSelectedDateListener(new FunctionCallBack<Integer>() {
			@Override
			public void onFunctionCall(Integer... date) {
				Calendar calendar = Calendar.getInstance();
				calendar.set(date[0], date[1], date[2]);
				functionCallBack.onFunctionCall(calendar);
			}
		});
		newFragment.show(fragmentManager, "datePicker");
	}

	public void openTimePicker(FragmentManager fragmentManager, final FunctionCallBack<Calendar> functionCallBack) {
		CustomTimePickerDialog timePickerDialog = new CustomTimePickerDialog();
		timePickerDialog.setSelectedTimeListener(new FunctionCallBack<Integer>() {
			@Override
			public void onFunctionCall(Integer... time) {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, time[0]);
				calendar.set(Calendar.MINUTE, time[1]);
				calendar.set(Calendar.SECOND, 0);
				functionCallBack.onFunctionCall(calendar);
			}
		});
		timePickerDialog.show(fragmentManager, "timePicker");
	}

	public Intent openFileSelector(FragmentActivity activity) {
		if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED &&
				ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
						!= PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(activity,
					new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE,
							Manifest.permission.WRITE_EXTERNAL_STORAGE },
					ActivityRequestCodes.READ_WRITE_EXTERNAL_STORAGE);
			return null;
		}
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			String[] mimes = { "application/msword", "application/pdf", "text/plain", "application/rtf",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document" };
			intent.putExtra(Intent.EXTRA_MIME_TYPES, mimes);
		}
		return intent;
	}

	public void snackBar(View rootView, @StringRes int resourceId, int length) {
		Snackbar.make(rootView, resourceId, length).show();
	}

	public void snackBar(View rootView, @StringRes int resourceId) {
		snackBar(rootView, resourceId, Snackbar.LENGTH_SHORT);
	}

	public void snackBar(View rootView, String message) {
		snackBar(rootView, message, Snackbar.LENGTH_SHORT);
	}

	public void snackBar(View rootView, String message, int length) {
		Snackbar.make(rootView, message, length).show();
	}
}
