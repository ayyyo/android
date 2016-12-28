package com.ayyayo.g.dagger.module;

import com.ayyayo.g.App;
import com.ayyayo.g.common.SharedPreferencesUtility;
import com.ayyayo.g.model.UserModel;

import dagger.Module;
import dagger.Provides;

@Module
public class StorageModule {

	@Provides
	SharedPreferencesUtility provideSharedPreferencesUtility (App application) {
		return new SharedPreferencesUtility(application);
	}

	@Provides
	UserModel provideUserDetail (SharedPreferencesUtility sharedPreferencesUtility) {
		return sharedPreferencesUtility.getCurrentUser();
	}

}
