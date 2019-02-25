package com.elfstack.translate.viewmodel;

import android.app.Application;

import com.elfstack.translate.BR;
import com.elfstack.translate.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.AndroidViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class LanguagesItemViewModel extends AndroidViewModel {

    public boolean isSrc;
    public final ObservableList<Pair<String, String>> languages = new ObservableArrayList<>();
    public ItemBinding<Pair<String, String>> itemBinding = null;
    public final Pair<String, String> auto = Pair.create("auto", getString(R.string.auto));

    public LanguagesItemViewModel(@NonNull Application application) {
        super(application);
        initLanguage();
    }

    private String getString(int resId) {
        return getApplication().getString(resId);
    }


    private void initLanguage() {
        languages.add(Pair.create("zh", getString(R.string.zh)));
        languages.add(Pair.create("cht", getString(R.string.cht)));
        languages.add(Pair.create("en", getString(R.string.en)));
        languages.add(Pair.create("yue", getString(R.string.yue)));
        languages.add(Pair.create("wyw", getString(R.string.wyw)));
        languages.add(Pair.create("jp", getString(R.string.jp)));
        languages.add(Pair.create("kor", getString(R.string.kor)));
        languages.add(Pair.create("fra", getString(R.string.fra)));
        languages.add(Pair.create("spa", getString(R.string.spa)));
        languages.add(Pair.create("th", getString(R.string.th)));
        languages.add(Pair.create("ara", getString(R.string.ara)));
        languages.add(Pair.create("ru", getString(R.string.ru)));
        languages.add(Pair.create("pt", getString(R.string.pt)));
        languages.add(Pair.create("de", getString(R.string.de)));
        languages.add(Pair.create("it", getString(R.string.it)));
        languages.add(Pair.create("el", getString(R.string.el)));
        languages.add(Pair.create("nl", getString(R.string.nl)));
        languages.add(Pair.create("pl", getString(R.string.pl)));
        languages.add(Pair.create("bul", getString(R.string.bul)));
        languages.add(Pair.create("est", getString(R.string.est)));
        languages.add(Pair.create("dan", getString(R.string.dan)));
        languages.add(Pair.create("fin", getString(R.string.fin)));
        languages.add(Pair.create("cs", getString(R.string.cs)));
        languages.add(Pair.create("rom", getString(R.string.rom)));
        languages.add(Pair.create("slo", getString(R.string.slo)));
        languages.add(Pair.create("swe", getString(R.string.swe)));
        languages.add(Pair.create("hu", getString(R.string.hu)));
        languages.add(Pair.create("vie", getString(R.string.vie)));
    }
}
