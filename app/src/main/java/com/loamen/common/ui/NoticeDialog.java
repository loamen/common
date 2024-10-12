package com.loamen.common.ui;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.loamen.common.R;
import com.loamen.common.bean.Setting;
import com.loamen.common.databinding.DialogNoticeBinding;
import com.loamen.common.picasso.RoundTransformation;
import com.loamen.common.util.ResUtil;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * 描述
 */
public class NoticeDialog implements DialogInterface.OnDismissListener {
    private final DialogNoticeBinding binding;
    private final FragmentActivity activity;
    private final AlertDialog dialog;
    private Setting mSetting;

    public NoticeDialog setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    private View.OnClickListener onClickListener;

    public static NoticeDialog create(FragmentActivity activity, Setting setting) {
        return new NoticeDialog(activity, setting);
    }

    public NoticeDialog(FragmentActivity activity, Setting setting) {
        this.activity = activity;
        this.binding = DialogNoticeBinding.inflate(LayoutInflater.from(activity));
        this.dialog = new MaterialAlertDialogBuilder(activity).setView(binding.getRoot()).create();
        this.mSetting = setting;
        initListener();
    }

    private void initListener() {
        binding.negative.setOnClickListener(v -> {
            dialog.dismiss();
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
        });
        binding.positive.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

    private void initDialog() {
        WindowManager.LayoutParams params = Objects.requireNonNull(dialog.getWindow()).getAttributes();
        params.width = (int) (ResUtil.getScreenWidth(activity) * 0.66f);
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setDimAmount(0);
        dialog.setOnDismissListener(this);
        dialog.show();
    }

    private void initView() {
        changeContent();
    }

    public void refresh(Setting setting) {
        this.mSetting = setting;
        changeContent();
    }

    private void changeContent() {
        binding.info.setText(mSetting.getRemark());
        changeImage(mSetting.getImageUrl());
    }

    public void changeImage(String path) {
        binding.code.setImageResource(R.drawable.app_homepage_qrcode);
        if (TextUtils.isEmpty(path)) return;
        Picasso.get()
                .load(path)
                .config(Bitmap.Config.RGB_565)
                .transform(new RoundTransformation(Base64.encodeToString(path.getBytes(), Base64.DEFAULT))
                        .centerCorp(true))
                .placeholder(R.drawable.app_homepage_qrcode)
                .error(R.drawable.app_homepage_qrcode)
                .into(binding.code);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

    }

    public void show() {
        initDialog();
        initView();
    }
}
