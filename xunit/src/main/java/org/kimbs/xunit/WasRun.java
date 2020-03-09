package org.kimbs.xunit;

public class WasRun extends TestCase {

    private String log;

    public WasRun(String name) {
        super(name);
    }

    @Override
    public void setUp() {
        this.log = "setUp";
    }

    @Override
    public void tearDown() {
        this.log += " tearDown";
    }

    public void testMethod() {
        this.log += " testMethod";
    }

    public void testBrokenMethod() {
        throw new AssertionError();
    }

    public String getLog() {
        return this.log;
    }
}
