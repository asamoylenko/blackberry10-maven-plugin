package com.filmon.maven.packaging;

import org.apache.maven.plugin.MojoExecutionException;


import java.io.File;
import java.util.ArrayList;

public class PackagerArgumentsBuilder {

    private File apkFile;
    private File barFile;
    private File androidSdkFolder;
    private File compatibilityExceptionsFile;
    private String author;
    private float minimalOsVersion;
    private AppCategory applicationCategory;
    private boolean verifyApk;
    private AppEntryPointNameTruncation appEntryPointNameTruncation;
    private int warningLevelThreshold;

    //$currentDir/cmd_tools/1.6/bin/apk2bar $apkLocation -t $barDirectory -a $authorName -os $minimalOsVersion -cm -rv -etr -w$warningLevelThreshold

    public PackagerArgumentsBuilder(File apk, File bar, String author, AppCategory category)
            throws MojoExecutionException {
        setApkFile(apk);
        setBarFile(bar);
        setAuthor(author);
        setApplicationCategory(category);
    }

    public String[] create() throws MojoExecutionException {
        ArrayList<String> args = new ArrayList<String>();

        args.add(getApkFile().getPath());
        if (getCompatibilityExceptionsFile() != null) {
            args.add(getCompatibilityExceptionsFile().getPath());
        }
        if (getAndroidSdkFolder() != null) {
            args.add(getAndroidSdkFolder().getPath());
        }
        args.add("-t");
        args.add(getBarFile().getPath());
        args.add("-a");
        args.add(getAuthor());
        args.add("-os");
        args.add(String.valueOf(getMinimalOsVersion()));
        args.add(getApplicationCategory().getValue());
        if (isVerifyApk()) {
            args.add("-rv");
        }
        if (getAppEntryPointNameTruncation() == null) {
            args.add(AppEntryPointNameTruncation.NONE.getValue());
        } else {
            args.add(getAppEntryPointNameTruncation().getValue());
        }
        if (getWarningLevelThreshold() != -1) {
            args.add("-w" + getWarningLevelThreshold());
        }

        return args.toArray(new String[args.size()]);
    }

    public File getApkFile() {
        return apkFile;
    }

    public PackagerArgumentsBuilder setApkFile(final File apkFile)
            throws MojoExecutionException {
        if (apkFile == null || !apkFile.isFile()) {
            throw new MojoExecutionException("APK file was not specified or is not a file.");
        } else if (!apkFile.exists() || !apkFile.canRead()) {
            throw new MojoExecutionException("Specified APK file does not exist or cannot be read.");
        }

        this.apkFile = apkFile;
        return this;
    }

    public File getBarFile() {
        return barFile;
    }

    public PackagerArgumentsBuilder setBarFile(final File barFile)
            throws MojoExecutionException {

        if (barFile == null) {
            throw new MojoExecutionException("BAR file was not specified.");
        } else if (!barFile.getParentFile().canWrite()) {
            throw new MojoExecutionException("No permission to write for parent directory of specified BAR file.");
        }

        /* Apk2Bar tool accepts directory as a parameter and
           generates BAR file with the exact same name as APK's.
        */
        this.barFile = barFile.getParentFile();
        return this;
    }

    public File getAndroidSdkFolder() {
        return androidSdkFolder;
    }

    public PackagerArgumentsBuilder setAndroidSdkFolder(final File androidSdkFolder)
            throws MojoExecutionException {

        if (androidSdkFolder != null) {
            if (!androidSdkFolder.exists()) {
                throw new MojoExecutionException("Android SDK folder does not exist.");
            } else if (!androidSdkFolder.isDirectory() || !androidSdkFolder.canRead()) {
                throw new MojoExecutionException("Android SDK folder is not a directory or cannot be read.");
            }
        }

        this.androidSdkFolder = androidSdkFolder;
        return this;
    }

    public File getCompatibilityExceptionsFile() {
        return compatibilityExceptionsFile;
    }

    public PackagerArgumentsBuilder setCompatibilityExceptionsFile(final File compatibilityExceptionsFile)
            throws MojoExecutionException {

        if (compatibilityExceptionsFile != null) {
            if (!compatibilityExceptionsFile.exists()) {
                throw new MojoExecutionException("Compatibility exceptions file does not exist.");
            } else if (!compatibilityExceptionsFile.isFile() || !compatibilityExceptionsFile.canRead()) {
                throw new MojoExecutionException("Compatibility exceptions file is not a file or cannot be read.");
            }
        }

        this.compatibilityExceptionsFile = compatibilityExceptionsFile;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public PackagerArgumentsBuilder setAuthor(final String author)
            throws MojoExecutionException {

        if (author == null) {
            throw new MojoExecutionException("Author was not specified.");
        }

        this.author = author;
        return this;
    }

    public float getMinimalOsVersion() {
        return minimalOsVersion;
    }

    public PackagerArgumentsBuilder setMinimalOsVersion(final float minimalOsVersion)
            throws MojoExecutionException {

        if (minimalOsVersion < 10) {
            throw new MojoExecutionException("Illegal argument. Minimal OS version cannot be less than 10.");
        }

        this.minimalOsVersion = minimalOsVersion;
        return this;
    }

    public AppCategory getApplicationCategory() {
        return applicationCategory;
    }

    public PackagerArgumentsBuilder setApplicationCategory(final AppCategory applicationCategory) {
        this.applicationCategory = applicationCategory;
        return this;
    }

    public PackagerArgumentsBuilder setApplicationCategory(final String applicationCategory)
            throws MojoExecutionException {

        return setApplicationCategory(AppCategory.parseCategory(applicationCategory));
    }

    public boolean isVerifyApk() {
        return verifyApk;
    }

    public PackagerArgumentsBuilder setVerifyApk(final boolean verifyApk) {
        this.verifyApk = verifyApk;
        return this;
    }

    public AppEntryPointNameTruncation getAppEntryPointNameTruncation() {
        return appEntryPointNameTruncation;
    }

    public PackagerArgumentsBuilder setAppEntryPointNameTruncation
            (final AppEntryPointNameTruncation appEntryPointNameTruncation)
            throws MojoExecutionException {

        this.appEntryPointNameTruncation = appEntryPointNameTruncation;
        return this;
    }

    public PackagerArgumentsBuilder setAppEntryPointNameTruncation
            (final String appEntryPointNameTruncation)
            throws MojoExecutionException {

        return setAppEntryPointNameTruncation(AppEntryPointNameTruncation
                .parseAppEntryPointNameTruncation(appEntryPointNameTruncation));
    }

    public int getWarningLevelThreshold() {
        return warningLevelThreshold;
    }

    public PackagerArgumentsBuilder setWarningLevelThreshold(final int warningLevelThreshold)
            throws MojoExecutionException {

        if (warningLevelThreshold < -1 || warningLevelThreshold > 5) {
            throw new MojoExecutionException("Warning level threshold value cannot be less than -1 (no impact) and larger than 5 (severe)");
        }

        this.warningLevelThreshold = warningLevelThreshold;
        return this;
    }
}
