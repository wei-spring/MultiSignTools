package com.edaixi.signtool.bean;

/**
 * Created by wei_spring on 2016/12/25.
 */
public class ApkSignInfo {
    private String apkPath;
    private String channelPath;
    private String keystorePath;
    private String empmtyFileString;
    private String aliasString;
    private String keystorePwd;

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public String getChannelPath() {
        return channelPath;
    }

    public void setChannelPath(String channelPath) {
        this.channelPath = channelPath;
    }

    public String getKeystorePath() {
        return keystorePath;
    }

    public void setKeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
    }

    public String getEmpmtyFileString() {
        return empmtyFileString;
    }

    public void setEmpmtyFileString(String empmtyFileString) {
        this.empmtyFileString = empmtyFileString;
    }

    public String getAliasString() {
        return aliasString;
    }

    public void setAliasString(String aliasString) {
        this.aliasString = aliasString;
    }

    public String getKeystorePwd() {
        return keystorePwd;
    }

    public void setKeystorePwd(String keystorePwd) {
        this.keystorePwd = keystorePwd;
    }
}
