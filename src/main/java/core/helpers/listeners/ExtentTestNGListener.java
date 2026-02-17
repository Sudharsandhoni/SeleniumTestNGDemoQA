package core.helpers.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import core.constants.TestGlobals;
import core.context.TestContextManager;
import core.exceptions.CustomException;
import core.helpers.reports.ExtentManager;
import core.helpers.reports.ExtentTestManager;
import core.screenshots.ScreenshotHelper;
import core.screenshots.ScreenshotType;
import core.utils.LogUtils;
import core.utils.SeleniumUtils;

public class ExtentTestNGListener implements ITestListener {



	@Override
	public void onTestStart(ITestResult result) {

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		ExtentTestManager.getTest().pass("Test passed");

	}

	@Override
	public void onTestFailure(ITestResult result) {
		TestGlobals.addFailedTests(result);
		ExtentTestManager.getTest().fail(result.getThrowable());
//		try {
//			String screenshotPath = SeleniumUtils.takeScreenshot(result.getMethod().getMethodName());
//			ExtentTestManager.getTest().addScreenCaptureFromPath(screenshotPath);
//			ExtentTestManager.unload();
//		} catch (CustomException e) {
//			e.printStackTrace();
//		}

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentTestManager.getTest().skip("Test skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentManager.getInstance().flush();
	}
}
