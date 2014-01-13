package com.filmon.maven.mojo;

import com.filmon.maven.signing.KeyToolArgumentsBuilder;
import com.filmon.maven.signing.SignerArgumentsBuilder;
import com.filmon.maven.util.ExitTrappingExecutor;

import net.rim.device.codesigning.barsigner.BarSigner;
import net.rim.device.codesigning.keytool.KeyTool;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.concurrent.Callable;

@Mojo(name="sign", defaultPhase = LifecyclePhase.PACKAGE)
public class SignMojo extends BlackberryAbstractMojo {

    @Override
    public void doExecute() throws MojoExecutionException, MojoFailureException {
        checkBarFile();
        checkCertificateFile();

        if (createKeyStorage()) {
            signPackage();
        }
    }

    protected boolean createKeyStorage() throws MojoExecutionException {
        getLog().info("Creating key storage...");
        getLog().info("Key storage: " + getKeyStorage());

        KeyToolArgumentsBuilder argumentsBuilder
                = new KeyToolArgumentsBuilder(getKeyStorage().getFile(),
                getCertificate().getPassword(),
                getCertificate().getAuthor());

        final String[] args = argumentsBuilder.create();

        Callable<Void> keyStorageCallable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                KeyTool.main(args);
                return null;
            }
        };
        ExitTrappingExecutor executor = new ExitTrappingExecutor();
        boolean keyStorageCreationSuccessful = executor.execute(keyStorageCallable) == 0
                                               && getKeyStorage().getFile().exists();

        if (!keyStorageCreationSuccessful) {
            throw new MojoExecutionException("Failed to create a key storage. Path: " +
                    getKeyStorage().getFile().getPath());
        }

        getLog().info("Key storage successfully created.");

        return keyStorageCreationSuccessful;
    }

    protected boolean signPackage() throws MojoExecutionException {
        getLog().info("Signing package...");
        getLog().info("Certificate: " + getKeyStorage());

        SignerArgumentsBuilder argumentsBuilder
                = new SignerArgumentsBuilder(getCertificate().getFile(),
                                             getCertificate().getPassword(),
                                             getKeyStorage().getFile(),
                                             getBarPackage().getBarFile());
        final String[] args = argumentsBuilder.create();

        Callable<Void> signerCallable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                BarSigner.main(args);
                return null;
            }
        };
        ExitTrappingExecutor executor = new ExitTrappingExecutor();
        boolean signingSuccessful = executor.execute(signerCallable) == 0;

        getLog().info(signingSuccessful ? "Package signed successfully." : "Package signing failed.");

        return signingSuccessful;
    }


    private void checkBarFile() throws MojoExecutionException {
        if (getBarPackage().getBarFile() == null
                || !getBarPackage().getBarFile().exists()
                || !getBarPackage().getBarFile().canWrite()) {
            throw new MojoExecutionException("BAR package was not specified or doesn't " +
                    "exist, or there is no permission to write.");
        }
    }

    private void checkCertificateFile() throws MojoExecutionException {
        if (getCertificate().getFile() == null
                || !getCertificate().getFile().exists()
                || !getCertificate().getFile().canRead()) {
            throw new MojoExecutionException("Certificate was not specified or doesn't " +
                    "exist, or there is no permission to read.");
        }
    }

}
