package com.ayyayo.g.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ayyayo.g.serializerDeserializer.CalenderSerializerDeserializer;
import com.ayyayo.g.serializerDeserializer.DateSerializerDeserializer;
import com.ayyayo.g.serializerDeserializer.StringDeserializer;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

public class JsonConverter {
	private final Gson gson;

	public JsonConverter () {
		gson = new GsonBuilder()
				.registerTypeAdapter(String.class, new StringDeserializer())
				.registerTypeAdapter(Date.class, new DateSerializerDeserializer())
				.registerTypeAdapter(Calendar.class, new CalenderSerializerDeserializer())
				.create();
	}

	public String toJson (Object object) {
		return gson.toJson(object);
	}

	public <T> T fromJson (String s, Class<T> classOfT) {
		return gson.fromJson(s, classOfT);
	}

	public <T> T fromJson (String json, Type typeOfT) {
		return gson.fromJson(json, typeOfT);
	}

	public Gson getGson () {
		return gson;
	}
}
