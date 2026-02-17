package core.helpers.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;

import com.aventstack.extentreports.ExtentReports;

import core.constants.TestGlobals;
import core.helpers.reports.ExtentManager;

public class SuiteListener implements ISuiteListener {
	private ExtentReports extent = ExtentManager.getInstance();

	@Override
	public void onStart(ISuite suite) {
		
		// Retry awareness
		if (TestGlobals.isRetryRun()) {
			extent.setSystemInfo("Retry Suite", suite.getName());
			extent.setSystemInfo("Execution Type", "Retry Run");
		} else {
			extent.setSystemInfo("Main Suite", suite.getName());
			extent.setSystemInfo("Execution Type", "Main Run");
		}
	}
}
