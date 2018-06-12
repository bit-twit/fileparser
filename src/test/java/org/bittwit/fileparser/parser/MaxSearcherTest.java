package org.bittwit.fileparser.parser;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class MaxSearcherTest {

    @Test
    public void getIntegersFromLine() {
        String test = "12312&&8(#)#)$)$;67&&6@#@#@";
        List<Integer> integers = MaxSearcher.getIntegersFromLine(test).collect(Collectors.toList());
        assertTrue(integers.size() == 4);
        assertTrue(integers.contains((12312)));
        assertTrue(integers.contains((8)));
        assertTrue(integers.contains((67)));
        assertTrue(integers.contains((6)));
    }
}