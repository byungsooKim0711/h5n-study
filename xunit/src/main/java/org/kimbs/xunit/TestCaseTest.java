package org.kimbs.xunit;

import org.kimbs.xunit.annotation.Test;

public class TestCaseTest extends TestCase {

    public TestCaseTest(String name) {
        super(name);
    }

    public static TestSuite suite() {
        return new TestSuite(TestCaseTest.class);
    }

    @Test
    public void TemplateMethod() {
        WasRun wasRun = new WasRun("testMethod");
        TestResult result = new TestResult();
        wasRun.run(result);
        Assert.assertEquals("setUp testMethod tearDown", wasRun.getLog());
    }

    @Test
    public void Result() {
        WasRun wasRun = new WasRun("testMethod");
        TestResult result = new TestResult();
        wasRun.run(result);
        Assert.assertEquals("1 run, 0 failed", result.getSummary());
    }

    @Test
    public void FailedResultFormatting() {
        TestResult result = new TestResult();
        result.testStarted();
        result.testFailed();
        Assert.assertEquals("1 run, 1 failed", result.getSummary());
    }

    @Test
    public void FailedResult() {
        WasRun wasRun = new WasRun("testBrokenMethod");
        TestResult result = new TestResult();
        wasRun.run(result);
        Assert.assertEquals("1 run, 1 failed", result.getSummary());
    }

    @Test
    public void Suite() {
        TestSuite suite = new TestSuite();
        suite.add(new WasRun("testMethod"));
        suite.add(new WasRun("testBrokenMethod"));
        TestResult result = new TestResult();
        suite.run(result);
        Assert.assertEquals("2 run, 1 failed", result.getSummary());
    }
}
