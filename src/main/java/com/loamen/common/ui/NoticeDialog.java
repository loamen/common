package com.loamen.common.ui;


import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.loamen.common.R;
import com.loamen.common.bean.Setting;
import com.loamen.common.picasso.RoundTransformation;
import com.squareup.picasso.Picasso;

import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * 描述
 *
 */
public class NoticeDialog extends BaseDialog {
    private final ImageView ivImage;
    private final TextView tvRemark;
    private Setting mSetting;
    private final LinearLayout llExit;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private View.OnClickListener onClickListener;

    public NoticeDialog(@NonNull Context context, Setting setting) {
        super(context);
        setContentView(R.layout.dialog_notice);
        setCanceledOnTouchOutside(false);
        ivImage = findViewById(R.id.iv_image);
        tvRemark = findViewById(R.id.tv_remark);
        llExit = findViewById(R.id.ll_exit);
        this.mSetting = setting;

        llExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onClickListener != null) {
                    onClickListener.onClick(v);
                }
            }
        });

        findViewById(R.id.ll_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        changeContent();
    }

    public void refresh(Setting setting) {
        this.mSetting = setting;
        changeContent();
    }

    private void changeContent() {
        tvRemark.setText(mSetting.getRemark());
        changeImage(mSetting.getImageUrl());
    }

    @Override
    public void onBackPressed() {
        if (onClickListener != null) {
            onClickListener.onClick(llExit);
        } else {
            super.onBackPressed();
        }
    }

    public void changeImage(String path) {
        ivImage.setImageResource(R.drawable.app_homepage_qrcode);
        if (TextUtils.isEmpty(path)) return;
        Picasso.get()
                .load(path)
                .config(Bitmap.Config.RGB_565)
                .transform(new RoundTransformation(Base64.encodeToString(path.getBytes(), Base64.DEFAULT))
                        .centerCorp(true)
//                        .override(AutoSizeUtils.mm2px(getContext(), 300), AutoSizeUtils.mm2px(getContext(), 400))
                        .roundRadius(AutoSizeUtils.mm2px(getContext(), 15), RoundTransformation.RoundType.ALL))
                .placeholder(R.drawable.app_homepage_qrcode)
                .error(R.drawable.app_homepage_qrcode)
                .into(ivImage);
    }
}
