package core.context;

public class TestContextManager {

	private static ThreadLocal<TestContext> context = new ThreadLocal<>();

	public static void init(String testName, int retryCount) {
		TestContext ctx = new TestContext();
		ctx.setTestName(testName);
		ctx.setRetryCount(retryCount);
		ctx.resetSteps();
		context.set(ctx);
	}

	public static TestContext get() {
		return context.get();
	}

	public static void unload() {
		context.remove();
	}
}
