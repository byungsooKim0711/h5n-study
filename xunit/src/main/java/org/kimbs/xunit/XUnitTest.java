package org.kimbs.xunit;

public class XUnitTest {

    public static void main(String[] args) {
        TestSuite suite = TestCaseTest.suite();

        TestResult testResult = new TestResult();
        suite.run(testResult);

        System.out.println(testResult.getSummary());
    }
}
