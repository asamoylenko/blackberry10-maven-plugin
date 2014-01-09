package com.filmon.maven.signing;

import java.io.File;

public class Certificate {

    private File file;
    private String password;
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
