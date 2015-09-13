package com.leafriend.flix;

public class Flix {

    private Options options;

    public static void main(String[] args) {

        Options options = new Options();

        new Flix(options).run();

    }

    public Flix(Options options) {
        this.options = options;
    }

    public void run() {

    }

}
