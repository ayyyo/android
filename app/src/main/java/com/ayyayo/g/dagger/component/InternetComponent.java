package com.ayyayo.g.dagger.component;

import com.ayyayo.g.dagger.module.InternetModule;
import com.ayyayo.g.dagger.module.StorageModule;
import com.ayyayo.g.dagger.scope.ApplicationScope;
import com.ayyayo.g.ui.activity.user.LoginActivity;
import com.ayyayo.g.ui.activity.user.SetupActivity;
import com.ayyayo.g.ui.MainActivity;

import dagger.Component;

@ApplicationScope
@Component(
		modules = {
				InternetModule.class, StorageModule.class,
		}, dependencies = { AppComponent.class }
)
public interface InternetComponent {

	void inject(LoginActivity activity);

	void inject(SetupActivity activity);

	void inject(MainActivity activity);


}
