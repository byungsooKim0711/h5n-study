package org.kimbs.xunit;

public class Assert {

    public static void assertEquals(Object expected, Object actual) {
        if (expected == null && actual == null) {
            return ;
        }

        if (!expected.equals(actual)) {
            throw new AssertionError("expected <" + expected + "> but was <" + actual + ">");
        }
    }
}
