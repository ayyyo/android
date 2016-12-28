package com.ayyayo.g.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ayyayo.g.common.constants.Random;
import com.ayyayo.g.model.AccessToken;
import com.ayyayo.g.model.ReasonModel;
import com.ayyayo.g.model.StateModel;
import com.ayyayo.g.model.UserModel;

import java.util.Calendar;
import java.util.List;

import io.branch.referral.Branch;

public class SharedPreferencesUtility {
	private static final String LOG_TAG = SharedPreferencesUtility.class.getSimpleName();
	private SharedPreferences sharedPreferences;
	private Context context;

	public SharedPreferencesUtility (Context context) {
		this.context = context;
		sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
	}

	public AccessToken getAccessToken () {
		AccessToken token = null;
		if (!sharedPreferences.getString(Random.SHARED_PREFERENCES_TOKEN_DATA, "").equals("")) {
			Gson gson = new GsonBuilder().create();
			token = gson.fromJson(sharedPreferences.getString(Random.SHARED_PREFERENCES_TOKEN_DATA, ""), AccessToken.class);
		}
		return token;
	}

	public void setAccessToken (AccessToken accessToken) {
		accessToken.start_time = Calendar.getInstance().getTimeInMillis();
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(Random.SHARED_PREFERENCES_TOKEN_DATA, new Gson().toJson(accessToken));
		editor.apply();
	}

	public void reset () {
		Branch.getInstance(context.getApplicationContext()).logout();
		sharedPreferences.edit().clear().apply();
	}

	public boolean isUserLoggedIn () {
		return getCurrentUser() != null;
	}

	public UserModel getCurrentUser () {
		UserModel user = null;
		if (!sharedPreferences.getString(Random.SHARED_PREFERENCES_CURRENT_USER, "").equals("")) {
			Gson gson = new GsonBuilder().create();
			user = gson.fromJson(sharedPreferences.getString(Random.SHARED_PREFERENCES_CURRENT_USER, ""), UserModel.class);
		}
		return user;
	}

	public void setCurrentUser (UserModel user) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(Random.SHARED_PREFERENCES_CURRENT_USER, new Gson().toJson(user));
		editor.apply();
	}

	public void saveStates(List<StateModel> stateModelList) {
		for (int i = stateModelList.size() - 1; i >= 0; i--) {
			if (stateModelList.get(i) == null)
				stateModelList.remove(i);
		}
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(Random.SHARED_PREFERENCES_POSSIBLE_STATE, new JsonConverter().toJson(stateModelList));
		editor.apply();
	}

	public void saveReasons(List<ReasonModel> reasonModelList) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(Random.SHARED_PREFERENCES_REASONS, new JsonConverter().toJson(reasonModelList));
		editor.apply();
	}

	public List<ReasonModel> getReasons() {
		return new JsonConverter().fromJson(
				sharedPreferences.getString(Random.SHARED_PREFERENCES_REASONS, "[]"),
				new TypeToken<List<ReasonModel>>() {
				}.getType());
	}

	public List<StateModel> getStateList() {
		return new JsonConverter().fromJson(
				sharedPreferences.getString(Random.SHARED_PREFERENCES_POSSIBLE_STATE, "[]"),
				new TypeToken<List<StateModel>>() {
				}.getType());
	}

	public StateModel getState(int id) {
		List<StateModel> stateModels = new JsonConverter().fromJson(
				sharedPreferences.getString(Random.SHARED_PREFERENCES_POSSIBLE_STATE, "[]"),
				new TypeToken<List<StateModel>>() {
				}.getType());
		for (StateModel stateModel : stateModels) {
			if (stateModel.id == id)
				return stateModel;
		}
		return null;
	}

	public void setCacheState(boolean state) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(Random.SHARED_PREFERENCES_CACHE_UPDATED, state);
		editor.apply();
	}

	public boolean isCacheUpdated() {
		return sharedPreferences.getBoolean(Random.SHARED_PREFERENCES_CACHE_UPDATED, false);
	}
}
