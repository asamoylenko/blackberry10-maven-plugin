package com.filmon.maven.mojo;

import com.filmon.maven.util.ExitTrappingExecutor;

import net.rim.device.codesigning.barsigner.BarSigner;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.concurrent.Callable;

@Mojo(name="verify", defaultPhase = LifecyclePhase.PACKAGE)
public class VerifyMojo extends BlackberryAbstractMojo {

    @Override
    protected void doExecute() throws MojoExecutionException, MojoFailureException {
        verifyPackage();
    }

    protected boolean verifyPackage() throws MojoExecutionException {
        getLog().info("Verifying package...");

        if (getBarPackage().getBarFile() == null
                || !getBarPackage().getBarFile().exists()
                || !getBarPackage().getBarFile().canRead()) {
            throw new MojoExecutionException("BAR package was not specified or doesn't " +
                                            "exist, or there is no permission to read.");
        }

        final String[] args = {"-verify", getBarPackage().getBarFile().getPath()};

        Callable<Void> signerCallable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                BarSigner.main(args);
                return null;
            }
        };
        ExitTrappingExecutor executor = new ExitTrappingExecutor();
        boolean signingSuccessful = executor.execute(signerCallable) == 0;

        getLog().info("Verification complete.");

        return signingSuccessful;
    }

}
