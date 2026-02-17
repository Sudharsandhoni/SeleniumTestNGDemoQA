package core.helpers.reports;

import java.util.function.Supplier;

import com.aventstack.extentreports.ExtentTest;

import core.constants.TestGlobals;
import core.context.TestContextManager;
import core.exceptions.CustomException;
import core.screenshots.ScreenshotHelper;
import core.screenshots.ScreenshotPolicy;
import core.screenshots.ScreenshotType;
import core.utils.SeleniumUtils;

public class ExtentTestReportLogger {
	private final SeleniumUtils selenium;

	public ExtentTestReportLogger(SeleniumUtils selenium) {
		this.selenium = selenium;
	}

	public void info(String message) {
		ExtentTestManager.getTest().info(message);
	}

	public void pass(String message) {
		ExtentTestManager.getTest().pass(message);
	}

	public void fail(String message) {
		ExtentTestManager.getTest().fail(message);
	}

	public void step(String action, StepAction operation) {
		stepInternal(action, operation, null, StepCaptureMode.DEFAULT);
	}

	public void stepWithCapture(String action, StepAction operation) {
		stepInternal(action, operation, null, StepCaptureMode.FORCE_ALL);
	}

	public void stepWithoutCapture(String action, StepAction operation) {
		stepInternal(action, operation, null, StepCaptureMode.NO_SCREENSHOTS);
	}

	public <T> T step(String action, ThrowingSupplier<T> operation) {
		return stepInternal(action, null, operation, StepCaptureMode.DEFAULT);
	}

	public <T> T stepWithCapture(String action, ThrowingSupplier<T> operation) {
		return stepInternal(action, null, operation, StepCaptureMode.FORCE_ALL);
	}

	public <T> T stepWithoutCapture(String action, ThrowingSupplier<T> operation) {
		return stepInternal(action, null, operation, StepCaptureMode.NO_SCREENSHOTS);
	}

	private <T> T stepInternal(String action, StepAction operation, ThrowingSupplier<T> supplier,
			StepCaptureMode mode) {
		int stepNo = TestContextManager.get().getCurrentStepNumber();

		ExtentTest parent = ExtentTestManager.getTest();

		ExtentTest stepNode = parent.createNode(String.format("[STEP %02d] - %s", stepNo, action));
		TestContextManager.get().setCurrentStepNode(stepNode);

		boolean before = mode.before(ScreenshotPolicy.captureBefore());
		boolean after = mode.after(ScreenshotPolicy.captureAfter());
		boolean fail = mode.fail(ScreenshotPolicy.captureOnFailure());

		try {
			// test.info("[STEP " + stepNo + "] " + action);

			if (before) {
				ScreenshotHelper.capture(selenium, ScreenshotType.BEFORE_ACTION, action);
			}

			T result = null;
			if (supplier != null) {
				result = supplier.get();
			} else if (operation != null) {
				operation.run();
			}

			if (after) {
				ScreenshotHelper.capture(selenium, ScreenshotType.AFTER_ACTION, action);
			}
			stepNode.pass("Step executed successfully");
			return result;

		} catch (Throwable t) {
			if (fail) {
				ScreenshotHelper.capture(selenium, ScreenshotType.FAILURE, action);
			}
//			stepNode.fail("❌ Step " + stepNo + " failed: " + action).fail(t);
//			throw new RuntimeException("Step failed: " + action, t);

			stepNode.fail(buildFailureMessage(stepNo, action, t));

			stepNode.info(
					"<details><summary>Show technical details</summary><pre>" + getStackTrace(t) + "</pre></details>");
			if (t instanceof AssertionError ae) {
				throw ae;
			}
			throw new RuntimeException(t);
		} finally {
			TestContextManager.get().setCurrentStepNode(null);
			TestContextManager.get().nextStep();
		}
	}

	public void takeScreenshotAndAttach(String action) {
		String screenshotPath;
		try {
			screenshotPath = selenium.takeScreenshot(action);
			ExtentTestManager.getTest().addScreenCaptureFromPath(screenshotPath);
		} catch (CustomException e) {
			ExtentTestManager.getTest().warning("Screenshot failed: " + e.getMessage());
		}

	}

	public void takeScreenshotAndAttach() {
		takeScreenshotAndAttach("screenshot");

	}

	private String buildFailureMessage(int stepNo, String action, Throwable t) {
		String reason = (t.getMessage() != null) ? cleanAssertionMessage(t.getMessage()) : "Step execution failed";

		return """
				❌ Step %02d FAILED
				Action: %s
				Reason:
				%s
				""".formatted(stepNo, action, reason);
	}

	private String cleanAssertionMessage(String message) {
		return message.replace("The following asserts failed:", "").replace("expected [true] but found [false]", "")
				.trim();
	}

	private String getStackTrace(Throwable t) {
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement e : t.getStackTrace()) {
			sb.append(e).append("\n");
		}
		return sb.toString();
	}

}
