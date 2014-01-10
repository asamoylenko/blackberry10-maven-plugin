package com.filmon.maven.signing;

import java.io.File;

public class Certificate {

    /**
     * Certificate (*.csk) file location.
     */
    private File file;

    /**
     * Certificate password. The same password
     * will be set to key storage.
     */
    private String password;

    /**
     * This parameter specifies the author name for the BAR files.
     */
    private String author;

    public File getFile() {
        return file;
    }

    public void setFile(final File file) {
        this.file = file;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Path: " + (getFile() == null ? null : getFile().getPath()) +
                ", password: " + getPassword() +
                ", author: " + getAuthor();
    }
}
