package com.wei_spring.signtool.bean;

/**
 * Created by wei_spring on 2020/12/25.
 */
public class ApkSignInfo {
    private String apkPath;
    private String channelPath;
    private String keystorePath;
    private String emptyFileString = "channel_";
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

    public String getEmptyFileString() {
        return emptyFileString;
    }

    public void setEmptyFileString(String emptyFileString) {
        this.emptyFileString = emptyFileString;
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

    @Override
    public String toString() {
        return "ApkSignInfo{" +
                "apkPath='" + apkPath + '\'' +
                ", channelPath='" + channelPath + '\'' +
                ", keystorePath='" + keystorePath + '\'' +
                ", empmtyFileString='" + emptyFileString + '\'' +
                ", aliasString='" + aliasString + '\'' +
                ", keystorePwd='" + keystorePwd + '\'' +
                '}';
    }
}
