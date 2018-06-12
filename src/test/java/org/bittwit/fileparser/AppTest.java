package org.bittwit.fileparser;

import org.bittwit.fileparser.generator.TestGenerator;
import org.junit.Test;

public class AppTest {

    @Test
    public void main() {
        new TestGenerator().generateFiles();
        App.main(new String[]{});
    }
}
