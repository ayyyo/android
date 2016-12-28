package com.ayyayo.g.ui.dialoge;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;

import com.ayyayo.g.R;
import com.ayyayo.g.listener.FunctionCallBack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OkCancelAlertDialog extends DialogFragment {

  @BindView(R.id.title)
  AppCompatTextView titleView;
  @BindView(R.id.body)
  AppCompatTextView bodyView;

  private LayoutInflater inflater;
  private View view;
  private FunctionCallBack<Boolean> callback;
  private String title = "Title";
  private String body = "body";

  public void initialize(String title, String body, FunctionCallBack<Boolean> callback) {
    this.title = title;
    this.body = body;
    this.callback = callback;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    inflater = LayoutInflater.from(getContext());
    View dialogView = inflater.inflate(R.layout.dialoge_ok_cancel, null);
    ButterKnife.bind(this, dialogView);

    if (title != null) {
      titleView.setVisibility(View.VISIBLE);
      titleView.setText(title);
    }

    if (body != null) {
      bodyView.setVisibility(View.VISIBLE);
      bodyView.setText(body);
    }

    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
    alertDialogBuilder.setView(dialogView);
    setCancelable(false);
    return alertDialogBuilder.create();
  }

  @OnClick(R.id.button_cancel)
  public void onClickCancel() {
    if (callback != null)
      callback.onFunctionCall(false);
    dismiss();
  }

  @OnClick(R.id.button_ok)
  public void onClickOk() {
    if (callback != null)
      callback.onFunctionCall(true);
    dismiss();
  }
}