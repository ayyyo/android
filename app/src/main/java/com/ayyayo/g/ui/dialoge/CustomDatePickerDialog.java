package com.ayyayo.g.ui.dialoge;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.ayyayo.g.R;
import com.ayyayo.g.listener.FunctionCallBack;

import java.util.Calendar;

public class CustomDatePickerDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	private FunctionCallBack<Integer> selectedDateListener;

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), R.style.DialogTheme, this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		selectedDate(year, month, day);
	}

	public void selectedDate(int year, int month, int day) {
		if (selectedDateListener != null)
			selectedDateListener.onFunctionCall(year, month, day);
	}

	public void setSelectedDateListener(FunctionCallBack<Integer> selectedDate) {
		this.selectedDateListener = selectedDate;
	}
}