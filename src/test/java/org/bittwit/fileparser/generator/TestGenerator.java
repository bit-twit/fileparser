package org.bittwit.generator;

import java.io.File;
import java.util.Random;

public class RandomIntegerFileGenerator {
    Random random = new Random();

    public static void main(String args) {
        RandomIntegerFileGenerator generator = new RandomIntegerFileGenerator();
        generator.generate();
    }

    public void generate(File file) {

    }
}
