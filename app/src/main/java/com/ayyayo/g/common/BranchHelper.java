package com.ayyayo.g.common;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.ayyayo.g.common.constants.BranchConstant;
import com.ayyayo.g.common.constants.IntentConstant;
import com.ayyayo.g.listener.ResultCallBack;
import com.ayyayo.g.model.ApplicantModel;
// mv: branch
import com.ayyayo.g.App;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.SharingHelper;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.util.ShareSheetStyle;

public class BranchHelper {

	public BranchUniversalObject getApplicantBranchObject(ApplicantModel applicant) {
		BranchUniversalObject branchObject = new BranchUniversalObject();
		// The identifier is what Branch will use to de-dupe the content across many different Universal Objects
		branchObject.setCanonicalIdentifier(String.format("applicant/%s", applicant.id));

		// This is where you define the open graph structure and how the object will appear on Facebook or in a deep view
		branchObject.setTitle(applicant.name);
		if (applicant._root_ != null) {
			branchObject.setContentDescription(String
					.format("%s - %s", applicant._root_.client_name, applicant._root_.role));
		}
		branchObject.setContentImageUrl("");

		// You use this to specify whether this content can be discovered publicly - default is public
		branchObject.setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC);

		// Here is where you can add custom keys/values to the deep link data
		branchObject.addContentMetadata(BranchConstant.DATA, String.valueOf(applicant.id));
		branchObject.addContentMetadata(BranchConstant.TYPE, BranchConstant.TYPE_OPEN_ACTIVITY);
		branchObject.addContentMetadata(BranchConstant.ACTIVITY, BranchConstant.ACTIVITY_APPLICANT);
		return branchObject;
	}

	public void getDeepLink(Context context, BranchUniversalObject branchObject,
	                        String feature, String desktopUrl, final ResultCallBack<String> urlCallback) {
		branchObject.listOnGoogleSearch(context);
		branchObject.generateShortUrl(context, getLinkProperties(feature, desktopUrl),
				new Branch.BranchLinkCreateListener() {
					@Override
					public void onLinkCreate(String url, BranchError error) {
						if (error == null) {
							urlCallback.onResultCallBack(url, null);
						} else {
							urlCallback.onResultCallBack(null, new Exception());
						}
					}
				});
	}

	private LinkProperties getLinkProperties(String feature, String desktopUrl) {
		return new LinkProperties()
				.setChannel("android")
				.setFeature(feature)
				.addControlParameter("$desktop_url", desktopUrl);
	}

	public void shareDeepLink(AppCompatActivity activity, BranchUniversalObject branchObject,
							  String feature, String desktopUrl,
							  final ResultCallBack<String> urlCallback) {
		branchObject.showShareSheet(activity,
				getLinkProperties(feature, desktopUrl),
				getShareSheetStyle(activity),
				new Branch.BranchLinkShareListener() {
					@Override
					public void onShareLinkDialogLaunched() {
					}

					@Override
					public void onShareLinkDialogDismissed() {
					}

					@Override
					public void onLinkShareResponse(String sharedLink, String sharedChannel,
					                                BranchError error) {
						if (error == null) {
							urlCallback.onResultCallBack(sharedLink, null);
						} else {
							urlCallback.onResultCallBack(null, new Exception());
						}
					}

					@Override
					public void onChannelSelected(String channelName) {
					}
				});
	}

	private ShareSheetStyle getShareSheetStyle(Context context) {
		return new ShareSheetStyle(context, "Check this out!",
				"Candidate link: ")
				.setCopyUrlStyle(ContextCompat.getDrawable(context, android.R.drawable.ic_menu_send),
						"Copy", "Added to clipboard")
				.setMoreOptionStyle(ContextCompat.getDrawable(context, android.R.drawable.ic_menu_search),
						"Show more")
				.addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
				.addPreferredSharingOption(SharingHelper.SHARE_WITH.EMAIL)
				.addPreferredSharingOption(SharingHelper.SHARE_WITH.WHATS_APP)
				.setAsFullWidthStyle(true)
				.setSharingTitle("Share With");
	}

	public boolean openActivity(Context context, String data, String activity) {
		if (activity.equalsIgnoreCase(BranchConstant.ACTIVITY_APPLICANT)) {
			Intent i = new Intent(context, App.class);
			i.putExtra(IntentConstant.ID, Integer.parseInt(data));
			i.putExtra(IntentConstant.BACK_INTENT, true);
			context.startActivity(i);
			return true;
		}
		return false;
	}
}
