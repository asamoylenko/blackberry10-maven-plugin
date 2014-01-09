package com.filmon.maven.mojo;

import com.filmon.maven.packaging.AppCategory;
import com.filmon.maven.packaging.PackagerArgumentsBuilder;
import com.filmon.maven.util.ExitTrappingExecutor;

import net.rim.tools.apk2bar.Apk2Bar;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

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
                                      && getBarPackage().getBarFile().exists();

        if (!packagingSuccessful) {
            throw new MojoExecutionException("Failed to package application. Path: " + getBarPackage().getBarFile().getPath());
        }

        getLog().info("Application packaged successfully.");

        return packagingSuccessful;
    }
}
