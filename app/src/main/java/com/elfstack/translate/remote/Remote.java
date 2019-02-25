package com.elfstack.translate.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Remote {
    public static final String BASE_URL = "https://api.translate.elfstack.com/";
    private static TranslationService sTranslationService;

    public static TranslationService getService() {
        if (sTranslationService == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            sTranslationService = new Retrofit.Builder()
                   .baseUrl(BASE_URL)
                   .addConverterFactory(GsonConverterFactory.create())
                   .client(httpClient)
                   .build().create(TranslationService.class);
        }

        return sTranslationService;
    }
}
