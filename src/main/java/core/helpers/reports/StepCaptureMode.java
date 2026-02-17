package core.helpers.reports;

public enum StepCaptureMode {
    DEFAULT {
        boolean before(boolean policy) { return policy; }
        boolean after(boolean policy)  { return policy; }
        boolean fail(boolean policy)   { return policy; }
    },
    FORCE_ALL {
        boolean before(boolean policy) { return true; }
        boolean after(boolean policy)  { return true; }
        boolean fail(boolean policy)   { return true; }
    },
    NO_SCREENSHOTS {
        boolean before(boolean policy) { return false; }
        boolean after(boolean policy)  { return false; }
        boolean fail(boolean policy)   { return false; }
    };

    abstract boolean before(boolean policy);
    abstract boolean after(boolean policy);
    abstract boolean fail(boolean policy);
}
