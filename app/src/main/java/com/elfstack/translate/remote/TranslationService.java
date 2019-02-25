package com.elfstack.translate.remote;

import com.elfstack.translate.model.SavedTrans;
import com.elfstack.translate.model.TransRequest;
import com.elfstack.translate.model.TransResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TranslationService {
    @POST("/translate")
    Call<TransResult> getTranslation(@Body TransRequest request);

    @GET("/s/{hash}")
    Call<SavedTrans> getSavedSentence(@Path("hash") String hash);
}
