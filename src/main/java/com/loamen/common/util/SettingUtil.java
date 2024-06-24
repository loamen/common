package com.loamen.common.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loamen.common.api.Config;
import com.loamen.common.bean.Setting;
import com.loamen.common.ui.NoticeDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.hawk.Hawk;

public class SettingUtil {
    public interface SettingCallback {
        void onEventOccurred();
    }

    /**
     * 显示通知弹窗
     */
    private NoticeDialog noticeDialog;

    /**
     * 获取服务器配置并返回接口
     *
     * @param callback 回调方法
     */
    public void getConfig(TextView tvNotice, SettingCallback callback) {

        OkGo.<String>get(Config.CONFIG_URL)
                .execute(new AbsCallback<String>() {
                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        if (response.body() != null) {
                            return response.body().string();
                        } else {
                            throw new IllegalStateException("网络请求错误");
                        }
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String json = response.body();

                            GsonBuilder gsonBuilder = new GsonBuilder();
                            gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                            Gson gson = gsonBuilder.create();
                            Setting setting = gson.fromJson(json, Setting.class);
                            if (setting == null || TextUtils.isEmpty(setting.getApiUrl())) return;

                            //保存配置
                            Config.SETTING = setting;

                            //显示弹窗就不显示跑马灯
                            if (setting.getShowMarquee() && !setting.getShowNotice() && tvNotice != null) {
                                tvNotice.setVisibility(View.VISIBLE);
                                tvNotice.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                                tvNotice.setSingleLine(true);
                                tvNotice.setSelected(true);
                                tvNotice.setFocusable(true);
                                tvNotice.setFocusableInTouchMode(true);
                                tvNotice.setText(setting.getMarqueeContent());
                            }

                            String apiUrl = Hawk.get(Config.API_URL, "");
                            //显示默认数据
                            if (apiUrl.isEmpty() && !TextUtils.isEmpty(setting.getApiUrl())) {
                                //保存配置接口地址
                                Hawk.put(Config.API_URL, setting.getApiUrl());
                                //回调方法
                                if (callback != null) {
                                    callback.onEventOccurred();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        //如果链接失败，尝试备份地址
                        if (!Config.CONFIG_URL.equals(Config.CONFIG_URL_BAK)) {
                            Config.CONFIG_URL = Config.CONFIG_URL_BAK;
                            getConfig(tvNotice, callback);
                        }
                    }
                });
    }

    /**
     * 显示公众号
     *
     * @param view        像是的TextView控件
     * @param delayMillis 延迟
     */
    public static void showAbout(TextView view, long delayMillis) {
        if (delayMillis < 1000) {
            delayMillis = 2000;
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            view.setText("公众号：东曦影视");
        }, delayMillis);
    }

    /**
     * 显示弹窗
     *
     * @param context  上下文
     * @param callback 回调
     */
    public void showNoticeDialog(Context context, SettingCallback callback) {
        Setting setting = Config.SETTING;
        if (setting == null) {
            Toast.makeText(context, "再按一次返回键退出应用", Toast.LENGTH_SHORT).show();
        } else {
            if (noticeDialog == null) {
                noticeDialog = new NoticeDialog(context, setting);
                noticeDialog.setOnClickListener(view -> {
                    //退出程序
                    if (callback != null) {
                        callback.onEventOccurred();
                    }
                });
            }
            noticeDialog.show();
        }
    }
}
