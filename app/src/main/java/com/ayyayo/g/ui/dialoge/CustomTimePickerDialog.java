package com.ayyayo.g.ui.dialoge;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import com.ayyayo.g.R;
import com.ayyayo.g.listener.FunctionCallBack;

import java.util.Calendar;

public class CustomTimePickerDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

	private FunctionCallBack<Integer> selectedTimeFunctionResultCallback;

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of DatePickerDialog and return it
		return new TimePickerDialog(getActivity(), R.style.DialogTheme, this, hourOfDay, minute, true);
	}


	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		selectedTime(hourOfDay, minute);
	}

	public void selectedTime(int hourOfDay, int minute) {
		if (selectedTimeFunctionResultCallback != null) {
			selectedTimeFunctionResultCallback.onFunctionCall(hourOfDay, minute);
		}
	}

	public void setSelectedTimeListener(FunctionCallBack<Integer> selectedTimeFunctionResultCallback) {
		this.selectedTimeFunctionResultCallback = selectedTimeFunctionResultCallback;
	}
}