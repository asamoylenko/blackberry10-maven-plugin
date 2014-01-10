package com.filmon.maven.mojo;

import com.filmon.maven.signing.Certificate;
import com.filmon.maven.signing.KeyStorage;
import com.filmon.maven.packaging.Package;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class BlackberryAbstractMojo extends AbstractMojo {

    /**
     * Package parameters.
     */
    @Parameter(required = true)
    private Package barPackage;

    /**
     * Certificate parameters.
     */
    @Parameter(required = true)
    private Certificate certificate;

    /**
     * Key storage parameters.
     */
    @Parameter(required = true)
    private KeyStorage keyStorage;

    /**
     * A flag indicating if you need to skip plugin execution or not.
     */
    @Parameter(required = false, defaultValue = "false")
    private boolean skip;

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        if (!isSkip()) {
            doExecute();
        }
    }

    protected abstract void doExecute() throws MojoExecutionException, MojoFailureException;

    public Package getBarPackage() {
        return barPackage;
    }

    public void setBarPackage(final Package barPackage) {
        this.barPackage = barPackage;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(final Certificate certificate) {
        this.certificate = certificate;
    }

    public KeyStorage getKeyStorage() {
        return keyStorage;
    }

    public void setKeyStorage(final KeyStorage keyStorage) {
        this.keyStorage = keyStorage;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(final boolean skip) {
        this.skip = skip;
    }
}
