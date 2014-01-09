package com.filmon.maven.signing;

import java.io.File;

public class KeyStorage {

    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(final File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Path: " + (getFile() == null ? null : getFile().getPath());
    }
}
