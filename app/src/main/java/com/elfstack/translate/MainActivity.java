package com.elfstack.translate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import com.elfstack.translate.component.TransResultCard;
import com.elfstack.translate.databinding.ActivityMainBinding;
import com.elfstack.translate.databinding.DialogSelectLanguageBinding;
import com.elfstack.translate.model.TransResult;
import com.elfstack.translate.viewmodel.LanguagesItemViewModel;
import com.elfstack.translate.viewmodel.TranslatorViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class MainActivity extends AppCompatActivity {

    BottomSheetDialog mBottomSheetDialog;
    LanguagesItemViewModel mLanguagesItemViewModel;
    TranslatorViewModel mTranslatorViewModel;

    @BindView(R.id.viewContainer) ViewGroup viewGroup;
    @BindView(R.id.btnSrcLanguage) Button mBtnSrcLang;
    @BindView(R.id.btnDstLanguage) Button mBtnDstLang;
    @BindView(R.id.fabTranslate) FloatingActionButton mFabTranslate;

    @OnClick(R.id.btnSrcLanguage) void setBtnSrcLang() {
        mLanguagesItemViewModel.isSrc = true;
        showDialog();
    }

    @OnClick(R.id.btnDstLanguage) void setBtnDstLang() {
        mLanguagesItemViewModel.isSrc = false;
        showDialog();
    }

    @OnClick(R.id.fabTranslate) void getTranslation() {
        mTranslatorViewModel.getTranslation();
        showTranslation();
    }

    @OnClick(R.id.btnClearText) void clearText() {
        mTranslatorViewModel.srcContent.postValue("");
        viewGroup.removeAllViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViewModel();

        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showTranslation() {
        mTranslatorViewModel.getTranslation().observe(this, t -> {
            if (t != null) {
                TransResultCard card = new TransResultCard(this)
                        .setContent(mTranslatorViewModel.list2str(t.transResult))
                        .setDstLang(mTranslatorViewModel.getString(t.to))
                        .setOnReverseTranslation(reverseTrans)
                        .build();
                viewGroup.removeAllViews();
                viewGroup.addView(card);
            }
        });
    }

    private void bindViewModel() {
        mTranslatorViewModel = ViewModelProviders.of(this).get(TranslatorViewModel.class);
        mLanguagesItemViewModel = ViewModelProviders.of(this).get(LanguagesItemViewModel.class);

        mLanguagesItemViewModel.itemBinding = ItemBinding.<Pair<String, String>>of(BR.language, R.layout.item_selectable)
                .bindExtra(BR.listener, listener)
                .bindExtra(BR.isSelected, isSelected);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setTranslatorViewModel(mTranslatorViewModel);
    }

    private void showDialog() {
        if (mBottomSheetDialog == null) {
            mBottomSheetDialog = new BottomSheetDialog(this);
        }
        DialogSelectLanguageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_select_language, null, false);
        binding.setViewModel(mLanguagesItemViewModel);
        binding.setIsSelected(isSelected);
        binding.setListener(listener);
        mBottomSheetDialog.setContentView(binding.getRoot());
        mBottomSheetDialog.show();
    }

    public interface OnItemClickListener {
        void onItemClick(String item);
    }

    OnItemClickListener listener = new OnItemClickListener() {
        @Override
        public void onItemClick(String item) {
            if (mLanguagesItemViewModel.isSrc) {
                mTranslatorViewModel.srcLang.postValue(item);
            } else {
                mTranslatorViewModel.dstLang.postValue(item);
            }
            mBottomSheetDialog.hide();
        }
    };

    public interface IsItemSelected {
        boolean isSelected(String cod);
    }

    IsItemSelected isSelected = new IsItemSelected() {
        @Override
        public boolean isSelected(String cod) {
            if (mLanguagesItemViewModel.isSrc) {
                return cod == mTranslatorViewModel.srcLang.getValue();
            } else {
                return cod == mTranslatorViewModel.dstLang.getValue();
            }
        }
    };

    TransResultCard.OnReverseTranslation reverseTrans = () -> {
        mTranslatorViewModel.srcContent.postValue(mTranslatorViewModel.getDstContent().getValue());

        if (mTranslatorViewModel.srcLang.getValue().equals("auto")) {
            TransResult result = mTranslatorViewModel.transResult.getValue();
            if (result != null) {
                mTranslatorViewModel.srcLang.postValue(result.from);
            }
        }

        mTranslatorViewModel.swapLanguage();
        mTranslatorViewModel.getTranslation().observe(this, t -> {
           if (t != null) {
               showTranslation();
           }
        });
    };
}
