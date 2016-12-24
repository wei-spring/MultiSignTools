package com.edaixi.signtool.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wei_spring on 2016/12/23.
 */

@XmlRootElement(name = "multiInfo")
public class MultiSignWrapper {

    private String userName;

    @XmlElement(name = "userName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userPwd;

    @XmlElement(name = "userPwd")
    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }


    private String apkPath;

    @XmlElement(name = "apkPath")
    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }


    private String channelPath;

    @XmlElement(name = "channelPath")
    public String getChannelPath() {
        return channelPath;
    }

    public void setChannelPath(String channelPath) {
        this.channelPath = channelPath;
    }


    private String keystorePath;

    @XmlElement(name = "keystorePath")
    public String getkeystorePath() {
        return keystorePath;
    }

    public void setkeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
    }


    private String aliasString;

    @XmlElement(name = "aliasString")
    public String getaliasString() {
        return aliasString;
    }

    public void setaliasString(String aliasString) {
        this.aliasString = aliasString;
    }


    private String keystorePwd;

    @XmlElement(name = "keystorePwd")
    public String getkeystorePwd() {
        return keystorePwd;
    }

    public void setkeystorePwd(String keystorePwd) {
        this.keystorePwd = keystorePwd;
    }

    @Override
    public String toString() {
        return "userName:" + userName
                + "\nuserPwd:" + userPwd
                + "\napkPath:" + apkPath
                + "\nchannelPath:" + channelPath
                + "\nkeystorePath:" + keystorePath
                + "\naliasString:" + aliasString
                + "\nkeystorePwd:" + keystorePwd
                ;
    }
}
