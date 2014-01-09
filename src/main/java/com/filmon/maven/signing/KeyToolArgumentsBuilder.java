package com.filmon.maven.signing;

import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.util.ArrayList;

public class KeyToolArgumentsBuilder {

    //$currentDir/cmd_tools/1.6/bin/blackberry-keytool -genkeypair -keystore $keyStoragePath -storepass $certificatePassword -author $authorName
    private File keyStorageFile;
    private String password;
    private String author;

    public KeyToolArgumentsBuilder(File keyStorageFile, String password, String author)
            throws MojoExecutionException {

        setKeyStorageFile(keyStorageFile);
        setPassword(password);
        setAuthor(author);
    }

    public String[] create() {
        ArrayList<String> args = new ArrayList<String>();

        args.add("-genkeypair");
        args.add("-keystore");
        args.add(getKeyStorageFile().getPath());
        args.add("-storepass");
        args.add(getPassword());
        args.add("-author");
        args.add(getAuthor());

        return args.toArray(new String[args.size()]);
    }

    public File getKeyStorageFile() {
        return keyStorageFile;
    }

    public KeyToolArgumentsBuilder setKeyStorageFile(final File keyStorageFile)
            throws MojoExecutionException {

        if (keyStorageFile == null) {
            throw new MojoExecutionException("Key storage file was not specified.");
        } else if (!keyStorageFile.getParentFile().canWrite()) {
            throw new MojoExecutionException("No permission to write for parent directory of specified key storage file.");
        }

        this.keyStorageFile = keyStorageFile;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public KeyToolArgumentsBuilder setPassword(final String password)
            throws MojoExecutionException {

        if (password == null) {
            throw new MojoExecutionException("Certificate password must be specified.");
        }

        this.password = password;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public KeyToolArgumentsBuilder setAuthor(final String author)
            throws MojoExecutionException {

        if (author == null) {
            throw new MojoExecutionException("Author was not specified.");
        }

        this.author = author;
        return this;
    }

}
