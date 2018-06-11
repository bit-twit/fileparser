package org.bittwit.fileparser;

import java.io.File;

public class App {

    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
    }

    public static String relativeToWorkingPath(final String fileName) {
        return System.getProperty("user.dir") + File.separator + fileName;
    }
}
