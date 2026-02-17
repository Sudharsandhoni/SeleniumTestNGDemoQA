package core.screenshots;

import core.utils.ConfigReader;

public final class ScreenshotPolicy {

    private static final boolean BEFORE =
        Boolean.parseBoolean(ConfigReader.getPropertyValue("SCREENSHOT_BEFORE_STEP"));

    private static final boolean AFTER =
        Boolean.parseBoolean(ConfigReader.getPropertyValue("SCREENSHOT_AFTER_STEP"));

    private static final boolean ON_FAILURE =
        Boolean.parseBoolean(ConfigReader.getPropertyValue("SCREENSHOT_ON_FAILURE"));

    private ScreenshotPolicy() {}

    public static boolean captureBefore() {
        return BEFORE;
    }

    public static boolean captureAfter() {
        return AFTER;
    }

    public static boolean captureOnFailure() {
        return ON_FAILURE;
    }
}
