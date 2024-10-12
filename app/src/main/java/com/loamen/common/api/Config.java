package com.loamen.common.api;

import com.loamen.common.bean.Setting;

public class Config {
    public static Setting SETTING = new Setting();
    /**
     * 配置地址
     */
    public static String CONFIG_URL = "https://raw.kkgithub.com/lmclub/box/main/app/conf/setting.json"; //配置地址
    /**
     * //备份配置地址，如果获取配置失败尝试备用配置地址
     */
    public static final String CONFIG_URL_BAK = "https://jsd.proxy.aks.moe/gh/lmclub/box@main/app/conf/setting.json";
    /**
     * 公众号
     */
    public static final String GZH = "公众号：东曦影视";
    public static final String DEFAULT_API_URL = "http://box.iqinu.com";

    public static final String HAWK_API_URL = "api_url";
    public static final String HAWK_API_HISTORY = "api_history";
}
