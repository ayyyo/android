package com.ayyayo.g.dagger.component;

import com.ayyayo.g.App;
import com.ayyayo.g.common.BranchHelper;
import com.ayyayo.g.common.FileHelper;
import com.ayyayo.g.common.JsonConverter;
import com.ayyayo.g.common.UIHelper;
import com.ayyayo.g.dagger.module.AppModule;
import com.ayyayo.g.database.DatabaseHandler;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
		modules = {
				AppModule.class
		}
)
public interface AppComponent {

	App getApp();

	UIHelper getUiHelper();

	FileHelper getFileHelper();

	JsonConverter getJsonConverter();

	DatabaseHandler getDatabaseHandler();

	BranchHelper getBranchHelper();

	void inject(App app);
}

