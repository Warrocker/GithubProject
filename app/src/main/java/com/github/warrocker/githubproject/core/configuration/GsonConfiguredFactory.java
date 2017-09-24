package com.github.warrocker.githubproject.core.configuration;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.github.warrocker.githubproject.core.configuration.ExclusionEliminationStrategy.Direction.DESERIALIZATION;
import static com.github.warrocker.githubproject.core.configuration.ExclusionEliminationStrategy.Direction.SERIALIZATION;

/**
 * Created by Warrocker.
 */
public class GsonConfiguredFactory {

    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String TAG = "GsonConfiguredFactory";

    public static Gson getGson() {
        return new GsonBuilder()
                .addDeserializationExclusionStrategy(new ExclusionEliminationStrategy(DESERIALIZATION))
                .addSerializationExclusionStrategy(new ExclusionEliminationStrategy(SERIALIZATION))
                .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> {
                    DateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
                    String asString = json.getAsString();
                    if (asString == null) return null;
                    try {
                        return simpleDateFormat.parse(asString);
                    } catch (ParseException e) {
                        Log.e(TAG, "deserialize: ", e);
                        return null;
                    }
                })
                .setLenient()
                .create();
    }
}
