package com.filmon.maven.signing;

import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.util.ArrayList;

public class SignerArgumentsBuilder {

    private File certificateFile;
    private String password;
    private File keyStoreFile;
    private File barFile;

    public SignerArgumentsBuilder(File certificateFile, String password, File keyStoreFile, File bar)
            throws MojoExecutionException {

        setCertificateFile(certificateFile);
        setPassword(password);
        setKeyStoreFile(keyStoreFile);
        setBarFile(bar);
    }

    public String[] create() {
        ArrayList<String> args = new ArrayList<String>();

        args.add("-bbidtoken");
        args.add(getCertificateFile().getPath());
        args.add("-keystore");
        args.add(getKeyStoreFile().getPath());
        args.add("-storepass");
        args.add(getPassword());
        args.add(getBarFile().getPath());

        return args.toArray(new String[args.size()]);
    }

    public File getCertificateFile() {
        return certificateFile;
    }

    public SignerArgumentsBuilder setCertificateFile(final File certificateFile)
            throws MojoExecutionException {

        if (certificateFile == null || !certificateFile.exists()) {
            throw new MojoExecutionException("Certificate is not specified or does not exist.");
        } else if (!certificateFile.isFile() || !certificateFile.canRead()) {
            throw new MojoExecutionException("Certificate is not a file or cannot be read.");
        }

        this.certificateFile = certificateFile;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SignerArgumentsBuilder setPassword(final String password)
            throws MojoExecutionException {

        if (password == null) {
            throw new MojoExecutionException("Certificate password must be specified.");
        }

        this.password = password;
        return this;
    }

    public File getKeyStoreFile() {
        return keyStoreFile;
    }

    public SignerArgumentsBuilder setKeyStoreFile(final File keyStoreFile)
            throws MojoExecutionException {

        if (keyStoreFile == null || !keyStoreFile.exists()) {
            throw new MojoExecutionException("Key store is not specified or does not exist.");
        } else if (!keyStoreFile.isFile() || !keyStoreFile.canRead()) {
            throw new MojoExecutionException("Key store is not a file or cannot be read.");
        }

        this.keyStoreFile = keyStoreFile;
        return this;
    }

    public File getBarFile() {
        return barFile;
    }

    public SignerArgumentsBuilder setBarFile(final File barFile)
            throws MojoExecutionException {

        if (barFile == null || !barFile.exists()) {
            throw new MojoExecutionException("Key store is not specified or does not exist.");
        } else if (!barFile.isFile() || !barFile.canWrite()) {
            throw new MojoExecutionException("Key store is not a file or no permission to write.");
        }

        this.barFile = barFile;
        return this;
    }
}
