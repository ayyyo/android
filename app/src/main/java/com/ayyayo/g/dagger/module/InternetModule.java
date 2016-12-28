package com.ayyayo.g.dagger.module;

import com.ayyayo.g.App;
import com.ayyayo.g.common.JsonConverter;
import com.ayyayo.g.common.SharedPreferencesUtility;
import com.ayyayo.g.common.constants.EndPoints;
import com.ayyayo.g.connectivity.QuezxConnection;
import com.ayyayo.g.connectivity.RetroFitConnector;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

@Module
public class InternetModule {

	String baseUrl;

	public InternetModule () {
		this.baseUrl = EndPoints.BASE_URL;
	}

	@Provides
	Cache provideOkHttpCache (App application) {
		int cacheSize = 10 * 1024 * 1024; // 10 MiB
		return new Cache(application.getCacheDir(), cacheSize);
	}

	@Provides
	OkHttpClient provideOkHttpClient (Cache cache) {
		OkHttpClient client = new OkHttpClient();
		client.setCache(cache);
		return client;
	}

	@Provides
	Retrofit.Builder provideRetrofitBuilder (JsonConverter gson, OkHttpClient okHttpClient) {
		return new Retrofit.Builder()
				.addConverterFactory(GsonConverterFactory.create(gson.getGson()))
				.baseUrl(baseUrl)
				.client(okHttpClient);
	}

	@Provides
	RetroFitConnector provideRetrofitConnector(OkHttpClient okHttpClient,
	                                           Retrofit.Builder builder) {
		return new RetroFitConnector(okHttpClient, builder);
	}


	@Provides
	QuezxConnection provideQuezxConnection (RetroFitConnector connector, JsonConverter gson,
	                                        SharedPreferencesUtility sharedPreferencesUtility) {
		return new QuezxConnection(connector, gson, sharedPreferencesUtility);
	}

}
