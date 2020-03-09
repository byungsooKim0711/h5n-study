package org.kimbs.xunit;

public class TestResult {

    private int runCount = 0;
    private int errorCount = 0;

    public void testStarted() {
        this.runCount++;
    }

    public void testFailed() {
        this.errorCount++;
    }

    public String getSummary() {
        return this.runCount + " run, " + errorCount + " failed";
    }
}
