package com.filmon.maven.packaging;

import java.io.File;

public class Package {

    /**
     * APK file location.
     */
    private File apkFile;

    /**
     * BAR file destination.
     */
    private File barFile;

    /**
     * Android SDK folder path.
     * Not necessary if ANDROID_HOME environment variable is set.
     */
    private File androidSdkFolder = null;

    /**
     * This argument specifies the path to the compatibility exceptions
     * file. Specify this argument only if you also specify the verifyApk parameter.
     * This file contains a list of the APIs and features that the BlackBerry 10 OS
     * does not support. If this argument is not specified, the apk2bar tool uses
     * a default value of ../blackberry/Apk2Bar_compatibility_excepts.xml.
     */
    private File compatibilityExceptionsFile;

    /**
     * This optional argument allows you to specify the
     * minimal target Blackberry OS version; e.g. 10, or 10.0.9
     */
    private float minimalOsVersion = 10;

    /**
     * This argument specifies a warning impact level, represented by an integer of -1 to 5.
     * If an APK file generates any warnings that are equal to or exceed the specified impact
     * level, the plugin does not repackage the app. If argument is not specified
     * or set to -1 the plugin repackages all APK files, and ignores all warnings.
     */
    private int warningLevelThreshold = -1;

    /**
     * This parameter defines the application category. On the BlackBerry 10 OS,
     * the application is grouped in certain category according to this parameter.
     * Can take: @param games or @param media.
     */
    private String applicationCategory;

    /**
     * This argument instructs the plugin to verify
     * APK files before being repackaged as BAR files.
     */
    private boolean verifyApk = false;

    /**
     * This argument truncates the application's Entry-Point-Name in the manifest file so that
     * it does not exceed 25 characters (the maximum allowed on the BlackBerry 10 OS).
     * Can take: @param left, @param right, @param none .
     */
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
