package com.ayyayo.g.dagger.module;

import com.ayyayo.g.App;
import com.ayyayo.g.common.BranchHelper;
import com.ayyayo.g.common.FileHelper;
import com.ayyayo.g.common.JsonConverter;
import com.ayyayo.g.common.UIHelper;
import com.ayyayo.g.database.DatabaseHandler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

	private App app;

	public AppModule (App app) {
		this.app = app;
	}

	@Provides
	@Singleton
	public App provideApp () {
		return app;
	}

	@Provides
	@Singleton
	public FileHelper provideFileHelper() {
		return new FileHelper();
	}

	@Provides
	@Singleton
	public UIHelper provideUiHelper() {
		return new UIHelper();
	}

	@Provides
	@Singleton
	JsonConverter provideJsonConverter () {
		return new JsonConverter();
	}

	@Provides
	@Singleton
	BranchHelper provideBranchHelper() {
		return new BranchHelper();
	}

	@Provides
	@Singleton
	public DatabaseHandler provideDatabaseHandler(App application, JsonConverter jsonConverter) {
		return new DatabaseHandler(application, jsonConverter);
	}
}