package com.leafriend.flix;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Flix {

    private Options options;

    private int directoryCount;

    private int fileCount;

    private List<File> files;

    public static void main(String[] args) {

        Options options = new Options();

        new Flix(options).run();

    }

    public Flix(Options options) {
        this.options = options;
        this.directoryCount = 0;
        this.fileCount = 0;
        this.files = new ArrayList<>();
    }

    public void run() {

    }

    public void traverse(File dir) throws IOException {
        for (File file : dir.listFiles()) {
            String path = file.getCanonicalPath();
            if (file.isFile()) {
                fileCount++;
                files.add(file);
                if (options.isVerbose()) {
                    String name = file.getName();
                    System.out.print(name);
                    // try { Thread.sleep(100); } catch (InterruptedException e)
                    // {
                    // // TODO Auto-generated catch block e.printStackTrace(); }
                    for (int i = 0; i < name.length(); i++)
                        System.out.print("\b");
                    for (int i = 0; i < name.length(); i++)
                        System.out.print(" ");
                    for (int i = 0; i < name.length(); i++)
                        System.out.print("\b");
                }
            } else if (file.isDirectory()) {
                directoryCount++;
                if (options.isVerbose()) {
                    System.out.print("\n");
                    System.out.print(path + File.separator);
                }
                traverse(file);
            } else {
                error("Path '" + path + "' is not a file or directory");
            }
        }
    }

    private void error(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
