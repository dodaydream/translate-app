package com.elfstack.translate.viewmodel;

import android.app.Application;
import android.util.Log;

import com.elfstack.translate.model.SavedTrans;
import com.elfstack.translate.model.TransLine;
import com.elfstack.translate.model.TransRequest;
import com.elfstack.translate.model.TransResult;
import com.elfstack.translate.remote.Remote;
import com.elfstack.translate.remote.TranslationService;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TranslatorViewModel extends AndroidViewModel {

    public static final String TAG = "TranslatorViewModel";

    public MutableLiveData<String> srcLang = new MutableLiveData<>();
    public MutableLiveData<String> dstLang = new MutableLiveData<>();
    public MutableLiveData<String> srcContent = new MutableLiveData<>();

    public MutableLiveData<TransResult> transResult = new MutableLiveData<>();

    public TranslatorViewModel(@NonNull Application application) {
        super(application);
        srcLang.setValue("auto");
        dstLang.setValue("en");
    }

    public String getString(String lang) {
        if (lang != null) {
            int i = getApplication().getResources().getIdentifier(lang, "string", getApplication().getPackageName());
            return getApplication().getString(i);
        }
        return "";
    }

    public void swapLanguage() {
        String srcLang = this.srcLang.getValue();
        String dstLang = this.dstLang.getValue();

        this.srcLang.setValue(dstLang);
        this.dstLang.setValue(srcLang);
    }

    public String list2str(List<TransLine> transLineList) {
        String str = "";
        for (TransLine line : transLineList) {
            str += (line.dst + '\n');
        }
        str = str.trim();
        return str;
    }

    public LiveData<String> getDstContent() {
        return Transformations.map(transResult, result -> list2str(result.transResult));
    }

    public LiveData<TransResult> getTranslation () {
        transResult.setValue(null);
        TransRequest request = new TransRequest(srcLang.getValue(), srcContent.getValue(), dstLang.getValue());
        Remote.getService().getTranslation(request).enqueue(new Callback<TransResult>() {
            @Override
            public void onResponse(Call<TransResult> call, Response<TransResult> response) {
                transResult.postValue(response.body());
            }

            @Override
            public void onFailure(Call<TransResult> call, Throwable t) {
                // implement prompt on failure
            }
        });
        return transResult;
    }
}
