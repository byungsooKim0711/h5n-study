package org.kimbs.xunit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestSuite implements Test {

    private List<Test> tests = new ArrayList<>();

    public TestSuite(Class<? extends TestCase> testClass) {
        Arrays.stream(testClass.getMethods())
                .filter(method -> {
                    return method.getAnnotation(org.kimbs.xunit.annotation.Test.class) != null;
                })
                .forEach(method -> {
                    try {
                        this.add(testClass.getConstructor(String.class).newInstance(method.getName()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public TestSuite() {

    }

    @Override
    public void run(TestResult result) {
        tests.forEach(t -> {
            t.run(result);
        });
    }

    public void add(Test test) {
        tests.add(test);
    }
}
