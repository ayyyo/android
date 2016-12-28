package com.ayyayo.g.dagger.component;

import com.ayyayo.g.common.SharedPreferencesUtility;
import com.ayyayo.g.dagger.module.StorageModule;
import com.ayyayo.g.dagger.scope.ApplicationScope;
import com.ayyayo.g.ui.MainActivity;

import dagger.Component;

@ApplicationScope
@Component(
		modules = {
				StorageModule.class,
		}, dependencies = { AppComponent.class }
)
public interface StorageComponent {

	SharedPreferencesUtility getSharedPreferencesUtility();

	void inject(MainActivity activity);


}
