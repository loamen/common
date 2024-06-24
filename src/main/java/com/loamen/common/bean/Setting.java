package com.loamen.common.bean;

import java.io.Serializable;

public class Setting implements Serializable {
    /**
     * 新版本号
     */
    private String newVersion;
    /**
     * 是否禁用旧版 true-禁用，false-不禁用
     */
    private Boolean disableOldVersion = false;
    /**
     * 是否显示首页通知弹框
     */
    private Boolean showNotice = false;
    /**
     * 首页通知弹框图片
     */
    private String imageUrl;
    /**
     * API配置地址，不为空则默认显示该地址内容
     */
    private String apiUrl;
    /**
     * 首页弹框显示内容
     */
    private String remark;

    /**
     * 是否显示跑马灯
     */
    private Boolean showMarquee = false;

    /**
     * 跑马灯内容
     */
    private String marqueeContent = "";

    public Boolean getShowMarquee() {
        return showMarquee;
    }

    public void setShowMarquee(Boolean showMarquee) {
        this.showMarquee = showMarquee;
    }

    public String getMarqueeContent() {
        return marqueeContent;
    }

    public void setMarqueeContent(String marqueeContent) {
        this.marqueeContent = marqueeContent;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public Boolean getDisableOldVersion() {
        return disableOldVersion;
    }

    public void setDisableOldVersion(Boolean disableOldVersion) {
        this.disableOldVersion = disableOldVersion;
    }

    public Boolean getShowNotice() {
        return showNotice;
    }

    public void setShowNotice(Boolean showNotice) {
        this.showNotice = showNotice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}