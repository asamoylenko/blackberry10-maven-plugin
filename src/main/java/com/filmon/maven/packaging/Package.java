package com.filmon.maven.packaging;

import java.io.File;

public class Package {

    private File apkFile;
    private File barFile;
    private File androidSdkFolder = null;
    private File compatibilityExceptionsFile;
    private float minimalOsVersion = 10;
    private int warningLevelThreshold = -1;
    private String applicationCategory;
    private boolean verifyApk = false;
    private String appEntryPointNameTruncation = "none";

    public File getApkFile() {
        return apkFile;
    }

    public void setApkFile(final File apkFile) {
        this.apkFile = apkFile;
    }

    public File getBarFile() {
        return barFile;
    }

    public void setBarFile(final File barFile) {
        this.barFile = barFile;
    }

    public File getAndroidSdkFolder() {
        return androidSdkFolder;
    }

    public void setAndroidSdkFolder(final File androidSdkFolder) {
        this.androidSdkFolder = androidSdkFolder;
    }

    public float getMinimalOsVersion() {
        return minimalOsVersion;
    }

    public void setMinimalOsVersion(final float minimalOsVersion) {
        this.minimalOsVersion = minimalOsVersion;
    }

    public int getWarningLevelThreshold() {
        return warningLevelThreshold;
    }

    public void setWarningLevelThreshold(final int warningLevelThreshold) {
        this.warningLevelThreshold = warningLevelThreshold;
    }

    public File getCompatibilityExceptionsFile() {
        return compatibilityExceptionsFile;
    }

    public void setCompatibilityExceptionsFile(final File compatibilityExceptionsFile) {
        this.compatibilityExceptionsFile = compatibilityExceptionsFile;
    }

    public String getApplicationCategory() {
        return applicationCategory;
    }

    public void setApplicationCategory(final String applicationCategory) {
        this.applicationCategory = applicationCategory;
    }

    public boolean isVerifyApk() {
        return verifyApk;
    }

    public void setVerifyApk(final boolean verifyApk) {
        this.verifyApk = verifyApk;
    }

    public String getAppEntryPointNameTruncation() {
        return appEntryPointNameTruncation;
    }

    public void setAppEntryPointNameTruncation(final String appEntryPointNameTruncation) {
        this.appEntryPointNameTruncation = appEntryPointNameTruncation;
    }

    @Override
    public String toString() {
        return "APK file: " + (getApkFile() == null ? null : getApkFile().getPath()) +
                ", BAR file: " + (getBarFile() == null ? null : getBarFile().getPath()) +
                ", minimal OS version: " + getMinimalOsVersion() +
                ", warning level threshold: " + getWarningLevelThreshold() +
                ", compatibility exceptions file: " +
                (getCompatibilityExceptionsFile() == null ? null : getCompatibilityExceptionsFile().getPath()) +
                ", application category: " + getApplicationCategory() +
                ", verify APK: " + isVerifyApk() +
                ", app entry point name truncation: " + getAppEntryPointNameTruncation();
    }
}
