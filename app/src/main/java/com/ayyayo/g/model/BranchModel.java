package com.ayyayo.g.model;

import com.google.gson.reflect.TypeToken;
import com.ayyayo.g.common.JsonConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.branch.indexing.BranchUniversalObject;
import timber.log.Timber;

public class BranchModel {
	public boolean is_first_session;
	public boolean clicked_branch_link;
	public boolean match_guaranteed;
	public String $og_description;
	public String $canonical_identifier;
	public String $og_title;
	public String creation_source;
	public String source;
	public String id;
	public String branch_type;
	public String branch_data;
	public String branch_activity;
	public String referring_link;
	public String channel;
	public String feature;
	public Boolean $publicly_indexable;
	public Boolean $desktop_url;
	public Boolean $identity_id;
	public Boolean $one_time_use;
	public List<String> tags;

	public static BranchModel getInstance(JsonConverter json, JSONObject referringParams) {
		Timber.e(referringParams.toString());
		BranchModel branch = json.fromJson(referringParams.toString(), BranchModel.class);
		try {
			branch.is_first_session = referringParams.getBoolean("+is_first_session");
			branch.clicked_branch_link = referringParams.getBoolean("+clicked_branch_link");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return branch;
	}

	public static BranchModel getInstance(JsonConverter json, BranchUniversalObject branchObject) {
		BranchModel branch = json.fromJson(json.toJson(branchObject.getMetadata()), BranchModel.class);
		branch.is_first_session = Boolean.parseBoolean(branchObject.getMetadata()
				.get("+is_first_session"));
		branch.match_guaranteed = Boolean.parseBoolean(branchObject.getMetadata()
				.get("+match_guaranteed"));
		branch.clicked_branch_link = Boolean.parseBoolean(branchObject.getMetadata()
				.get("+clicked_branch_link"));
		branch.id = branchObject.getMetadata().get("~id");
		branch.creation_source = branchObject.getMetadata().get("~creation_source");
		branch.referring_link = branchObject.getMetadata().get("~referring_link");
		branch.channel = branchObject.getMetadata().get("~channel");
		branch.feature = branchObject.getMetadata().get("~feature");
		branch.tags = json.fromJson(branchObject.getMetadata().get("~tags"),
				new TypeToken<List<String>>() {
				}.getType());
		return branch;
	}
}
