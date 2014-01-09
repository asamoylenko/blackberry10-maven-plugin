package com.filmon.maven.packaging;

import org.apache.maven.plugin.MojoExecutionException;

public enum AppCategory {
    GAMES ("-cg"),
    MEDIA ("-cm");

    private final String value;

    public static AppCategory parseCategory(String arg) throws MojoExecutionException {
        if (arg == null) {
            throw new MojoExecutionException("Application category must be specified. " +
                                             "Set either 'games' or 'media' value.");
        }

        if (arg.toLowerCase().equals("games")) {
            return GAMES;
        } else if (arg.toLowerCase().equals("media")) {
            return MEDIA;
        } else {
            throw new MojoExecutionException("Illegal application category. Only 'games' " +
                                             "and 'media' values are allowed.");
        }
    }

    private  AppCategory(final String value) {
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
