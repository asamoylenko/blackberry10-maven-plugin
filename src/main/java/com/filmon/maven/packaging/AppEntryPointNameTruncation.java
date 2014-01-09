package com.filmon.maven.packaging;

import org.apache.maven.plugin.MojoExecutionException;

public enum AppEntryPointNameTruncation {
    LEFT ("-etl"),
    RIGHT ("-etr"),
    NONE ("-etn");

    private final String value;

    public static AppEntryPointNameTruncation parseAppEntryPointNameTruncation(String arg)
            throws MojoExecutionException {
        if (arg == null) {
            return NONE;
        } else if (arg.toLowerCase().equals("left")) {
            return LEFT;
        } else if (arg.toLowerCase().equals("right")) {
            return RIGHT;
        } else {
            throw new MojoExecutionException("Illegal application entry point truncation method. " +
                                             "Only values 'left', 'right' and 'none' are allowed.");
        }
    }

    private  AppEntryPointNameTruncation(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
