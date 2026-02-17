package core.screenshots;

public enum ScreenshotType {

    // Captured automatically before an action (click/type/etc.)
    BEFORE_ACTION,

    // Optional – captured after an action if you want visual confirmation
    AFTER_ACTION,

    // Used when a major page transition happens
    NAVIGATION,

    // Captured when a page declares itself “loaded”
    PAGE_LOAD,

    // Used for explicit logical checkpoints
    STEP,

    // Used when a verification fails (Assert / validation mismatch)
    ASSERTION_FAILURE,

    // Used when any unexpected exception occurs
    FAILURE,

    // Used during retry attempts to show flaky behavior
    RETRY_CONTEXT,

    // Manually triggered by framework for diagnostics
    DEBUG
}

