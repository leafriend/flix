package com.leafriend.flix;

import java.io.File;

/**
 * Contains running options.
 */
public class Options {

    private File scanDirectory = new File(".");

    private boolean isVerbose;

    private File outputFile = new File("flix.xlsx");

    /**
     * Gets directory to scan
     *
     * @return directory to scan
     */
    public File getScanDirectory() {
        return scanDirectory;
    }

    /**
     * Gets is verbose or not
     *
     * @return <code>true</code> when it's verbose
     */
    public boolean isVerbose() {
        return isVerbose;
    }

    /**
     * Gets output file
     *
     * @return output file
     */
    public File getOutputFile() {
        return outputFile;
    }

}
