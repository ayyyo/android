package com.ayyayo.g.dagger.component;

import com.ayyayo.g.dagger.module.InternetModule;
import com.ayyayo.g.dagger.module.StorageModule;
import com.ayyayo.g.dagger.scope.ApplicationScope;

import dagger.Component;

@ApplicationScope
@Component(
		modules = {
				InternetModule.class, StorageModule.class
		}, dependencies = { AppComponent.class }
)
public interface CComponent {

//  void inject (NavBaseActivity activity);

}
