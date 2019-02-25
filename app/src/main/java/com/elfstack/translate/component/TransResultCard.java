package com.elfstack.translate.component;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.elfstack.translate.R;
import com.elfstack.translate.databinding.CardTransResultBinding;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransResultCard extends LinearLayout {

    public String dstLang;
    public String content;
    private PopupMenu popupMenu;
    public interface OnReverseTranslation {
        void onReverseTranslation();
    }

    OnReverseTranslation onReverseTranslation;


    public TransResultCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TransResultCard(Context context) {
        super(context);
    }

    public TransResultCard setDstLang(String dstLang) {
        this.dstLang = dstLang;
        return this;
    }

    public TransResultCard setContent(String content) {
        this.content = content;
        return this;
    }

    public TransResultCard setOnReverseTranslation(OnReverseTranslation onReverseTranslation) {
        this.onReverseTranslation = onReverseTranslation;
        return this;
    }

    public TransResultCard build() {
        CardTransResultBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.card_trans_result, this, true);
        binding.setDstContent(content);
        binding.setDstLang(dstLang);
        initMenu();

        ButterKnife.bind(this);
        return this;
    }

    private void initMenu() {
        popupMenu = new PopupMenu(getContext(), findViewById(R.id.btnMore));
        Menu menu = popupMenu.getMenu();
        MenuInflater menuInflater = ((Activity) getContext()).getMenuInflater();
        menuInflater.inflate(R.menu.menu_trans_result, menu);

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_share_result:
                    share();
                    break;
                case R.id.action_reverse:
                    onReverseTranslation.onReverseTranslation();
                    break;
            }
            return false;
        });
    }

    private void share() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        getContext().startActivity(Intent.createChooser(sharingIntent, getContext().getString(R.string.share_with)));
    }

    @Override
    public View getRootView() {
        return super.getRootView();
    }

    @OnClick(R.id.btnCopy) void copy() {
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("translation", content);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getContext(), R.string.text_copied_clipboard, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnMore) void more() {
        popupMenu.show();
    }
}
