package tests.utils;

import org.testng.Assert;

import tests.data.demoQA.CheckBoxNode;

public final class TreeAssertions {

    public static void assertEquals(CheckBoxNode expected, CheckBoxNode actual) {
        Assert.assertEquals(actual.getName(), expected.getName(), "Name mismatch");
        Assert.assertEquals(actual.getState(), expected.getState(), "State mismatch");

        Assert.assertEquals(
                actual.getChildren().size(),
                expected.getChildren().size(),
                "Children count mismatch for " + actual.getName()
        );

        for (int i = 0; i < expected.getChildren().size(); i++) {
            assertEquals(expected.getChildren().get(i), actual.getChildren().get(i));
        }
    }
}