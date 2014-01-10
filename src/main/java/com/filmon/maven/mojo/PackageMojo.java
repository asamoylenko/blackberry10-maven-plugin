package com.filmon.maven.mojo;

import com.filmon.maven.packaging.AppCategory;
import com.filmon.maven.packaging.PackagerArgumentsBuilder;
import com.filmon.maven.util.ExitTrappingExecutor;

import net.rim.tools.apk2bar.Apk2Bar;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;
import java.util.concurrent.Callable;

@Mojo(name="package", defaultPhase = LifecyclePhase.PACKAGE)
public class PackageMojo extends BlackberryAbstractMojo {

    @Override
    public void doExecute() throws MojoExecutionException, MojoFailureException {
        packageApplication();
    }

    protected boolean packageApplication() throws MojoExecutionException {
        getLog().info("Packaging application...");
        getLog().info("BAR package: " + getKeyStorage());

        PackagerArgumentsBuilder argumentsBuilder = new PackagerArgumentsBuilder(getBarPackage().getApkFile(),
                getBarPackage().getBarFile(),
                getCertificate().getAuthor(),
                AppCategory.parseCategory(getBarPackage().getApplicationCategory()));
        argumentsBuilder.setMinimalOsVersion(getBarPackage().getMinimalOsVersion());
        argumentsBuilder.setVerifyApk(true);
        argumentsBuilder.setAppEntryPointNameTruncation(getBarPackage().getAppEntryPointNameTruncation());
        argumentsBuilder.setWarningLevelThreshold(getBarPackage().getWarningLevelThreshold());
        final  String[] args = argumentsBuilder.create();

        Callable<Void> packagingCallable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Apk2Bar.main(args);
                return null;
            }
        };
        ExitTrappingExecutor executor = new ExitTrappingExecutor();
        boolean packagingSuccessful = executor.execute(packagingCallable) == 0
                                      && checkPackage();

        if (!packagingSuccessful) {
            throw new MojoExecutionException("Failed to package application. Path: " +
                                             getBarPackage().getBarFile().getPath());
        }

        getLog().info("Application packaged successfully.");

        return packagingSuccessful;
    }

    /**
     * Checks if BAR file exists and renames it if necessary.
     */
    private boolean checkPackage() {
        if (getBarPackage().getApkFile() == null
                || !getBarPackage().getApkFile().canRead()) {
            getLog().error("APK file is not specified or cannot be read.");
            return false;
        }

        String apkName = getBarPackage().getApkFile().getName();

        if (getBarPackage().getBarFile() == null) {
            getLog().error("BAR file is not specified.");
            return false;
        }

        File currentBarFile = new File(getBarPackage().getBarFile().getParent(),
                                       apkName.replace(".apk", ".bar"));

        if (!currentBarFile.exists()) {
            getLog().error("BAR file does not exist.");
            return false;
        }

        File desirableBarFile = getBarPackage().getBarFile();

        if (currentBarFile.getPath().equals(desirableBarFile.getPath())) {
            // APK file name matches BAR file name, no need to rename.
            return true;
        }

        if (!currentBarFile.canWrite()) {
            getLog().error("BAR file does not exist or no permission to write.");
            return false;
        }

        // If a file with such name already exists, try to delete it.
        if (desirableBarFile.exists() && !desirableBarFile.delete()) {
            getLog().error("Failed to delete existing BAR file with desirable " +
                           "name. Path: " + desirableBarFile.getPath());
            return false;
        }

        return currentBarFile.renameTo(desirableBarFile);
    }
}
